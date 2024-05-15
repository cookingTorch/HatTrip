package com.ssafy.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.AttractionDto;

@Mapper
public interface RecommandMapper {
	List<AttractionDto> getAttListByWord(String word) throws SQLException;
}
