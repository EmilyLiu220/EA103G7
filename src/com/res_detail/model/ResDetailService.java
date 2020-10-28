package com.res_detail.model;

import java.sql.Connection;
import java.util.List;

public class ResDetailService {
	private ResDetailDAO_interface dao;

	public ResDetailService() {
//		dao = new ResDetailJDBCDAO();
		dao = new ResDetailDAO();
	}
	
	public ResDetailVO addResDetail(String seat_no, String res_no, Connection outer_con) {

		ResDetailVO resDetailVO = new ResDetailVO();

		resDetailVO.setSeat_no(seat_no);
		resDetailVO.setRes_no(res_no);

		dao.insert(resDetailVO, outer_con);

		return resDetailVO;
	}

	public ResDetailVO updateResDetail(String res_de_no, String seat_no, String res_no) {

		ResDetailVO resDetailVO = new ResDetailVO();
		
		resDetailVO.setRes_de_no(res_de_no);
		resDetailVO.setSeat_no(seat_no);
		resDetailVO.setRes_no(res_no);

		dao.update(resDetailVO);

		return resDetailVO;

	}
	
	public void deleteResDetail (String res_no, String[] seats_no, Connection outer_con) {
		dao.delete(res_no, seats_no, outer_con);
	}

	public ResDetailVO getOneResOrder(String res_de_no) {
		return dao.findByPrimaryKey(res_de_no);
	}
	
	public List<ResDetailVO> getAllResNO(String res_no) {
		return dao.getResNoAll(res_no);
	}

	public List<ResDetailVO> getAll() {
		return dao.getAll();
	}
}
