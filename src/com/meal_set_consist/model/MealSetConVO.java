package com.meal_set_consist.model;

import java.io.Serializable;

public class MealSetConVO implements Serializable {
	
	private String meal_set_no;
	private String meal_no;
	private Integer meal_qty;
	
	
	public String getMeal_set_no() {
		return meal_set_no;
	}
	public void setMeal_set_no(String meal_set_no) {
		this.meal_set_no = meal_set_no;
	}
	public String getMeal_no() {
		return meal_no;
	}
	public void setMeal_no(String meal_no) {
		this.meal_no = meal_no;
	}
	public Integer getMeal_qty() {
		return meal_qty;
	}
	public void setMeal_qty(Integer meal_qty) {
		this.meal_qty = meal_qty;
	}
	
	

}
