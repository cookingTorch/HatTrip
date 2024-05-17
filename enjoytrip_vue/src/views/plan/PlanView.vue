<template>
    <div class="travel-planner">
      <div class="left-panel">
        <!-- 여기에 지도 script가 들어갑니다 -->
      </div>
      <div class="right-panel">
        <h2>여행 계획</h2>
        <ul>
          <li v-for="(landmark, index) in travelPlan" :key="index">{{ landmark }}</li>
        </ul>
        <button @click="submitTravelPlan">여행 계획 제출</button>
      </div>
    </div>
  </template>

<script setup>

</script>

<style>

</style>