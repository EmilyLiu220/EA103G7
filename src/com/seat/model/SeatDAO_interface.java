package com.seat.model;

import java.util.List;

public interface SeatDAO_interface {
	public void insert(SeatVO seatVO);

	public void update(SeatVO seatVO);

	public SeatVO findByPrimaryKey(String seat_no);

	public List<SeatVO> getAll();
}
