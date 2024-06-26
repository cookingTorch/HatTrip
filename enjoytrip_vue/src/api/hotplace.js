import http from "@/api/http.js";

function registHotplace(param, success, fail) {
    http.post(`/hotplace/regist`, param, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }).then(success).catch(fail);
}

function listHotplaces(param, success, fail) {
    http.get(`/hotplace/list`, { params: param }).then(success).catch(fail);
}

function listContentTypes(success, fail) {
    http.get(`/hotplace/type`).then(success).catch(fail);
}

function searchHotplaces(param, success, fail) {
    http.get(`/hotplace/search`, { params: param }).then(success).catch(fail);
}

function getThumbnail(hotplaceId, success, fail) {
    console.log("id :", hotplaceId);
    http.get(`/hotplace/thumbnail?hotplaceId=${hotplaceId}`).then(success).catch(fail);
}

function getHotplaceDetail(hotplaceId, loginUser, success, fail) {
    console.log("id :", hotplaceId);
    http.get(`/hotplace/view?loginUser=${loginUser}&hotplaceId=${hotplaceId}`).then(success).catch(fail);
}

function listHotplaceImage(hotplaceId, success, fail) {
    http.get(`/hotplace/image?hotplaceId=${hotplaceId}`).then(success).catch(fail);
}

function deleteHotplace(hotplaceId, success, fail) {
    http.delete(`/hotplace/delete?hotplaceId=${hotplaceId}`).then(success).catch(fail);
}

function postLike(userId, hotplaceId, success, fail) {
    console.log("userId :", userId);
    console.log("likeId :", hotplaceId);
    http.post(`/hotplace/like?userId=${userId}&hotplaceId=${hotplaceId}`).then(success).catch(fail);
}

function deleteLike(userId, hotplaceId, success, fail) {
    console.log("userId :", userId);
    console.log("likeId :", hotplaceId);
    http.delete(`/hotplace/unlike?userId=${userId}&hotplaceId=${hotplaceId}`).then(success).catch(fail);
}

export {
    registHotplace,
    listHotplaces,
    listContentTypes,
    searchHotplaces,
    getThumbnail,
    getHotplaceDetail,
    listHotplaceImage,
    deleteHotplace,
    postLike,
    deleteLike
}