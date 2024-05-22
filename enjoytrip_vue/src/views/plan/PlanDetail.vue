<template>
    <div class="col-md-12 row">
        <div class="col-md-6 flex">
            <div class="mt-3 text-center fw-bold" style="font-size: 2rem;"></div>
            <div id="map" class="mt-3" style="width: 100%; height: 600px" ref="mapContainer"></div>
        </div>
        <div class="col-md-6 flex table-container">
            <table class="table table-striped table-hover">
              <thead>
                <tr>
                  <th scope="col">이미지</th>
                  <th scope="col">이름</th>
                  <th scope="col">주소</th>
                </tr>
              </thead>
              <tbody id="travelInfoTable">
                <tr v-for="travel in travelPlan" :key="travel.contentId">
                  <td>
                    <img :src="travel.imgUrl" alt="Image" width="30%" v-if="travel.imgUrl">
                    <img :src="noImg" width="30%" v-else>
                  </td>
                  <td>{{ travel.title }}</td>
                  <td>{{ travel.addr1 }}</td>
                </tr>
              </tbody>
            </table>
        </div>
    </div>
</template>

<script setup>
import { types, displayMarker } from "@/api/map.js"
import { useRoute } from 'vue-router';
import { ref, onMounted, onUpdated } from "vue"; 
import { storeToRefs } from "pinia";
import { useUserStore } from '@/stores/user-store';
import { getPlan } from '@/api/plan.js';
import noImg from '@/assets/img/noImg.jpg';

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);

const userId = userInfo.value.userId;
const route = useRoute();

const mapContainer= ref(null);
const travelPlan= ref([]);
const planTitle = ref("");
const { planNo } = route.params;

var map;
var markers = [];
var positions = [];

onMounted(() => {
    loadScript();
    //fetchPlan();
});

const loadScript = () => {
    const key = import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    const script = document.createElement('script')
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${key}&autoload=false&libraries=services`
    script.addEventListener('load', () => kakao.maps.load(fetchPlan))
    document.head.appendChild(script);
}

const moveCenter = (lat, lng) => {
    map.setCenter(new kakao.maps.LatLng(lat, lng))
    map.setLevel(6)
}

const fetchPlan = () => {

    const options = {
        center: new kakao.maps.LatLng(37.500613, 127.036431),
        level: 4,
    }
    map = new kakao.maps.Map(mapContainer.value, options);

    getPlan(
        planNo,
        ({ data }) => {
            console.log(planNo, " get " ,data);
            travelPlan.value = data;

            data.forEach((trip, index) => {
                let markerInfo = {
                    imgUrl: trip.imgUrl,
                    contentId: trip.contentId,
                    title: trip.title,
                    latlng: new kakao.maps.LatLng(trip.latitude, trip.longitude),
                    contentTypeId: trip.contentTypeId,
                };

                if(index==0){
                    moveCenter(trip.latitude, trip.longitude);
                }

                positions.push(markerInfo);
            })
            
            for (var i = 0; i < markers.length; i++)
                markers[i].setMap(null)
            
            markers = displayMarker(positions, map, false, true);

        }
    )
}

</script>
  
<style scoped>

.plan-title {
    width: 100%;
    margin-bottom: 10px;
}

.plan-buttons {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 20px;
}
  
.travel-planner {
  display: flex;
  height: 100vh;
}

.col-md-6 {
  flex: 1;
  padding: 20px;
  box-sizing: border-box;
}

/* Styles for the scrollable container */
.scrollable-container {
  max-height: 600px; /* Adjust this value as needed */
  overflow-y: auto;
  border: 1px solid #ddd; /* Optional: to visually distinguish the scrollable area */
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

.table-container {
    max-height: 800px; /* 원하는 높이로 설정 */
    overflow-y: auto;
  }
  .table-container table {
    width: 100%;
    table-layout: fixed;
  }

/* Additional styles for table and buttons if needed */
table {
  width: 100%;
}

td {
  padding: 10px;
  vertical-align: middle;
}

button {
  margin-top: 10px;
  margin-right: 5px;
}

</style>