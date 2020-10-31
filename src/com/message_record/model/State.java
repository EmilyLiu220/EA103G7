package com.message_record.model;

import java.util.List;
import java.util.Set;

public class State {
	private String type;
	// total users information
	private List<String> msgJson;

	public State(String type, List<String> msgJson) {
		super();
		this.type = type;
		this.setMsgJson(msgJson);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getMsgJson() {
		return msgJson;
	}

	public void setMsgJson(List<String> msgJson) {
		this.msgJson = msgJson;
	}

}
