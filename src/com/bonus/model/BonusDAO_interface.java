package com.bonus.model;

import java.util.*;

import com.meal_set.model.MealSetVO;

public interface BonusDAO_interface { // 介面負責定義規格
	public void insertFromBack(BonusVO bonusVO);
	public void update(BonusVO bonusVO);
//	public BonusVO searchByNo(String bns_no);
	public void delete(String review_no);
	public BonusVO findByPrimaryKey(String bns_no);
	public List<BonusVO> getAll();
}
 