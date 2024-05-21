<template>
    <div class="mt-3 text-center fs-2" role="alert">
        전국 관광지 정보
    </div>
    <hr class="center-hr">
    <MapContainer type="attraction"/>
</template>
  
<script setup>
import MapContainer from '@/components/common/MapContainer.vue'
</script>
  
<style scoped>
.center-hr {
    display: block;
    margin: 20px auto;
    width: 170px;
    border: 0;
    border-top: 5px solid rgb(77, 77, 119);
    margin-bottom: 30px;
}
</style>