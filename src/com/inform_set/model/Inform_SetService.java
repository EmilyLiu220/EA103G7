package com.inform_set.model;

import java.util.List;

public class Inform_SetService {
	
	private Inform_SetDAO_interface dao;
	
	public Inform_SetService() {
		dao = new Inform_SetDAO();
	}
	
	public Inform_SetVO addIs(String emp_no, String is_cont, java.sql.Date is_date) {
		
		Inform_SetVO inform_setVO = new Inform_SetVO();
		
		inform_setVO.setEmp_no(emp_no);
		inform_setVO.setIs_cont(is_cont);
		inform_setVO.setIs_date(is_date);

		return dao.insert(inform_setVO);
	}
	
	public Inform_SetVO updateIs(String is_no, String emp_no, String is_cont, java.sql.Date is_date) {

		Inform_SetVO inform_setVO = new Inform_SetVO();
		
		inform_setVO.setIs_no(is_no);
		inform_setVO.setEmp_no(emp_no);
		inform_setVO.setIs_cont(is_cont);
		inform_setVO.setIs_date(is_date);
		dao.update(inform_setVO);
		
		return inform_setVO;
	}
	
	public void deleteIs(String is_no) {
		dao.delete(is_no);
	}
	
	public Inform_SetVO getOneIs(String is_no) {
		return dao.findByIsNo(is_no);
	}
	
	public List<Inform_SetVO> getIsByEmp(String emp_no) {
		return dao.findByEmpNo(emp_no);
	}
	
	public List<Inform_SetVO> getIsByCont(String is_cont) {
		return dao.findByIsCont(is_cont);
	}
	
	public List<Inform_SetVO> getIsByDate(String startDate, String stopDate) {
		return dao.findByDate(startDate, stopDate);
	}
	
	public List<Inform_SetVO> getIsByComplex(String is_no, String emp_no, String[] is_cont, String startDate, String stopDate){
		return dao.findByComplex(is_no, emp_no, is_cont, startDate, stopDate);
	}
	
	public List<Inform_SetVO> getAll() {
		return dao.getAll();
	}
	
}
