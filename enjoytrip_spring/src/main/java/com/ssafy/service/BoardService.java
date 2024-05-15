package com.ssafy.service;

import com.ssafy.controller.PagedDto;
import com.ssafy.dto.BoardDto;

public interface BoardService {

	void writeArticle(BoardDto boardDto) throws Exception;
	PagedDto<BoardDto> listArticle(int pageNo, int articlesPerPage) throws Exception;
    PagedDto<BoardDto> searchArticle(int pageNo, int articlesPerPage, String searchType, String keyword) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void modifyArticle(BoardDto boardDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
}
