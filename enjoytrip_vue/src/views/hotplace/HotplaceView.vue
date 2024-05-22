<template>
    <div class="container justify-content-center">
        <div class="mainContain">
            <div class="mt-3 text-center fs-2" role="alert">
                핫플레이스
            </div>
            <hr class="center-hr">
            <div class="search">
                <div class="search-left">
                <button type="button" class="btn btn-outline-primary" @click="pushToRegister">
                    새 핫플레이스 등록
                </button>
                </div>
                <div class="search-right">
                    <button type="button" :class="['btn', param.likes ? 'btn-danger' : 'btn-outline-danger', 'me-2']" @click="toggleShowLikes">
                        찜목록
                    </button>
                    <div class="input-group">
                        <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            {{ selectedOption }}
                        </button>
                        <ul class="dropdown-menu">
                            <li v-for="option in selectOption" :key="option.value">
                            <a class="dropdown-item" href="#" @click="changeSearchType(option)">{{ option.text }}</a>
                            </li>
                        </ul>
                        <input type="text" class="form-control" v-model="param.keyword" placeholder="검색어..." @keyup.enter="searchHotPlaceList(param.keyword)">
                        <button type="button" class="btn btn-outline-secondary" id="basic-addon2" @click="searchHotPlaceList(param.keyword)">
                            검색
                        </button>
                    </div>
                </div>
            </div>
            <div>
                <div class="mt-5 mb-4">
                    <div class="row">
                        <div class="col-md-4" v-for="hotplace in hotplaces" :key="hotplace.hotplaceId">
                            <div class="card mb-4 box-shadow" @click="moveToDetail(hotplace.hotplaceId)">
                                <img class="card-img-top" :src="thumbnailUrls[hotplace.hotplaceId] || 'default-thumbnail.jpg'" alt="Thumbnail">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center mb-4">
                                        <h5 class="card-title mb-0">{{ hotplace.title }}</h5>
                                        <span :class="['badge', getBadgeClass(hotplace.contentTypeId)]">{{ hotplace.contentType }}</span>
                                    </div>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <small class="text-muted">{{ hotplace.userId }}</small>
                                        <div class="btn-group">
                                            <button class="btn btn-sm" @click.stop="toggleLike(hotplace)">
                                                <i :class="[hotplace.liked ? 'fas' : 'far', 'fa-heart', 'like-icon']"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="btn-box">
                    <div class="btn-group" role="group" aria-label="Basic outlined example" id="travelInfoListPage">
                        <button type="button" class="btn btn-outline-primary" @click="changePage(currentPage - 1)" :disabled="currentPage <= 1">이전</button>
                        <button
                            type="button"
                            v-for="n in totalPages"
                            :key="n"
                            :class="['btn', (currentPage === n ? 'btn-primary' : 'btn-outline-primary')]"
                            style="margin-top: 0;"
                            @click="changePage(n)"
                        >
                            {{ n }}
                        </button>
                        <button type="button" class="btn btn-outline-primary" @click="changePage(currentPage + 1)" :disabled="currentPage >= totalPages">다음</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watchEffect } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useUserStore } from '@/stores/user-store';
import {
    listHotplaces,
    searchHotplaces,
    getThumbnail,
    postLike,
    deleteLike
} from "@/api/hotplace";

const router = useRouter();
const userStore = useUserStore();
const { isLogin } = storeToRefs(userStore);
const { userInfo } = storeToRefs(userStore);

const userId = computed(() => {
  return userInfo.value && isLogin.value ? userInfo.value.userId : 'Guest';
});

const selectOption = ref([
    { text: "검색조건", value: "" },
    { text: "핫플 이름", value: "title" },
    { text: "관광지 유형", value: "content_type" },
    { text: "작성자", value: "user_id" }
]);

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

const hotplaces = ref([]);
const currentPage = ref(1);
const totalPages = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
    loginUser: userId.value,
    likes: false,
    pageNo: 1,
    spp: VITE_ARTICLE_LIST_SIZE,
    searchType: "",
    keyword: "",
});

watchEffect(() => {
    param.value.loginUser = userId.value;
});

const thumbnailUrls = ref({});

const selectedOption = ref("검색조건");

onMounted(() => {
    getHotplaceList();
});

const changeSearchType = (val) => {
    console.log("HotplaceList에서 선택한 조건 : " + val.text);
    selectedOption.value = val.text;
    param.value.searchType = val.value;
};

const getHotplaceList = () => {
    console.log("get Article List", param.value);
    listHotplaces(
        param.value,
        ({ data }) => {
            hotplaces.value = data.content;
            currentPage.value = data.pageNo;
            totalPages.value = data.totalPages;
            console.log(data);
        },
        (error) => {
            console.log(error);
        }
    );
};

