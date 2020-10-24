package com.mem.model;

import java.util.List;

public interface MemDAO_interface {
	public Object insert(MemVO memVO);
	public void updateByMem(MemVO memVO);
	public void updateByEmp(MemVO memVO);
	public void forgetPsw(MemVO memVO);
	public MemVO login(String mem_act);
	public MemVO findByMem_no(String mem_no);
	public List<MemVO> getAll();
}
