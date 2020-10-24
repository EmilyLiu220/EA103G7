package com.time_peri.model;

import java.util.List;

public interface TimePeriDAO_interface {
	public void insert(TimePeriVO timePeriVO);

	public void update(TimePeriVO timePeriVO);

	public TimePeriVO findByPrimaryKey(String time_peri_no);

	public List<TimePeriVO> getAll();
}
