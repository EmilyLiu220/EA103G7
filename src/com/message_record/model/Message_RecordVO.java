package com.message_record.model;

import java.io.Serializable;

public class Message_RecordVO implements Serializable{
	
	private static final long serialVersionUID = 2869485720871607525L;
	
	private String msg_no;
	private String emp_no;
	private String mem_no;
	private String msg_cont;
	private java.sql.Date msg_time;
	private Integer msg_sts;
	private Integer read_sts;
	
	public String getMsg_no() {
		return msg_no;
	}
	
	public void setMsg_no(String msg_no) {
		this.msg_no = msg_no;
	}
	
	public String getEmp_no() {
		return emp_no;
	}
	
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	
	public String getMem_no() {
		return mem_no;
	}
	
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	
	public String getMsg_cont() {
		return msg_cont;
	}
	
	public void setMsg_cont(String msg_cont) {
		this.msg_cont = msg_cont;
	}
	
	public java.sql.Date getMsg_time() {
		return msg_time;
	}
	
	public void setMsg_time(java.sql.Date msg_time) {
		this.msg_time = msg_time;
	}
	
	public Integer getMsg_sts() {
		return msg_sts;
	}
	
	public void setMsg_sts(Integer msg_sts) {
		this.msg_sts = msg_sts;
	}

	public Integer getRead_sts() {
		return read_sts;
	}

	public void setRead_sts(Integer read_sts) {
		this.read_sts = read_sts;
	}
	
}
