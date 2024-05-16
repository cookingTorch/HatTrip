package com.ssafy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.dto.HotplaceDto;
import com.ssafy.dto.PagedDto;

public interface HotplaceService {

	void registHotplace(HotplaceDto hotplaceDto) throws Exception;
	void updateImageSrc(HotplaceDto hotplaceDto) throws Exception;
	void saveFiles(List<MultipartFile> files, String imageSrc) throws Exception;
	PagedDto<HotplaceDto> listHotplaces(int pageNo, int placesPerPage) throws Exception;
	HotplaceDto getHotplace(int hotplaceId) throws Exception;
	void deleteHotplace(int hotplaceId) throws Exception;
	
}
