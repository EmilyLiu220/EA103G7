package com.rest_day.model;

import java.util.List;

public class RestDayService {
	private RestDayDAO_interface dao;

	public RestDayService() {
//		dao = new SeatObjJDBCDAO();
		dao = new RestDayDAO();
	}

	public RestDayVO addRestDay(String emp_no, java.sql.Date rest_day_sup_sta, java.sql.Date rest_day_sup_end,
			String time_peri_no, Integer rest_day_fix, Integer rest_sts) {

		RestDayVO restDayVO = new RestDayVO();

		restDayVO.setEmp_no(emp_no);
		restDayVO.setRest_day_sup_sta(rest_day_sup_sta);
		restDayVO.setRest_day_sup_end(rest_day_sup_end);
		restDayVO.setTime_peri_no(time_peri_no);
		restDayVO.setRest_day_fix(rest_day_fix);
		restDayVO.setRest_sts(rest_sts);

		dao.insert(restDayVO);

		return restDayVO;
	}

	public RestDayVO updateRestDay(String rest_day_no, String emp_no, java.sql.Date rest_day_sup_sta,
			java.sql.Date rest_day_sup_end, String time_peri_no, Integer rest_day_fix, Integer rest_sts) {

		RestDayVO restDayVO = new RestDayVO();

		restDayVO.setRest_day_no(rest_day_no);
		restDayVO.setEmp_no(emp_no);
		restDayVO.setRest_day_sup_sta(rest_day_sup_sta);
		restDayVO.setRest_day_sup_end(rest_day_sup_end);
		restDayVO.setTime_peri_no(time_peri_no);
		restDayVO.setRest_day_fix(rest_day_fix);
		restDayVO.setRest_sts(rest_sts);

		dao.update(restDayVO);

		return restDayVO;
	}

	public RestDayVO getOneRestDay(String rest_day_no) {
		return dao.findByPrimaryKey(rest_day_no);
	}

	public List<RestDayVO> getAll() {
		return dao.getAll();
	}

	public void deleteRestDay(String rest_day_no) {
		dao.delete(rest_day_no);
	}
}
