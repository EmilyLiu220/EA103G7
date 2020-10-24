package com.seat.model;

/*
 * JavaBean的三要素
 * 1.要是public的類別，且有一個預設建構子
 * 2.存取屬性需靠 getXXX() & setXXX()方法
 * 3.需要implements java.io.Serializable，成為可序列化之類別
 */
public class SeatVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String seat_no; // PK
	private String seat_obj_no;
	private String seat_name;
	private Integer seat_isdel;
	private Double seat_x;
	private Double seat_y;
	private Integer seat_l;
	private Integer seat_w;
	private Integer seat_f;

	public String getSeat_no() {
		return seat_no;
	}

	public void setSeat_no(String seat_no) {
		this.seat_no = seat_no;
	}

	public String getSeat_obj_no() {
		return seat_obj_no;
	}

	public void setSeat_obj_no(String seat_obj_no) {
		this.seat_obj_no = seat_obj_no;
	}

	public String getSeat_name() {
		return seat_name;
	}

	public void setSeat_name(String seat_name) {
		this.seat_name = seat_name;
	}

	public Integer getSeat_isdel() {
		return seat_isdel;
	}

	public void setSeat_isdel(Integer seat_isdel) {
		this.seat_isdel = seat_isdel;
	}

	public Double getSeat_x() {
		return seat_x;
	}

	public void setSeat_x(Double seat_x) {
		this.seat_x = seat_x;
	}

	public Double getSeat_y() {
		return seat_y;
	}

	public void setSeat_y(Double seat_y) {
		this.seat_y = seat_y;
	}

	public Integer getSeat_l() {
		return seat_l;
	}

	public void setSeat_l(Integer seat_l) {
		this.seat_l = seat_l;
	}

	public Integer getSeat_w() {
		return seat_w;
	}

	public void setSeat_w(Integer seat_w) {
		this.seat_w = seat_w;
	}

	public Integer getSeat_f() {
		return seat_f;
	}

	public void setSeat_f(Integer seat_f) {
		this.seat_f = seat_f;
	}

	@Override
	public String toString() {
		return "{seat_no:" + seat_no + ", seat_obj_no:" + seat_obj_no + ", seat_name:" + seat_name + ", seat_isdel:"
				+ seat_isdel + ", seat_x:" + seat_x + ", seat_y:" + seat_y + ", seat_l:" + seat_l + ", seat_w:" + seat_w
				+ ", seat_f:" + seat_f + "}";
	}
}
