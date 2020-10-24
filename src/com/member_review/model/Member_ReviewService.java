package com.member_review.model;

import java.util.List;

public class Member_ReviewService {
	
	private Member_ReviewDAO_interface dao;

	public Member_ReviewService() {
		dao = new Member_ReviewDAO();
	}
	
	public Member_ReviewVO addMember_Review(String meal_order_no, String mem_review_con,
			java.sql.Date review_date) {
		
		Member_ReviewVO member_reviewVO = new Member_ReviewVO(); 
		
		member_reviewVO.setMeal_order_no(meal_order_no);
		member_reviewVO.setMem_review_con(mem_review_con);
		member_reviewVO.setReview_date(review_date);
		dao.insert(member_reviewVO);
		
		return member_reviewVO;
	}
	
	public Member_ReviewVO updateMember_Review(String review_no, String meal_order_no, String mem_review_con,
			java.sql.Date review_date) {
		
		Member_ReviewVO member_reviewVO = new Member_ReviewVO(); 
		
		member_reviewVO.setReview_no(review_no);
		member_reviewVO.setMeal_order_no(meal_order_no);
		member_reviewVO.setMem_review_con(mem_review_con);
		member_reviewVO.setReview_date(review_date);
		dao.update(member_reviewVO);
		
		return member_reviewVO;
	}
	
	public void deleteMember_Review(String review_no) {
		dao.delete(review_no);
	}

	public Member_ReviewVO getOneMember_Review(String review_no) {
		return dao.findByPrimaryKey(review_no);
	}

	public List<Member_ReviewVO> getAll() {
		return dao.getAll();
	}
}