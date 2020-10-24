package com.fun_auth.model;

import java.util.List;

public class Fun_authService {

	private Fun_authDAO_interface dao;
	
	public Fun_authService() {
		dao = new Fun_authDAO();
	}
	
	public Fun_authVO addFun_auth(String fun_no, String fun_name, String fun_des) {
		
		Fun_authVO fun_authVO = new Fun_authVO();
		
		fun_authVO.setFun_no(fun_no);
		fun_authVO.setFun_name(fun_name);
		fun_authVO.setFun_des(fun_des);
		dao.insert(fun_authVO);
		
		return fun_authVO;
	}
	
	public Fun_authVO updateFun_auth(String fun_name, String fun_des, String fun_no) {
		
		Fun_authVO fun_authVO = new Fun_authVO();
		
		fun_authVO.setFun_name(fun_name);
		fun_authVO.setFun_des(fun_des);
		fun_authVO.setFun_no(fun_no);
		dao.update(fun_authVO);
		
		return fun_authVO;
	}
	
	public void deleteFun_auth(String fun_no) {
		dao.delete(fun_no);
	}
	
	public List<Fun_authVO> getAll() {
		return dao.getAll();
	}
	
	public Fun_authVO getOneFun(String fun_no) {
		return dao.findByFun_no(fun_no);
	}
	
}
