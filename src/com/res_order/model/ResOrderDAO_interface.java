package com.res_order.model;

import java.util.List;

public interface ResOrderDAO_interface {
	public String insert(ResOrderVO resOrderVO, String seats_no[]);

	public void update(ResOrderVO resOrderVO);

	public ResOrderVO findByPrimaryKey(String res_no);
	
	public ResOrderVO findByMEM_NO(String mem_no);
	
	public List<ResOrderVO> findByMEM_NO_getAll(String mem_no);
	
	public List<ResOrderVO> findByResDate_And_TimePeri_getAll(String res_date, String time_peri_no);
	
	public List<ResOrderVO> getAll();
}
