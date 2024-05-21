<template>
<div class="container justify-content-center">
    <div class="mainContain">
        <div class="mt-3 text-center fs-2" role="alert">
            핫플레이스
        </div>
        <hr class="center-hr">
        <div>
            <div class="row my-2">
                <div class="d-flex align-items-center">
                    <h2 class="text-secondary">{{ hotplace.title }}</h2>
                    <span :class="['badge', getBadgeClass(hotplace.contentTypeId), 'ms-3']">{{ hotplace.contentType }}</span>
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
                    <button v-if="userId == hotplace.userId" type="button" class="btn btn-outline-danger mb-3 ms-1" @click="">
                        삭제
                    </button>
                    <button type="button" class="btn btn-outline-primary mb-3 ms-1" @click="moveList">
                        목록으로
                    </button>
                </div>
                <div mb-3>
                    <!-- 이미지 -->
                </div>
                <div class="text-secondary mb-3">
                    {{ hotplace.description }}
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<style scoped>
    @import "@/assets/css/hotplace.css";
</style>
  
<script setup>
import { ref, computed, onMounted } from 'vue';
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { getHotplaceDetail } from '@/api/hotplace';
import { useUserStore } from '@/stores/user-store';

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);
const { isLogin } = storeToRefs(userStore);

const userId = computed(() => {
  return userInfo.value && isLogin ? userInfo.value.userId : 'Guest';
});

const route = useRoute();
const router = useRouter();
const hotplace = ref({});
const loading = ref(true);

onMounted(() => {
    getHotplaceDetail(
        route.params.hotplaceId,
        (response) => {
            hotplace.value = response.data;
            loading.value = false;
        },
        (error) => {
            console.error('Failed to load hotplace details', error);
            loading.value = false;
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
</script>