<template>
    <div class="container">
        <div class="mt-3 text-center fs-2" role="alert">
            나만의 핫플레이스
        </div>
        <hr class="center-hr">
        <div class="mainContent row">
            <HotplaceMap @updateLocation="updateMarkerLocation" @updateAddress="updateAddressInfo" /> <!-- 이벤트를 받아서 처리하는 핸들러를 지정 -->
            <div class="hotPlaceFormDiv">
                <form class="hotPlaceForm" @submit.prevent="submitForm">
                    <fieldset>
                        <div class="mb-3">
                            <label for="inputPhoto" class="form-label" style="color: red;"> &#128247; 사진을 올려주세요</label>
                            <input type="file" @change="handleFileUpload" class="form-control" id="inputPhoto" multiple ref="inputPhotoRef">
                        </div>
                        <div class="mb-3">
                            <label for="placeNameInput" class="form-label">핫플 이름</label>
                            <input type="text" id="placeNameInput" class="form-control" v-model="formData.title" required>
                        </div>
                        <div class="mb-3">
                            <label for="placeCategorySelect" class="form-label">장소유형</label>
                            <select id="placeCategorySelect" class="form-select" v-model="formData.contentTypeId">
                                <option v-for="contentType in contentTypes" :key="contentType.contentTypeId" :value="contentType.contentTypeId">
                                    {{ contentType.contentType }}
                                </option>
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
import HotplaceMap from "./item/HotplaceMap.vue";
import { ref, computed, onMounted } from 'vue';
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useUserStore } from '@/stores/user-store';
import { registHotplace, listContentTypes } from "@/api/hotplace";

const router = useRouter();

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);
const { isLogin } = storeToRefs(userStore);

const contentTypes = ref([]);

const location = ref({
    latitude: null,
    longitude: null
});
const inputPhotoRef = ref(null);
const files = ref([]);
const formData = ref({
    userId: '',
    contentTypeId: 12,
    title: '',
    addr: '',
    tel: '',
    latitude: 0,
    longitude: 0,
    description: ''
});

onMounted(() => {
    getContentTypeList();
});

const getContentTypeList = () => {
    listContentTypes(
        ({ data }) => {
            contentTypes.value = data;
        },
        (error) => {
            console.log(error);
        }
    );
}

function updateMarkerLocation({ latitude, longitude }) {
    location.value.latitude = latitude;
    location.value.longitude = longitude;
    formData.value.latitude = latitude;
    formData.value.longitude = longitude;
    console.log(`Updated location:\nLatitude = ${formData.value.latitude}\nLongitude = ${formData.value.longitude}`);
}

function updateAddressInfo({ addr }) {
    formData.value.addr = addr;
    console.log(`Updated address: ${formData.value.addr}`);
}

function handleFileUpload(event) {
    files.value = event.target.files;
}

async function submitForm() {
    if (location.value.latitude == null) {
        
    }
    const uploadData = new FormData();
    
    // Append form data
    const computedUserId = computed(() => {
        return userInfo.value && isLogin ? userInfo.value.userId : 'Guest';
    }).value; // 여기서 .value를 사용하여 실제 값을 추출합니다.
    uploadData.append('userId', computedUserId);
    uploadData.append('contentTypeId', parseInt(formData.value.contentTypeId, 10));
    uploadData.append('title', formData.value.title);
    uploadData.append('addr', formData.value.addr);
    uploadData.append('tel', formData.value.tel);
    uploadData.append('latitude', formData.value.latitude);
    uploadData.append('longitude', formData.value.longitude);
    uploadData.append('description', formData.value.description);
    // Append files to the FormData
    for (let i = 0; i < files.value.length; i++) {
        uploadData.append(`images`, files.value[i]);
    }

    registHotplace(
        uploadData,
        (response) => {
            let msg = "등록 처리시 문제 발생했습니다.";
            if (response.status == 200) {
                msg = "핫플레이스 등록이 완료되었습니다.";
                files.value = [];
                if (inputPhotoRef.value) {
                    inputPhotoRef.value.value = '';  // 파일 입력 필드 초기화
                }
                formData.value.contentTypeId = 12;
                formData.value.title = '';
                formData.value.tel = '';
                formData.value.description = '';
                router.push({ name: "hotplace" });
            }
            alert(msg);

        },
        (error) => console.log(error)
    );
}
</script>

<style scoped src="@/assets/css/hotplace.css">
</style>
