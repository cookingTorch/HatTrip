package com.ssafy.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.AttractionDto;

public interface RecommandService {

	List<AttractionDto> getAttListByWord(String word) throws SQLException;

}
