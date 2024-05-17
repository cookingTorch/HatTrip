<script setup>
import { ref, computed, watch } from "vue"
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router"
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board"
import { useUserStore } from '@/stores/user-store';

const router = useRouter()
const route = useRoute()

const props = defineProps({ type: String })

const isUseId = ref(false)

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);
const { isLogin } = storeToRefs(userStore);

const article = ref({
  boardNo: 0,
  title: "",
  content: "",
  userId: "",
  hits: 0,
  createAt: "",
})

if (props.type === "modify") {
  let { articleno } = route.params
  console.log(articleno + "번글 얻어와서 수정할거야")
  getModifyArticle(
    articleno,
    ({ data }) => {
      article.value = data
      isUseId.value = true
    },
    (error) => {
      console.log(error)
    }
  )
  isUseId.value = true
} else {
  article.value.userId = computed(() => {
    return userInfo.value && isLogin ? userInfo.value.userId : 'Guest';
  });
  isUseId.value = true;
}

function onSubmit() {
  // event.preventDefault();
  if (!article.value.title.trim()) {
    alert('제목을 입력해 주세요.');
    return; // 함수 실행을 여기서 중단
  }
  if (!article.value.content.trim()) {
    alert('내용을 입력해 주세요.');
    return; // 함수 실행을 여기서 중단
  }
  props.type === "regist" ? writeArticle() : updateArticle()
}

function writeArticle() {
  console.log("글등록하자!!", article.value)
  registArticle(
    article.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다."
      if (response.status == 200) msg = "글등록이 완료되었습니다."
      alert(msg)
      moveList()
    },
    (error) => console.log(error)
  )
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정하자!!", article.value)
  modifyArticle(
    article.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다."
      if (response.status == 200) msg = "글정보 수정이 완료되었습니다."
      alert(msg)
      moveList()
      // router.push({ name: "article-view" });
      // router.push(`/board/view/${article.value.articleNo}`);
    },
    (error) => console.log(error)
  )
}

function moveList() {
  router.replace({ name: "board" })
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.userId"
        :disabled="isUseId"
        placeholder="작성자ID..."
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.title" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="article.content" rows="10"></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
