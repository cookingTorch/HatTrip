package com.ssafy.dto;

public class BoardDto {

	private int boardNo;
	private String userId;
	private String title;
	private String content;
	private int hits;
	private String createAt;
	
	public BoardDto() {}

	public BoardDto(int boardNo, String userId, String title, String content, int hits, String createAt) {
		super();
		this.boardNo = boardNo;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.hits = hits;
		this.createAt = createAt;
	}

	public BoardDto(String userId, String title, String content, int hits, String createAt) {
		super();
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.hits = hits;
		this.createAt = createAt;
	}

	public BoardDto(String userId, String title, String content, int hits) {
		super();
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.hits = hits;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "BoardDto [boardNo=" + boardNo + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", hits=" + hits + ", createAt=" + createAt + "]";
	}
	
}
