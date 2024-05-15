package com.ssafy.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.dto.AttractionDto;
import com.ssafy.dto.GugunDto;
import com.ssafy.dto.PosDto;
// import com.ssafy.dto.HotplaceDto;
// import com.ssafy.dto.PlanDto;
import com.ssafy.dto.SearchDto;
import com.ssafy.dto.SidoDto;
import com.ssafy.mapper.AttractionMapper;
import com.ssafy.util.KNearest;

@Service 
public class AttractionServiceImpl implements AttractionService{
	
	private AttractionMapper attractionMapper;
	
	private AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}
	
	public List<AttractionDto> getAttListByParams(SearchDto searchDto) throws Exception{
		return attractionMapper.getAttListByParams(searchDto);
	}

	@Override
	public List<SidoDto> getSido() throws SQLException {
		return attractionMapper.getSido();
	}

	@Override
	public List<GugunDto> getGugun(int sido_code) throws SQLException {
		return attractionMapper.getGugun(sido_code);
	}

//	@Override
//	public void save(PlanDto planDto) throws SQLException {
//		attractionMapper.save(planDto);
//		
//	}
//
//	@Override
//	public PlanDto getPlan(String userid) throws SQLException {
//		return attractionMapper.getPlan(userid);
//	}
//
//	@Override
//	public void regist(HotplaceDto hotplaceDto) throws SQLException {
//		attractionMapper.regist(hotplaceDto);
//		
//	}
//
//	@Override
//	public HotplaceDto getPlace(String userid) throws SQLException {
//		return attractionMapper.getPlace(userid);
//	}

	@Override
	public List<AttractionDto> getAdjListByRoute(int startPlaceId, int endPlaceId) throws SQLException {
		List<AttractionDto> list = attractionMapper.getAdjListByRoute(startPlaceId, endPlaceId);
		AttractionDto end = attractionMapper.getAttById(endPlaceId);
		AttractionDto start = attractionMapper.getAttById(startPlaceId);
		
		if(list!=null) {
			return KNearest.getKNearest(start, end, list, 50);
		}
		else
			return list;
	}

	@Override
	public List<AttractionDto> getAttListByPos(PosDto posDto) throws SQLException {
		return attractionMapper.getAttListByPos(posDto);
	}
}
