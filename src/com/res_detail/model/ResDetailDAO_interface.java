package com.res_detail.model;

import java.sql.Connection;
import java.util.List;

public interface ResDetailDAO_interface {
	public void insert(ResDetailVO resDetailVO, Connection outer_con);

	public void update(ResDetailVO resDetailVO);
	
	public void delete(String res_no, String[]seats_no, Connection outer_con);
	
	public ResDetailVO findByPrimaryKey(String res_de_no);

	public List<ResDetailVO> getAll();
	
	public List<ResDetailVO> getResNoAll(String res_no);
}
