import { createRouter, createWebHistory } from 'vue-router'
import { storeToRefs } from "pinia";
import { useUserStore } from '@/stores/user-store'
import HomeView from '../views/HomeView.vue'
import BoardListView from '../views/board/BoardListView.vue'
import BoardWriteView from '../views/board/BoardWriteView.vue'
import BoardDetail from '../views/board/BoardDetail.vue'
import BoardModify from '../views/board/BoardModify.vue'
import MemberLogin from '../views/user/MemberLogin.vue'
import MemberRegist from '../views/user/MemberRegist.vue'
import MemberModify from '../views/user/MemberModify.vue'
import AttractionInfo from '@/views/attraction/AttractionInfo.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/board/list',
      name: 'board',
      component: BoardListView
    },
    {
      path: '/board/write',
      name: 'boardWrite',
      component: BoardWriteView
    },
    {
      path: '/board/view/:articleno',
      name: 'boardView',
      component: BoardDetail
    },
    {
      path: '/board/modify/:articleno',
      name: 'boardModify',
      component: BoardModify
    },
    {
      path: '/login',
      name: 'login',
      component: MemberLogin
    },
    {
      path: '/regist',
      name: 'regist',
      component: MemberRegist
    },
    {
      path: '/mypage',
      name: 'modify',
      component: MemberModify
    },
    {
      path: '/info',
      name: 'attractionInfo',
      component: AttractionInfo
    }
  ]
});

router.beforeEach(async (to, from, next) => {
  if (to.fullPath == "/login" || to.fullPath == "/" || to.fullPath == "/regist") {
    next();
  } else {
    const userStore = useUserStore();
    const { isLogin } = storeToRefs(userStore);

    let token = sessionStorage.getItem("accessToken");

    if (token) {
      await userStore.checkInitialLoginState();
      if (!isLogin) {
        next({ name: "login" });
      } else {
        next();
      }
    } else {
      // 세션 스토리지에서 토큰 확인 실패 시 로그인 페이지로 리다이렉트
      next({ name: 'login' });
    }
  }
});

export default router;

// const onlyAuthUser = async (to, from, next) => {
//   const userStore = useUserStore();
//   const { userInfo, isLogin } = storeToRefs(userStore);
//   const { getUserInfo } = userStore;
//
//   let token = sessionStorage.getItem("accessToken");
//
//   if (token) {
//     await userStore.checkInitialLoginState();
//     if (!isLogin) {
//       next({ name: "login" });
//     } else {
//       next();
//     }
//   } else {
//     // 세션 스토리지에서 토큰 확인 실패 시 로그인 페이지로 리다이렉트
//     next({ name: 'login' });
//   }
// };

// router.beforeEach((to, from, next) => { // 페이지를 이동하기 전에 호출되는 함수
//   const userStore = useUserStore();
//   if (to.fullPath !== "/login" && to.fullPath !== "/" && to.fullPath !== "/regist" && !userStore.isLoggedIn) {
//     // /login으로 가고 있지 않고 로그인되어 있지 않으면 /login으로 redirect
//     // 로그인하지 않은 상태에서 /를 요청하는 경우 (프로젝트가 처음 실행될 때)
//     next("/login");
//   } else if (to.fullPath == "/login" && userStore.isLoggedIn) {
//     // /login으로 가고 있고 로그인되어 있으면 /으로 redirect
//     // 로그인한 상태에서 /login을 요청하는 경우
//     next("/");
//   } else {
//     next();
//   }
// });
