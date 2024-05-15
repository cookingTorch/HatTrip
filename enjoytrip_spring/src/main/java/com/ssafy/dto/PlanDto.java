package com.ssafy.dto;

public class PlanDto {
	
	private int planNo;
	private String userId;
	private String[] title;
	private String[] address;
	private String[] latitude;
	private String[] longitude;
	public PlanDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanDto(String userId, String[] title, String[] address, String[] latitude, String[] longitude) {
		this.userId = userId;
		this.title = title;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getPlanNo() {
		return planNo;
	}
	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public String[] getAddress() {
		return address;
	}

	public void setAddress(String[] address) {
		this.address = address;
	}

	public String[] getLatitude() {
		return latitude;
	}

	public void setLatitude(String[] latitude) {
		this.latitude = latitude;
	}

	public String[] getLongitude() {
		return longitude;
	}

	public void setLongitude(String[] longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "PlanDto [planNo=" + planNo + ", userId=" + userId + ", title=" + title + ", address=" + address
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	

	
}
