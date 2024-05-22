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
import HotplaceRegister from '@/views/hotplace/HotplaceRegister.vue';
import HotplaceView from '@/views/hotplace/HotplaceView.vue'
import PlanCreate from '@/views/plan/PlanCreate.vue'
import PlanView from '@/views/plan/PlanView.vue'
import PlanDetail from '@/views/plan/PlanDetail.vue'
import HotplaceDetail from '@/views/hotplace/HotplaceDetail.vue';

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
    },
    {
      path: '/hotplace',
      name: 'hotplace',
      component: HotplaceView
    },
    {
      path: '/hotplace/regist',
      name: 'hotplaceRegister',
      component: HotplaceRegister
    },
    {
      path: '/plan/addPlan',
      name: 'planCreate',
      component: PlanCreate
    },
    {
      path: '/plan/planView',
      name: 'planView',
      component: PlanView,
    },
    {
      path: '/plan/detail/:planNo',
      name: 'planDetail',
      component: PlanDetail,
    },
    {
      path: '/hotplace/datail/:hotplaceId',
      name: 'hotplaceDetail',
      component: HotplaceDetail
    },
    
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
