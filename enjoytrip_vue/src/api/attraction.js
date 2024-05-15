import http from "@/api/http.js";

function getSido(success, fail) {
    http.get(`/tour/getSido`).then(success).catch(fail);
}
  
function getGugun(sidoCode, success, fail) {
  http.post(`/tour/getGugun?sido=${sidoCode}`).then(success).catch(fail);
}
  
function getList(search, success, fail) {
  console.log("search : ", search);
  http.post(`/tour/list`, JSON.stringify(search)).then(success).catch(fail);
}
  
function getSearchRoute(startPlaceId, endPlaceId, success, fail) {   
  http.get(`/tour/searchRoute?startPlaceId=${startPlaceId}&endPlaceId=${endPlaceId}`)
  .then(success).catch(fail);
}

function getListByPos(pos, success, fail){
    http.post(`/tour/searchByPos`, JSON.stringify(pos)).then(success).catch(fail);
}
  
export {
    getSido,
    getGugun,
    getList,
    getSearchRoute,
    getListByPos,
}