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
              <button class="btn btn-primary">
                  <router-link :to="{ name: 'regist' }" class="nav-link">
                      회원가입
                  </router-link>
              </button>
              <button type="submit" class="btn btn-primary">로그인</button>
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

  const { isLogin, isLoginError } = storeToRefs(userStore);
  const { userLogin, getUserInfo } = userStore;
  // const { changeMenuState } = useMenuStore();

  onMounted(() => {
    userStore.userLogout();
  });

  const login = async () => {
    await userLogin({
      userId: userId.value,
      userPwd: userPwd.value
    });
    let token = sessionStorage.getItem("accessToken");
    if (isLogin.value) {
      getUserInfo(token);
      // changeMenuState();
      router.replace("/");
    }
  }
  
  // const login = () => {
  //   // 여기에서 로그인 처리를 할 수 있습니다.
  //   // 로그인 처리 후에는 다음 페이지로 리디렉션하거나 필요한 작업을 수행합니다.
  //   loginMember(
  //       userId.value,
  //       userPwd.value,
  //       ({ data }) => {
  //           userStore.userid = data.userId;
  //           userStore.username = data.userName;
  //           userStore.useremail = data.email;
  //           if(userStore.isLoggedIn){
  //               router.push("/");
  //           }
  //       },
  //       (error) => {
  //       console.log(error)
  //       }
  //   )
  // };
  </script>
  
  <style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* 화면 세로 중앙 정렬을 위해 전체 높이를 활용 */
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

.btn-primary {
  margin-top: 1rem;
}
</style>