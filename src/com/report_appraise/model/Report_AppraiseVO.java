package com.report_appraise.model;

import java.sql.Date;

public class Report_AppraiseVO implements java.io.Serializable {
	
	private String report_no;
	private String review_no;
	private String mem_no;
	private String emp_no;
	private Date report_date;
	private String report_con;
	private Integer reply_sts;
	
	public String getReport_no() {
		return report_no;
	}
	public void setReport_no(String report_no) {
		this.report_no = report_no;
	}
	public String getReview_no() {
		return review_no;
	}
	public void setReview_no(String review_no) {
		this.review_no = review_no;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public Date getReport_date() {
		return report_date;
	}
	public void setReport_date(Date report_date) {
		this.report_date = report_date;
	}
	public String getReport_con() {
		return report_con;
	}
	public void setReport_con(String report_con) {
		this.report_con = report_con;
	}
	public Integer getReply_sts() {
		return reply_sts;
	}
	public void setReply_sts(Integer reply_sts) {
		this.reply_sts = reply_sts;
	}
}
