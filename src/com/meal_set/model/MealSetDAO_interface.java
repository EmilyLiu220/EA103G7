package com.meal_set.model;

import java.util.List;

import com.meal_set_consist.model.MealSetConVO;

public interface MealSetDAO_interface {
	
	public void insert(MealSetVO mealSetVO,List<MealSetConVO>conList);
	public void update(MealSetVO mealSetVO,List<MealSetConVO>conList);
	public MealSetVO searchByNo(String keyWord);
	public List<MealSetVO> searchByNoAndName(String keyWord);
	public List<MealSetVO> searchByMealSetSts(Integer mealSetSts);
	public List<MealSetVO> getAll();
	
}
