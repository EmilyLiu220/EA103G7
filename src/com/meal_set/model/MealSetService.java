package com.meal_set.model;

import java.util.List;

import com.meal_set_consist.model.MealSetConVO;

public class MealSetService {

	private MealSetDAO_interface dao;

	public MealSetService() {
		dao = new MealSetDAO();
	}

	public MealSetVO addMealSet(String mealSetName, String mealSetInfo, byte[] mealSetImg, Integer mealSetPrice,
			Integer mealSetSts, Integer CatNo,List<MealSetConVO> conList) {
		MealSetVO mealSetVO = new MealSetVO();
		mealSetVO.setMeal_set_name(mealSetName);
		mealSetVO.setMeal_set_info(mealSetInfo);
		mealSetVO.setMeal_set_img(mealSetImg);
		mealSetVO.setMeal_set_price(mealSetPrice);
		mealSetVO.setMeal_set_sts(mealSetSts);
		mealSetVO.setCat_no(CatNo);
		dao.insert(mealSetVO,conList);
		return mealSetVO;
	}

	public MealSetVO updateMealSet(String mealSetNo, String mealSetName, String mealSetInfo, byte[] mealSetImg,
			Integer mealSetPrice, Integer mealSetSts, Integer CatNo,List<MealSetConVO> conList) {
		MealSetVO mealSetVO = new MealSetVO();
		mealSetVO.setMeal_set_no(mealSetNo);
		mealSetVO.setMeal_set_name(mealSetName);
		mealSetVO.setMeal_set_info(mealSetInfo);
		mealSetVO.setMeal_set_img(mealSetImg);
		mealSetVO.setMeal_set_price(mealSetPrice);
		mealSetVO.setMeal_set_sts(mealSetSts);
		mealSetVO.setCat_no(CatNo);
		dao.update(mealSetVO,conList);
		return mealSetVO;
	}

	public MealSetVO searchByNo(String keyWord) {
		return dao.searchByNo(keyWord);
	};

	public List<MealSetVO> searchByNoAndName(String keyWord) {
		return dao.searchByNoAndName(keyWord);
	};
	
	public List<MealSetVO> searchByMealSetSts(Integer mealSetSts) {
		return dao.searchByMealSetSts(mealSetSts);
	};

	public List<MealSetVO> getAll() {
		return dao.getAll();
	};

}
