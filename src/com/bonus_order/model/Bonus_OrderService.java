package com.bonus_order.model;

import java.util.List;
import java.sql.Connection;

public class Bonus_OrderService {
	
	private Bonus_OrderDAO_interface dao;

	public Bonus_OrderService() {
		dao = new Bonus_OrderDAO();
	}
	
	public Bonus_OrderVO addBonus_Order(String mem_no, java.sql.Date bo_date, String promo_code, Connection outer_con) {
		
		Bonus_OrderVO bonus_orderVO = new Bonus_OrderVO(); 
		
		bonus_orderVO.setMem_no(mem_no);
		bonus_orderVO.setBo_date(bo_date);
		bonus_orderVO.setPromo_code(promo_code);
		dao.insert(bonus_orderVO, outer_con);
		
		return bonus_orderVO;
	}
	
	public Bonus_OrderVO updateBonus_Order(String bo_no, String mem_no, java.sql.Date bo_date, String promo_code) {
		
		Bonus_OrderVO bonus_orderVO = new Bonus_OrderVO(); 		
		
		bonus_orderVO.setBo_no(bo_no);
		bonus_orderVO.setMem_no(mem_no);
		bonus_orderVO.setBo_date(bo_date);
		bonus_orderVO.setPromo_code(promo_code);
		dao.update(bonus_orderVO);
		
		return bonus_orderVO;
	}
	
	public void deleteBonus_Order(String bo_no) {
		dao.delete(bo_no);
	}

	public Bonus_OrderVO getOneBonus_Order(String bo_no) {
		return dao.findByPrimaryKey(bo_no);
	}

	public List<Bonus_OrderVO> getAll() {
		return dao.getAll();
	}
}
