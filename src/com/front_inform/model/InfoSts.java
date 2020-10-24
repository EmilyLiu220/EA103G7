package com.front_inform.model;

public enum InfoSts {
	Normal(0,"一般通知"), Confirm(1, "確認用餐"), Await(2, "尚未回覆"), Cancel(3, "取消用餐");
	
	private Integer infoSts;
	private String infoStsMsg;
	
	InfoSts(int infoSts, String infoStsMsg) {
		this.infoSts = infoSts;
		this.infoStsMsg = infoStsMsg;
	}
	
	public Integer getInfoSts() {
		return this.infoSts;
	}
	
	public String getInfoStsMsg() {
		return this.infoStsMsg;
	}
	
	public static InfoSts findByInfoSts(Integer infoSts) {
		for(InfoSts sts : values()) {
			if(sts.getInfoSts().equals(infoSts))
				return sts;
		}
		return null;
	}
	
}
