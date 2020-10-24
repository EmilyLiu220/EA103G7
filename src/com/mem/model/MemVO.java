package com.mem.model;

import java.sql.Date;

public class MemVO implements java.io.Serializable {
	
	private String mem_no;
	private String mem_name;
	private String mem_act;
	private String mem_psw;
	private String mem_gen;
	private Date mem_bir;
	private String mem_tel;
	private String mem_adrs;
	private String mem_mail;
	private Integer mem_bns;
	private Integer mem_od_m;
	private Integer mem_od_r;
	private Integer mem_review;
	private Integer mem_repo;
	private Integer mem_sts;
	
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_act() {
		return mem_act;
	}
	public void setMem_act(String mem_act) {
		this.mem_act = mem_act;
	}
	public String getMem_psw() {
		return mem_psw;
	}
	public void setMem_psw(String mem_psw) {
		this.mem_psw = mem_psw;
	}
	public String getMem_gen() {
		return mem_gen;
	}
	public void setMem_gen(String mem_gen) {
		this.mem_gen = mem_gen;
	}
	public Date getMem_bir() {
		return mem_bir;
	}
	public void setMem_bir(Date mem_bir) {
		this.mem_bir = mem_bir;
	}
	public String getMem_tel() {
		return mem_tel;
	}
	public void setMem_tel(String mem_tel) {
		this.mem_tel = mem_tel;
	}
	public String getMem_adrs() {
		return mem_adrs;
	}
	public void setMem_adrs(String mem_adrs) {
		this.mem_adrs = mem_adrs;
	}
	public String getMem_mail() {
		return mem_mail;
	}
	public void setMem_mail(String mem_mail) {
		this.mem_mail = mem_mail;
	}
	public Integer getMem_bns() {
		return mem_bns;
	}
	public void setMem_bns(Integer mem_bns) {
		this.mem_bns = mem_bns;
	}
	public Integer getMem_od_m() {
		return mem_od_m;
	}
	public void setMem_od_m(Integer mem_od_m) {
		this.mem_od_m = mem_od_m;
	}
	public Integer getMem_od_r() {
		return mem_od_r;
	}
	public void setMem_od_r(Integer mem_od_r) {
		this.mem_od_r = mem_od_r;
	}
	public Integer getMem_review() {
		return mem_review;
	}
	public void setMem_review(Integer mem_review) {
		this.mem_review = mem_review;
	}
	public Integer getMem_repo() {
		return mem_repo;
	}
	public void setMem_repo(Integer mem_repo) {
		this.mem_repo = mem_repo;
	}
	public Integer getMem_sts() {
		return mem_sts;
	}
	public void setMem_sts(Integer mem_sts) {
		this.mem_sts = mem_sts;
	}
	
}
