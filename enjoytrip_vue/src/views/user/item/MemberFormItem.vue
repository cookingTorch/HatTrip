<template>
    <form @submit.prevent="onSubmit">
          <div class="mb-3">
            <label for="userId" class="form-label">아이디</label>
            <input type="text" class="form-control" id="userId" :disabled="isUseId" v-model="UserData.userId" required>
          </div>
          <div class="mb-3">
            <label for="userName" class="form-label">이름</label>
            <input type="text" class="form-control" id="userName" v-model="UserData.userName" required>
          </div>
          <div class="mb-3">
            <label for="userPwd" class="form-label">비밀번호</label>
            <input type="password" class="form-control" id="userPwd" v-model="UserData.userPwd" required>
          </div>
          <div class="mb-3">
            <label for="email" class="form-label">이메일</label>
            <input type="email" class="form-control" id="email" v-model="UserData.email" required>
          </div>
          <button type="submit" class="btn btn-primary" v-if="type === 'regist'">회원가입</button>
          <button type="submit" class="btn btn-primary" v-else>회원정보수정</button>
        </form>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue"
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router"
import { useUserStore } from "@/stores/user-store.js"
import { joinMember, modifyMember } from '@/api/member.js';

  
const props = defineProps({ type: String })

const userStore = useUserStore();

const isUseId = ref(false);
const { userInfo } = storeToRefs(userStore);
  
const UserData = reactive({
  userId: "",
  userName: "",
  userPwd: "",
  email: "",
});
const router = useRouter();

onMounted(() => {
  
  if(props.type==='modify'){
    isUseId.value = true;
    UserData.userId = userInfo.value.userId;
    UserData.userName = userInfo.value.userName;
    UserData.email = userInfo.value.email;
  } else {
    isUseId.value = false;
  }
});

function onSubmit() {

  props.type === "regist" ? register() : modify();
}
  
const register = () => {
  joinMember(
    UserData,
    () => {
      router.push({ name: 'home' });
      alert("가입이 완료되었습니다. 로그인 해 주세요")
    },
    () => {
      alert("Something went wrong. Please check your input and try again. \nIf the problem persists, contact support.")
    }
  );
};

const modify = () => {
  modifyMember(
    UserData,
    () => {
      router.push({ name: 'home' });
      alert("수정이 완료되었습니다.")
    },
    () => {
      alert("Something went wrong. Please check your input and try again. \nIf the problem persists, contact support.")
    }
  );
};
</script>
  
  <style>
  .register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  }
  
  .register-card {
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