package com.meal_order.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.meal_order_detail.model.MealOrderDetailVO;

public class MealOrderService {

	private MealOrderDAO_interface dao;

	public MealOrderService() {
		dao = new MealOrderDAO();
	}

	public Map<String,Object> addOrder(String memNo, String empNo, Integer mealOrderSts, Integer amount, Integer notiSts,
			Integer paySts, Timestamp pickupTime,List<MealOrderDetailVO> detailList,Connection ...rescon) {
		
		Map<String,Object> map = new HashMap<>();

		MealOrderVO mealOrderVO = new MealOrderVO();
		mealOrderVO.setMem_no(memNo);
		mealOrderVO.setEmp_no(empNo);
		mealOrderVO.setMeal_order_sts(mealOrderSts);
		mealOrderVO.setAmount(amount);
		mealOrderVO.setNoti_sts(notiSts);
		mealOrderVO.setPay_sts(paySts);
//		mealOrderVO.setOrder_time(orderTime);
		mealOrderVO.setPickup_time(pickupTime);
		
		map = dao.insert(mealOrderVO,detailList,rescon);
		return map;

	}

	public MealOrderVO updateOrderSts(String mealOrderNo,Integer mealOrderSts, Integer notiSts, Integer paySts) {
		MealOrderVO mealOrderVO = new MealOrderVO();
		mealOrderVO.setMeal_order_no(mealOrderNo);
		mealOrderVO.setMeal_order_sts(mealOrderSts);
		mealOrderVO.setNoti_sts(notiSts);
		mealOrderVO.setPay_sts(paySts);
		dao.update(mealOrderVO);
		return mealOrderVO;
	}
	
	public void updatePickupTime(String mealOrderNo) {
		dao.updatePickupTime(mealOrderNo);
	}
	
	public List<MealOrderVO> searchToday(Date today){
		return dao.searchToday(today);
	}
	
	public List<MealOrderVO> searchByMemNo(String memNo) {
		return dao.searchByMemNo(memNo);

	}

	public MealOrderVO searchByOrderNo(String mealOrderNo) {
		return dao.searchByOrderNo(mealOrderNo);

	}

	public List<MealOrderVO> searchByOrderSts(Integer mealOrderSts) {
		return dao.searchByOrderSts(mealOrderSts);

	}

	public List<MealOrderVO> searchByNotiSts(Integer notiSts) {
		return dao.searchByOrderSts(notiSts);

	}

	public List<MealOrderVO> searchByPaySts(Integer paySts) {
		return dao.searchByOrderSts(paySts);

	}

	public List<MealOrderVO> getAll() {
		return dao.getAll();
	};
	
	public List<MealOrderVO> getAll(Map<String,String[]> queryMap) {
		return dao.getAll(queryMap);
	};
	
	public String dateFormat(Timestamp timestamp) {
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return ft.format(timestamp);
		
	}

}
