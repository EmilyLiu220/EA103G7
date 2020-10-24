package com.seat_obj.model;

/*
 * JavaBean的三要素
 * 1.要是public的類別，且有一個預設建構子
 * 2.存取屬性需靠 getXXX() & setXXX()方法
 * 3.需要implements java.io.Serializable，成為可序列化之類別
 */
public class SeatObjVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String seat_obj_no; // PK
	private byte[] seat_obj;
	private Integer seat_obj_sts;
	private Integer seat_people;
	private Integer seat_use;

	public Integer getSeat_use() {
		return seat_use;
	}

	public void setSeat_use(Integer seat_use) {
		this.seat_use = seat_use;
	}

	public String getSeat_obj_no() {
		return seat_obj_no;
	}

	public void setSeat_obj_no(String seat_obj_no) {
		this.seat_obj_no = seat_obj_no;
	}

	public byte[] getSeat_obj() {
		return seat_obj;
	}

	public void setSeat_obj(byte[] seat_obj) {
		this.seat_obj = seat_obj;
	}

	public Integer getSeat_obj_sts() {
		return seat_obj_sts;
	}

	public void setSeat_obj_sts(Integer seat_obj_sts) {
		this.seat_obj_sts = seat_obj_sts;
	}

	public Integer getSeat_people() {
		return seat_people;
	}

	public void setSeat_people(Integer seat_people) {
		this.seat_people = seat_people;
	}
}
