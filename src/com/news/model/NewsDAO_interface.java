package com.news.model;

import java.util.*;

public interface NewsDAO_interface {
	public void insert(NewsVO newsVO);
	public void update(NewsVO newsVO);
	public void delete(String news_no);
	public NewsVO findByPrimaryKey(String news_no);
	public List<NewsVO> getAll();
	public List<NewsVO> getnewsno(String emp_no);
	public List<NewsVO> frontNews_sts(Integer news_sts);
}
