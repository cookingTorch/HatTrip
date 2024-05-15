package com.ssafy.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "MemberDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class MemberDto {
	
	@Schema(description = "아이디")
	private String userId;
	@Schema(description = "비밀번호")
	private String userPwd;
	@Schema(description = "이름")
	private String userName;
	@Schema(description = "이메일")
	private String email;
	@Schema(description = "가입일")
	private String joinDate;
	@Schema(description = "refreshToken")
	private String refreshToken;
	
	public MemberDto() {}
	
	public MemberDto(String userId, String userName, String userPwd, String email) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "MemberDto [userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName + ", email=" + email
				+ ", joinDate=" + joinDate + ", refreshToken=" + refreshToken + "]";
	}
	
}
