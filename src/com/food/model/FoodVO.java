package com.food.model;

public class FoodVO implements java.io.Serializable{
	public FoodVO() {}
	
	private String fd_no;
	private String fd_name;
	private Integer fd_isdel;
	private Integer fd_stk;
	private Integer stk_ll;
	private Double cal;
	private Double prot;            
	private Double carb;
	private Double fat;
	public String getFd_no() {
		return fd_no;
	}
	public void setFd_no(String fd_no) {
		this.fd_no = fd_no;
	}
	public String getFd_name() {
		return fd_name;
	}
	public void setFd_name(String fd_name) {
		this.fd_name = fd_name;
	}
	public Integer getFd_isdel() {
		return fd_isdel;
	}
	public void setFd_isdel(Integer fd_isdel) {
		this.fd_isdel = fd_isdel;
	}
	public Integer getFd_stk() {
		return fd_stk;
	}
	public void setFd_stk(Integer fd_stk) {
		this.fd_stk = fd_stk;
	}
	public Integer getStk_ll() {
		return stk_ll;
	}
	public void setStk_ll(Integer stk_ll) {
		this.stk_ll = stk_ll;
	}
	public Double getCal() {
		return cal;
	}
	public void setCal(Double cal) {
		this.cal = cal;
	}
	public Double getProt() {
		return prot;
	}
	public void setProt(Double prot) {
		this.prot = prot;
	}
	public Double getCarb() {
		return carb;
	}
	public void setCarb(Double carb) {
		this.carb = carb;
	}
	public Double getFat() {
		return fat;
	}
	public void setFat(Double fat) {
		this.fat = fat;
	}
}
