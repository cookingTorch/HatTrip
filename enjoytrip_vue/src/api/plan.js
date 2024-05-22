import http from "@/api/http.js";

function getLastId(success, fail) {
    http.get(`/plan/getLastId`).then(success).catch(fail)
}

function addPlan(planCombined, success, fail) {
  http.post(`/plan/addPlan`, JSON.stringify(planCombined)).then(success).catch(fail);
}

function getPlan(id, success, fail) {
  console.log("get plan ", id);
  http.get(`/plan/getPlan?planNo=${id}`).then(success).catch(fail);
}

function getPlanUser(id, success, fail) {
    console.log("get plan User", id);
    http.post(`/plan/getPlanUser?planNo=${id}`).then(success).catch(fail);
  }

function deletePlan(id, success, fail) {
    console.log("get plan ", id);
    http.delete(`/plan/deletePlan?planNo=${id}`).then(success).catch(fail);
  }

export{
    getLastId,
    addPlan, 
    getPlan, 
    getPlanUser,
    deletePlan,
}