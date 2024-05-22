package com.ssafy.service;

import com.ssafy.dto.AttractionDto;
import com.ssafy.dto.PlanDto;
import com.ssafy.dto.PlanUserDto;
import java.util.List;

public interface PlanService {

	void addPlan(PlanDto planDto) throws Exception;
	void addPlanUser(PlanUserDto planUserDto) throws Exception;
	
	List<AttractionDto> getPlan(int planNo) throws Exception;
	List<PlanUserDto> getPlanUser(String userId) throws Exception;
	
	void deletePlan(int planNo) throws Exception;
	void deletePlanUser(int planNo) throws Exception;
	
	int getLastIdx() throws Exception;
}
