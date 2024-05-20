<script setup>
</script>

<template>
    <div class="mainImg shadow">
        <div class="mainContent row align-items-center">
            <div class="me-auto mb-lg-20 mainPhrases col">
                <div>
                    지금 <span>Enjoy Trip</span>과 함께 우리지역의 관광지를 알아보고
                </div>
                <div>
                    나만의 여행 계획을 만들어 보세요!!!
                </div>
                <hr width="80px">
                <div>
                    나만의 여행 계획을 짜보고 공유해 보세요
                </div>
                <button id="lookMoreSchedule" class="btn btn-primary">
                    일정더보기
                </button>
            </div>
            <div class="mb-2 me-2 mb-lg-0 inquireForm col-3">
                <form id="questForm">
                    <fieldset>
                        <legend>문의하기!</legend>
                        <span class="form-label" for="questForm">이름과 연락처를 남겨주세요.</span>
                        <div class="mb-3">
                        <input type="text" id="nameTextInput" class="form-control" placeholder="이름">
                        </div>
                        <div class="mb-3">
                        <input type="text" id="phoneNumberTextInput" class="form-control" placeholder="전화번호">
                        </div>
                        <div class="mb-3">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="infoOfferAgreeCheck">
                            <span class="form-check-label" for="infoOfferAgreeCheck">
                            정보제공동의
                            </span>
                        </div>
                        </div>
                        <button type="submit" class="btn btn-primary">보내기</button>
                    </fieldset>
                    </form>
            </div>
        </div>
    </div>
</template>

<style scoped>
@import "@/assets/css/home.css";
</style>