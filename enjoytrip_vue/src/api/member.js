import http from "@/api/http.js";

async function userConfirm(param, success, fail) {
  await http.post(`/member/login`, param).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  http.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await http.get(`/member/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  http.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await http.post(`/member/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await http.get(`/member/logout/${userid}`).then(success).catch(fail);
}

function joinMember(member, success, fail) {
  http.post(`/member/join`, JSON.stringify(member)).then(success).catch(fail);
}

function modifyMember(member, success, fail){
  http.put(`/member/modify`, JSON.stringify(member)).then(success).catch(fail);
}

export {
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  joinMember,
  modifyMember
};

// function joinMember(member, success, fail) {
//   http.post(`/member/join`, JSON.stringify(member))
//   .then(success).catch(fail);
// }

// function loginMember(userId, userPwd, success, fail) {
//   http.post(`/member/login?userId=${userId}&userPwd=${userPwd}`)
//   .then(success).catch(fail);
// }

// function logoutMember(success, fail) {
//   http.get(`/member/logout`)
//   .then(success).catch(fail);
// }

// function deleteMember(userId, success, fail) {   
//   http.delete(`/member/delete?userid=${userId}`)
//   .then(success).catch(fail);
// }

// function modifyMember(member, success, fail){
//   http.put(`/member/modify`,JSON.stringify(member))
//   .then(success).catch(fail);
// }

// export {
//   joinMember,
//   loginMember,
//   logoutMember,
//   deleteMember,
//   modifyMember,
// };