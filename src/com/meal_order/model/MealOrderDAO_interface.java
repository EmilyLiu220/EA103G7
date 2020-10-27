package com.meal_order.model;

import java.sql.Connection;
import java.util.*;

import com.meal_order_detail.model.MealOrderDetailVO;

public interface MealOrderDAO_interface {
	
	public Map<String,Object> insert (MealOrderVO mealOrderVO, List<MealOrderDetailVO> detailList,Connection ... rescon);
	public void update (MealOrderVO mealOrderVO);
	public void updatePickupTime (String mealOrderNo);
	public MealOrderVO searchByOrderNo(String mealOrderNo);
	public List<MealOrderVO> searchByMemNo(String memNo);
	public List<MealOrderVO>searchByOrderSts(Integer orderSts);
	public List<MealOrderVO>searchByNotiSts(Integer orderSts);
	public List<MealOrderVO>searchByPaySts(Integer orderSts);
	public List<MealOrderVO> searchToday(Date today);
	public List<MealOrderVO>getAll();
	public List<MealOrderVO>getAll(Map<String,String[]> queryMap);
	
	
}
