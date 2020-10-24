package com.emp_auth.model;

import java.util.List;

import com.emp.model.EmpVO;

public interface Emp_authDAO_interface {
	public void insert(Emp_authVO emp_authVO);
	public void delete(String emp_no);
	public List<Emp_authVO> findByEmp_no(String emp_no);
	public List<Emp_authVO> getAll();
}
