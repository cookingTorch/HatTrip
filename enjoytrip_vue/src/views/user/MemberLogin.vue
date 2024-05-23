<template>
  <div class="login-container">
      <div class="login-card">
          <h2>Login</h2>
          <form @submit.prevent="login">
              <div class="mb-3">
                  <label for="text" class="form-label">아이디</label>
                  <input type="text" class="form-control" id="userId" v-model="userId" required>
              </div>
              <div class="mb-3">
                  <label for="password" class="form-label">비밀번호</label>
                  <input type="password" class="form-control" id="userPwd" v-model="userPwd" @keyup.enter="login" required>
              </div>
              <button class="btn btn-outline-primary">
                  <router-link :to="{ name: 'regist' }" class="nav-link">
                      회원가입
                  </router-link>
              </button>
              <button type="submit" class="btn btn-primary ms-2">로그인</button>
          </form>
      </div>
  </div>

</template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { storeToRefs } from "pinia"
  import { useRouter } from "vue-router"
  import { useUserStore } from '@/stores/user-store.js'
  
  const userId = ref('');
  const userPwd = ref('');
  const userStore = useUserStore();
  const router = useRouter();

  const { isLogin } = storeToRefs(userStore);
  const { userLogin, getUserInfo } = userStore;

  onMounted(() => {
  });

  const login = async () => {
    await userLogin({
      userId: userId.value,
      userPwd: userPwd.value
    });
    let token = sessionStorage.getItem("accessToken");
    if (isLogin.value) {
      getUserInfo(token);
      router.replace("/");
    }
  }
  
  </script>
  
  <style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh; 
}

.login-card {
  width: 400px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.form-label {
  margin-bottom: 0.5rem;
}

.btn {
  margin-top: 1rem;
}
</style>