package com.rest_day.model;

/*
 * JavaBean的三要素
 * 1.要是public的類別，且有一個預設建構子
 * 2.存取屬性需靠 getXXX() & setXXX()方法
 * 3.需要implements java.io.Serializable，成為可序列化之類別
 */
public class RestDayVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String rest_day_no; // PK
	private String emp_no;
	private java.sql.Date rest_day_sup_sta;
	private java.sql.Date rest_day_sup_end;
	private String time_peri_no;
	private Integer rest_day_fix;
	private Integer rest_sts;

	public String getRest_day_no() {
		return rest_day_no;
	}

	public void setRest_day_no(String rest_day_no) {
		this.rest_day_no = rest_day_no;
	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	public java.sql.Date getRest_day_sup_sta() {
		return rest_day_sup_sta;
	}

	public void setRest_day_sup_sta(java.sql.Date rest_day_sup_sta) {
		this.rest_day_sup_sta = rest_day_sup_sta;
	}

	public java.sql.Date getRest_day_sup_end() {
		return rest_day_sup_end;
	}

	public void setRest_day_sup_end(java.sql.Date rest_day_sup_end) {
		this.rest_day_sup_end = rest_day_sup_end;
	}

	public String getTime_peri_no() {
		return time_peri_no;
	}

	public void setTime_peri_no(String time_peri_no) {
		this.time_peri_no = time_peri_no;
	}

	public Integer getRest_day_fix() {
		return rest_day_fix;
	}

	public void setRest_day_fix(Integer rest_day_fix) {
		this.rest_day_fix = rest_day_fix;
	}

	public Integer getRest_sts() {
		return rest_sts;
	}

	public void setRest_sts(Integer rest_sts) {
		this.rest_sts = rest_sts;
	}
}
