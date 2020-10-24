package com.meal_part.model;

import java.util.List;
import java.util.Map;

import com.meal_part.model.Meal_partVO;

public class Meal_partService {

	private Meal_partDAO_interface dao;
	

	public Meal_partService() {
		dao = new Meal_partDAO();
	}

	public Meal_partVO addMeal_part(String meal_no,String fd_no,Double fd_gw) {

		Meal_partVO meal_partVO = new Meal_partVO();

		meal_partVO.setMeal_no(meal_no);
		meal_partVO.setFd_no(fd_no);
		meal_partVO.setFd_gw(fd_gw);
		dao.insert(meal_partVO);
		return meal_partVO;
	}

	public Meal_partVO updateMeal_part(String meal_no,String fd_no,Double fd_gw) {

		Meal_partVO meal_partVO = new Meal_partVO();
		meal_partVO.setMeal_no(meal_no);
		meal_partVO.setFd_no(fd_no);
		meal_partVO.setFd_gw(fd_gw);
		dao.update(meal_partVO);
		return meal_partVO;
	}

	public void deleteMeal_part(String meal_no,String fd_no) {
		dao.delete(meal_no,fd_no);
	}

	public Meal_partVO getOneMeal_part(String meal_no,String fd_no) {
		return dao.findByPrimaryKey(meal_no,fd_no);
	}
	
	public List<Meal_partVO> getAll() {
		return dao.getAll();
	}	
	
	public Map<String,Double> getNut(String meal_no) {
		return dao.get_NUT_ByMealno(meal_no);
	}	
	
}
