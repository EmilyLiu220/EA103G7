package com.bonus.model;

import java.util.*;

public interface BonusDAO_interface { // 介面負責定義規格
	public void insert(BonusVO bonusVO);
	public void update(BonusVO bonusVO);
	public void delete(String review_no);
	public BonusVO findByPrimaryKey(String bns_no);
	public List<BonusVO> getAll();
}
 