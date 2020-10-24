package com.member_review.model;

import java.sql.Date;

public class Member_ReviewVO implements java.io.Serializable   {
	
	private String review_no;
	private String meal_order_no;
	private String mem_review_con;
	private Date review_date;
	private Integer review_sts;
	private Integer review_del;
	
	public String getReview_no() {
		return review_no;
	}
	public void setReview_no(String review_no) {
		this.review_no = review_no;
	}
	public String getMeal_order_no() {
		return meal_order_no;
	}
	public void setMeal_order_no(String meal_order_no) {
		this.meal_order_no = meal_order_no;
	}
	public String getMem_review_con() {
		return mem_review_con;
	}
	public void setMem_review_con(String mem_review_con) {
		this.mem_review_con = mem_review_con;
	}
	public Date getReview_date() {
		return review_date;
	}
	public void setReview_date(Date review_date) {
		this.review_date = review_date;
	}
	public Integer getReview_sts() {
		return review_sts;
	}
	public void setReview_sts(Integer review_sts) {
		this.review_sts = review_sts;
	}
	public Integer getReview_del() {
		return review_del;
	}
	public void setReview_del(Integer review_del) {
		this.review_del = review_del;
	}
}
