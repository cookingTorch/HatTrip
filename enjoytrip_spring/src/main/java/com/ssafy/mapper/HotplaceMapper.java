package com.ssafy.mapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.ContentTypeDto;
import com.ssafy.dto.HotplaceDto;

@Mapper
public interface HotplaceMapper {
	
	int getSidoCode(String sidoName) throws SQLException;
	int getGugunCode(int sidoCode, String gugunName) throws SQLException;
	void registHotplace(HotplaceDto hotplaceDto) throws SQLException;
	void registDescription(int hotplaceId, String description) throws SQLException;
	void updateImageSrc(HotplaceDto hotplaceDto) throws SQLException;
	List<HotplaceDto> listHotplaces(int offset, int placesPerPage) throws SQLException;
	int countHotplaces() throws SQLException;
	HotplaceDto getHotplace(int hotplaceId) throws SQLException;
	void deleteHotplace(int hotplaceId) throws SQLException;
	String getContentType(int contentTypeId) throws SQLException;
	List<ContentTypeDto> listContentTypes() throws SQLException;
	List<HotplaceDto> searchHotplaces(int offset, int placesPerPage, String searchType, String keyword) throws SQLException;
	int countSearchedPlaces(String searchType, String keyword);
	List<Integer> searchContentTypeIds(String keyword) throws SQLException;
	List<HotplaceDto> searchHotplacesByTypeIds(int offset, int placesPerPage, List<Integer> contentTypeIds) throws SQLException;
	int countSearchedPlacesByTypeIds(List<Integer> contentTypeIds) throws IOException;
	String getImageSrcByHotplaceId(int hotplaceId) throws SQLException;
	
}
