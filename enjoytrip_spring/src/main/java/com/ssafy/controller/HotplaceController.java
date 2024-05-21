package com.ssafy.controller;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.dto.ContentTypeDto;
import com.ssafy.dto.HotplaceDto;
import com.ssafy.dto.PagedDto;
import com.ssafy.service.HotplaceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/hotplace")
@CrossOrigin("*")
public class HotplaceController {
	
	@Value("${file.path.upload-images}")
	private String uploadImagePath;
	
	@Value("${file.path.access-images}")
	private String accessImagePath;
	
	private final HotplaceService hotplaceService;
	
	public HotplaceController(HotplaceService hotplaceService) {
		super();
		this.hotplaceService = hotplaceService;
	}
	
	@Operation(summary = "핫플레이스 등록", description = "새로운 핫플레이스 등록.")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "404", description = "Page Not Found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@PostMapping(value="/regist")
	public ResponseEntity<?> regist(@RequestParam(value="userId") String userId,
			@RequestParam(value="contentTypeId") int contentTypeId,
			@RequestParam(value="title") String title,
			@RequestParam(value="addr") String addr1,
			@RequestParam(value="tel") String tel,
			@RequestParam(value="latitude") double latitude,
			@RequestParam(value="longitude") double longitude,
			@RequestParam(value="description") String description,
			@RequestParam(value = "images", required = false) Optional<List<MultipartFile>> optionalFiles) {
		
		try {
			System.out.println(userId);
			System.out.println(contentTypeId);
			System.out.println(title);
			System.out.println(addr1);
			System.out.println(tel);
			System.out.println(latitude);
			System.out.println(longitude);
			System.out.println(description);
			HotplaceDto hotplaceDto = new HotplaceDto(userId, contentTypeId, title, addr1, tel, latitude, longitude);
			hotplaceService.registHotplace(hotplaceDto, description);
			
			String imageSrc = "/hotplace/" + hotplaceDto.getHotplaceId() + "/";
		    String localPath = uploadImagePath + imageSrc;
			// File 객체 생성
		    File directory = new File(localPath);

		    // 해당 경로에 디렉토리가 존재하는지 확인하고, 없으면 생성
		    if (!directory.exists()) {
		        if (directory.mkdirs()) {
		            System.out.println("Directory created successfully.");
		        } else {
		            System.out.println("Failed to create directory.");
		        }
		    }
		    
		    List<MultipartFile> files;
	        if (optionalFiles.isPresent() && !optionalFiles.get().isEmpty()) {
	            files = optionalFiles.get();
	        } else {
	            files = new ArrayList<>();
	        }
		    
		    hotplaceDto.setImageSrc(imageSrc);
		    hotplaceService.updateImageSrc(hotplaceDto);
		    
		    try {
		    	hotplaceService.saveFiles(files, localPath);
		    } catch (Exception e) {
		    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
		    }
		    
			HttpHeaders headers = new HttpHeaders();
        	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(hotplaceDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "핫플레이스 목록", description = "핫플레이스 전체 목록 반환.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@GetMapping(value="/list")
    public ResponseEntity<?> list(
    	@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
    	@RequestParam(value = "placesPerPage", defaultValue = "3") int placesPerPage) {
		
        try {
            PagedDto<HotplaceDto> result = hotplaceService.listHotplaces(pageNo, placesPerPage);
            if(result != null) {
            	HttpHeaders headers = new HttpHeaders();
            	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(result);
            } else {
            	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
        	return exceptionHandling(e);
        }
    }
	
	@Operation(summary = "핫플레이스 조회", description = "핫플레이스 번호를 받아 해당 핫플레이스를 조회")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "404", description = "Page Not Found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@GetMapping(value="/view")
	public ResponseEntity<?> view(@RequestParam(value="hotplaceId") int hotplaceId) {
	    
	    try {
	        HotplaceDto hotplaceDto = hotplaceService.getHotplace(hotplaceId);
	        if(hotplaceDto != null) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
	            return ResponseEntity.ok().headers(headers).body(hotplaceDto);
	        } else {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        }
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	@Operation(summary = "핫플레이스 삭제", description = "핫플레이스 삭제")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value="hotplaceId") int hotplaceId) {
        try {
            // 여기에 articleNo를 사용하여 해당 게시물을 삭제하는 로직을 구현합니다.
            hotplaceService.deleteHotplace(hotplaceId);
            return ResponseEntity.ok("핫플레이스가 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("핫플레이스 삭제에 실패했습니다.");
        }
    }
	
	@Operation(summary = "핫플레이스 검색", description = "핫플레이스 키워드 검색")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@GetMapping(value="/search")
	public ResponseEntity<?> search(
	    	@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
	    	@RequestParam(value = "placesPerPage", defaultValue = "3") int placesPerPage,
			@RequestParam(value = "searchType") String searchType,
		    @RequestParam(value = "keyword") String keyword) {
	    try {
	    	keyword = URLDecoder.decode(keyword, "UTF-8");
	        PagedDto<HotplaceDto> result = hotplaceService.searchHotplaces(pageNo, placesPerPage, searchType, keyword);
	        if(result!=null && !result.getContent().isEmpty()) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
	            return ResponseEntity.ok().headers(headers).body(result);
	        } else {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        }
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}
	
	@Operation(summary = "관광지 유형", description = "관광지 유형 목록")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@GetMapping("/type")
    public ResponseEntity<?> type() {
		
		try {
            List<ContentTypeDto> result = hotplaceService.listContentTypes();
            if(result != null) {
            	HttpHeaders headers = new HttpHeaders();
            	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(result);
            } else {
            	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
        	return exceptionHandling(e);
        }
		
    }
	
	@Operation(summary = "핫플레이스 썸네일", description = "핫플레이스 첫 번째 이미지")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@GetMapping("/thumbnail")
    public ResponseEntity<?> thumbnail(@RequestParam(value = "hotplaceId") int hotplaceId) {
		
		try {
            String result = hotplaceService.getThumbNail(hotplaceId);
            if(result != null) {
            	result = accessImagePath + result;
            	HttpHeaders headers = new HttpHeaders();
            	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(result);
            } else {
            	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
        	return exceptionHandling(e);
        }
		
    }
	
	@Operation(summary = "핫플레이스 이미지", description = "핫플레이스 이미지 목록")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@GetMapping("/image")
    public ResponseEntity<?> image(@RequestParam(value = "hotplaceId") int hotplaceId) {
		
		try {
            List<String> images = hotplaceService.listHotplaceImages(hotplaceId);
            if(images != null) {
            	List<String> result = new ArrayList<>();
            	for (String image : images) {
            		if (image != null) {
            			result.add(accessImagePath + image);
            		}
            	}
            	HttpHeaders headers = new HttpHeaders();
            	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(result);
            } else {
            	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
        	return exceptionHandling(e);
        }
		
    }
	
    private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
//		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}
}
