package com.ssafy.dto;

public class ContentTypeDto {
	
	int contentTypeId;
	String ContentType;
	
	public ContentTypeDto(int contentTypeId, String contentType) {
		super();
		this.contentTypeId = contentTypeId;
		ContentType = contentType;
	}

	public int getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public String getContentType() {
		return ContentType;
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	
}
