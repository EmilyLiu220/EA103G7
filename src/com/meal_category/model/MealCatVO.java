package com.meal_category.model;

import java.io.Serializable;

public class MealCatVO implements Serializable{
	
	private Integer cat_no;
	private String cat_name;
	
	public Integer getCat_no() {
		return cat_no;
	}
	public void setCat_no(Integer cat_no) {
		this.cat_no = cat_no;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	
	

}
