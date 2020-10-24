package com.meal_order_detail.model;

import java.io.Serializable;

public class MealOrderDetailVO implements Serializable{
	
	private String meal_order_detail_no;
	private String meal_order_no;
	private String meal_no;
	private String meal_set_no;
	private Integer qty;
	private Integer detail_amount;
	private Integer asgn_sts;
	private String meal_name;
	
	
	public String getMeal_name() {
		return meal_name;
	}
	public void setMeal_name(String meal_name) {
		this.meal_name = meal_name;
	}
	public String getMeal_order_detail_no() {
		return meal_order_detail_no;
	}
	public void setMeal_order_detail_no(String meal_order_detail_no) {
		this.meal_order_detail_no = meal_order_detail_no;
	}
	public String getMeal_order_no() {
		return meal_order_no;
	}
	public void setMeal_order_no(String meal_order_no) {
		this.meal_order_no = meal_order_no;
	}
	public String getMeal_no() {
		return meal_no;
	}
	public void setMeal_no(String meal_no) {
		this.meal_no = meal_no;
	}
	public String getMeal_set_no() {
		return meal_set_no;
	}
	public void setMeal_set_no(String meal_set_no) {
		this.meal_set_no = meal_set_no;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getDetail_amount() {
		return detail_amount;
	}
	public void setDetail_amount(Integer detail_amount) {
		this.detail_amount = detail_amount;
	}
	public Integer getAsgn_sts() {
		return asgn_sts;
	}
	public void setAsgn_sts(Integer asgn_sts) {
		this.asgn_sts = asgn_sts;
	}
	
	

}
