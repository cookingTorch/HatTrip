package com.ssafy.dto;

public class AttractionDto {

	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private String zipCode;
	private String tel;
	private String imgUrl;
	private int sido;
	private int gugun;
	private double latitude;
	private double longitude;
	
	public AttractionDto() {}
	
	public AttractionDto(int contentId, int contentTypeId, String title, String addr1, String addr2, String zipCode, String tel,
			String imgUrl, int sido, int gugun, double latitude, double longitude) {
		super();
		this.contentId = contentId;
		this.contentTypeId = contentTypeId;
		this.title = title;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.zipCode = zipCode;
		this.tel = tel;
		this.imgUrl = imgUrl;
		this.sido = sido;
		this.gugun = gugun;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public AttractionDto(int contentId, int contentTypeId, String title, String addr1, String imgUrl, double latitude,
			double longitude) {
		super();
		this.contentId = contentId;
		this.contentTypeId = contentTypeId;
		this.title = title;
		this.addr1 = addr1;
		this.imgUrl = imgUrl;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public AttractionDto(int contentTypeId, int sido, int gugun) {
		super();
		this.contentTypeId = contentTypeId;
		this.sido = sido;
		this.gugun = gugun;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public int getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getSido() {
		return sido;
	}

	public void setSido(int sido) {
		this.sido = sido;
	}

	public int getGugun() {
		return gugun;
	}

	public void setGugun(int gugun) {
		this.gugun = gugun;
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

	@Override
	public String toString() {
		return "AttractionDto [contentId=" + contentId + ", contentTypeId=" + contentTypeId + ", title=" + title
				+ ", addr1=" + addr1 + ", addr2=" + addr2 + ", zipCode=" + zipCode + ", tel=" + tel + ", imgUrl="
				+ imgUrl + ", sido=" + sido + ", gugun=" + gugun + ", latitude=" + latitude + ", longitude=" + longitude
				+ "]";
	}
	
	
}
