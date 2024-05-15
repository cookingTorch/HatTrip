package com.ssafy.dto;

public class HotplaceDto {

	private String place_name;
	private String userId;
	private int contentId;
	private String content;
	private String date;
	
	public HotplaceDto() {}

	public HotplaceDto(String place_name, String userId, int contentId, String content, String date) {
		super();
		this.place_name = place_name;
		this.userId = userId;
		this.contentId = contentId;
		this.content = content;
		this.date = date;
	}

	public String getPlace_name() {
		return place_name;
	}

	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
