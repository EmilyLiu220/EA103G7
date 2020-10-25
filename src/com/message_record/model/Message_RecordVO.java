package com.message_record.model;

import java.io.Serializable;

public class Message_RecordVO implements Serializable{
	
	private static final long serialVersionUID = 2869485720871607525L;
	
	private String type;
	private String sender;
	private String receiver;
	private String msgJson;
	
	public Message_RecordVO (String type, String sender, String receiver, String msgJson) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.msgJson = msgJson;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMsgJson() {
		return msgJson;
	}

	public void setMsgJson(String msgJson) {
		this.msgJson = msgJson;
	}
	
}
