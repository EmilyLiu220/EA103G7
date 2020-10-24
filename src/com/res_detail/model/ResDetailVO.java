package com.res_detail.model;

/*
 * JavaBean的三要素
 * 1.要是public的類別，且有一個預設建構子
 * 2.存取屬性需靠 getXXX() & setXXX()方法
 * 3.需要implements java.io.Serializable，成為可序列化之類別
 */
public class ResDetailVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String res_de_no; // PK
	private String seat_no;
	private String res_no;

	public String getRes_de_no() {
		return res_de_no;
	}

	public void setRes_de_no(String res_de_no) {
		this.res_de_no = res_de_no;
	}

	public String getSeat_no() {
		return seat_no;
	}

	public void setSeat_no(String seat_no) {
		this.seat_no = seat_no;
	}

	public String getRes_no() {
		return res_no;
	}

	public void setRes_no(String res_no) {
		this.res_no = res_no;
	}

}
