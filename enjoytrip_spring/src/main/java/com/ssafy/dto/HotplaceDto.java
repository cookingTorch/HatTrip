package com.ssafy.dto;

public class HotplaceDto {

	private int hotplaceId;
	private String userId;
	private int contentTypeId;
	private String contentType;
	private String title;
	private String addr1;
	private String addr2;
	private String tel;
	private String imageSrc;
	private int sidoCode;
	private int gugunCode;
	private double latitude;
	private double longitude;
	private String description;
	private boolean liked;
	
	public HotplaceDto() {
	}

	public HotplaceDto(int hotplaceId, String userId, int contentTypeId, String contentType, String title, String addr1, String addr2,
			String tel, String imageSrc, int sidoCode, int gugunCode, double latitude, double longitude, boolean liked) {
		super();
		this.hotplaceId = hotplaceId;
		this.userId = userId;
		this.contentTypeId = contentTypeId;
		this.contentType = contentType;
		this.title = title;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.tel = tel;
		this.imageSrc = imageSrc;
		this.sidoCode = sidoCode;
		this.gugunCode = gugunCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.liked = liked;
	}

	public HotplaceDto(String userId, int contentTypeId, String title, String addr1, String tel,
			double latitude, double longitude) {
		super();
		this.userId = userId;
		this.contentTypeId = contentTypeId;
		this.title = title;
		this.addr1 = addr1;
		this.tel = tel;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getHotplaceId() {
		return hotplaceId;
	}

	public void setHotplaceId(int hotplaceId) {
		this.hotplaceId = hotplaceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	public int getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

}
