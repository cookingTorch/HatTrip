<template>
    <div class="mt-3 text-center fs-2" role="alert">
        여행 계획
    </div>
    <hr class="center-hr">
    <div>
        <MapContainer type="plan"/>
    </div>
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