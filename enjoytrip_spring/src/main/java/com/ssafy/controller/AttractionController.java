package com.ssafy.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.ssafy.dto.AttractionDto;
import com.ssafy.dto.GugunDto;
import com.ssafy.dto.PosDto;
import com.ssafy.dto.SearchDto;
import com.ssafy.dto.SidoDto;
import com.ssafy.service.AttractionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@RestController
@RequestMapping("/tour")
@CrossOrigin("*")
public class AttractionController {

	private AttractionService attractionService;

	public AttractionController(AttractionService attractionService) {
		this.attractionService = attractionService;
	}

	@Operation(summary = "검색", description = "검색")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@PostMapping(value="/list")
	public ResponseEntity<?> search(@RequestBody SearchDto searchDto){
		System.out.println(searchDto);
		try {
			List<AttractionDto> list = attractionService.getAttListByParams(searchDto);
			if(list!=null && !list.isEmpty()) {
            	HttpHeaders headers = new HttpHeaders();
            	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(list);
            } else {
            	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
        	return exceptionHandling(e);
        }
	}
		
	@Operation(summary = "시도목록", description = "시도 목록 반환.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@GetMapping(value = "/getSido")
    public ResponseEntity<?> getSido() {
        try {
            List<SidoDto> sidoList = attractionService.getSido();
            HttpHeaders headers = new HttpHeaders();
        	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(sidoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving sido list: " + e.getMessage());
        }
    }
	
	@PostMapping("/getGugun")
    public ResponseEntity<?> getGugun(@RequestParam(value= "sido") int sido) {
        try {
            List<GugunDto> gugunList = attractionService.getGugun(sido);
            HttpHeaders headers = new HttpHeaders();
        	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(gugunList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving gugun list: " + e.getMessage());
        }
    }
	
	@Operation(summary = "경로탐색", description = "ex) 125408 125504")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@PostMapping(value="/searchRoute")
	public ResponseEntity<?> searchRoute(@RequestParam(value= "startPlaceId") int startPlaceId, @RequestParam(value= "endPlaceId")int endPlaceId) {
		
	    try {
	        List<AttractionDto> list = attractionService.getAdjListByRoute(startPlaceId, endPlaceId);
	        HttpHeaders headers = new HttpHeaders();
        	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(list);
	    } catch (Exception e) {
	    	return exceptionHandling(e);
	    }
	}
	
	@Operation(summary = "범위 내 탐색", description = "네 꼭짓점 안에 있는거 반환")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@PostMapping(value="/searchByPos")
	public ResponseEntity<?> searchByPos(@RequestBody PosDto posDto) {
		
	    try {
	        List<AttractionDto> list = attractionService.getAttListByPos(posDto);
	        HttpHeaders headers = new HttpHeaders();
        	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(list);
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
