package com.member_review.model;

import java.util.*;

public interface Member_ReviewDAO_interface { // 介面負責定義規格
	public void insert(Member_ReviewVO member_reviewVO);
	public void update(Member_ReviewVO member_reviewVO);
	public void delete(String review_no);
	public Member_ReviewVO findByPrimaryKey(String review_no);
	public List<Member_ReviewVO> getAll();
}
 