package com.ssafy.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SidoCodeUtil {
	private Map<String, String> sidoMap;
	
	public SidoCodeUtil() {
		this.sidoMap = generateSidoMap();
	}
	
	private Map<String, String> generateSidoMap() {
		Map<String, String> sidoMap;
		
		sidoMap = new HashMap<>();
		sidoMap.put("서울", "서울");
		sidoMap.put("인천", "인천");
		sidoMap.put("대전", "대전");
		sidoMap.put("대구", "대구");
		sidoMap.put("광주", "광주");
		sidoMap.put("부산", "부산");
		sidoMap.put("울산", "울산");
		sidoMap.put("세종", "세종특별자치시");
		sidoMap.put("경기", "경기도");
		sidoMap.put("강원", "강원도");
		sidoMap.put("충북", "충청북도");
		sidoMap.put("충남", "충청남도");
		sidoMap.put("경북", "경상북도");
		sidoMap.put("경남", "경상남도");
		sidoMap.put("전북", "전라북도");
		sidoMap.put("전남", "전라남도");
		sidoMap.put("제주", "제주도");
		return sidoMap;
	}
	
	public String convert(String prefix) {
		return sidoMap.get(prefix);
	}
}
