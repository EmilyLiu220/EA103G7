package com.seat_obj.model;

import java.util.List;

public interface SeatObjDAO_interface {
	public void insert(SeatObjVO seatObjVO);

	public void update(SeatObjVO seatObjVO);

	public SeatObjVO findByPrimaryKey(String seat_obj_no);
	
	public List<SeatObjVO> getAll();
}
