package com.ssafy.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.PlanCombinedDto;
import com.ssafy.dto.PlanDto;
import com.ssafy.dto.PlanUserDto;
import com.ssafy.service.PlanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/plan")
@CrossOrigin("*")
public class PlanController {

	private final PlanService planService;

	public PlanController(PlanService planService) {
		super();
		this.planService = planService;
	}
	
	@Operation(summary = "여행계획 추가", description = "여행계획추가")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@PostMapping(value="/addPlan")
	public ResponseEntity<?> addPlan(@RequestBody PlanCombinedDto planCombinedDto) {
	    try {
	    	for(PlanDto plan : planCombinedDto.getPlanList()) {
	    		planService.addPlan(plan);
	    	}
	    	
	    	for(PlanUserDto planUser : planCombinedDto.getPlanUserList()) {
	    		planService.addPlanUser(planUser);
	    	}
            return ResponseEntity.ok().body("완료");
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }
	
	@Operation(summary = "마지막 index 조회", description = "마지막 index + 1 return")
	@ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Page Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
	@GetMapping(value="/getLastId")
	public ResponseEntity<?> getLastId(){
		try {
			int id = planService.getLastIdx();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return ResponseEntity.ok().headers(headers).body(id);
			
		} catch (Exception e) {
            return exceptionHandling(e);
        }
	}
	
	@Operation(summary = "계획 조회", description = "계획번호에 맞는 모든 계획 조회")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Page Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    @GetMapping(value="/getPlan")
    public ResponseEntity<?> getPlan(@RequestParam(value="planNo") int planNo){
		try {
			List<PlanDto> list = planService.getPlan(planNo);
			if(list != null && !list.isEmpty()) {
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
	
	@Operation(summary = "유저로 계획 조회", description = "유저 id에 해당하는 모든 여행계획 조회")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Page Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    @GetMapping(value="/getPlanUser")
    public ResponseEntity<?> getPlanUser(@RequestParam(value="userId") String userId){
		try {
			List<PlanUserDto> list = planService.getPlanUser(userId);
			if(list != null && !list.isEmpty()) {
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
	
	@Operation(summary = "여행계획 삭제", description = "여행계획삭제")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@DeleteMapping(value="/deletePlan")
	public ResponseEntity<?> deletePlan(@RequestParam(value="planNo") int planNo) {
	    try {
	    	planService.deletePlan(planNo);
	    	planService.deletePlanUser(planNo);
            return ResponseEntity.ok().body("완료");
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
