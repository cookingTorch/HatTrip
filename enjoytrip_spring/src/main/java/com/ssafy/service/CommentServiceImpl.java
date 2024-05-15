package com.ssafy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.dto.CommentDto;
import com.ssafy.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	
	private CommentMapper commentMapper;
	
	CommentServiceImpl(CommentMapper commentMapper) {
		super();
		this.commentMapper = commentMapper;
	}

	@Override
	public void addComment(CommentDto commentDto) throws Exception {
		commentMapper.addComment(commentDto);
	}

	@Override
	public List<CommentDto> listComments(int boardNo) throws Exception {
		return commentMapper.listComments(boardNo);
	}

	@Override
	public void deleteComment(int commentNo) throws Exception {
		commentMapper.deleteComment(commentNo);
	}

	@Override
	public void updateComment(CommentDto commentDto) throws Exception {
		commentMapper.updateComment(commentDto);
	}
	
}
