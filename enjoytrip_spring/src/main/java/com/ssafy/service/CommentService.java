package com.ssafy.service;

import java.util.List;

import com.ssafy.dto.CommentDto;

public interface CommentService {

	void addComment(CommentDto commentDto) throws Exception;
	List<CommentDto> listComments(int boardNo) throws Exception;
	void deleteComment(int commentNo) throws Exception;
	void updateComment(CommentDto commentDto) throws Exception;
}
