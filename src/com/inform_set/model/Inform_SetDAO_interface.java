package com.inform_set.model;

import java.util.List;

public interface Inform_SetDAO_interface {
	
	public Inform_SetVO insert(Inform_SetVO inform_setVO);
	public void update(Inform_SetVO inform_setVO);
	public void delete(String is_no);
	public Inform_SetVO findByIsNo(String is_no);
	public List<Inform_SetVO> findByEmpNo(String emp_no);
	public List<Inform_SetVO> findByIsCont(String is_cont);
	public List<Inform_SetVO> findByDate(String startDate, String stopDate);
	public List<Inform_SetVO> findByComplex(String is_no, String emp_no, String[] is_cont, String startDate, String stopDate);
	public List<Inform_SetVO> getAll();

}