const changePage = (val) => {
    console.log(val + "번 페이지로 이동 준비 끝!!!");
    param.value.pageNo = val;
    listHotplaces(
        param.value,
        ({ data }) => {
            hotplaces.value = data.content;
            currentPage.value = data.pageNo;
            totalPages.value = data.totalPages;
            console.log(data);
        },
        (error) => {
            console.log(error);
        }
    );
};

const pushToRegister = () => {
    router.push({ name: "hotplaceRegister" });
};

const searchHotPlaceList = (keyword) => {
    console.log("get Hotplace List", keyword);
    param.value.keyword = keyword;
    if (selectedOption.value === "검색조건") {
        alert("검색 조건을 선택해주세요.");
        return; // 함수 실행을 여기서 중단
    }
    param.value.pageNo = 1;
    const queryParam = {
        loginUser: param.value.loginUser,
        likes: param.value.likes,
        pageNo: param.value.pageNo,
        searchType: encodeURIComponent(param.value.searchType),
        keyword: encodeURIComponent(param.value.keyword),
    };
    searchHotplaces(
        queryParam,
        ({ data }) => {
            hotplaces.value = data.content;
            currentPage.value = data.pageNo;
            totalPages.value = data.totalPages;
            console.log(data);
            isSearched.value = true;
            lastSearch.value.searchType = queryParam.searchType;
            lastSearch.value.keyword = queryParam.keyword;
        },
        (error) => {
            console.log(error);
        }
    );
};

watchEffect(() => {
    hotplaces.value.forEach(hotplace => {
        getThumbnail(
            hotplace.hotplaceId,
            ({ data }) => {
                thumbnailUrls.value[hotplace.hotplaceId] = data;
                console.log("Img Url :", thumbnailUrls.value[hotplace.hotplaceId]);
            }, 
            (error) => {
                console.error('Thumbnail load error:', error);
                thumbnailUrls.value[hotplace.hotplaceId] = '';
            }
        );
    });
});

function moveToDetail(hotplaceId) {
    router.push({ name: 'hotplaceDetail', params: { hotplaceId: hotplaceId }});
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
            },
            (error) => {
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
            },
            (error) => {
                console.log(error);
            }
        );
    }
    hotplace.liked = !hotplace.liked;  // 직접적으로 객체의 liked 상태를 토글
};

const isSearched = ref(false);
const lastSearch = ref({
    loginUser: userId.value,
    likes: false,
    pageNo: 1,
    searchType: '',
    keyword: ''
});

watchEffect(() => {
    lastSearch.value.loginUser = param.value.loginUser;
});

watchEffect(() => {
    lastSearch.value.likes = param.value.likes;
});

const toggleShowLikes = () => {
    if (param.value.likes) {
        lastSearch.value.likes = false;
        param.value.likes = false;
    } else {
        lastSearch.value.likes = true;
        param.value.likes = true;
    }
    if (isSearched.value) {
        searchHotplaces(
            lastSearch.value,
            ({ data }) => {
                hotplaces.value = data.content;
                currentPage.value = data.pageNo;
                totalPages.value = data.totalPages;
                console.log(data);
            },
            (error) => {
                console.log(error);
            }
        );
    } else {
        listHotplaces(
            lastSearch.value,
            ({ data }) => {
                hotplaces.value = data.content;
                currentPage.value = data.pageNo;
                totalPages.value = data.totalPages;
                console.log(data);
            },
            (error) => {
                console.log(error);
            }
        );
    }
};
</script>

<style scoped>
@import "@/assets/css/hotplace.css";

.input-group {
    display: flex;
    flex-wrap: nowrap;
}

.card-img-top {
    width: 100%; /* 이미지가 카드의 너비에 맞게 조정됩니다. */
    height: 250px; /* 이미지 높이를 200px로 고정합니다. */
    object-fit: cover; /* 이미지가 비율을 유지하면서 지정된 높이와 너비에 맞게 조정됩니다. */
}

.card {
    transition: all 0.3s ease; /* 애니메이션 효과를 부드럽게 만듭니다 */
    box-shadow: 0 4px 6px rgba(0,0,0,0.1); /* 그림자 효과를 추가합니다 */
    cursor: pointer; /* 마우스 포인터를 손 모양으로 변경합니다 */
}

.card:hover {
    transform: scale(1.05); /* 카드를 5% 크게 만듭니다 */
    box-shadow: 0 8px 16px rgba(0,0,0,0.2); /* 호버시 그림자를 더 진하게 합니다 */
}

.like-icon {
    font-size: 20px;
}
</style>