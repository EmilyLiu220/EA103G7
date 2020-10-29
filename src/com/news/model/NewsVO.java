package com.news.model;

import java.sql.Date;

public class NewsVO implements java.io.Serializable {
	private String news_no;
	private String emp_no;
	private String news_cont;
	private Date news_date;
	private Integer news_sts;

	public String getNews_no() {
		return news_no;
	}

	public void setNews_no(String news_no) {
		this.news_no = news_no;
	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	public String getNews_cont() {
		return news_cont;
	}

	public void setNews_cont(String news_cont) {
		this.news_cont = news_cont;
	}

	public Date getNews_date() {
		return news_date;
	}

	public void setNews_date(Date news_date) {
		this.news_date = news_date;
	}

	
	public Integer getNews_sts() {
		return news_sts;
	}

	public void setNews_sts(Integer news_sts) {
		this.news_sts = news_sts;
	}
	
}
