package com.ssafy.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.AttractionDto;
import com.ssafy.dto.GugunDto;
import com.ssafy.dto.PosDto;
// import com.ssafy.dto.HotplaceDto;
// import com.ssafy.dto.PlanDto;
import com.ssafy.dto.SearchDto;
import com.ssafy.dto.SidoDto;
// import com.ssafy.util.KNearest;

public interface AttractionService {
	public List<AttractionDto> getAttListByParams(SearchDto searchDto) throws Exception;

	public List<SidoDto> getSido() throws SQLException;

	public List<GugunDto> getGugun(int sido_code) throws SQLException;

//	public void save(PlanDto planDto) throws SQLException;
//
//	public PlanDto getPlan(String userid) throws SQLException;
//
//	public void regist(HotplaceDto hotplaceDto) throws SQLException;
//
//	public HotplaceDto getPlace(String userid) throws SQLException;
	
	public List<AttractionDto> getAdjListByRoute(int startPlaceId, int endPlaceId) throws SQLException;
	
	public List<AttractionDto> getAttListByPos(PosDto posDto) throws SQLException;
}
