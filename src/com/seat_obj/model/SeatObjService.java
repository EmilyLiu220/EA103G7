package com.seat_obj.model;

import java.util.List;

public class SeatObjService {
	private SeatObjDAO_interface dao;

	public SeatObjService() {
//		dao = new SeatObjJDBCDAO();
		dao = new SeatObjDAO();
	}

	public SeatObjVO addSeatObj(byte[] seat_obj, Integer seat_obj_sts, Integer seat_people, Integer seat_use) {

		SeatObjVO seatObjVO = new SeatObjVO();

		seatObjVO.setSeat_obj(seat_obj);
		seatObjVO.setSeat_obj_sts(seat_obj_sts);
		seatObjVO.setSeat_people(seat_people);
		seatObjVO.setSeat_use(seat_use);

		dao.insert(seatObjVO);

		return seatObjVO;
	}

	public SeatObjVO updateSeatObj(String seat_obj_no, byte[] seat_obj, Integer seat_obj_sts, Integer seat_people, Integer seat_use) {

		SeatObjVO seatObjVO = new SeatObjVO();

		seatObjVO.setSeat_obj_no(seat_obj_no);
		seatObjVO.setSeat_obj(seat_obj);
		seatObjVO.setSeat_obj_sts(seat_obj_sts);
		seatObjVO.setSeat_people(seat_people);
		seatObjVO.setSeat_use(seat_use);

		dao.update(seatObjVO);

		return seatObjVO;
	}
	
	public SeatObjVO getOneSeatObj(String seat_obj_no) {
		return dao.findByPrimaryKey(seat_obj_no);
	}
	
	public List<SeatObjVO> getAll() {
		return dao.getAll();
	}
}
