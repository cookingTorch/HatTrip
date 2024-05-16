package com.ssafy.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
	
	@Value("${file.path}")
	private String uploadPath;
	
	@Value("${file.path.upload-images}")
	private String uploadImagePath;
	
	@Value("${file.path.upload-files}")
	private String uploadFilePath;
	
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
			@RequestParam("images") List<MultipartFile> files) {
		
		try {
			HotplaceDto hotplaceDto = new HotplaceDto(userId, contentTypeId, title, addr1, tel, latitude, longitude);
			hotplaceService.registHotplace(hotplaceDto);
			
			String imageSrc = uploadImagePath + "/" + hotplaceDto.getHotplaceId();
		    // File 객체 생성
		    File directory = new File(imageSrc);

		    // 해당 경로에 디렉토리가 존재하는지 확인하고, 없으면 생성
		    if (!directory.exists()) {
		        if (directory.mkdirs()) {
		            System.out.println("Directory created successfully.");
		        } else {
		            System.out.println("Failed to create directory.");
		        }
		    }
		    
		    hotplaceDto.setImageSrc(imageSrc);
		    hotplaceService.updateImageSrc(hotplaceDto);
		    
		    try {
		    	hotplaceService.saveFiles(files, imageSrc);
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
    	@RequestParam(value = "placesPerPage", defaultValue = "10") int placesPerPage) {
		
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
	
    private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
//		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}
}
