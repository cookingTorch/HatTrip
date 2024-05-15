import axios from "axios";

// axios 객체 생성
export default axios.create({
  // TODO: 요청 기본 URL 설정하기
  baseURL: import.meta.env.VITE_APP_API_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});
