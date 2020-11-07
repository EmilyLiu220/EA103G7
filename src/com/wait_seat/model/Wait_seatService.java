package com.wait_seat.model;

import java.util.List;

public class Wait_seatService {

	private Wait_seatDAO_interface dao;

	public Wait_seatService() {
		dao =new Wait_seatDAO();
	}

	public Wait_seatVO addWait_seat(String mem_no,String n_mem_name,String phone_m,Integer delay) {

		Wait_seatVO wait_seatVO = new Wait_seatVO();

		wait_seatVO.setMem_no(mem_no);
		wait_seatVO.setN_mem_name(n_mem_name);
		wait_seatVO.setPhone_m(phone_m);
		wait_seatVO.setDelay(delay);
		dao.insert(wait_seatVO);
		return wait_seatVO;
	}

	public Wait_seatVO updateWait_seat(String wait_seat_no,String mem_no,String n_mem_name,String phone_m,Integer delay,Integer wait_n) {

		Wait_seatVO wait_seatVO = new Wait_seatVO();

		wait_seatVO.setWait_seat_no(wait_seat_no);
		wait_seatVO.setMem_no(mem_no);
		wait_seatVO.setN_mem_name(n_mem_name);
		wait_seatVO.setPhone_m(phone_m);
		wait_seatVO.setDelay(delay);
		wait_seatVO.setWait_n(wait_n);
		dao.update(wait_seatVO);

		return wait_seatVO;
	}
	
	public Wait_seatVO update2Wait_seat(String wait_seat_no,String mem_no,String n_mem_name,String phone_m,Integer delay,String new_wait_seat_no) {

		Wait_seatVO wait_seatVO = new Wait_seatVO();

		wait_seatVO.setWait_seat_no(wait_seat_no);
		wait_seatVO.setMem_no(mem_no);
		wait_seatVO.setN_mem_name(n_mem_name);
		wait_seatVO.setPhone_m(phone_m);
		wait_seatVO.setDelay(delay);
		dao.update2(wait_seatVO, new_wait_seat_no);

		return wait_seatVO;
	}

	public void deleteWait_seat(String wait_seat_no) {
		dao.delete(wait_seat_no);
	}

	public Wait_seatVO getOneWait_seat(String wait_seat_no) {
		return dao.findByPrimaryKey(wait_seat_no);
	}
	
	public List<Wait_seatVO> getAll() {
		return dao.getAll();
	}
	
	public Wait_seatVO getFirst() {
		return dao.getFirst(); //查第一筆資訊
	}
	
	public Integer getCount() {
		return dao.getAll().size();
	}
	
	public List<Wait_seatVO> getAllForUser() {
		return dao.getAllForUser(); //顯示給客人看得
	}
}
