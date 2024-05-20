<template>
    <div class="container">
        <div class="mt-3 text-center fs-2" role="alert">
            나만의 핫플레이스
        </div>
        <hr class="center-hr">
        <div class="mainContent row">
            <HotplaceMap @updateLocation="updateMarkerLocation" /> <!-- 이벤트를 받아서 처리하는 핸들러를 지정 -->
            <div class="hotPlaceFormDiv">
                <form class="hotPlaceForm" @submit.prevent="submitForm">
                    <fieldset>
                        <div class="mb-3">
                            <label for="inputPhoto" class="form-label" style="color: red;"> &#128247; 사진을 올려주세요</label>
                            <input type="file" @change="handleFileUpload" class="form-control" id="inputPhoto" multiple>
                        </div>
                        <div class="mb-3">
                            <label for="placeNameInput" class="form-label">핫플 이름</label>
                            <input type="text" id="placeNameInput" class="form-control" v-model="formData.title" required>
                        </div>
                        <div class="mb-3">
                            <label for="placeCategorySelect" class="form-label">장소유형</label>
                            <select id="placeCategorySelect" class="form-select" v-model="formData.contentTypeId">
                                <option value="12" selected>관광지</option>
                                <option value="14">문화시설</option>
                                <option value="15">축제공연행사</option>
                                <option value="25">여행코스</option>
                                <option value="28">레포츠</option>
                                <option value="32">숙박</option>
                                <option value="38">쇼핑</option>
                                <option value="39">음식점</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="placeTelInput" class="form-label">전화번호</label>
                            <input type="text" id="placeTelInput" class="form-control" v-model="formData.tel">
                        </div>
                        <div class="mb-3">
                            <label for="detailedDescription" class="form-label">핫플 상세설명</label>
                            <textarea class="form-control" id="detailedDescription" rows="6" v-model="formData.description"></textarea>
                        </div>
                        <div class="d-flex justify-content-end">
                            <button type="submit" class="btn btn-primary submitBtn">등록</button>
                        </div>
                    </fieldset>
                  </form>
            </div>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios';
import HotplaceMap from "./item/HotplaceMap.vue";
import { ref, computed } from 'vue';
import { storeToRefs } from "pinia";
import { useUserStore } from '@/stores/user-store';

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);
const { isLogin } = storeToRefs(userStore);

const location = ref({
    latitude: null,
    longitude: null
});
const files = ref([]);
const formData = ref({
    userId: '',
    contentTypeId: 12,
    title: '',
    tel: '',
    latitude: 0,
    longitude: 0,
    description: ''
});

function updateMarkerLocation({ latitude, longitude }) {
    location.value.latitude = latitude;
    location.value.longitude = longitude;
    console.log(`Updated location: Latitude = ${latitude}, Longitude = ${longitude}`);
    formData.value.latitude = latitude;
    formData.value.longitude = longitude;
}

function handleFileUpload(event) {
    files.value = event.target.files;
}

async function submitForm() {
    if (location.value.latitude == null) {
        
    }
    const uploadData = new FormData();
    // Append files to the FormData
    for (let i = 0; i < files.value.length; i++) {
        uploadData.append(`files`, files.value[i]);
    }

    // Append form data
    uploadData.append('userId',
        computed(() => {
            return userInfo.value && isLogin ? userInfo.value.userId : 'Guest';
        })
    );
    uploadData.append('contentTypeId', parseInt(formData.value.contentTypeId, 10));
    uploadData.append('title', formData.value.title);
    uploadData.append('tel', formData.value.tel);
    uploadData.append('description', formData.value.description);
    uploadData.append('latitude', );
    uploadData.append('longitude',);

    try {
        const response = await axios.post('/regist', uploadData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        console.log('서버 응답:', response.data);
    } catch (error) {
        console.error('업로드 실패:', error);
    }
}
</script>

<style scoped src="@/assets/css/hotplace.css">
</style>
