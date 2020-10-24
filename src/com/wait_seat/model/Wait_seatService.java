package com.wait_seat.model;

import java.util.List;

import com.wait_seat.model.Wait_seatVO;

public class Wait_seatService {

	private Wait_seatDAO_interface dao;

	public Wait_seatService() {
		dao = new Wait_seatDAO();
	}

	public Wait_seatVO addWait_seat(String wait_seat_no,String mem_no,String n_mem_name,String phone_m) {

		Wait_seatVO wait_seatVO = new Wait_seatVO();

		wait_seatVO.setWait_seat_no(wait_seat_no);
		wait_seatVO.setMem_no(mem_no);
		wait_seatVO.setN_mem_name(n_mem_name);
		wait_seatVO.setPhone_m(phone_m);
		dao.insert(wait_seatVO);
		return wait_seatVO;
	}

	public Wait_seatVO updateNut(String wait_seat_no,String mem_no,String n_mem_name,String phone_m) {

		Wait_seatVO wait_seatVO = new Wait_seatVO();

		wait_seatVO.setWait_seat_no(wait_seat_no);
		wait_seatVO.setMem_no(mem_no);
		wait_seatVO.setN_mem_name(n_mem_name);
		wait_seatVO.setPhone_m(phone_m);
		dao.update(wait_seatVO);

		return wait_seatVO;
	}

	public void deleteNut(String wait_seat_no) {
		dao.delete(wait_seat_no);
	}

	public Wait_seatVO getOneNut(String wait_seat_no) {
		return dao.findByPrimaryKey(wait_seat_no);
	}
	
	public List<Wait_seatVO> getAll() {
		return dao.getAll();
	}
}
