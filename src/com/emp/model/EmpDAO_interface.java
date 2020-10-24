package com.emp.model;

import java.util.List;

public interface EmpDAO_interface {
	public Object insert(EmpVO empVO);
	public void updateByEmp(EmpVO empVO);
	public void updateBySv(EmpVO empVO);
	public EmpVO findByEmp_no(String emp_no);
	public List<EmpVO> getAll();
}
