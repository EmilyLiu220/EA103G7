package com.meal.model;

import java.io.InputStream;
import java.io.Serializable;

public class MealVO implements Serializable{
	
	public MealVO() {}
	
	private String meal_no;
	private String meal_name;
	private String meal_info;
	private byte[] meal_img;
	private Integer meal_price;
	private Integer meal_sts;
	private Integer cat_no;
	private Integer meal_qty;
	
	
	
	public Integer getMeal_qty() {
		return meal_qty;
	}
	public void setMeal_qty(Integer meal_qty) {
		this.meal_qty = meal_qty;
	}
	public String getMeal_no() {
		return meal_no;
	}
	public void setMeal_no(String meal_no) {
		this.meal_no = meal_no;
	}
	public String getMeal_name() {
		return meal_name;
	}
	public void setMeal_name(String meal_name) {
		this.meal_name = meal_name;
	}
	public String getMeal_info() {
		return meal_info;
	}
	public void setMeal_info(String meal_info) {
		this.meal_info = meal_info;
	}
	public byte[] getMeal_img() {
		return meal_img;
	}
	public void setMeal_img(byte[] meal_img) {
		this.meal_img = meal_img;
	}
	public Integer getMeal_price() {
		return meal_price;
	}
	public void setMeal_price(Integer meal_price) {
		this.meal_price = meal_price;
	}
	public Integer getMeal_sts() {
		return meal_sts;
	}
	public void setMeal_sts(Integer meal_sts) {
		this.meal_sts = meal_sts;
	}
	public Integer getCat_no() {
		return cat_no;
	}
	public void setCat_no(Integer cat_no) {
		this.cat_no = cat_no;
	}
	
	

}
