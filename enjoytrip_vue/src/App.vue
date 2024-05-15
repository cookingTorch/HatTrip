<script setup>
import { RouterLink, RouterView } from 'vue-router';
import HeaderNav from "@/components/HeaderNav.vue";
import FooterNav from "@/components/FooterNav.vue";

</script>

<template>
  <div> 
    <HeaderNav></HeaderNav>
    <router-view></router-view>
    <FooterNav></FooterNav>
  </div>
</template>

<style>

</style>