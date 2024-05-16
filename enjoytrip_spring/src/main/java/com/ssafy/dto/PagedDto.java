package com.ssafy.dto;

import java.util.List;

public class PagedDto<T> {
	private List<T> content;
    private int pageNo;
    private int articlesPerPage;
    private int totalElements;
    private int totalPages;

    public PagedDto(List<T> content, int pageNo, int articlesPerPage, int totalElements) {
        this.content = content;
        this.pageNo = pageNo;
        this.articlesPerPage = articlesPerPage;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / articlesPerPage);
    }
    
    public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getArticlesPerPage() {
		return articlesPerPage;
	}

	public void setArticlesPerPage(int articlesPerPage) {
		this.articlesPerPage = articlesPerPage;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}