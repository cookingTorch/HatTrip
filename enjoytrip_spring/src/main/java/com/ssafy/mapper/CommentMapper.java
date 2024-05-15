package com.ssafy.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.CommentDto;

public interface CommentMapper {
	void addComment(CommentDto commentDto) throws SQLException;
	List<CommentDto> listComments(int boardNo) throws SQLException;
	void deleteComment(int commentNo) throws SQLException;
	void updateComment(CommentDto commentDto) throws SQLException;
}
