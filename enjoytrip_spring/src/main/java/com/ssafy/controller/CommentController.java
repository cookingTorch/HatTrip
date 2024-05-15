package com.ssafy.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

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

import com.ssafy.dto.CommentDto;
import com.ssafy.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "댓글 조회", description = "특정 게시글의 모든 댓글을 조회")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Page Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    @GetMapping(value="/list")
    public ResponseEntity<?> listComments(@RequestParam(value="boardNo") int boardNo) {
        try {
            List<CommentDto> comments = commentService.listComments(boardNo);
            if(comments != null && !comments.isEmpty()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
                return ResponseEntity.ok().headers(headers).body(comments);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "댓글 작성", description = "새로운 댓글을 추가")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    @PostMapping(value="/post")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        try {
            commentService.addComment(commentDto);
            return ResponseEntity.ok().body("댓글이 추가되었습니다.");
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "댓글 수정", description = "기존 댓글 수정")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    @PutMapping(value="/update")
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto) {
        try {
            commentService.updateComment(commentDto);
            return ResponseEntity.ok().body("댓글이 수정되었습니다.");
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "댓글 삭제", description = "댓글 삭제")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam(value="commentNo") int commentNo) {
        try {
            commentService.deleteComment(commentNo);
            return ResponseEntity.ok().body("댓글이 삭제되었습니다.");
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }
    
    private ResponseEntity<String> exceptionHandling(Exception e) {
    	e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
    }
}
