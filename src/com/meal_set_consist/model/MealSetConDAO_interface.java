package com.meal_set_consist.model;

import java.sql.Connection;
import java.util.List;

public interface MealSetConDAO_interface {
	
	public void insert(MealSetConVO mealSetConVO,Connection con);
//	public void update(MealSetConVO mealSetConVO);
	public void delete(String mealSetConNo);
	public List<MealSetConVO> searchBySetNo(String meal_set_no);
//	public MealSetConVO searchByMealNo();
	public List<MealSetConVO> getAll();

}
