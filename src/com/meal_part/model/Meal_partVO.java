package com.meal_part.model;

public class Meal_partVO {
	public Meal_partVO() {}
	private String meal_no;
	private String fd_no;
	private Double fd_gw;
	public String getMeal_no() {
		return meal_no;
	}
	public void setMeal_no(String meal_no) {
		this.meal_no = meal_no;
	}
	public String getFd_no() {
		return fd_no;
	}
	public void setFd_no(String fd_no) {
		this.fd_no = fd_no;
	}
	public Double getFd_gw() {
		return fd_gw;
	}
	public void setFd_gw(Double fd_gw) {
		this.fd_gw = fd_gw;
	}
	
}
