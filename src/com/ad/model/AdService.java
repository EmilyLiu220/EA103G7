package com.ad.model;

import java.util.List;

import com.ad.model.AdVO;

public class AdService {
	
	private AdDAO_interface dao;
	
	public AdService() {
		dao= new AdDAO();
	}
	public AdVO addAd(String emp_no ,String ad_title ,String ad_cont ,java.sql.Date ad_add_date ,java.sql.Date ad_re_date ,byte[] ad_img,Integer ad_sts){
		
	AdVO adVO =new AdVO();
	
	adVO.setEmp_no(emp_no);
	adVO.setAd_title(ad_title);
	adVO.setAd_cont(ad_cont);
	adVO.setAd_add_date(ad_add_date);
	adVO.setAd_re_date(ad_re_date);
	adVO.setAd_img(ad_img);
	adVO.setAd_sts(ad_sts);
	dao.insert(adVO);
	
	return adVO;
	}
	
	public AdVO updateAd(String ad_no ,String emp_no ,String ad_title ,String ad_cont ,java.sql.Date ad_add_date ,java.sql.Date ad_re_date ,byte[] ad_img,Integer ad_sts) {
		
		AdVO adVO =new AdVO();
		
		adVO.setAd_no(ad_no);
		adVO.setEmp_no(emp_no);
		adVO.setAd_title(ad_title);
		adVO.setAd_cont(ad_cont);
		adVO.setAd_add_date(ad_add_date);
		adVO.setAd_re_date(ad_re_date);
		adVO.setAd_img(ad_img);
		adVO.setAd_sts(ad_sts);
		dao.update(adVO);
		
		return adVO;
	}
	
	public void deleteAd(String ad_no) {
		dao.delete(ad_no);
	}
	
	public AdVO getOneAd(String ad_no) {
		return dao.findByPrimaryKey(ad_no);
	}
	
	public List<AdVO> getAll() {
		return dao.getAll();
	}
	public List<AdVO> getadno(String emp_no) {
		return dao.getadno(emp_no);
	}
//public AdVO upad(String ad_no ,String emp_no ,java.sql.Date ad_add_date ,java.sql.Date ad_re_date ,Integer ad_sts) {
//		
//		AdVO adVO =new AdVO();
//		
//		adVO.setAd_no(ad_no);
//		adVO.setEmp_no(emp_no);
//		adVO.setAd_add_date(ad_add_date);
//		adVO.setAd_re_date(ad_re_date);
//		adVO.setAd_sts(ad_sts);
//		dao.upad(adVO);
//		
//		return adVO;
//	}

//public AdVO downad(String ad_no ,String emp_no ,java.sql.Date ad_add_date ,java.sql.Date ad_re_date ,Integer ad_sts) {
//	
//	AdVO adVO =new AdVO();
//	
//	adVO.setAd_no(ad_no);
//	adVO.setEmp_no(emp_no);
//	adVO.setAd_add_date(ad_add_date);
//	adVO.setAd_re_date(ad_re_date);
//	adVO.setAd_sts(ad_sts);
//	dao.upad(adVO);
//	
//	return adVO;
//}
public List<AdVO> find_adsts(Integer ad_sts) {
	return dao.find_adsts(ad_sts);
}
}
