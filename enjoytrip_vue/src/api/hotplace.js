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

export {
    registHotplace,
    listHotplaces,
    listContentTypes,
    searchHotplaces,
    getThumbnail
}