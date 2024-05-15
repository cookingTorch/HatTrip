package com.ssafy.dto;

public class SearchDto {

	private int sido;
	private int gugun;
	private int type;
	
	public int getSido() {
		return sido;
	}
	
	public void setSido(int sido) {
		this.sido = sido;
	}
	
	public int getGugun() {
		return gugun;
	}
	
	public void setGugun(int gugun) {
		this.gugun = gugun;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "SearchDto [sido=" + sido + ", gugun=" + gugun + ", type=" + type + "]";
	}
	
	
}
