package com.ad.model;

import java.sql.Date;

public class AdVO implements java.io.Serializable {
	private String ad_no;
	private String emp_no;
	private String ad_title;
	private String ad_cont;
	private Date ad_add_date;
	private Date ad_re_date;
	private byte[] ad_img;
	private Integer ad_sts;

	public String getAd_no() {
		return ad_no;
	}

	public void setAd_no(String ad_no) {
		this.ad_no = ad_no;
	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	public String getAd_title() {
		return ad_title;
	}

	public void setAd_title(String ad_title) {
		this.ad_title = ad_title;
	}

	public String getAd_cont() {
		return ad_cont;
	}

	public void setAd_cont(String ad_cont) {
		this.ad_cont = ad_cont;
	}

	public Date getAd_add_date() {
		return ad_add_date;
	}

	public void setAd_add_date(Date ad_add_date) {
		this.ad_add_date = ad_add_date;
	}

	public Date getAd_re_date() {
		return ad_re_date;
	}

	public void setAd_re_date(Date ad_re_date) {
		this.ad_re_date = ad_re_date;
	}

	public byte[] getAd_img() {
		return ad_img;
	}

	public void setAd_img(byte[] ad_img) {
		this.ad_img = ad_img;
	}

	public Integer getAd_sts() {
		return ad_sts;
	}

	public void setAd_sts(Integer ad_sts) {
		this.ad_sts = ad_sts;
	}

}