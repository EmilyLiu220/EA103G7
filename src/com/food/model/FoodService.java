package com.food.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.model.FoodVO;
import com.food.model.FoodService;
import com.meal_set_consist.model.MealSetConVO;
import com.meal_set_consist.model.MealSetConService;
import com.meal_order_detail.model.MealOrderDetailVO;
import com.meal_part.model.Meal_partService;
import com.meal_part.model.Meal_partVO;;
public class FoodService {

	private FoodDAO_interface dao;

	public FoodService() {
		dao = new FoodDAO();
	}

	public FoodVO addFood(String fd_name,int fd_isdel,int fd_stk,int stk_ll,double cal,double prot,double carb,double fat) {

		FoodVO foodVO = new FoodVO();

		foodVO.setFd_name(fd_name);
		foodVO.setFd_isdel(fd_isdel);
		foodVO.setFd_stk(fd_stk);
		foodVO.setStk_ll(stk_ll);
		foodVO.setCal(cal);
		foodVO.setProt(prot);
		foodVO.setCarb(carb);
		foodVO.setFat(fat);
		dao.insert(foodVO);
		return foodVO;
	}

	public FoodVO updateFood(String fd_No, String fd_name,int fd_isdel,int fd_stk,int stk_ll,double cal,double prot,double carb,double fat) {

		FoodVO foodVO = new FoodVO();

		foodVO.setFd_no(fd_No);
		foodVO.setFd_name(fd_name);
		foodVO.setFd_isdel(fd_isdel);
		foodVO.setFd_stk(fd_stk);
		foodVO.setStk_ll(stk_ll);
		foodVO.setCal(cal);
		foodVO.setProt(prot);
		foodVO.setCarb(carb);
		foodVO.setFat(fat);
		dao.update(foodVO);

		return foodVO;
	}

	public void deleteFood(String fd_no) {
		dao.delete(fd_no);
	}

	public FoodVO getOneFood(String fd_no) {
		return dao.findByPrimaryKey(fd_no);
	}
	
	public List<FoodVO> getAll() {
		return dao.getAll();
	}
	
	public Double get_cal_by_VO(FoodVO foodVO) {
		return foodVO.getCal();
	}
}
