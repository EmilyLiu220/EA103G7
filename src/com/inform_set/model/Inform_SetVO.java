package com.inform_set.model;

import java.io.Serializable;

public class Inform_SetVO implements Serializable, Comparable<Inform_SetVO> {

	private static final long serialVersionUID = -8947956968263648314L;

	private String is_no;
	private String emp_no;
	private String is_cont;
	private java.sql.Date is_date;
	
	public String getIs_no() {
		return is_no;
	}
	
	public void setIs_no(String is_no) {
		this.is_no = is_no;
	}
	
	public String getEmp_no() {
		return emp_no;
	}
	
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	
	public String getIs_cont() {
		return is_cont;
	}
	
	public void setIs_cont(String is_cont) {
		this.is_cont = is_cont;
	}
	
	public java.sql.Date getIs_date() {
		return is_date;
	}
	
	public void setIs_date(java.sql.Date is_date) {
		this.is_date = is_date;
	}
	
	public int compareTo(Inform_SetVO aisVO) { 
		if (this.is_date.after(aisVO.is_date)) {
			return -1;	// 回傳負值 → 擺左邊
		} else if(this.is_date.equals(aisVO.is_date)){ // 萬一兩者一樣大 → 0	
			return 0;
		} else {
			return 1;  // 回傳正值 → 擺右邊
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) { // 先直接比對址( Address )
			return true;
		}					// 再觀察兩物件是否皆由相同類別產生
		if ( obj != null && this.getClass() == obj.getClass()) {
			Inform_SetVO isVO = (Inform_SetVO) obj; // 因其進入類別的身分是 Object → 須轉型、變回員工類別
			   // 基本型別可直接使用 == 比較	// String 類別已將 .equals()方法改寫過了，可直接使用
			if(this.is_no.equals(isVO.is_no)) {
				return true;
			}
		}
		return false; // 若上述兩者都不相符 → false
	}
	
	@Override
	public int hashCode() { // 數字要大，故以質數平方相乘，避免 hashCode 碰撞，碰撞就需要再看 equals，效率↓
		final int prime = 31; // 必須是質數
		int sum = 1;
		sum = sum * prime * Integer.parseInt(this.is_no.substring(2)); // 因員工編號既是一組數字了
		sum = sum * prime * is_date.hashCode(); // 因為字串本身也有 hashCode → 整數
		return sum;
	}

}
