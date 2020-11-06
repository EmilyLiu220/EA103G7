package com.report_appraise.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.report_appraise.model.*;
import com.mem.model.MemVO;
import com.member_review.model.Member_ReviewService;
import com.member_review.model.Member_ReviewVO;


public class Report_AppraiseServlet extends HttpServlet { // 控制器Servlet收到請求後進入insert方法，再由Service呼叫DAO做事情

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("report_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入檢舉編號才能查詢！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report_appraise/select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String report_no = null;
				try {
					report_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("檢舉編號格式不正確");
				}

				/*************************** 2.開始查詢資料 *****************************************/
				Report_AppraiseService report_appraiseSvc = new Report_AppraiseService(); 
				Report_AppraiseVO report_appraiseVO = report_appraiseSvc.getOneReport_Appraise(report_no); 
				if (report_appraiseVO == null) {
					errorMsgs.add("查無資料！");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report_appraise/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("report_appraiseVO", report_appraiseVO);
				String url = "/back-end/member_review/listOneMember_Review.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report_appraise/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOneForReportAppraise".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String review_no = new String(req.getParameter("review_no").trim());
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member_review/listAllMember_Review.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始查詢資料 *****************************************/
				Member_ReviewService member_reviewSvc = new Member_ReviewService(); 
				Member_ReviewVO member_reviewVO = member_reviewSvc.getOneMember_Review(review_no); 
//				Report_AppraiseService report_appraiseSvc = new Report_AppraiseService(); 
//				Report_AppraiseVO report_appraiseVO = report_appraiseSvc.getOneReport_Appraise(report_no); 
				if (member_reviewVO == null) {
					errorMsgs.add("查無資料！");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member_review/listAllMember_Review.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("member_reviewVO", member_reviewVO);
//				req.setAttribute("report_appraiseVO", report_appraiseVO);
				String url = "/front-end/report_appraise/addReport_Appraise.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member_review/listAllMember_Review.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String report_no = new String(req.getParameter("report_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				Report_AppraiseService report_appraiseSvc = new Report_AppraiseService();
				Report_AppraiseVO report_appraiseVO = report_appraiseSvc.getOneReport_Appraise(report_no); // 呼叫Service內getOneEmp的方法

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("report_appraiseVO", report_appraiseVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/report_appraise/update_report_appraise_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_report_appraise_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/report_appraise/listAllReport_Appraise.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String report_no = new String(req.getParameter("report_no").trim());

				String review_no = req.getParameter("review_no");
				if ("".equals(review_no) || review_no.trim().length() == 0) {
					errorMsgs.add("評價編號：請勿空白");
				}

				String mem_no = req.getParameter("mem_no").trim();
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("會員編號：請勿空白");
				}

				String emp_no = req.getParameter("emp_no").trim();
				if (emp_no == null || emp_no.trim().length() == 0) {
					errorMsgs.add("員工編號：請勿空白");
				}

				String report_con = req.getParameter("report_con").trim();
				if (report_con == null || report_con.trim().length() == 0) {
					errorMsgs.add("檢舉內容：請勿空白");
				}
				
				java.sql.Date report_date = null;
				try {
					report_date = java.sql.Date.valueOf(req.getParameter("report_date").trim());
				} catch (IllegalArgumentException e) {
					report_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期123!");
				}

				Integer reply_sts = null;
				try {
					reply_sts = new Integer(req.getParameter("reply_sts").trim());
				} catch (NumberFormatException e) {
					reply_sts = 0;
					errorMsgs.add("處理狀態請填數字！");
				}

				Report_AppraiseVO report_appraiseVO = new Report_AppraiseVO();
				report_appraiseVO.setReport_no(report_no);
				report_appraiseVO.setReview_no(review_no);
				report_appraiseVO.setMem_no(mem_no);
				report_appraiseVO.setEmp_no(emp_no);
				report_appraiseVO.setReport_date(report_date);
				report_appraiseVO.setReport_con(report_con);
				report_appraiseVO.setReply_sts(reply_sts);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("report_appraiseVO", report_appraiseVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/report_appraise/update_report_appraise_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				Report_AppraiseService report_appraiseSvc = new Report_AppraiseService();
				report_appraiseVO = report_appraiseSvc.updateReport_Appraise(report_no, review_no, mem_no, emp_no,
						report_date, report_con, reply_sts); // 呼叫Service內updateEmp的方法

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("report_appraiseVO", report_appraiseVO); 
				String url = "/back-end/report_appraise/listOneReport_Appraise.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/report_appraise/update_report_appraise_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insertReportAppraise".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();		
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//				String review_no = new String(req.getParameter("review_no").trim());

				String review_no = req.getParameter("review_no");
				String review_noReg = "^[(MR0-9)]{6}$";
				if (review_no == null || review_no.trim().length() == 0) {
					errorMsgs.add("評價編號：請勿空白");
				} else if (!review_no.trim().matches(review_noReg)) {
					errorMsgs.add("評價編號: 只能是英文字母MR和數字，且長度必須為6");
				}

				HttpSession session = req.getSession();
				MemVO memVO = (MemVO) session.getAttribute("memVO2");
				String mem_no = memVO.getMem_no();
				String mem_noReg = "^[(MEM0-9)]{7}$";
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("會員編號：請勿空白");
				} else if (!mem_no.trim().matches(mem_noReg)) {
					errorMsgs.add("會員編號: 只能是英文字母MEM和數字，且長度必須為7");
				}
				
				
				String emp_no = req.getParameter("emp_no");
				String emp_noReg = "^[(EMP0-9)]{7}$";
				if (emp_no == null || emp_no.trim().length() == 0) {
					errorMsgs.add("員工編號：請勿空白");
				} else if (!emp_no.trim().matches(emp_noReg)) {
					errorMsgs.add("員工編號: 只能是英文字母EMP和數字，且長度必須為7");
				}

				String report_con = req.getParameter("report_con").trim();
				if (report_con == null || report_con.trim().length() == 0) {
					errorMsgs.add("檢舉內容：請勿空白");
				}
				
				java.sql.Date report_date = null;
				try {
					report_date = java.sql.Date.valueOf(req.getParameter("report_date").trim());
				} catch (IllegalArgumentException e) {
					report_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期！");
				}

				Report_AppraiseVO report_appraiseVO = new Report_AppraiseVO();
				report_appraiseVO.setReview_no(review_no);
				report_appraiseVO.setMem_no(mem_no);
				report_appraiseVO.setMem_no(emp_no);
				report_appraiseVO.setReport_date(report_date);
				report_appraiseVO.setReport_con(report_con);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("report_appraiseVO", report_appraiseVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/report_appraise/addReport_Appraise.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始新增資料 ***************************************/
				Report_AppraiseService report_appraiseSvc = new Report_AppraiseService();
				report_appraiseVO = report_appraiseSvc.addReport_Appraise(review_no, mem_no, emp_no, report_date,
				report_con); 
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("report_appraiseVO", report_appraiseVO);
				String url = "/front-end/report_appraise/listOneReport_Appraise.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/report_appraise/addReport_Appraise.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String report_no = new String(req.getParameter("report_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				Report_AppraiseService report_appraiseSvc = new Report_AppraiseService();
				report_appraiseSvc.deleteReport_Appraise(report_no);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/report_appraise/listAllReport_Appraise.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/report_appraise/listAllReport_Appraise.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
