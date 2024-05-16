package com.ssafy.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.dto.HotplaceDto;
import com.ssafy.dto.PagedDto;
import com.ssafy.mapper.HotplaceMapper;
import com.ssafy.util.SidoCodeUtil;

@Service
public class HotplaceServiceImpl implements HotplaceService {
	
	private HotplaceMapper hotplaceMapper;
	private SidoCodeUtil sidoCodeUtil;
	
	public HotplaceServiceImpl(HotplaceMapper hotplaceMapper, SidoCodeUtil sidoCodeUtil) {
		super();
		this.hotplaceMapper = hotplaceMapper;
		this.sidoCodeUtil = sidoCodeUtil;
	}

	@Override
	public void registHotplace(HotplaceDto hotplaceDto) throws Exception {
		StringTokenizer st = new StringTokenizer(hotplaceDto.getAddr1());
		String sidoName = sidoCodeUtil.convert(st.nextToken().substring(0, 2));
		int sidoCode = hotplaceMapper.getSidoCode(sidoName);
		int gugunCode = hotplaceMapper.getGugunCode(sidoCode, st.nextToken());
		hotplaceDto.setSidoCode(sidoCode);
		hotplaceDto.setGugunCode(gugunCode);
		hotplaceMapper.registHotPlace(hotplaceDto);
	}

	@Override
	public void updateImageSrc(HotplaceDto hotplaceDto) throws Exception {
		hotplaceMapper.updateImageSrc(hotplaceDto);
	}

	@Override
	public void saveFiles(List<MultipartFile> files, String imageSrc) throws Exception {
		// 파일 저장 로직
		for (MultipartFile file : files) {
            if (!file.isEmpty()) {
            	continue;
            }
            
            String originalFilename = file.getOriginalFilename();
            String filePath = Paths.get(imageSrc, originalFilename).toString();

            // 파일을 디스크에 저장
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            byte[] bytes = file.getBytes();
            stream.write(bytes);
		}
	}

	@Override
	public PagedDto<HotplaceDto> listHotplaces(int pageNo, int placesPerPage) throws Exception {
        int offset = (pageNo - 1) * placesPerPage;
        List<HotplaceDto> content = hotplaceMapper.listHotplaces(offset, placesPerPage);
        int totalElements = hotplaceMapper.countHotplaces();
        return new PagedDto<>(content, pageNo, placesPerPage, totalElements);
	}

	@Override
	public HotplaceDto getHotplace(int hotplaceId) throws Exception {
		return hotplaceMapper.getHotplace(hotplaceId);
	}

	@Override
	public void deleteHotplace(int hotplaceId) throws Exception {
		hotplaceMapper.deleteHotplace(hotplaceId);
	}
	
}
