<template>
    <div class="container justify-content-center">
      <div class="mainContain">
        <div class="mt-3 text-center fs-2" role="alert">
          나의 여행 계획
        </div>
        <hr class="center-hr">
        <div class="search">
          <div class="search-left">
            <button type="button" class="btn btn-outline-primary" @click="movePlanCreate()">
              여행계획작성
            </button>
          </div>
        </div>
        <div>
          <div>
            <div v-if="plans && plans.length > 0">
              <table class="table table-striped table-hover">
                <thead>
                  <tr>
                    <th scope="col">글번호</th>
                    <th scope="col">제목</th>
                  </tr>
                </thead>
                <tbody id="travelInfoTable">
                  <tr v-for="plan in plans" :key="plan.planNo">
                    <td><router-link
                      :to="{ name: 'planDetail', params: { planNo: plan.planNo } }"
                      class="article-title link-dark"
                      >
                      {{ plan.planNo }}
                    </router-link></td>
                    <td>
                      <router-link
                      :to="{ name: 'planDetail', params: { planNo: plan.planNo } }"
                      class="article-title link-dark"
                      >
                      {{ plan.title }}
                    </router-link>
                  </td>
                </tr>
              </tbody>
            </table>
            </div>
            <div v-else>
              <h1 class="text-secondary" style="text-align: center;">아직 작성한 여행계획이 없습니다.</h1>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { getPlanUser } from '@/api/plan.js';
import { storeToRefs } from "pinia";
import { useUserStore } from '@/stores/user-store';

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);

const userId = userInfo.value.userId;
    
const plans = ref([]);

onMounted(() => {
    getPlanList();
});
  
const getPlanList = () => {
    console.log("getPlanList", userId);

    getPlanUser(
      userId,
      ({ data }) => {
        plans.value = data;
        console.log(data);
      },
      (error) => {
        
      }
    );
};

const router = useRouter();

const movePlanCreate = () => {
  router.push({ name : 'planCreate' });
}
</script>
  
<style scoped src="@/assets/css/board.css">
</style>
  