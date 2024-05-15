<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="container justify-content-center">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <div class="mt-3 text-center fs-2" role="alert">
          글수정
        </div>
        <hr class="center-hr">
      </div>
      <div class="col-lg-10 text-start">
        <BoardFormItem type="modify" />
      </div>
    </div>
  </div>
</template>

<style scoped src="@/assets/css/board.css">
</style>