package com.report_appraise.model;

import java.util.*;

public interface Report_AppraiseDAO_interface {
	public void insert(Report_AppraiseVO report_appraiseVO);
	public void update(Report_AppraiseVO report_appraiseVO);
	public void delete(String report_appraise);
	public Report_AppraiseVO findByPrimaryKey(String report_appraise);
	public List<Report_AppraiseVO> getAll();
}
