package com.ssafy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

import com.ssafy.dto.AttractionDto;
import com.ssafy.dto.GugunDto;
import com.ssafy.dto.PosDto;
// import com.ssafy.dto.HotplaceDto;
// import com.ssafy.dto.PlanDto;
import com.ssafy.dto.SearchDto;
import com.ssafy.dto.SidoDto;

@Mapper
public interface AttractionMapper {

	List<AttractionDto> getAttListByParams(SearchDto searchDto) throws SQLException;

	List<SidoDto> getSido() throws SQLException;

	List<GugunDto> getGugun(int sido_code) throws SQLException;

//	void save(PlanDto planDto) throws SQLException;

//	PlanDto getPlan(String userid) throws SQLException;

//	void regist(HotplaceDto hotplaceDto) throws SQLException;

//	HotplaceDto getPlace(String userid) throws SQLException;
	
	AttractionDto getAttById(int id) throws SQLException;
	
	List<AttractionDto> getAdjListByRoute(int startPlaceId, int endPlaceId) throws SQLException;
	
	List<AttractionDto> getAttListByPos(PosDto posDto) throws SQLException;
}
