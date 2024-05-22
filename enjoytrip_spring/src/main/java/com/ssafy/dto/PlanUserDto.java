package com.ssafy.dto;

public class PlanUserDto {
	
	private int planNo;
	private String userId;
	private String title;
	
	public PlanUserDto(int planNo, String userId, String title) {
		super();
		this.planNo = planNo;
		this.userId = userId;
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	
}
