<script setup>
import { computed, onMounted } from 'vue';
import { useRoute, useRouter } from "vue-router"
import { storeToRefs } from "pinia";
import { useUserStore } from '@/stores/user-store';

const userStore = useUserStore();
const router = useRouter();

// // 반응형을 유지하면서 스토어에서 속성을 추출하려면, storeToRefs()를 사용
// // https://pinia.vuejs.kr/core-concepts/
// const { menuList } = storeToRefs(menuStore);
// const { changeMenuState } = menuStore;

const { userInfo } = storeToRefs(userStore);
const { isLogin } = storeToRefs(userStore);
const { userLogout } = userStore;

onMounted(() => {
  if ((!isLogin) && sessionStorage.getItem('accessToken')) {
    userStore.checkInitialLoginState();
  }
});

const userName = computed(() => {
  return userInfo.value && isLogin ? userInfo.value.userName : '게스트';
});

const logout = () => {
  userLogout();
  router.push("/login");
};
</script>

<template>
<div id="mainNav">
    <!-- 상단 navbar start -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top shadow">
        <div class="container">
          <router-link :to="{ name: 'home' }" class="navbar-brand text-primary fw-bold">
            <img src="../assets/img/ssafy_logo.png" alt="" width="60" />
            Enjoy Trip
          </router-link>
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-lg-0">
              <li class="nav-item">
                <router-link :to="{ name: 'attractionInfo' }" class="nav-link">
                    지역별여행지
                </router-link>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  여행계획
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <li>
                    <router-link :to="{ name: 'planCreate' }" class="dropdown-item">
                      여행 계획 작성
                    </router-link>
                  </li>
                  <li>
                    <router-link :to="{ name: 'planView' }" class="dropdown-item">
                      나의 여행 계획
                    </router-link>
                  </li>
                </ul>
              </li>
              <li class="nav-item">
                <router-link :to="{ name: 'hotplace' }" class="nav-link">
                  핫플레이스
                </router-link>
              </li>
              <li class="nav-item">
                <router-link :to="{ name: 'board' }" class="nav-link">
                  Q&A 게시판
                </router-link>
              </li>
            </ul>
            <!-- 로그인 전 -->
            <div v-if="!isLogin" class="before-login">
              <ul class="navbar-nav mb-2 me-2 mb-lg-0">
                <li class="nav-item">
                  <router-link :to="{ name: 'regist' }" class="nav-link">
                    회원가입
                  </router-link>
                </li>
                <li class="nav-item">
                  <router-link :to="{ name: 'login' }" class="nav-link">
                    로그인
                  </router-link>
                </li>
              </ul>
            </div>
            <!-- 로그인 후 -->
            <div v-else class="after-login">
              <ul class="navbar-nav mb-2 me-2 mb-lg-0">
                <li class="nav-item">
                  <a class="nav-link">{{ userName }} 님 , </a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" aria-current="page" href="#" @click="logout">로그아웃</a>
                </li>
                <li class="nav-item">
                  <router-link :to="{ name: 'modify' }" class="nav-link">
                    마이페이지
                  </router-link>
                </li>
              </ul>
            </div>
          </div>
        </div>
    </nav>
    <!-- 상단 navbar end -->
</div>
<div style="height: 70px"></div>
</template>

<style scoped src="@/assets/css/header.css">
</style>