package com.meal_order.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import oracle.sql.TIMESTAMP;

public class MealOrderVO implements Serializable{
	
	public MealOrderVO() {}
	
	private String meal_order_no;
	private String mem_no;
	private String emp_no;
	private Integer meal_order_sts;
	private Integer amount;
	private Timestamp order_time;
	private Integer noti_sts;
	private Integer pay_sts;
	private Timestamp pickup_time;
	
	
	public String getMeal_order_no() {
		return meal_order_no;
	}
	public void setMeal_order_no(String meal_order_no) {
		this.meal_order_no = meal_order_no;
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
	public Integer getMeal_order_sts() {
		return meal_order_sts;
	}
	public void setMeal_order_sts(Integer meal_order_sts) {
		this.meal_order_sts = meal_order_sts;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Timestamp getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Timestamp order_time) {
		this.order_time = order_time;
	}
	public Integer getNoti_sts() {
		return noti_sts;
	}
	public void setNoti_sts(Integer noti_sts) {
		this.noti_sts = noti_sts;
	}
	public Integer getPay_sts() {
		return pay_sts;
	}
	public void setPay_sts(Integer pay_sts) {
		this.pay_sts = pay_sts;
	}
	public Timestamp getPickup_time() {
		return pickup_time;
	}
	public void setPickup_time(Timestamp pickup_time) {
		this.pickup_time = pickup_time;
	}
	
	
	

}
