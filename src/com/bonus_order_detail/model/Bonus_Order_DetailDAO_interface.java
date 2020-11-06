package com.bonus_order_detail.model;

import java.sql.Connection;
import java.util.List;

import com.bonus.model.BonusVO;

public interface Bonus_Order_DetailDAO_interface {// 介面負責定義規格
	public void insert(Bonus_Order_DetailVO bonus_order_detailVO);
	public void update(Bonus_Order_DetailVO bonus_order_detailVO);
	public void delete(String review_no);
	public Bonus_Order_DetailVO findByPrimaryKey(String bo_no);
	public List<Bonus_Order_DetailVO> getAll();
	public void insert2 (String bo_no, java.sql.Connection con, List<BonusVO> list);
}
