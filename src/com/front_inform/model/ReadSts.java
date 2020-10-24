package com.front_inform.model;

public enum ReadSts {
	Unread(0, "未讀"), Read(1, "已讀");
	
	private Integer readSts;
	private String readStsMsg;

	ReadSts(Integer readSts, String readStsMsg) {
		this.readSts = readSts;
		this.readStsMsg = readStsMsg;
	}
	
	public Integer getReadSts() {
		return this.readSts;
	}
	
	public String getReadStsMsg() {
		return this.readStsMsg;
	}
	
	public static ReadSts findByReadSts(Integer readSts) {
		for(ReadSts sts : values()) {
			if(sts.getReadSts().equals(readSts))
				return sts;
		}
		return null;
	}
	
}
