<template>
  <div class="container justify-content-center">
    <div class="mainContain">
      <div class="mt-3 text-center fs-2" role="alert">
        Q&A
      </div>
      <hr class="center-hr">
      <div class="search">
        <div class="search-left">
          <button type="button" class="btn btn-outline-primary" @click="moveWrite">
            글쓰기
          </button>
        </div>
        <div class="search-right">
          <div class="input-group">
              <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                {{ selectedOption }}
              </button>
              <ul class="dropdown-menu">
                <li v-for="option in selectOption" :key="option.value">
                  <a class="dropdown-item" href="#" @click="changeSearchType(option)">{{ option.text }}</a>
                </li>
              </ul>
            <input type="text" class="form-control" v-model="param.keyword" placeholder="검색어..." @keyup.enter="searchArticles(param.keyword)">
            <button type="button" class="btn btn-outline-secondary" id="basic-addon2" @click="searchArticles(param.keyword)">
              검색
            </button>
          </div>
        </div>
      </div>
      <div>
        <div>
          <table class="table table-striped table-hover">
            <thead>
              <tr>
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">조회수</th>
                <th scope="col">작성일</th>
              </tr>
            </thead>
            <tbody id="travelInfoTable">
              <tr v-for="article in articles" :key="article.boardNo">
                <td>{{ article.boardNo }}</td>
                <td>
                  <router-link
                    :to="{ name: 'boardView', params: { articleno: article.boardNo } }"
                    class="article-title link-dark"
                  >
                    {{ article.title }}
                  </router-link>
                </td>
                <td>{{ article.userId }}</td>
                <td>{{ article.hits }}</td>
                <td>{{ article.createAt }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="btn-box">
          <div class="btn-group" role="group" aria-label="Basic outlined example" id="travelInfoListPage">
            <button type="button" class="btn btn-outline-primary" @click="changePage(currentPage - 1)">이전</button>
            <button
              type="button"
              v-for="n in totalPages"
              :key="n"
              :class="['btn', (currentPage === n ? 'btn-primary' : 'btn-outline-primary')]"
              style="margin-top: 0;"
              @click="changePage(n)"
            >
              {{ n }}
            </button>
            <button type="button" class="btn btn-outline-primary" @click="changePage(currentPage + 1)">다음</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from '@/api/http';
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle, searchArticle } from '@/api/board.js';

const router = useRouter();

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "작성자", value: "user_id" },
  { text: "제목", value: "title" },
  { text: "내용", value: "content" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPages = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pageNo: 1,
  spp: VITE_ARTICLE_LIST_SIZE,
  searchType: "",
  keyword: "",
});

const selectedOption = ref("검색조건");

onMounted(() => {
  getArticleList();
});

const changeSearchType = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val.text);
  selectedOption.value = val.text;
  param.value.searchType = val.value;
};

const getArticleList = () => {
  console.log("get Article List", param.value);
  listArticle(
    param.value,
    ({ data }) => {
      articles.value = data.content;
      currentPage.value = data.pageNo;
      totalPages.value = data.totalPages;
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};

const changePage = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  param.value.pageNo = val;
  listArticle(
    param.value,
    ({ data }) => {
      articles.value = data.content;
      currentPage.value = data.pageNo;
      totalPages.value = data.totalPages;
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};

const moveWrite = () => {
  router.push({ name: "boardWrite" });
};

const searchArticles = (keyword) => {
  console.log("get Article List", keyword);
  param.value.keyword = keyword;
  if (selectedOption.value === "검색조건") {
    alert("검색 조건을 선택해주세요.");
    return; // 함수 실행을 여기서 중단
  }
  const queryParam = {
    pageNo: param.value.pageNo,
    searchType: encodeURIComponent(param.value.searchType),
    keyword: encodeURIComponent(param.value.keyword),
  };
  searchArticle(
    queryParam,
    ({ data }) => {
      articles.value = data.content;
      currentPage.value = data.pageNo;
      totalPages.value = data.totalPages;
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};
// export default {
//   data() {
//     return {
//       articles: [],
//       pageNum: 1,
//       pageSize: 10,
//       searchKeyword: '',
//     };
//   },
//   computed: {
//     displayedArticles() {
//       const start = (this.pageNum - 1) * this.pageSize;
//       const end = start + this.pageSize;
//       return this.articles.slice(start, end);
//     },
//     totalPages() {
//       return Math.ceil(this.articles.length / this.pageSize);
//     }
//   },
//   methods: {
//     fetchArticles() {
//       listArticle(param,
//         ({ data }) => {
//           articles.value = data.articles;
//         },
//         (error) => {
//           console.log(error);
//         });
//     // axios.get(`/board/list?page=${this.pageNum}&size=${this.pageSize}`)
//     //   .then(response => {
//     //     console.log("Fetched data:", response.data);
//     //     this.articles = response.data;
//     //     // 데이터를 받아온 후에 총 페이지 수를 업데이트
//     //     this.updateTotalPages(this.articles.length);
//     //   })
//     //   .catch(error => console.error('Error fetching articles:', error));
//   },
//   updateTotalPages(length) {
//     this.totalPages = Math.ceil(length / this.pageSize);
//   },
//   searchListSet() {
//     axios.get(`/board/search?keyword=${this.searchKeyword}&page=1&size=${this.pageSize}`)
//       .then(response => {
//         this.articles = response.data;
//         this.pageNum = 1; // 검색 후 페이지를 처음으로 리셋
//         this.updateTotalPages(this.articles.length);
//       })
//       .catch(error => console.error('Error searching articles:', error));
//   },
//     changePage(newPage) {
//       if (newPage >= 1 && newPage <= this.totalPages) {
//         this.pageNum = newPage;
//         this.fetchArticles();
//       }
//     },
//     setVariable(boardNo) {
//       // 필요한 경우 사용할 로직
//       console.log('Article number set:', boardNo);
//     }
//   },
//   mounted() {
//     this.fetchArticles();
//   }
// }
</script>

<style scoped src="@/assets/css/board.css">
</style>
