package com.res_order.model;

/*
 * JavaBean的三要素
 * 1.要是public的類別，且有一個預設建構子
 * 2.存取屬性需靠 getXXX() & setXXX()方法
 * 3.需要implements java.io.Serializable，成為可序列化之類別
 */
public class ResOrderVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String res_no; // PK
	private String meal_order_no;
	private String mem_no;
	private String emp_no;
	private java.sql.Timestamp res_time;
	private java.sql.Date res_date;
	private Integer people;
	private String time_peri_no;
	private Integer info_sts;
	private Integer seat_sts;

	public String getRes_no() {
		return res_no;
	}

	public void setRes_no(String res_no) {
		this.res_no = res_no;
	}

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

	public java.sql.Timestamp getRes_time() {
		return res_time;
	}

	public void setRes_time(java.sql.Timestamp res_time) {
		this.res_time = res_time;
	}

	public java.sql.Date getRes_date() {
		return res_date;
	}

	public void setRes_date(java.sql.Date res_date) {
		this.res_date = res_date;
	}

	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}

	public String getTime_peri_no() {
		return time_peri_no;
	}

	public void setTime_peri_no(String time_peri_no) {
		this.time_peri_no = time_peri_no;
	}

	public Integer getInfo_sts() {
		return info_sts;
	}

	public void setInfo_sts(Integer info_sts) {
		this.info_sts = info_sts;
	}

	public Integer getSeat_sts() {
		return seat_sts;
	}

	public void setSeat_sts(Integer seat_sts) {
		this.seat_sts = seat_sts;
	}
}
