package com.ad.model;

import java.util.List;

import com.ad.model.AdVO;

public interface AdDAO_interface {
	public void insert(AdVO adVO);
	public void update(AdVO adVO);
	public void delete(String ad_no);
	public AdVO findByPrimaryKey(String ad_no);
	public List<AdVO> getAll();
}


