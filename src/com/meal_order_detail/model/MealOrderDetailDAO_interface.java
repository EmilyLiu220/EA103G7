package com.meal_order_detail.model;

import java.sql.Connection;
import java.util.List;

public interface MealOrderDetailDAO_interface {
	
	public void insert(MealOrderDetailVO mealOrderDetailVO,Connection con);
	public void update(MealOrderDetailVO mealOrderDetailVO);
	public List<MealOrderDetailVO> searchByOrderNo(String mealOrderNo);
	public List<MealOrderDetailVO> searchByAsgnSts(Integer asgnSts);

}
