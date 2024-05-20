<template>
    <div style="width: 59.5%;">
        <div ref="mapContainer" style="height: 600px; position: relative; overflow: hidden;">
        <div class="hAddr">
            <span class="title">지도 정보</span>
            <span id="centerAddr"></span>
        </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, reactive, defineEmits } from 'vue'

const { VITE_KAKAO_MAP_SERVICE_KEY } = import.meta.env;
const emits = defineEmits(['updateLocation', 'updateAddress']);  // 이벤트 이름을 정의합니다.

const mapContainer = ref(null);
const clickLatlng = ref(null);
const infoWindow = ref(null); // 인포윈도우 참조
const state = reactive({
    centerAddress: ''
});

onMounted(() => {
    loadKakaoMap(mapContainer.value);
});

const loadKakaoMap = (container) => {
    const script = document.createElement('script');
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${VITE_KAKAO_MAP_SERVICE_KEY}&autoload=false&libraries=services`
    document.head.appendChild(script);

    script.onload = () => {
        window.kakao.maps.load(() => {
            const options = {
                center: new window.kakao.maps.LatLng(33.450701, 126.570667),
                level: 6
            };

            const mapInstance = new window.kakao.maps.Map(container, options);
            const geocoder = new window.kakao.maps.services.Geocoder();

            // 지도 타입 컨트롤 추가
            const mapTypeControl = new window.kakao.maps.MapTypeControl();
            mapInstance.addControl(mapTypeControl, window.kakao.maps.ControlPosition.TOPRIGHT);

            // 줌 컨트롤 추가
            const zoomControl = new window.kakao.maps.ZoomControl();
            mapInstance.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);

            // 마커 설정
            const marker = new window.kakao.maps.Marker({
                position: mapInstance.getCenter()
            });
            marker.setMap(mapInstance);

            infoWindow.value = new window.kakao.maps.InfoWindow({zindex:1});

            // 지도 클릭 이벤트 등록
            window.kakao.maps.event.addListener(mapInstance, 'click', function(mouseEvent) {
                const latlng = mouseEvent.latLng;
                marker.setPosition(latlng);
                
                // const message = `클릭한 위치의 위도는 ${latlng.getLat()} 이고, 경도는 ${latlng.getLng()} 입니다`;
                // clickLatlng.value.innerText = message; // 결과를 div에 표시
                emits('updateLocation', { latitude: latlng.getLat(), longitude: latlng.getLng() });  // 이벤트를 발생시킵니다.
                searchDetailAddrFromCoords(geocoder, mouseEvent.latLng, mapInstance, marker);
            });

            // 지도 중심 좌표 변경 이벤트 등록: 중심 좌표의 주소 정보 표시 (displayCenterInfo 함수 호출)
            window.kakao.maps.event.addListener(mapInstance, 'idle', function() {
                searchAddrFromCoords(mapInstance.getCenter(), displayCenterInfo);
            });
        });
    };
};

function searchAddrFromCoords(coords, callback) {
    const geocoder = new window.kakao.maps.services.Geocoder();
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
}

function searchDetailAddrFromCoords(geocoder, coords, map, marker) {
    geocoder.coord2Address(coords.getLng(), coords.getLat(), function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            const detailAddr = !!result[0].road_address ? `<div>${result[0].road_address.address_name}</div>` : '';
            const content = `<div class="bAddr">
                                <span class="title">주소</span>
                                ${detailAddr}
                                <div>${result[0].address.address_name}</div>
                            </div>`;

            marker.setPosition(coords);
            marker.setMap(map);
            infoWindow.value.setContent(content);
            infoWindow.value.open(map, marker);

            emits('updateAddress', { road: result[0].road_address?.address_name, jibun: result[0].address.address_name }); // 주소 업데이트 이벤트 발생
        }
    });
}

function displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        for (var i = 0; i < result.length; i++) {
            if (result[i].region_type === 'H') { // H는 행정동을 의미
                state.centerAddress = result[i].address_name;
                document.getElementById('centerAddr').innerText = state.centerAddress; // HTML 요소에 주소 반영
                break;
            }
        }
    }
}
</script>

<style>
.map_wrap {
    position: relative;
    width: 100%;
    height: 350px;
}
.title {
    font-weight: bold;
    display: block;
}
.hAddr {
    position: absolute;
    left: 10px;
    top: 10px;
    border-radius: 2px;
    background: #fff;
    background: rgba(255,255,255,0.8);
    z-index: 1000;
    padding: 5px;
}
#centerAddr {
    display: block;
    margin-top: 2px;
    font-weight: normal;
}
.bAddr {
    padding: 5px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    }
</style>
