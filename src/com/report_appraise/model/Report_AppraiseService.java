package com.report_appraise.model;

import java.util.List;

public class Report_AppraiseService {
	
	private Report_AppraiseDAO_interface dao;

	public Report_AppraiseService() {
		dao = new Report_AppraiseDAO();
	}
	
	public Report_AppraiseVO addReport_Appraise(String review_no, String mem_no, String emp_no,
			java.sql.Date report_date, String report_con) {
		
		Report_AppraiseVO report_appraiseVO = new Report_AppraiseVO(); 
		report_appraiseVO.setReview_no(review_no);
		report_appraiseVO.setMem_no(mem_no);
		report_appraiseVO.setEmp_no(emp_no);
		report_appraiseVO.setReport_date(report_date);
		report_appraiseVO.setReport_con(report_con);
		dao.insert(report_appraiseVO);
		
		return report_appraiseVO;
	}
	
	public Report_AppraiseVO updateReport_Appraise(String report_no, String review_no, String mem_no,
			String emp_no, java.sql.Date report_date, String report_con, Integer reply_sts) {
		
		Report_AppraiseVO report_appraiseVO = new Report_AppraiseVO(); 
		
		report_appraiseVO.setReview_no(report_no);
		report_appraiseVO.setReview_no(review_no);
		report_appraiseVO.setMem_no(mem_no);
		report_appraiseVO.setEmp_no(emp_no);
		report_appraiseVO.setReport_date(report_date);
		report_appraiseVO.setReport_con(report_con);
		report_appraiseVO.setReply_sts(reply_sts);
		dao.update(report_appraiseVO);
		
		return report_appraiseVO;
	}
	
	public void deleteReport_Appraise(String report_no) {
		dao.delete(report_no);
	}

	public Report_AppraiseVO getOneReport_Appraise(String report_no) {
		return dao.findByPrimaryKey(report_no);
	}

	public List<Report_AppraiseVO> getAll() {
		return dao.getAll();
	}
}