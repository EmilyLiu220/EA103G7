package com.emp.model;

public class EmpVO implements java.io.Serializable {
	
	private String emp_no;
	private String emp_psw;
	private String emp_name;
	private Integer emp_sts;
	
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public String getEmp_psw() {
		return emp_psw;
	}
	public void setEmp_psw(String emp_psw) {
		this.emp_psw = emp_psw;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public Integer getEmp_sts() {
		return emp_sts;
	}
	public void setEmp_sts(Integer emp_sts) {
		this.emp_sts = emp_sts;
	}
	
}
