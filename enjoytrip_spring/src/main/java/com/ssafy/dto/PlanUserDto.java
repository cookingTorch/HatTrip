package com.ssafy.dto;

public class PlanUserDto {
	
	private int planNo;
	private String userId;
	
	public PlanUserDto(int planNo, String userId) {
		super();
		this.planNo = planNo;
		this.userId = userId;
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
