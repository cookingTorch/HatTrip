<template>
    <div ref="mapContainer" style="height: 350px; width: 400px; position: relative; overflow: hidden;"></div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
    lat: Number,
    lng: Number,
    markerText: {
        type: String,
        default: '장소'
    }
});

const { VITE_KAKAO_MAP_SERVICE_KEY } = import.meta.env;

const mapContainer = ref(null);

onMounted(() => {
    loadKakaoMap(mapContainer.value);
});

const loadKakaoMap = (container) => {
    const script = document.createElement('script');
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${VITE_KAKAO_MAP_SERVICE_KEY}&autoload=false&libraries=services`
    document.head.appendChild(script);

    script.onload = () => {
        window.kakao.maps.load(() => {
            const latLng = new kakao.maps.LatLng(props.lat, props.lng);
            const options = {
                center: latLng,
                level: 3,
                marker: {
                    position: latLng,
                    text: props.markerText
                }
            };
            new window.kakao.maps.StaticMap(container, options);
        });
    };
};
</script>
