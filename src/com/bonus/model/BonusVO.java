package com.bonus.model;

import java.sql.Date;

public class BonusVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public BonusVO() {
	bns_no = "";
	bns_name = "";
	bns_price = 0;
	bns_stks = 0;
	bns_date = null;
	bns_img = null;
}
	private String bns_no;
	private String bns_name;
	private Integer bns_price;
	private Integer bns_stks;
	private Date bns_date;
	private Integer bns_sts;
	private byte[] bns_img;
	
	public String getBns_no() {
		return bns_no;
	}
	public void setBns_no(String bns_no) {
		this.bns_no = bns_no;
	}
	public String getBns_name() {
		return bns_name;
	}
	public void setBns_name(String bns_name) {
		this.bns_name = bns_name;
	}
	public Integer getBns_price() {
		return bns_price;
	}
	public void setBns_price(Integer bns_price) {
		this.bns_price = bns_price;
	}
	public Integer getBns_stks() {
		return bns_stks;
	}
	public void setBns_stks(Integer bns_stks) {
		this.bns_stks = bns_stks;
	}
	public Date getBns_date() {
		return bns_date;
	}
	public void setBns_date(Date bns_date) {
		this.bns_date = bns_date;
	}
	public Integer getBns_sts() {
		return bns_sts;
	}
	public void setBns_sts(Integer bns_sts) {
		this.bns_sts = bns_sts;
	}
	public byte[] getBns_img() {
		return bns_img;
	}
	public void setBns_img(byte[] bns_img) {
		this.bns_img = bns_img;
	}	
	
	@Override
	public String toString() {
		return "BONUS [bns_no=" + bns_no + ", bns_name=" + bns_name + ", bns_price=" + bns_price + ", bns_stks=" + bns_stks
				+ ", bns_date=" + bns_date + ", bns_sts=" + bns_sts + ", bns_img=" + bns_img +"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bns_no == null) ? 0 : bns_no.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BonusVO other = (BonusVO) obj;
		if (bns_no == null) {
			if (other.bns_no != null)
				return false;
		} else if (!bns_no.equals(other.bns_no))
			return false;
		return true;
	}

}

