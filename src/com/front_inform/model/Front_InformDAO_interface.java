package com.front_inform.model;

import java.util.*;

public interface Front_InformDAO_interface{
	
	public void insertInfo(String mem_no, String info_cont);
	public void insertFromRO(String mem_no, String res_no, String info_cont);
	public void insertResCheInform(String res_no);
	public void updateSts(Front_InformVO front_informVO);
	public List<Front_InformVO> findByMemNo(String mem_no);
	public void updateReadSts(String mem_no);
	public List<Front_InformVO> findAll();
	
}
