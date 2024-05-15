<template>
    <div class="container">
      <nav-component></nav-component> <!-- Include your navigation component -->
      <div class="col-md-12 justify-content-center">
        <div class="mt-3 text-center fw-bold" style="font-size: 2rem;">
          전국 관광지 정보
        </div>
        <div class="row">
          <form id="form-search" class="d-flex" @submit.prevent="search">
            <input type="hidden" name="action" value="search">
            <select v-model="selectedSido" @change="fetchGugun" class="form-select me-2">
              <option selected>시도선택</option>
              <option v-for="sido in sidos" :key="sido.code" :value="sido.code">{{ sido.name }}</option>
            </select>
            <select v-model="selectedGugun" class="form-select me-2">
              <option selected>구군선택</option>
              <option v-for="gugun in guguns" :key="gugun.code" :value="gugun.code">{{ gugun.name }}</option>
            </select>
            <select v-model="selectedType" class="form-select me-2">
              <option v-for="type in types" :key="type.value" :value="type.value">{{ type.text }}</option>
            </select>
            <button id="btn-search" class="btn btn-outline-success" type="button"
                @click="searchByCondition">검색</button>
          </form>
        </div>
        <div id="map" class="mt-3" style="width: 100%; height: 600px" ref="mapContainer"></div>
        <div class="row">
          <table class="table table-striped">
            <thead>
              <tr>
                <th>대표이미지</th>
                <th>관광지명</th>
                <th>주소</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="trip in trips" :key="trip.id" @click="moveCenter(trip.latitude, trip.longitude)">
                <td><img :src="trip.imgUrl" width="200px" v-if="trip.imgUrl">
                    <span v-else class="gray">존재하는 이미지가 없습니다.</span></td>
                <td>{{ trip.title }}</td>
                <td>{{ trip.addr1 }} {{ trip.addr2 }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
</template>
  
<script setup>
import { getSido, getGugun, getList, getListByPos } from "@/api/attraction.js"
import { ref, onMounted } from "vue";

const types = [
    { value: -1, text: '전체' },
    { value: 12, text: '관광지' },
    { value: 14, text: '문화시설' },
    { value: 15, text: '축제공연행사' },
    { value: 25, text: '여행코스' },
    { value: 28, text: '레포츠' },
    { value: 32, text: '숙박' },
    { value: 38, text: '쇼핑' },
    { value: 39, text: '음식점' },
]

const sidos= ref([]);
const guguns= ref([]);
const selectedSido= ref(1);
const selectedGugun= ref(0);
const selectedType= ref(-1);
const trips= ref([]);
const mapContainer = ref(null);

var map;
var markers = [];
var positions = [];

onMounted(() => {
    fetchSidos();
    fetchGugun();
    loadScript();
});

const fetchSidos = () => {
    console.log("Fetch Sidos");

    getSido(
        ({ data }) => {
            sidos.value = data;
            console.log(sidos.value);
        },
        (error) => {
            console.log(error);
        }
    );
};

const fetchGugun = () => {
    console.log("Fetch gugun");
    console.log(selectedSido);

    getGugun(
        selectedSido.value,
        ({ data }) => {
            guguns.value = data;
            console.log(guguns.value);
        },
        (error) => {
            console.log(error);
        }
    );
};


const loadScript = () => {
    const key = import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    const script = document.createElement('script')
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${key}&autoload=false`
    script.addEventListener('load', () => kakao.maps.load(initMap))
    document.head.appendChild(script)
}

const initMap = () => {
    const options = {
        center: new kakao.maps.LatLng(37.500613, 127.036431),
        level: 7,
    }
    map = new kakao.maps.Map(mapContainer.value, options);

    kakao.maps.event.addListener(map, 'dragend', function() {  
        if(map.getLevel() < 7){ 
            searchByPos();
        }
    });
}

const searchByCondition = () => {
    console.log("get List ", selectedSido, selectedGugun, selectedType);
    if(!selectedSido || !selectedGugun){
        alert("다시");
    } else {
        const search = {
            sido:selectedSido.value,
            gugun:selectedGugun.value,
            type:selectedType.value,
        }

        getList(
            search,
            ({ data }) => {
                trips.value = data;
                console.log(trips.value);
                positions = [];

                data.forEach((trip) => {
                    let markerInfo = {
                        title: trip.title,
                        latlng: new kakao.maps.LatLng(trip.latitude, trip.longitude),
                        contentTypeId: trip.contentTypeId,
                    };

                    positions.push(markerInfo);
                })

                displayMarker()
                
                map.setCenter(positions[0].latlng);
                map.setLevel(7);
            },
            (error) => {
                console.log(error);
            }
        )
    }
};

const searchByPos = () => {
    var bounds = map.getBounds(); 

    let minLat = bounds.getSouthWest().getLat();
    let minLon = bounds.getSouthWest().getLng();

    let maxLat = bounds.getNorthEast().getLat();
    let maxLon = bounds.getNorthEast().getLng();

    console.log(minLat, maxLat, minLon, maxLon);

    const pos = {
        minLat: minLat,
        maxLat: maxLat,
        minLon: minLon,
        maxLon: maxLon
    }

    getListByPos(
        pos,
        ({ data }) => {
            trips.value = data;
            console.log(trips.value);
            positions = [];

            data.forEach((trip) => {
                let markerInfo = {
                    title: trip.title,
                    latlng: new kakao.maps.LatLng(trip.latitude, trip.longitude),
                    contentTypeId: trip.contentTypeId,
                };

                positions.push(markerInfo);
            })

            displayMarker()
        },
        (error) => {
            console.log(error);
        }
    )
}

// function getList(search, success, fail) {
//   console.log("search : ", search);
//   http.post(`/tour/list`, JSON.stringify(search)).then(success).catch(fail);
// }
  

const displayMarker = () => {
    for (var i = 0; i < markers.length; i++)
        markers[i].setMap(null)
    markers = [];

    var imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/f43775aa-d206-423b-933b-615f374a8263/image.png";
    for (var i = 0; i < positions.length; i++) {
        var contentTypeId = positions[i].contentTypeId;
        console.log(contentTypeId)
        switch (contentTypeId) {
            case 12:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/bf5d1a2c-f698-4364-8d9a-72f5c47881db/image.png";
                break;
            case 14:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/eecea87b-5d84-4dd7-ad63-270838fd1554/image.png";
                break;
            case 15:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/edba1b43-4668-4ab0-a71c-aad60b61e745/image.png";
                break;
            case 25:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/4ac49508-be8a-434b-a55a-2aa5128633fa/image.png";
                break;
            case 28:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/2291b92e-0c07-46d6-aedc-883e9e1a6a76/image.png";
                break;
            case 32:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/64b14158-cee1-459e-9fbd-1d320a053559/image.png";
                break;
            case 38:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/e479fdbd-b706-4d7d-9a4b-a68310155afd/image.png";
                break;
            case 39:
                imgSrc = "https://velog.velcdn.com/images/gangintheremark/post/973b2b92-734c-4f1a-9da2-a9354974709d/image.png";
                break;
        }

        var imageSize = new kakao.maps.Size(30, 30)
        var markerImage = new kakao.maps.MarkerImage(imgSrc, imageSize)
        console.log(positions[i])
        var marker = new kakao.maps.Marker({
            map,
            position: positions[i].latlng,
            title: positions[i].title,
            image: markerImage
        });
        markers.push(marker);
    }
}

const moveCenter = (lat, lng) => {
    map.setCenter(new kakao.maps.LatLng(lat, lng))
    map.setLevel(2)
}

  </script>
  
  <style>
  </style>