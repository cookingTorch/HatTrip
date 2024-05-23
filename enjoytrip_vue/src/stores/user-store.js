import { ref } from "vue"
import { useRouter } from "vue-router"
import { defineStore } from "pinia"
import { jwtDecode } from "jwt-decode"

import router from "@/router/index.js"
import { httpStatusCode } from "@/util/http-status.js"
import { userConfirm, findById, tokenRegeneration, logout } from "@/api/member.js"

export const useUserStore = defineStore("user", () => {

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const userInfo = ref(null);
  const isValidToken = ref(false);

  const checkInitialLoginState = async () => {
    const accessToken = sessionStorage.getItem("accessToken");
    if (accessToken) {
      const decoded = jwtDecode(accessToken);
      await getUserInfo(accessToken).then(() => {
        isLogin.value = true;
        isValidToken.value = true;
      }).catch((error) => {
        isLogin.value = false;
        isValidToken.value = false;
      });
    }
  };

  const userLogin = async (loginUser) => {
    await userConfirm(
      loginUser,
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let { data } = response;
          let accessToken = data["access-token"];
          let refreshToken = data["refresh-token"];
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          sessionStorage.setItem("accessToken", accessToken);
          sessionStorage.setItem("refreshToken", refreshToken);
          
        }
      },
      (error) => {
        alert("아이디 또는 비밀번호가 틀렸습니다!")
        isLogin.value = false;
        isLoginError.value = true;
        isValidToken.value = false;
      }
    );
  };

  const getUserInfo = async (token) => {
    let decodeToken = jwtDecode(token);
    await findById(
      decodeToken.userId,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          userInfo.value = response.data.userInfo;
        } else {
          console.log("유저 정보 없음!!!!");
        }
      },
      async (error) => {
        console.error(
          "g[토큰 만료되어 사용 불가능.] : ",
          error.response.status,
          error.response.statusText
        );
        isValidToken.value = false;
        await tokenRegenerate();
      }
    );
  };

  const tokenRegenerate = async () => {
    console.log("토큰 재생성");
    await tokenRegeneration(
      JSON.stringify(userInfo.value),
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let accessToken = response.data["access-token"]
          sessionStorage.setItem("accessToken", accessToken)
          isValidToken.value = true
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await logout(
            userInfo.value.userid,
            async (response) => {
              if (response.status === httpStatusCode.OK) {
                console.log("리프레시 토큰 제거 성공");
              } else {
                console.log("리프레시 토큰 제거 실패");
              }
              isLogin.value = false;
              userInfo.value = null;
              isValidToken.value = false;
              await router.push({ name: "login" });
              alert("세션이 만료되었습니다. 다시 로그인해 주세요.");
            },
            (error) => {
              isLogin.value = false;
              userInfo.value = null;
            }
          );
        }
      }
    );
  };

  const userLogout = async () => {
    await logout(
      userInfo.value ? userInfo.value.userId : null,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          isLogin.value = false;
          userInfo.value = null;
          isValidToken.value = false;
          console.log("isLogin: ", isLogin.value);
          sessionStorage.removeItem("accessToken");
          sessionStorage.removeItem("refreshToken");
          alert("로그아웃 되었습니다.")
        } else {
          console.error("유저 정보 없음!!!!");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return {
    isLogin,
    isLoginError,
    userInfo,
    isValidToken,
    userLogin,
    getUserInfo,
    tokenRegenerate,
    userLogout,
    checkInitialLoginState
  };
});