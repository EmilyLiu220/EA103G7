package com.res_order.model;

import java.util.List;

public class ResOrderService {
	private ResOrderDAO_interface dao;

	public ResOrderService() {
//		dao = new ResOrderJDBCDAO();
		dao = new ResOrderDAO();
	}

	public String addResOrder(String meal_order_no, String mem_no, String emp_no, java.sql.Date res_date,
			Integer people, String time_peri_no, Integer info_sts, Integer seat_sts, String[] seats_no) {

		ResOrderVO resOrderVO = new ResOrderVO();

		resOrderVO.setMeal_order_no(meal_order_no);
		resOrderVO.setMem_no(mem_no);
		resOrderVO.setEmp_no(emp_no);
		resOrderVO.setRes_date(res_date);
		resOrderVO.setPeople(people);
		resOrderVO.setTime_peri_no(time_peri_no);
		resOrderVO.setInfo_sts(info_sts);
		resOrderVO.setSeat_sts(seat_sts);

		String next_res_no = dao.insert(resOrderVO, seats_no);

		return next_res_no;
	}

	public ResOrderVO updateResOrder(String res_no, String meal_order_no, String mem_no, String emp_no,
			java.sql.Date res_date, Integer people, String time_peri_no, Integer info_sts, Integer seat_sts, String[] seats_no) {

		ResOrderVO resOrderVO = new ResOrderVO();

		resOrderVO.setRes_no(res_no);
		resOrderVO.setMeal_order_no(meal_order_no);
		resOrderVO.setMem_no(mem_no);
		resOrderVO.setEmp_no(emp_no);
		resOrderVO.setRes_date(res_date);
		resOrderVO.setPeople(people);
		resOrderVO.setTime_peri_no(time_peri_no);
		resOrderVO.setInfo_sts(info_sts);
		resOrderVO.setSeat_sts(seat_sts);

		dao.update(resOrderVO, seats_no);

		return resOrderVO;

	}
	public ResOrderVO findByMealOrderNO(String meal_order_no){
		return dao.findByMealOrderNO(meal_order_no);
	}

	public ResOrderVO getOneResOrder(String res_no) {
		return dao.findByPrimaryKey(res_no);
	}

	public List<ResOrderVO> getOneMemberResOrder(String mem_no, String sts) {
		return dao.findByMEM_NO_getAll(mem_no, sts);
	}

	public List<ResOrderVO> getResDate_And_TimePeri_getAll(String res_date, String time_peri_no) {
		return dao.findByResDate_And_TimePeri_getAll(res_date, time_peri_no);
	}

	public List<ResOrderVO> getAll() {
		return dao.getAll();
	}
	
	// 當日用餐確認通知需要用到的方法
	public List<ResOrderVO> getByResDate(String res_date) {
		return dao.findByResDate(res_date);
	}
}
