package com.ssafy.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.BoardDto;
import com.ssafy.dto.PagedDto;
import com.ssafy.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@RestController
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {
	
	@Value("${file.path}")
	private String uploadPath;
	
	@Value("${file.path.upload-images}")
	private String uploadImagePath;
	
	@Value("${file.path.upload-files}")
	private String uploadFilePath;

	private final BoardService boardService;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	@Operation(summary = "게시판 목록", description = "게시판 전체 목록 반환.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@GetMapping(value="/list")
    public ResponseEntity<?> list(
    	@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
    	@RequestParam(value = "articlesPerPage", defaultValue = "10") int articlesPerPage) {
		
        try {
            PagedDto<BoardDto> result = boardService.listArticle(pageNo, articlesPerPage);
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
	
	@Operation(summary = "게시판 검색", description = "게시판 키워드 검색")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@GetMapping(value="/search")
	public ResponseEntity<?> search(
	    	@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
	    	@RequestParam(value = "articlesPerPage", defaultValue = "10") int articlesPerPage,
			@RequestParam(value = "searchType") String searchType,
		    @RequestParam(value = "keyword") String keyword) {
	    try {
	    	keyword = URLDecoder.decode(keyword, "UTF-8");
	        PagedDto<BoardDto> result = boardService.searchArticle(pageNo, articlesPerPage, searchType, keyword);
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
	
	@Operation(summary = "게시글 조회", description = "게시글 번호를 받아 해당 게시글을 조회")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "404", description = "Page Not Found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@GetMapping(value="/view")
	public ResponseEntity<?> view(@RequestParam(value="articleno") int articleno) {
	    
	    try {
	        BoardDto article = boardService.getArticle(articleno);
	        if(article != null) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
	            return ResponseEntity.ok().headers(headers).body(article);
	        } else {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        }
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	@Operation(summary = "게시글 작성", description = "새로운 게시글을 작성")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@PostMapping(value="/write")
	public ResponseEntity<?> write(@RequestBody BoardDto boardDto) {
	    
	    try {
	    	System.out.println(boardDto);
	    	boardService.writeArticle(boardDto);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
	        return ResponseEntity.ok().headers(headers).body(boardDto); 
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	@Operation(summary = "수정 페이지로 이동", description = "articleno의 수정 페이지로 이동")
	@GetMapping("/mvmodify")
	public ResponseEntity<?> redirectToModify(@RequestParam(value="articleno") int articleNo) {
			try {
		        BoardDto article = boardService.getArticle(articleNo);
		        if(article != null) {
		            HttpHeaders headers = new HttpHeaders();
		            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
		            return ResponseEntity.ok().headers(headers).body(article);
		        } else {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		        }
		    } catch (Exception e) {
		        return exceptionHandling(e);
		    }
	    }
	
	@Operation(summary = "게시글 수정", description = "게시글 수정")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@PutMapping(value="/modify")
	public ResponseEntity<?> modify(@RequestBody BoardDto boardDto) {
	    
	    try {
	    	boardService.modifyArticle(boardDto);
	        BoardDto article = boardService.getArticle(boardDto.getBoardNo());
	        if(article != null) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
	            return ResponseEntity.ok().headers(headers).body(article);
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	@Operation(summary = "게시글 삭제", description = "게시글 삭제")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value="articleNo") int articleNo) {
        try {
            // 여기에 articleNo를 사용하여 해당 게시물을 삭제하는 로직을 구현합니다.
            boardService.deleteArticle(articleNo);
            return ResponseEntity.ok("게시물이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 삭제에 실패했습니다.");
        }
    }
    
    private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
//		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
