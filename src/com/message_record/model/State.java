package com.message_record.model;

import java.util.Set;

public class State {
	private String type;
	// the user changing the state
	private String emp;
	// total users
	private Set<String> mems;

	public State(String type, String emp, Set<String> mems) {
		super();
		this.type = type;
		this.emp = emp;
		this.mems = mems;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public Set<String> getMems() {
		return mems;
	}

	public void setMems(Set<String> mems) {
		this.mems = mems;
	}

}
