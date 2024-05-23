<template>
    <div :class="type==='attraction' ? 'container' : 'travel-planner'">
        <div :class="type==='attraction' ? 'col-md-12 justify-content-center' : 'col-md-6 flex pt-0'">
            <div class="mt-3 text-center fw-bold" style="font-size: 2rem;">
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
            <div class="row" v-if="type==='attraction'">
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
                        <img :src="noImg" width="200px" v-else></td>
                    <td>{{ trip.title }}</td>
                    <td>{{ trip.addr1 }} {{ trip.addr2 }}</td>
                </tr>
                </tbody>
            </table>
            </div>
        </div>
        <div class="col-md-6 flex pt-0" v-if="type==='plan'">
            <div style="height:100px">
                <div class="plan-buttons mb-2">
                    <button class="btn btn-outline-danger me-2" @click="clearTravelPlan">초기화</button>
                    <button class="btn btn-outline-primary" @click="submitTravelPlan">제출</button>
                </div>
                <input type="text" class="plan-title form-control" v-model="planTitle" placeholder="여행계획 제목">
            </div>
            <div class="scrollable-container">
                <table>
                    <tr v-for="travel in travelPlan" :key="travel.contentId">
                        <td class="col-md-3">
                            <div class="row flex">
                            <img :src="travel.imgUrl" alt="Image" width="50%" v-if="travel.imgUrl">
                            <img :src="noImg" width="50%" v-else>
                            </div>
                        </td>
                        <td class="col-md-3">
                            <div class="row flex">{{ travel.title }}</div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</template>

<script setup>
import { getSido, getGugun, getList, getListByPos } from "@/api/attraction.js"
import { types, displayMarker } from "@/api/map.js"
import { getLastId, addPlan } from "@/api/plan.js"
import { ref, onMounted } from "vue"; 
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useUserStore } from '@/stores/user-store';
import noImg from '@/assets/img/noImg.jpg';

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);

const userId = userInfo.value.userId;
const router = useRouter();

const sidos= ref([]);
const guguns= ref([]);
const selectedSido= ref(1);
const selectedGugun= ref(0);
const selectedType= ref(-1);
const trips= ref([]);
const mapContainer= ref(null);
const travelPlan= ref([]);
const planTitle = ref(new Date().toISOString());

const props = defineProps({ type: String })

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
        },
        (error) => {
            console.log(error);
        }
    );
};

const loadScript = () => {
    const key = import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    const script = document.createElement('script')
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${key}&autoload=false&libraries=services`
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
        alert("Something went wrong. Please check your input and try again. \nIf the problem persists, contact support.");
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

                if(props.type=='plan')
                    markers = displayMarker(positions, map, true, false);
                else
                    markers = displayMarker(positions, map, false, false);
                
                    if(props.type=='plan'){
                            markers.forEach((marker, index) => {
                                kakao.maps.event.addListener(marker, 'click', () => {
                                    travelPlan.value.push({
                                        imgUrl: data[index].imgUrl,
                                        contentId: data[index].contentId,
                                        title: data[index].title,
                                        latitude: data[index].latitude,
                                        longitude: data[index].longitude,
                                        contentTypeId: data[index].contentTypeId
                                    });
                            });
                        });
                    }
                map.setCenter(positions[0].latlng);
                map.setLevel(7);
            },
            (error) => {
                alert("검색에 오류가 발생했습니다. 다시 시도해주세요")
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
            positions = [];

            data.forEach((trip) => {
                let markerInfo = {
                    imgUrl: trip.imgUrl,
                    contentId: trip.contentId,
                    title: trip.title,
                    latlng: new kakao.maps.LatLng(trip.latitude, trip.longitude),
                    contentTypeId: trip.contentTypeId,
                };

                positions.push(markerInfo);
            })
            
            for (var i = 0; i < markers.length; i++)
                markers[i].setMap(null)
            
            if(props.type=='plan')
                markers = displayMarker(positions, map, true, false);
            else
                markers = displayMarker(positions, map, false, false);

            if(props.type=='plan'){
                markers.forEach((marker, index) => {
                    kakao.maps.event.addListener(marker, 'click', () => {
                        travelPlan.value.push({
                            imgUrl: data[index].imgUrl,
                            contentId: data[index].contentId,
                            title: data[index].title,
                            latitude: data[index].latitude,
                            longitude: data[index].longitude,
                            contentTypeId: data[index].contentTypeId
                        });
                });
            });
            }
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

const clearTravelPlan = () => {
    travelPlan.value = [];
}

const submitTravelPlan = () => {

    getLastId(
        ({ data }) =>{
            console.log("lastid : " ,data);
            let lastPlanNo=data;
            let planCombined = {
                planList : [],
                planUserList : []
            };
                    
            let seqNo = 0;
        
            travelPlan.value.forEach((travel) => {
                planCombined.planList.push({
                    planNo: lastPlanNo,
                    seqNo: seqNo,
                    contentId: travel.contentId,
                })
                seqNo++;
            });
            
            planCombined.planUserList.push({
                title: planTitle.value,
                planNo: lastPlanNo,
                userId: userId,
            })
                
            console.log(planCombined);
        
            addPlan(
                planCombined,
                ()=>{
                    console.log("완료");
                    router.push({ name: 'planView' })
                    alert("여행 계획이 작성되었습니다.")
                },
                (error)=>{
                    console.log(error);
                }
            )
        },
        (error)=>{
            console.log(error)
        }
    );

}

</script>
  
<style scoped>

.plan-title {
    width: 100%;
}

.plan-buttons {
    display: flex;
    justify-content: flex-end;
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

/* Additional styles for table and buttons if needed */
table {
  width: 100%;
}

td {
  padding: 10px;
  vertical-align: middle;
}

.btn {
  white-space: nowrap; /* 텍스트가 줄바뀜 없이 한 줄로 표시되도록 설정 */
}

</style>