import http from "@/api/http.js";

function listArticle(param, success, fail) {
  http.get(`/board/list`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  http.get(`/board/view?articleno=${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("boardjs article", article);
  http.post(`/board/write`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {   
  http.get(`/board/mvmodify?articleno=${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  http.put(`/board/modify`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  http.delete(`/board/delete?articleNo=${articleno}`).then(success).catch(fail);
}

function searchArticle(param, success, fail) {
  http.get(`/board/search`, { params: param }).then(success).catch(fail);
}

function listComments(boardNo, success, fail) {
  http.get(`/comment/list?boardNo=${boardNo}`).then(success).catch(fail);
}

function addComment(comment, success, fail) {
  http.post(`/comment/post`, JSON.stringify(comment)).then(success).catch(fail);
}

function updateComment(comment, success, fail) {
  http.put(`/comment/update`, JSON.stringify(comment)).then(success).catch(fail);
}

function deleteComment(commentNo, success, fail) {
  http.delete(`/comment/delete?commentNo=${commentNo}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
  searchArticle,
  listComments,
  addComment,
  updateComment,
  deleteComment
};
