package com.ssafy.dto;

public class CommentDto {
	
	private int commentNo;
	private int boardNo;
	private String userId;
	private String content;
	private String createAt;
	
	public CommentDto() {}
	
	public CommentDto(int boardNo, String userId, String content, String createAt) {
		super();
		this.boardNo = boardNo;
		this.userId = userId;
		this.content = content;
		this.createAt = createAt;
	}
	
	public CommentDto(int commentNo, int boardNo, String userId, String content, String createAt) {
		super();
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.userId = userId;
		this.content = content;
		this.createAt = createAt;
	}
	
	public CommentDto(int commentNo, int boardNo, String userId, String content) {
		super();
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.userId = userId;
		this.content = content;
	}

	public int getCommentNo() {
		return commentNo;
	}
	
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
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
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreateAt() {
		return createAt;
	}
	
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	
	@Override
	public String toString() {
		return "CommentDto [commentNo=" + commentNo + ", boardNo=" + boardNo + ", userId=" + userId + ", content="
				+ content + ", createAt=" + createAt + "]";
	}
	
}
