package com.emp_auth.model;

import java.util.List;

public class Emp_authService {

	private Emp_authDAO_interface dao;
	
	public Emp_authService() {
		dao = new Emp_authDAO();
	}
	
	public Emp_authVO addEmp_auth(String fun_no, String emp_no) {
		
		Emp_authVO emp_authVO = new Emp_authVO();
		
		emp_authVO.setFun_no(fun_no);
		emp_authVO.setEmp_no(emp_no);
		dao.insert(emp_authVO);
		
		return emp_authVO;
	}
	
	public void deleteEmp_auth(String emp_no) {
		dao.delete(emp_no);
	}
	
	public List<Emp_authVO> getOneEmp_auth(String emp_no) {
		return dao.findByEmp_no(emp_no);
	}
	
	public List<Emp_authVO> getAll() {
		return dao.getAll();
	}
	
}
