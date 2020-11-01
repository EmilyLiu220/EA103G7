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
		
	public Map<String,Double> GetFdnoAndQtyByListMealOrderDetail(List<MealOrderDetailVO> list){
		Map<String,Integer> mealMap=new HashMap<String,Integer>(); 
		for(MealOrderDetailVO modVO:list) {
			if(modVO.getMeal_no()!=null) {
				if(mealMap.containsKey(modVO.getMeal_no())) { 
					mealMap.put(modVO.getMeal_no(), mealMap.get(modVO.getMeal_no())+modVO.getQty());
				}else{
					mealMap.put(modVO.getMeal_no(),modVO.getQty());
				}
			}
		}		
		Map<String,Integer> mealSetMap=new HashMap<String,Integer>();
		for(MealOrderDetailVO modVO:list) {
			if(modVO.getMeal_set_no()!=null) {
				mealSetMap.put(modVO.getMeal_set_no(),modVO.getQty());
			}
		}
		MealSetConService MSCSvc=new MealSetConService();
		for(Map.Entry<String,Integer> mealSetnoMap:mealSetMap.entrySet()) {
			for(MealSetConVO mscVO:MSCSvc.searchBySetNo(mealSetnoMap.getKey())) {
				if(mealMap.containsKey(mscVO.getMeal_no())) { 
					mealMap.put(mscVO.getMeal_no(), mealMap.get(mscVO.getMeal_no())+mscVO.getMeal_qty()*mealSetnoMap.getValue());
				}else {
					mealMap.put(mscVO.getMeal_no(), mscVO.getMeal_qty()*mealSetnoMap.getValue());
				}
			}
		}
		Meal_partService MPsvc=new Meal_partService();
		Map<String,Double> foodMap=new HashMap<String,Double>();
		for(Map.Entry<String,Integer> mealnoMap:mealMap.entrySet()) {
			for(Meal_partVO meal_partVO:MPsvc.get_meal_part_by_mealno(mealnoMap.getKey())) {
				if(foodMap.containsKey(meal_partVO.getFd_no())) {
					foodMap.put(meal_partVO.getFd_no(), foodMap.get(meal_partVO.getFd_no())+meal_partVO.getFd_gw()*mealnoMap.getValue());
				}else {
					foodMap.put(meal_partVO.getFd_no(), meal_partVO.getFd_gw()*mealnoMap.getValue());
				}
			}
		}
		return foodMap;
	}	
	
	public boolean check_food_and_update(List<MealOrderDetailVO> list) { 
		FoodService FDsvc=new FoodService();
		Map<String,Double> foodMap=FDsvc.GetFdnoAndQtyByListMealOrderDetail(list);
		boolean enough=true;
		for(Map.Entry<String,Double> fdnoMap:foodMap.entrySet()) {
			if(FDsvc.getOneFood(fdnoMap.getKey()).getFd_stk()-fdnoMap.getValue()<0) {
				enough=false;
				break;
			}
		}

		if(enough) {
			for(Map.Entry<String,Double> fdnoMap:foodMap.entrySet()) {
				String fd_no=FDsvc.getOneFood(fdnoMap.getKey()).getFd_no();
				String fd_name=FDsvc.getOneFood(fdnoMap.getKey()).getFd_name();
				Integer fd_isdel=FDsvc.getOneFood(fdnoMap.getKey()).getFd_isdel();
				Integer fd_stk=FDsvc.getOneFood(fdnoMap.getKey()).getFd_stk()-fdnoMap.getValue().intValue(); 
				//時間不夠了，照理說應該要食材庫存與庫存底線都改成Double，這裡直接將Double轉成Integer
				Integer stk_ll=FDsvc.getOneFood(fdnoMap.getKey()).getStk_ll();
				Double cal=FDsvc.getOneFood(fdnoMap.getKey()).getCal();
				Double prot=FDsvc.getOneFood(fdnoMap.getKey()).getProt();
				Double carb=FDsvc.getOneFood(fdnoMap.getKey()).getCarb();
				Double fat=FDsvc.getOneFood(fdnoMap.getKey()).getFat();	
				FDsvc.updateFood(fd_no, fd_name, fd_isdel, fd_stk, stk_ll, cal, prot, carb, fat);
			}
			return true;
		}
		return false;
	}
}
