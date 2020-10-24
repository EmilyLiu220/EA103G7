package com.bonus.model;

import java.sql.Date;

public class BonusVO implements java.io.Serializable {

	private String bns_no;
	private String bns_name;
	private Integer bns_price;
	private Integer bns_stks;
	private Date bns_date;
	private Integer bns_sts;
	private byte[] bns_img;
	
	public String getBns_no() {
		return bns_no;
	}
	public void setBns_no(String bns_no) {
		this.bns_no = bns_no;
	}
	public String getBns_name() {
		return bns_name;
	}
	public void setBns_name(String bns_name) {
		this.bns_name = bns_name;
	}
	public Integer getBns_price() {
		return bns_price;
	}
	public void setBns_price(Integer bns_price) {
		this.bns_price = bns_price;
	}
	public Integer getBns_stks() {
		return bns_stks;
	}
	public void setBns_stks(Integer bns_stks) {
		this.bns_stks = bns_stks;
	}
	public Date getBns_date() {
		return bns_date;
	}
	public void setBns_date(Date bns_date) {
		this.bns_date = bns_date;
	}
	public Integer getBns_sts() {
		return bns_sts;
	}
	public void setBns_sts(Integer bns_sts) {
		this.bns_sts = bns_sts;
	}
	public byte[] getBns_img() {
		return bns_img;
	}
	public void setBns_img(byte[] bns_img) {
		this.bns_img = bns_img;
	}	
}
