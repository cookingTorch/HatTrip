<template>
  <div class="container justify-content-center">
    <div class="mainContain">
      <div class="mt-3 text-center fs-2" role="alert">
        글보기
      </div>
      <hr class="center-hr">
      <div>
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ article.boardNo }}. {{ article.title }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">{{ article.userId }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.createAt }} 조회수 : {{ article.hits }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : {{ commentCount }}</div>
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
              글목록
            </button>
            <button v-if="userId == article.userId" type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
              <router-link
                :to="{ name: 'boardView', params: { articleno: article.boardNo } }"
              >
                글수정
              </router-link>
            </button>
            <button v-if="userId == article.userId" type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle">
              글삭제
            </button>
          </div>
        </div>
      </div>
      <div>
        <!-- 댓글창 -->
        <div class="mb-2">
          <h4>댓글</h4>
          <div>
            <div v-for="comment in comments" :key="comment.id" class="card mt-2">
              <div class="card-body">
                <p class="card-text">{{ comment.content }}</p>
                <div class="d-flex justify-content-between">
                  <small>{{ comment.userId }}</small>
                  <small>{{ comment.createAt }}</small>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="card mt-3">
          <div class="card-body">
            <h5 class="card-title mb-3">댓글 작성</h5>
            <textarea v-model="newComment" @keyup.enter="submitComment" class="form-control" rows="3"></textarea>
            <button class="btn btn-primary mt-4" @click="submitComment">등록</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/board.css";
</style>

<script setup>
import { ref, computed, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import {
  detailArticle,
  deleteArticle,
  listComments,
  addComment,
  updateComment,
  deleteComment
} from "@/api/board";
import { useUserStore } from '@/stores/user-store';

const route = useRoute();
const router = useRouter();

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);
const { isLogin } = storeToRefs(userStore);

const userId = computed(() => {
  return userInfo.value && isLogin ? userInfo.value.userId : 'Guest';
});

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;

const article = ref({});

onMounted(() => {
  getArticle();
  loadComments();
  console.log("아이디", userId);
});

const getArticle = () => {
  // const { articleno } = route.params;
  console.log(articleno + "번글 얻으러 가자!!!");
  detailArticle(
    articleno,
    ({ data }) => {
      article.value = data;
      console.log("게시글", article.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

function moveList() {
  router.push({ name: "board" });
}

function moveModify() {
  router.push({ name: "boardModify", params: { articleno } });
}

function onDeleteArticle() {
  // const { articleno } = route.params;
  console.log(articleno + "번글 삭제하러 가자!!!");
  deleteArticle(
    articleno,
    (response) => {
      if (response.status == 200) moveList();
    },
    (error) => {
      console.log(error);
    }
  );
}

// 댓글창
const comments = ref([]);

const commentCount = ref(0);
const newComment = ref("");

const loadComments = () => {
  listComments(
    articleno,
    (response) => {
      comments.value = response.data;
      commentCount.value = comments.value.length;
      console.log("댓글을 불러왔습니다.");
    },
    (error) => {
      console.error("댓글 로드 중 오류가 발생했습니다.", error);
    }
  );
};

const submitComment = () => {
  const comment = {
    boardNo: articleno,
    userId: userInfo.value.userId,
    content: newComment.value,
  };

  addComment(
    comment,
    (response) => {
      console.log("댓글 등록이 완료되었습니다.");
      newComment.value = ""; // 입력 필드 초기화
      loadComments(); // 댓글 목록 다시 불러오기
    },
    (error) => {
      console.error("댓글 등록 시 문제가 발생했습니다.", error);
    }
  );
};
</script>