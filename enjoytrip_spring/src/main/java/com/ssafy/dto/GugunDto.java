package com.ssafy.dto;

public class GugunDto {
	private int code;
	private String name;
	
	public GugunDto() {

	}
	public GugunDto(int code, String name) {
		this.code = code;
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "SidoDto [code=" + code + ", name=" + name + "]";
	}
	
	
}
