package com.meal_set_consist.model;

import java.sql.Connection;
import java.util.*;

public class MealSetConService {

	private MealSetConDAO_interface dao;

	public MealSetConService() {
		dao = new MealSetConDAO();
	}

	public MealSetConVO addMealSetCon(String mealSetNo, String mealNo, Integer qty,Connection con) {

		MealSetConVO mealSetConVO = new MealSetConVO();
		mealSetConVO.setMeal_set_no(mealSetNo);
		mealSetConVO.setMeal_no(mealNo);
		mealSetConVO.setMeal_qty(qty);
		dao.insert(mealSetConVO,con);
		return mealSetConVO;
	}
	
	public  void delete(String mealSetNo) {
		dao.delete(mealSetNo);
	}
	
	public List<MealSetConVO> searchBySetNo(String mealSetNo){
		
		return dao.searchBySetNo(mealSetNo);
		
	}
	
	public List<MealSetConVO> getAll(){
		return dao.getAll();
	}

}
