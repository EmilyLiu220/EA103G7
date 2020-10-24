package com.bonus_order_detail.model;

import java.sql.Date;

public class Bonus_Order_DetailVO implements java.io.Serializable {

		
		private String bo_no;
		private String bns_no;
		private Integer quantity;
		
		public String getBo_no() {
			return bo_no;
		}
		public void setBo_no(String bo_no) {
			this.bo_no = bo_no;
		}
		public String getBns_no() {
			return bns_no;
		}
		public void setBns_no(String bns_no) {
			this.bns_no = bns_no;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}	
	}

