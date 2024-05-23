<template>
<div class="container justify-content-center">
    <div class="mainContain">
        <div class="mt-3 text-center fs-2" role="alert">
            핫플레이스
        </div>
        <hr class="center-hr">
        <div>
            <div class="row my-2">
                <div class="d-flex justify-content-between">
                    <div class="d-flex align-items-center">
                        <h2 class="text-secondary">{{ hotplace.title }}</h2>
                        <span :class="['badge', getBadgeClass(hotplace.contentTypeId), 'ms-3']">{{ hotplace.contentType }}</span>
                    </div>
                    <div class="btn-group">
                        <button class="btn btn-sm" @click.stop="toggleLike(hotplace)">
                            <i :class="[hotplace.liked ? 'fas' : 'far', 'fa-heart', 'like-icon']"></i>
                        </button>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <div class="clearfix align-content-center">
                    <img
                        class="avatar me-2 float-md-start bg-light p-2"
                        src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                    />
                    <p>
                        <span class="fw-bold">{{ hotplace.userId }}</span> <br />
                    </p>
                    </div>
                </div>
                <div class="d-flex justify-content-end mb-3">
                    <button v-if="userId == hotplace.userId" type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteHotplace">
                        삭제
                    </button>
                    <button type="button" class="btn btn-outline-primary mb-3 ms-1" @click="moveList">
                        목록으로
                    </button>
                </div>
                <div class="imageContainer mb-5">
                    <div v-for="(imgSrc, index) in imgSrcs" :key="index" class="hotplace-image mb-3">
                        <img :src="imgSrc" class="img-fluid" alt="Hotplace image">
                    </div>
                </div>
                <div class="imageContainer text-secondary mb-3">
                    {{ hotplace.title }}
                </div>
                <div class="imageContainer mb-3">
                    :&nbsp;{{ hotplace.description }}
                </div>
                <div class="imageContainer mb-3">
                    <b>주소 :&nbsp;</b>{{ hotplace.addr1 }}
                </div>
                <div v-if="hotplace.tel" class="imageContainer mb-3">
                    <b>Tel.&nbsp;</b>{{ hotplace.tel }}
                </div>
                <div class="imageContainer mt-5 mb-3">
                    <HotplaceImageMap :lat=hotplace.latitude :lng=hotplace.longitude :marker-text=hotplace.title />
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<script setup>
import HotplaceImageMap from './item/HotplaceImageMap.vue';
import { ref, computed, onMounted } from 'vue';
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from '@/stores/user-store';
import {
    getHotplaceDetail,
    listHotplaceImage,
    deleteHotplace,
    postLike,
    deleteLike
} from '@/api/hotplace';

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);
const { isLogin } = storeToRefs(userStore);

const userId = computed(() => {
  return userInfo.value && isLogin ? userInfo.value.userId : 'Guest';
});

const route = useRoute();
const router = useRouter();
const hotplace = ref({});
const imgSrcs = ref([]);

onMounted(() => {
    getHotplaceDetail(
        route.params.hotplaceId,
        userId.value,
        (response) => {
            hotplace.value = response.data;
        },
        (error) => {
            console.error('Failed to load hotplace details', error);
        }
    );
    listHotplaceImage(
        route.params.hotplaceId,
        (response) => {
            imgSrcs.value = response.data;
        },
        (error) => {
            console.error('Failed to load hotplace details', error);
        }
    );
});

function moveList() {
    router.push({ name: "hotplace" });
}

const contentTypeColors = {
    '12': 'bg-red',
    '14': 'bg-blue',
    '15': 'bg-purple',
    '25': 'bg-olive',
    '28': 'bg-orange',
    '32': 'bg-green',
    '38': 'bg-navy',
    '39': 'bg-mint'
};

const getBadgeClass = (contentTypeId) => {
    return contentTypeColors[contentTypeId] || 'bg-secondary'; // 기본 색상은 secondary
};

function onDeleteHotplace() {
    if (confirm("삭제하시겠습니까?")) {
        deleteHotplace(
            hotplace.value.hotplaceId,
            (response) => {
                if (response.status == 200) moveList();
                alert("삭제되었습니다.");
            },
            (error) => {
                alert("삭제 중 문제가 발생했습니다.");
                console.log(error);
            }
        );
    }
}

const toggleLike = (hotplace) => {
    if (hotplace.liked) {
        console.log("user :", userId.value);
        console.log("clicked :", hotplace);
        deleteLike(
            userId.value,
            hotplace.hotplaceId,
            (response) => {
                if (response.status == 200) {
                    console.log("삭제");
                }
                hotplace.liked = !hotplace.liked;
            },
            (error) => {
                alert("취소에 문제가 발생했습니다.");
                console.log(error);
            }
        );
    } else {
        console.log("user :", userId);
        console.log("clicked :", hotplace);
        postLike(
            userId.value,
            hotplace.hotplaceId,
            (response) => {
                if (response.status == 200) {
                    console.log("추가");
                }
                hotplace.liked = !hotplace.liked;
            },
            (error) => {
                alert("추가에 문제가 발생했습니다.");
                console.log(error);
            }
        );
    }
};
</script>

<style scoped>
@import "@/assets/css/hotplace.css";

.images-container {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
}

.imageContainer {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
}

.hotplace-image {
    width: 80%;
}

.img-fluid {
    width: 100%;
}

.like-icon {
    font-size: 27px;
}
</style>