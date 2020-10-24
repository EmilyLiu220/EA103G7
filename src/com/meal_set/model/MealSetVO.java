package com.meal_set.model;

import java.io.Serializable;

public class MealSetVO implements Serializable{
	
	public MealSetVO() {}
	
	private String meal_set_no;
	private String meal_set_name;
	private String meal_set_info;
	private byte[] meal_set_img;
	private Integer meal_set_price;
	private Integer meal_set_sts;
	private Integer cat_no;
	private Integer meal_set_qty;
	
	
	public Integer getMeal_set_qty() {
		return meal_set_qty;
	}
	public void setMeal_set_qty(Integer meal_set_qty) {
		this.meal_set_qty = meal_set_qty;
	}
	public String getMeal_set_no() {
		return meal_set_no;
	}
	public void setMeal_set_no(String meal_set_no) {
		this.meal_set_no = meal_set_no;
	}
	public String getMeal_set_name() {
		return meal_set_name;
	}
	public void setMeal_set_name(String meal_set_name) {
		this.meal_set_name = meal_set_name;
	}
	public String getMeal_set_info() {
		return meal_set_info;
	}
	public void setMeal_set_info(String meal_set_info) {
		this.meal_set_info = meal_set_info;
	}
	public byte[] getMeal_set_img() {
		return meal_set_img;
	}
	public void setMeal_set_img(byte[] meal_set_img) {
		this.meal_set_img = meal_set_img;
	}
	public Integer getMeal_set_price() {
		return meal_set_price;
	}
	public void setMeal_set_price(Integer meal_set_price) {
		this.meal_set_price = meal_set_price;
	}
	public Integer getMeal_set_sts() {
		return meal_set_sts;
	}
	public void setMeal_set_sts(Integer meal_set_sts) {
		this.meal_set_sts = meal_set_sts;
	}
	public Integer getCat_no() {
		return cat_no;
	}
	public void setCat_no(Integer cat_no) {
		this.cat_no = cat_no;
	}
	
	
	
}
