package com.meal.model;

import java.util.*;

import com.meal_part.model.Meal_partVO;

public interface MealDAO_interface {
	 public void insert(MealVO mealVO,List<Meal_partVO> partList);
     public void update(MealVO mealVO,List<Meal_partVO> partList);
     public MealVO searchByNo(String keyWord);
     public List<MealVO> searchByNoAndName(String keyWord);
//     public MealVO findByMealNo(String mealNo);
//     public MealVO findByMealName(String mealName);
     public List<MealVO> searchByMealSts(Integer mealSts);
     public List<MealVO> getAll();
     public List<MealVO> getAll2();
	
}
