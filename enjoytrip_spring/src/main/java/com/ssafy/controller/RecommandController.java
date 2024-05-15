package com.ssafy.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.AttractionDto;
import com.ssafy.service.RecommandService;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@RestController
@RequestMapping("/recommand")
@CrossOrigin("*")
public class RecommandController {

    private RecommandService recommandService;
   
    public RecommandController(RecommandService recommandService) {
    	this.recommandService = recommandService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("word") String word) {
        try {
            List<AttractionDto> list = recommandService.getAttListByWord(word);
            HttpHeaders headers = new HttpHeaders();
        	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while processing the request.");
        }
    }
}