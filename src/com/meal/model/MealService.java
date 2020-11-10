package com.meal.model;

import java.io.InputStream;
import java.util.List;

import com.meal_part.model.Meal_partVO;

public class MealService {

	private MealDAO_interface dao;

	public MealService() {
		dao = new MealDAO();
	}

	public MealVO addMeal(String meal_name, String meal_info, byte[] meal_img, Integer meal_price, Integer meal_sts,Integer cat_no,List<Meal_partVO> partList) {
		MealVO mealVO = new MealVO();
		mealVO.setMeal_name(meal_name);
		mealVO.setMeal_info(meal_info);
		mealVO.setMeal_img(meal_img);
		mealVO.setMeal_price(meal_price);
		mealVO.setMeal_sts(meal_sts);
		mealVO.setCat_no(cat_no);
		dao.insert(mealVO,partList);

		return mealVO;

	};

	public MealVO updateMeal(String meal_no, String meal_name, String meal_info, byte[] meal_img, Integer meal_price,
			Integer meal_sts,Integer cat_no,List<Meal_partVO> partList) {
		MealVO mealVO = new MealVO();
		mealVO.setMeal_no(meal_no);
		mealVO.setMeal_name(meal_name);
		mealVO.setMeal_info(meal_info);
		mealVO.setMeal_img(meal_img);
		mealVO.setMeal_price(meal_price);
		mealVO.setMeal_sts(meal_sts);
		mealVO.setCat_no(cat_no);
		dao.update(mealVO,partList);

		return mealVO;
	};
	
	public MealVO searchByNo(String keyWord) {
		return dao.searchByNo(keyWord);
	};
	
	public List<MealVO> searchByNoAndName(String keyWord) {
		return dao.searchByNoAndName(keyWord);
	};
	
	public List<MealVO> searchByMealSts(Integer mealSts) {
		return dao.searchByMealSts(mealSts);
	};


	public List<MealVO> getAll(){
		return dao.getAll();
	}
	
	public List<MealVO> getAll2(){
		return dao.getAll2();
	}

}
