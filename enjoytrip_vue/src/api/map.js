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

const displayMarker = (positions, map, clickable, callback) => {
    
    var markers = [];

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

        if(clickable){
            kakao.maps.event.addListener(marker, 'click', function() {
                alert('marker click!');
            });
        }
        markers.push(marker);
    }

    return markers;
}

export{
    types,
    displayMarker,
}