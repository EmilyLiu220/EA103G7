package com.fun_auth.model;

import java.util.List;

public interface Fun_authDAO_interface {
	public void insert(Fun_authVO fun_authVO);
	public void update(Fun_authVO fun_authVO);
	public void delete(String fun_no);
	public List<Fun_authVO> getAll();
	public Fun_authVO findByFun_no(String fun_no);
}
