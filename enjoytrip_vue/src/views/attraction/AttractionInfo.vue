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
import { types, displayMarker } from "@/api/map.js"
import { ref, onMounted } from "vue";

const sidos= ref([]);
const guguns= ref([]);
const selectedSido= ref(1);
const selectedGugun= ref(0);
const selectedType= ref(-1);
const trips= ref([]);
const mapContainer= ref(null);

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
    document.head.appendChild(script);
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
                
                for (var i = 0; i < markers.length; i++)
                    markers[i].setMap(null)
                markers = displayMarker(positions, map);
                
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
            
            for (var i = 0; i < markers.length; i++)
                markers[i].setMap(null)
            markers = displayMarker(positions, map);
        },
        (error) => {
            console.log(error);
        }
    )
}

const moveCenter = (lat, lng) => {
    map.setCenter(new kakao.maps.LatLng(lat, lng))
    map.setLevel(2)
}

  </script>
  
  <style>
  </style>