<template>
    <div class="travel-planner row">
      <div class="col-md-6">
        <MapContainer type="plan"/>
      </div>
      <div class="col-md-6">
        <h2>여행 계획</h2>
        <ul>
          <li v-for="(landmark, index) in travelPlan" :key="index">{{ landmark }}</li>
        </ul>
        <button @click="submitTravelPlan">여행 계획 제출</button>
      </div>
    </div>
</template>

<script setup>
import MapContainer from '@/components/common/MapContainer.vue'
</script>

<style scoped>

.travel-planner {
  display: flex;
  height: 100vh;
}

.col-md-6 {
  flex: 1;
  padding: 20px;
  box-sizing: border-box;
}
</style>