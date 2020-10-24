package com.front_inform.model;

import java.io.Serializable;

public class Front_InformVO implements Serializable{
	
	private static final long serialVersionUID = -2880126589862278310L;
	
	private String info_no;
	private String mem_no;
	private String info_cont;
	private java.sql.Date info_date;
	private Integer info_sts;
	private Integer read_sts;
	private String res_no;
	
	public String getInfo_no() {
		return info_no;
	}
	
	public void setInfo_no(String info_no) {
		this.info_no = info_no;
	}
	
	public String getMem_no() {
		return mem_no;
	}
	
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	
	public String getInfo_cont() {
		return info_cont;
	}
	
	public void setInfo_cont(String info_cont) {
		this.info_cont = info_cont;
	}
	
	public java.sql.Date getInfo_date() {
		return info_date;
	}
	
	public void setInfo_date(java.sql.Date info_date) {
		this.info_date = info_date;
	}
	
	public Integer getInfo_sts() {
		return info_sts;
	}
	
	public void setInfo_sts(Integer info_sts) {
		this.info_sts = info_sts;
	}
	
	public String getRes_no() {
		return res_no;
	}
	
	public void setRes_no(String res_no) {
		this.res_no = res_no;
	}

	public Integer getRead_sts() {
		return read_sts;
	}

	public void setRead_sts(Integer read_sts) {
		this.read_sts = read_sts;
	}
	
}
