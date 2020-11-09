package com.bonus.model;

import java.sql.Date;
import java.util.List;

import com.meal_set.model.MealSetVO;

public class BonusService {
	
	private BonusDAO_interface dao;

	public BonusService() {
		dao = new BonusDAO();
	}
	
	public BonusVO addBonusFromBack(String bns_name, Integer bns_price, Integer bns_stks,
			java.sql.Date bns_date, byte[] bns_img) {
		
		BonusVO bonusVO = new BonusVO(); 
		
		bonusVO.setBns_name(bns_name);
		bonusVO.setBns_price(bns_price);
		bonusVO.setBns_stks(bns_stks);
		bonusVO.setBns_date(bns_date);
		bonusVO.setBns_img(bns_img);	
		dao.insertFromBack(bonusVO);
		
		return bonusVO;
	}
	
//	public BonusVO addBonusFromFront(String bns_name, Integer bns_stks) {
//		
//		BonusVO bonusVO = new BonusVO(); 
//		
//		bonusVO.setBns_name(bns_name);
//		bonusVO.setBns_stks(bns_stks);
//		dao.insertFromFront(bonusVO);
//		
//		return bonusVO;
//	}
	
	public BonusVO updateBonus(String bns_no, String bns_name, Integer bns_price, Integer bns_stks,
			java.sql.Date bns_date, Integer bns_sts, byte[] bns_img) {
		
		BonusVO bonusVO = new BonusVO(); 	
		
		bonusVO.setBns_no(bns_no);
		bonusVO.setBns_name(bns_name);
		bonusVO.setBns_price(bns_price);
		bonusVO.setBns_stks(bns_stks);
		bonusVO.setBns_date(bns_date);
		bonusVO.setBns_sts(bns_sts);
		bonusVO.setBns_img(bns_img);
		dao.update(bonusVO);
		
		return bonusVO;
	}
	
//	public BonusVO searchByNo(String bns_no) {
//		return dao.searchByNo(bns_no);
//	};
	
	public void deleteBonus(String bns_no) {
		dao.delete(bns_no);
	}

	public BonusVO getOneBonus(String bns_no) {
		return dao.findByPrimaryKey(bns_no);
	}

	public List<BonusVO> getAll() {
		return dao.getAll();
	}
}