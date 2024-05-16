package com.ssafy.dto;

import java.util.List;

public class PlanCombinedDto {
	
	private List<PlanDto> planList;
	private List<PlanUserDto> planUserList;
	
	public PlanCombinedDto(List<PlanDto> planList, List<PlanUserDto> planUserList) {
		super();
		this.planList = planList;
		this.planUserList = planUserList;
	}
	public List<PlanDto> getPlanList() {
		return planList;
	}
	public void setPlanList(List<PlanDto> planList) {
		this.planList = planList;
	}
	public List<PlanUserDto> getPlanUserList() {
		return planUserList;
	}
	public void setPlanUserList(List<PlanUserDto> planUserList) {
		this.planUserList = planUserList;
	}
	
	
}
