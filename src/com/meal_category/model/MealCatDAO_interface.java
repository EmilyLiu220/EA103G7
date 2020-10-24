package com.meal_category.model;

import java.util.List;

import com.meal.model.MealVO;

public interface MealCatDAO_interface {
	
	public void insert(MealCatVO mealCatVO);
	public void update(MealCatVO mealCatVO);
//	public MealCatVO search(String mealCatNO);
	public List<MealCatVO> getAll();
	
	

}
