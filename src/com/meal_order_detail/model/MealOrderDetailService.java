package com.meal_order_detail.model;

import java.sql.Connection;
import java.util.List;

public class MealOrderDetailService {

	private MealOrderDetailDAO_interface dao;

	public MealOrderDetailService() {
		dao = new MealOrderDetailDAO();
	}

	public MealOrderDetailVO addDetail(String mealOrderNo, String mealNo, String mealSetNo, Integer qty,
			Integer detailAmount, Integer asgnSts,Connection con) {

		MealOrderDetailVO mealOrderDetailVO = new MealOrderDetailVO();
		mealOrderDetailVO.setMeal_order_no(mealOrderNo);
		mealOrderDetailVO.setMeal_no(mealNo);
		mealOrderDetailVO.setMeal_set_no(mealSetNo);
		mealOrderDetailVO.setQty(qty);
		mealOrderDetailVO.setDetail_amount(detailAmount);
		mealOrderDetailVO.setAsgn_sts(asgnSts);
		dao.insert(mealOrderDetailVO,con);
		return mealOrderDetailVO;
	}

	public MealOrderDetailVO updateDetail(String mealOrderDetailNo, String mealOrderNo, String mealNo, String mealSetNo,
			Integer qty, Integer detailAmount, Integer asgnSts) {

		MealOrderDetailVO mealOrderDetailVO = new MealOrderDetailVO();
		mealOrderDetailVO.setMeal_order_no(mealOrderDetailNo);
		mealOrderDetailVO.setMeal_order_no(mealOrderNo);
		mealOrderDetailVO.setMeal_no(mealNo);
		mealOrderDetailVO.setMeal_set_no(mealSetNo);
		mealOrderDetailVO.setQty(qty);
		mealOrderDetailVO.setDetail_amount(detailAmount);
		mealOrderDetailVO.setAsgn_sts(asgnSts);
		dao.update(mealOrderDetailVO);
		return mealOrderDetailVO;
	}

	public List<MealOrderDetailVO> searchByOrderNo(String mealOrderNo) {
		return dao.searchByOrderNo(mealOrderNo);
	}
	public List<MealOrderDetailVO> searchByAsgnSts(Integer asgnSts) {
		return dao.searchByAsgnSts(asgnSts);
	}
}
