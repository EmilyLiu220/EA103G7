package com.meal_part.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import com.food.model.*;

public interface Meal_partDAO_interface {
	public void insert(Meal_partVO meal_partVO);
	public void insert(List<Meal_partVO>list,Connection con);
	public void update(Meal_partVO meal_partVO);
	public void delete(String meal_no,String fd_no);
	public Meal_partVO findByPrimaryKey(String meal_no,String fd_no);
	public List<Meal_partVO> getAll();
	public Map<String,Double> get_NUT_ByMealno(String meal_no);
}
