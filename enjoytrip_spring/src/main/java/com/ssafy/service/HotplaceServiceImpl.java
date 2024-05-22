package com.ssafy.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.dto.ContentTypeDto;
import com.ssafy.dto.HotplaceDto;
import com.ssafy.dto.PagedDto;
import com.ssafy.mapper.HotplaceMapper;
import com.ssafy.util.SidoCodeUtil;

@Service
public class HotplaceServiceImpl implements HotplaceService {
	
	@Value("${file.path.upload-images}")
	private String uploadImagePath;
	
	private HotplaceMapper hotplaceMapper;
	private SidoCodeUtil sidoCodeUtil;
	
	public HotplaceServiceImpl(HotplaceMapper hotplaceMapper, SidoCodeUtil sidoCodeUtil) {
		super();
		this.hotplaceMapper = hotplaceMapper;
		this.sidoCodeUtil = sidoCodeUtil;
	}

	@Override
	public void registHotplace(HotplaceDto hotplaceDto, String description) throws Exception {
		StringTokenizer st = new StringTokenizer(hotplaceDto.getAddr1());
		String sidoName = sidoCodeUtil.convert(st.nextToken());
		int sidoCode = hotplaceMapper.getSidoCode(sidoName);
		int gugunCode = hotplaceMapper.getGugunCode(sidoCode, st.nextToken());
		hotplaceDto.setSidoCode(sidoCode);
		hotplaceDto.setGugunCode(gugunCode);
		hotplaceMapper.registHotplace(hotplaceDto);
		hotplaceMapper.registDescription(hotplaceDto.getHotplaceId(), description);
	}

	@Override
	public void updateImageSrc(HotplaceDto hotplaceDto) throws Exception {
		hotplaceMapper.updateImageSrc(hotplaceDto);
	}

	@Override
	public void saveFiles(List<MultipartFile> files, String imageSrc) throws Exception {
		
		// 파일 저장 로직
		for (MultipartFile file : files) {
            if (file.isEmpty()) {
            	continue;
            }
            
            String filename = file.getOriginalFilename();
            String filePath = Paths.get(imageSrc, filename).toString();

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
        for (HotplaceDto hotplaceDto : content) {
        	hotplaceDto.setContentType(hotplaceMapper.getContentType(hotplaceDto.getContentTypeId()));
        	hotplaceDto.setDescription(hotplaceMapper.getDescriptionByHotplaceId(hotplaceDto.getHotplaceId()));
        }
        int totalElements = hotplaceMapper.countHotplaces();
        return new PagedDto<>(content, pageNo, placesPerPage, totalElements);
	}

	@Override
	public HotplaceDto getHotplace(int hotplaceId) throws Exception {
		HotplaceDto hotplaceDto;
		hotplaceDto = hotplaceMapper.getHotplace(hotplaceId);
		hotplaceDto.setContentType(hotplaceMapper.getContentType(hotplaceDto.getContentTypeId()));
		hotplaceDto.setDescription(hotplaceMapper.getDescriptionByHotplaceId(hotplaceId));
		return hotplaceDto;
	}

	@Override
	public void deleteHotplace(int hotplaceId) throws Exception {
		hotplaceMapper.deleteHotplace(hotplaceId);
	}

	@Override
	public List<ContentTypeDto> listContentTypes() throws Exception {
		return hotplaceMapper.listContentTypes();
	}

	@Override
	public PagedDto<HotplaceDto> searchHotplaces(int pageNo, int placesPerPage, String searchType, String keyword) throws Exception {
	    if (keyword.isBlank()) {
	        return listHotplaces(pageNo, placesPerPage);
	    }
	    System.out.println(searchType);
		System.out.println(keyword);
		int offset = (pageNo - 1) * placesPerPage;
		List<HotplaceDto> content;
		int totalElements;
	    if (searchType.equals("content_type")) {
	    	List<Integer> contentTypeIds = hotplaceMapper.searchContentTypeIds(keyword);
	    	content = hotplaceMapper.searchHotplacesByTypeIds(offset, placesPerPage, contentTypeIds);
	    	totalElements = hotplaceMapper.countSearchedPlacesByTypeIds(contentTypeIds);
	    } else {
	    	content = hotplaceMapper.searchHotplaces(offset, placesPerPage, searchType, keyword);
	    	totalElements = hotplaceMapper.countSearchedPlaces(searchType, keyword);
	    }
        for (HotplaceDto hotplaceDto : content) {
        	hotplaceDto.setContentType(hotplaceMapper.getContentType(hotplaceDto.getContentTypeId()));
        }
	    return new PagedDto<>(content, pageNo, placesPerPage, totalElements);
	}

	@Override
	public String getThumbNail(int hotplaceId) throws Exception {
		String imageSrc = hotplaceMapper.getImageSrcByHotplaceId(hotplaceId);
        // 폴더 경로 생성
        Path path = Paths.get(uploadImagePath, imageSrc);
        // 폴더 안의 파일 중 하나를 꺼내오기
        Optional<Path> pickedFile = pickFile(path);
        if (pickedFile.isPresent()) {
            return imageSrc + pickedFile.get().getFileName().toString();
        } else {
            return null;
        }
	}

	@Override
	public List<String> listHotplaceImages(int hotplaceId) throws Exception {
		String imageSrc = hotplaceMapper.getImageSrcByHotplaceId(hotplaceId);
		// 폴더 경로 생성
        Path path = Paths.get(uploadImagePath, imageSrc);
        // 폴더 안의 모든 파일 이름을 생성 시간 순으로 String 리스트로 가져오기
        List<String> images = getFilesSortedByCreationTime(path, imageSrc);
        return images;
	}
	
    private static Optional<Path> pickFile(Path directoryPath) throws IOException {
        try (Stream<Path> files = Files.list(directoryPath)) {
            return files.filter(Files::isRegularFile)
                        .min(Comparator.comparingLong(f -> getFileCreationTime(f)));
        }
    }
	
    private static long getFileCreationTime(Path file) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
            return attrs.creationTime().toMillis();
        } catch (IOException e) {
            e.printStackTrace();
            return Long.MAX_VALUE; // Error handling: assign the maximum value to push it to the end of the sort order
        }
    }
	
    private static List<String> getFilesSortedByCreationTime(Path directoryPath, String imageSrc) {
        try (Stream<Path> stream = Files.list(directoryPath)) {
            return stream
                .filter(Files::isRegularFile) // 디렉터리는 제외하고 정규 파일만 처리
                .sorted(Comparator.comparingLong(path -> getFileCreationTime(path))) // 생성 시간으로 정렬
                .map(path -> imageSrc + path.getFileName().toString()) // 파일 이름만 추출
                .collect(Collectors.toList()); // 리스트로 수집
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // 에러가 발생했을 경우 빈 리스트 반환
        }
    }

	@Override
	public void addLike(String userId, int hotplaceId) throws Exception {
		hotplaceMapper.addLike(userId, hotplaceId);
	}

	@Override
	public void deleteLike(String userId, int hotplaceId) throws Exception {
		hotplaceMapper.deleteLike(userId, hotplaceId);
	}
}
