package com.front_inform.model;

import java.util.*;

public interface Front_InformDAO_interface{
	
	public Front_InformVO findByFiNo(String info_no);
	
	// 新增或修改時，會回傳新增或修改的筆數，以此判斷是否呼叫 webSocket 執行推播動作
	public Front_InformVO insertInfo(String mem_no, String info_cont);
	public Front_InformVO insertFromRO(String mem_no, String res_no, String info_cont);
	public Front_InformVO insertResCheInform(String res_no);
	
	// 大量新增使用(for Inform_Set 的 insert)
	public List<Front_InformVO> insertManyIs(String is_no);
	
	public Integer updateSts(Front_InformVO front_informVO);
	public List<Front_InformVO> findByMemNo(String mem_no);
	public Integer updateReadSts(String mem_no);
	public List<Front_InformVO> findAll();
	
	// 取得特殊通知
	public List<Front_InformVO> findByComplex(String mem_no, Integer info_sts, String startDate, String stopDate);
	
	// for pollingThread_FI_Backup
	public Integer countData();
	public List<Front_InformVO> findNew(Integer count);
	
}
