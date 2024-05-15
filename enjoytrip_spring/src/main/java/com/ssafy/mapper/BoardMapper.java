package com.ssafy.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	void writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle(int offset, int articlesPerPage) throws SQLException;
	List<BoardDto> searchArticle(int offset, int articlesPerPage, String searchType, String keyword) throws SQLException;
	int countArticles();
	int countSearchedArticles(String searchType, String keyword);
	BoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	void modifyArticle(BoardDto boardDto) throws SQLException;
	void deleteArticle(int articleNo) throws SQLException;
	
}
 