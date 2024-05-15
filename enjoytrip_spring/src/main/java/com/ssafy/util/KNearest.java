package com.ssafy.util;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

import com.ssafy.dto.AttractionDto;

public class KNearest {
	
	static double a,b,c,d;
	
	static class Node implements Comparable<Node>{
		AttractionDto dto;
		double dist;
		
		Node(AttractionDto dto, double dist){
			this.dto=dto;
			this.dist=dist;
		}

		public int compareTo(Node o) {
			if(this.dist>o.dist) return 1;
			else if(this.dist==o.dist) return 0;
			else return -1;
		}
	}
	
	static void calCoef(AttractionDto start, AttractionDto end) {
		double x1 = start.getLongitude();
		double y1 = start.getLatitude();
		double x2 = end.getLongitude();
		double y2 = end.getLatitude();
		
		a = (y2-y1);
		b = (x1-x2);
		c = x2*y1-x1*y2;
		d = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2))+0.00000000001;
	}
	
	static double calDist(double x, double y) {
		
		return Math.abs(a*x + b*y + c)/d;
	}

	public static List<AttractionDto> getKNearest(AttractionDto start, AttractionDto end, List<AttractionDto> list, int i) {
		List<AttractionDto> ret = new ArrayList<>();
		PriorityQueue<Node> pq = new PriorityQueue<>();
		calCoef(start, end);
		
		for(AttractionDto dto : list) {
			pq.add(new Node(dto, calDist(dto.getLongitude(), dto.getLatitude())));
		}
		while(!pq.isEmpty() && i>0) {
			ret.add(pq.poll().dto);
			i--;
		}
		return ret;
	}
}
