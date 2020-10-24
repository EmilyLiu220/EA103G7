package com.message_record.model;

import java.util.List;

public interface Message_RecordDAO_interface {
	
	public void insert(Message_RecordVO message_recordVO);	
	public List<Message_RecordVO> findByMemNo(String mem_no);
	public List<Message_RecordVO> findByEmpNo(String emp_no);
	public void updateMemReadSts(String mem_no);
	public void updateEmpReadSts(String emp_no);
	
}
