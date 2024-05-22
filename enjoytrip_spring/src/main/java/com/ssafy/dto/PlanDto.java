package com.ssafy.dto;

public class PlanDto {
	
	private int planNo;
	private int seqNo;
	private int contentId;
	
	public PlanDto(int planNo, int seqNo, int contentId) {
		super();
		this.planNo = planNo;
		this.seqNo = seqNo;
		this.contentId = contentId;
	}

		public int getPlanNo() {
		return planNo;
	}

	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	
}
