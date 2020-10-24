package com.bonus_order_detail.model;

import java.sql.Date;
import java.util.List;

public class Bonus_Order_DetailService {
	
	private Bonus_Order_DetailDAO_interface dao;
	
	public Bonus_Order_DetailService() {
		dao = new Bonus_Order_DetailDAO();
	}
	
	public Bonus_Order_DetailVO addBonus_Order_Detail(String bo_no, String bns_no, Integer quantity) {
		
		Bonus_Order_DetailVO bonus_order_detailVO = new Bonus_Order_DetailVO(); 
		
		bonus_order_detailVO.setBo_no(bo_no);
		bonus_order_detailVO.setBns_no(bns_no);
		bonus_order_detailVO.setQuantity(quantity);
		dao.insert(bonus_order_detailVO);
		
		return bonus_order_detailVO;
	}
	
	public Bonus_Order_DetailVO updateBonus_Order_Detail(String bo_no, String bns_no, Integer quantity) {
		
		Bonus_Order_DetailVO bonus_order_detailVO = new Bonus_Order_DetailVO(); 		
		
		bonus_order_detailVO.setBo_no(bo_no);
		bonus_order_detailVO.setBns_no(bns_no);
		bonus_order_detailVO.setQuantity(quantity);
		dao.update(bonus_order_detailVO);
		
		return bonus_order_detailVO;
	}
	
	public void deleteBonus_Order_Detail(String bo_no) {
		dao.delete(bo_no);
	}

	public Bonus_Order_DetailVO getOneBonus_Order_Detail(String bo_no) {
		return dao.findByPrimaryKey(bo_no);
	}

	public List<Bonus_Order_DetailVO> getAll() {
		return dao.getAll();
	}
}
