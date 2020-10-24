package com.emp.model;

import java.util.List;

public class EmpService {

	private EmpDAO_interface dao;
	
	public EmpService() {
		dao = new EmpDAO();
	}
	
	public EmpVO addEmp(String emp_name) {
		
		EmpVO empVO = new EmpVO();
		
		empVO.setEmp_name(emp_name);
		dao.insert(empVO);
		
		return empVO;
	}
	
	public EmpVO updateByEmp(String emp_psw, String emp_name, String emp_no) {
		
		EmpVO empVO = new EmpVO();
		
		empVO.setEmp_psw(emp_psw);
		empVO.setEmp_name(emp_name);
		empVO.setEmp_no(emp_no);
		dao.updateByEmp(empVO);
		
		return empVO;
	}
	
	public EmpVO updateBySv(Integer emp_sts, String emp_no) {
		
		EmpVO empVO = new EmpVO();
		
		empVO.setEmp_sts(emp_sts);
		empVO.setEmp_no(emp_no);
		dao.updateBySv(empVO);
		
		return empVO;
	}
	
	public EmpVO getOneEmp(String emp_no) {
		return dao.findByEmp_no(emp_no);
	}
	
	public List<EmpVO> getAll() {
		return dao.getAll();
	}
	
}
