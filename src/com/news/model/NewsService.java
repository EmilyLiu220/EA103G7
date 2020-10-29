package com.news.model;

import java.util.List;


import com.news.model.NewsVO;

public class NewsService {
	
	private NewsDAO_interface dao;
	
	public NewsService() {
		dao= new NewsDAO();
	}
	
	public NewsVO addNews(String emp_no ,String news_cont ,java.sql.Date news_date,Integer news_sts) {
		
		NewsVO newsVO = new NewsVO();
		
		
		newsVO.setEmp_no(emp_no);
		newsVO.setNews_cont(news_cont);
		newsVO.setNews_date(news_date);
		newsVO.setNews_sts(news_sts);
		dao.insert(newsVO);
		
		return newsVO;
	}


	
	public NewsVO updateNews(String news_no ,String emp_no ,String news_cont ,java.sql.Date news_date,Integer news_sts) {
		
		NewsVO newsVO = new NewsVO();
		
		newsVO.setNews_no(news_no);
		newsVO.setEmp_no(emp_no);
		newsVO.setNews_cont(news_cont);
		newsVO.setNews_date(news_date);
		newsVO.setNews_sts(news_sts);
		dao.update(newsVO);
		
		return newsVO;
			
	}

	public void deleteNews(String news_no) {
		dao.delete(news_no);
	}

	public NewsVO getOneNews(String news_no) {
		return dao.findByPrimaryKey(news_no);
	}

	public List<NewsVO> getAll() {
		return dao.getAll();
	}
	public List<NewsVO> getnewsno(String emp_no) {
		return dao.getnewsno(emp_no);
	}
	public List<NewsVO> frontNews_sts(Integer news_sts) {
		return dao.frontNews_sts(news_sts);
	}
}
	
	

