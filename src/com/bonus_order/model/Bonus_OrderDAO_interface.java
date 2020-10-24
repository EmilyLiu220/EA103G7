package com.bonus_order.model;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import com.bonus_order_detail.model.Bonus_Order_DetailVO;


public interface Bonus_OrderDAO_interface { // 介面負責定義規格
	public void insert(Bonus_OrderVO bonus_orderVO, Connection outer_con);
	public void update(Bonus_OrderVO bonus_orderVO);
	public void delete(String bo_no);
	public Bonus_OrderVO findByPrimaryKey(String bo_no);
	public List<Bonus_OrderVO> getAll();
	public Set<Bonus_Order_DetailVO> getBonus_Order_DetailByBo_no(Integer bo_no);
	public void insertWithBonus_Order_Detail(Bonus_OrderVO bonus_orderVO , List<Bonus_Order_DetailVO> list);
}

