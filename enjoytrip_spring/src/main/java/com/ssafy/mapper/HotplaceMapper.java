package com.ssafy.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.HotplaceDto;

@Mapper
public interface HotplaceMapper {
	
	int getSidoCode(String sidoName) throws SQLException;
	int getGugunCode(int sidoCode, String gugunName) throws SQLException;
	void registHotPlace(HotplaceDto hotplaceDto) throws SQLException;
	void updateImageSrc(HotplaceDto hotplaceDto) throws SQLException;
	List<HotplaceDto> listHotplaces(int offset, int placesPerPage) throws SQLException;
	int countHotplaces() throws SQLException;
	HotplaceDto getHotplace(int hotplaceId) throws SQLException;
	void deleteHotplace(int hotplaceId) throws SQLException;
	
}
