package com.food.model;

import java.util.List;

public interface FoodDAO_interface {
	public void insert(FoodVO foodVO);
	public void update(FoodVO foodVO);
	public void delete(String fd_no);
	public FoodVO findByPrimaryKey(String fd_no);
	public List<FoodVO> getAll();
	public String getFdnameByFdno(String fd_no);
}
