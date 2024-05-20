package com.ssafy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.dto.ContentTypeDto;
import com.ssafy.dto.HotplaceDto;
import com.ssafy.dto.PagedDto;

public interface HotplaceService {

	void registHotplace(HotplaceDto hotplaceDto, String description) throws Exception;
	void updateImageSrc(HotplaceDto hotplaceDto) throws Exception;
	void saveFiles(List<MultipartFile> files, String imageSrc) throws Exception;
	PagedDto<HotplaceDto> listHotplaces(int pageNo, int placesPerPage) throws Exception;
	HotplaceDto getHotplace(int hotplaceId) throws Exception;
	void deleteHotplace(int hotplaceId) throws Exception;
	List<ContentTypeDto> listContentTypes() throws Exception;
	PagedDto<HotplaceDto> searchHotplaces(int pageNo, int placesPerPage, String searchType, String keyword) throws Exception;
	String getThumbNail(int hotplaceId) throws Exception;
	List<String> listHotplaceImages(int hotplaceId) throws Exception;
	
}
