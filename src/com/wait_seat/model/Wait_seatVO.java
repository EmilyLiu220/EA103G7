package com.wait_seat.model;

public class Wait_seatVO implements java.io.Serializable{
	public Wait_seatVO(){}
	private String wait_seat_no;
	private String mem_no;
	private String n_mem_name;
	private String phone_m;
	private Integer delay;
	private Integer wait_n;
	public String getWait_seat_no() {
		return wait_seat_no;
	}
	public void setWait_seat_no(String wait_seat_no) {
		this.wait_seat_no = wait_seat_no;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getN_mem_name() {
		return n_mem_name;
	}
	public void setN_mem_name(String n_mem_name) {
		this.n_mem_name = n_mem_name;
	}
	public String getPhone_m() {
		return phone_m;
	}
	public void setPhone_m(String phone_m) {
		this.phone_m = phone_m;
	}
	public Integer getDelay() {
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	public Integer getWait_n() {
		return wait_n;
	}
	public void setWait_n(Integer wait_n) {
		this.wait_n = wait_n;
	}
	
}
