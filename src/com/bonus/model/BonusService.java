package com.bonus.model;

import java.sql.Date;
import java.util.List;

public class BonusService {
	
	private BonusDAO_interface dao;

	public BonusService() {
		dao = new BonusDAO();
	}
	
	public BonusVO addBonus(String bns_name, Integer bns_price,Integer bns_stks,
			java.sql.Date bns_date, Integer bns_sts, byte[] bns_img) {
		
		BonusVO bonusVO = new BonusVO(); 
		
		bonusVO.setBns_name(bns_name);
		bonusVO.setBns_price(bns_price);
		bonusVO.setBns_stks(bns_stks);
		bonusVO.setBns_date(bns_date);
		bonusVO.setBns_sts(bns_sts);
		bonusVO.setBns_img(bns_img);	
		dao.insert(bonusVO);
		
		return bonusVO;
	}
	
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