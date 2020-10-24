package com.meal_category.model;

import java.util.List;

public class MealCatService {

	private MealCatDAO_interface dao;

	public MealCatService() {
		dao = new MealCatDAO();
	}

	public MealCatVO addMealCat(String mealCat_name) {
		MealCatVO mealCatVO = new MealCatVO();

		mealCatVO.setCat_name(mealCat_name);
		dao.insert(mealCatVO);

		return mealCatVO;
	}

	public MealCatVO updateMealCat(Integer mealCat_no, String mealCat_name) {
		MealCatVO mealCatVO = new MealCatVO();
		mealCatVO.setCat_no(mealCat_no);
		mealCatVO.setCat_name(mealCat_name);
		dao.update(mealCatVO);
		return mealCatVO;
	}

	public List<MealCatVO> getall() {
		return dao.getAll();

	}

}
