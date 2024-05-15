package com.ssafy.service;

import com.ssafy.dto.MemberDto;

public interface MemberService {
	
	void joinMember(MemberDto memberDto) throws Exception;
	void modifyMember(MemberDto memberDto)throws Exception;
	void deleteMember(String userid) throws Exception;
	MemberDto login(MemberDto memberDto) throws Exception;
	MemberDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	String getUserIdByRefreshToken(String refreshToken) throws Exception;
	//MemberDto searchByUserId(String userId) throws Exception;
	
}
