package com.ssafy.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.MemberDto;

@Mapper
public interface MemberMapper {
	
	void joinMember(MemberDto memberDto) throws SQLException;
	void modifyMember(MemberDto memberDto) throws SQLException;
	void deleteMember(String userId) throws SQLException;
	MemberDto login(MemberDto memberDto) throws SQLException;
	MemberDto userInfo(String userId) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userId) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	String getUserIdByRefreshToken(String refreshToken) throws SQLException;

}
