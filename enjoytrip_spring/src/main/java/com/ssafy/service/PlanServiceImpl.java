package com.ssafy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.dto.PlanDto;
import com.ssafy.dto.PlanUserDto;
import com.ssafy.mapper.PlanMapper;

@Service
public class PlanServiceImpl implements PlanService{
	
	private PlanMapper planMapper;

	public PlanServiceImpl(PlanMapper planMapper) {
		super();
		this.planMapper = planMapper;
	}

	@Override
	public void addPlan(PlanDto planDto) throws Exception {
		planMapper.addPlan(planDto);
	}

	@Override
	public void addPlanUser(PlanUserDto planUserDto) throws Exception {
		planMapper.addPlanUser(planUserDto);
	}

	@Override
	public List<PlanDto> getPlan(int planNo) throws Exception {
		return planMapper.getPlan(planNo);
	}

	@Override
	public List<PlanUserDto> getPlanUser(int planNo) throws Exception {
		return planMapper.getPlanUser(planNo);
	}

	@Override
	public void deletePlan(int planNo) throws Exception {
		planMapper.deletePlan(planNo);
	}

	@Override
	public void deletePlanUser(int planNo) throws Exception {
		planMapper.deletePlanUser(planNo);
	}

	@Override
	public int getLastIdx() throws Exception {
		return planMapper.getLastIdx();
	}

}
