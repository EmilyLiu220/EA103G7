package com.rest_day.model;

import java.util.List;

public interface RestDayDAO_interface {
	public void insert(RestDayVO restDayVO);

	public void update(RestDayVO restDayVO);

	public void delete(String rest_day_no);

	public RestDayVO findByPrimaryKey(String rest_day_no);

	public List<RestDayVO> getAll();
}
