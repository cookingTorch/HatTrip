package com.ssafy.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;


import com.ssafy.dto.AttractionDto;
import com.ssafy.mapper.RecommandMapper;

@Service
public class RecommandServiceImpl implements RecommandService {
	
	private RecommandMapper recommandMapper;
	
	private RecommandServiceImpl(RecommandMapper recommandMapper) {
		super();
		this.recommandMapper = recommandMapper;
	}

	@Override
	public List<AttractionDto> getAttListByWord(String word) throws SQLException {
		return recommandMapper.getAttListByWord(word);
	}
}
