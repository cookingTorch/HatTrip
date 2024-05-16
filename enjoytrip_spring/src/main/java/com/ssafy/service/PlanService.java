package com.ssafy.service;

import com.ssafy.dto.PlanDto;
import com.ssafy.dto.PlanUserDto;
import java.util.List;

public interface PlanService {

	void addPlan(PlanDto planDto) throws Exception;
	void addPlanUser(PlanUserDto planUserDto) throws Exception;
	
	List<PlanDto> getPlan(int planNo) throws Exception;
	List<PlanUserDto> getPlanUser(int planNo) throws Exception;
	
	void deletePlan(int planNo) throws Exception;
	void deletePlanUser(int planNo) throws Exception;
	
	int getLastIdx() throws Exception;
}
