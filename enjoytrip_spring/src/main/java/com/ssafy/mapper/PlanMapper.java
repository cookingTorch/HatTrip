package com.ssafy.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.PlanDto;
import com.ssafy.dto.PlanUserDto;

@Mapper
public interface PlanMapper {
	
	void addPlan(PlanDto planDto) throws SQLException;
	void addPlanUser(PlanUserDto planUserDto) throws SQLException;
	
	List<PlanDto> getPlan(int planNo) throws SQLException;
	List<PlanUserDto> getPlanUser(String userId) throws SQLException;
	
	void deletePlan(int planNo) throws SQLException;
	void deletePlanUser(int planNo) throws SQLException;

	int getLastIdx() throws SQLException;
}
