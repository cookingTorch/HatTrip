<template>
    <!-- 중앙 content start -->
    <div class="container">
      <!-- 중앙 center content end -->
      <div>
        <div class="mt-3 text-center fs-2" role="alert">
            나만의 핫플 자랑하기
        </div>
        <hr class="center-hr">
        <div class="mainContent row">
            <!-- kakao map start -->
            <!-- <div id="map" style="width: 59.5%; height: 600px; background-color: aqua;"></div> -->
            <HotplaceMap type="regist" />
            <!-- kakao map end -->
            <div class="hotPlaceFormDiv">
                <form class="hotPlaceForm">
                    <fieldset>
                        <div class="mb-3">
                            <label for="inputPhoto" class="form-label" style="color: red;"> &#128247; 사진을 올려주세요</label>
                            <input type="file" class="form-control" id="inputPhoto">
                        </div>
                        <div class="mb-3">
                            <label for="placeTextInput" class="form-label">핫플 이름</label>
                            <input type="text" id="placeTextInput" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label for="dateTextInput" class="form-label">다녀온 날짜</label>
                            <input type="date" id="dateTextInput" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label for="placeCategorySelect" class="form-label">장소유형</label>
                            <select id="placeCategorySelect" class="form-select">
                                <option value="0" selected>관광지 유형</option>
                                <option value="12">관광지</option>
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
                            <label for="detailedDescription" class="form-label">핫플 상세설명</label>
                            <textarea class="form-control" id="detailedDescription" rows="6"></textarea>
                        </div>
                        <div class="d-flex justify-content-end">
                            <button type="submit" class="btn btn-primary submitBtn">등록</button>
                        </div>
                    </fieldset>
                  </form>
            </div>
        </div>
      </div>
    </div>
    <!-- 중앙 content end -->
</template>

<script setup>
import HotplaceMap from "./item/HotplaceMap.vue";
</script>

<style scoped src="@/assets/css/hotplace.css">
</style>