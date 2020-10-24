package com.seat.model;

import java.util.List;

public class SeatService {
	
	private SeatDAO_interface dao;

	public SeatService() {
//		dao = new SeatJDBCDAO();
		dao = new SeatDAO();
	}


	public SeatVO addSeat(String seat_obj_no, String seat_name, Double seat_x, Double seat_y,
			Integer seat_l, Integer seat_w, Integer seat_f) {

		SeatVO seatVO = new SeatVO();

		seatVO.setSeat_obj_no(seat_obj_no);
		seatVO.setSeat_name(seat_name);
		seatVO.setSeat_isdel(0);
		seatVO.setSeat_x(seat_x);
		seatVO.setSeat_y(seat_y);
		seatVO.setSeat_l(seat_l);
		seatVO.setSeat_w(seat_w);
		seatVO.setSeat_f(seat_f);

		dao.insert(seatVO);

		return seatVO;
	}

	public SeatVO updateSeat(String seat_no, String seat_obj_no, String seat_name, Integer seat_isdel, Double seat_x, Double seat_y,
			Integer seat_l, Integer seat_w, Integer seat_f) {

		SeatVO seatVO = new SeatVO();
		
		seatVO.setSeat_no(seat_no);
		seatVO.setSeat_obj_no(seat_obj_no);
		seatVO.setSeat_name(seat_name);
		seatVO.setSeat_isdel(seat_isdel);
		seatVO.setSeat_x(seat_x);
		seatVO.setSeat_y(seat_y);
		seatVO.setSeat_l(seat_l);
		seatVO.setSeat_w(seat_w);
		seatVO.setSeat_f(seat_f);

		dao.update(seatVO);

		return seatVO;

	}

	public SeatVO getOneSeat(String seat_no) {
		return dao.findByPrimaryKey(seat_no);
	}

	public List<SeatVO> getAll() {
		return dao.getAll();
	}
}
