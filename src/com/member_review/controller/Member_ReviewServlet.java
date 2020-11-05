package com.member_review.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.member_review.model.*;
import com.report_appraise.model.Report_AppraiseService;
import com.report_appraise.model.Report_AppraiseVO;

public class Member_ReviewServlet extends HttpServlet { // 控制器Servlet收到請求後進入insert方法，再由Service呼叫DAO做事情

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOneForDisplay".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("review_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入評價編號才能查詢！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member_review/select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String review_no = null;
				try {
					review_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("評價編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member_review/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				Member_ReviewService member_reviewSvc = new Member_ReviewService(); // 呼叫回傳參數的建構子
				Member_ReviewVO member_reviewVO = member_reviewSvc.getOneMember_Review(review_no); // 呼叫Service內getOneEmp的方法
				if (member_reviewVO == null) {
					errorMsgs.add("查無資料！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member_review/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("member_reviewVO", member_reviewVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/member_review/listOneMember_Review.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member_review/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOneForReview".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("review_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入評價編號才能查詢！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report_appraise/listAllReport_Appraise.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String review_no = null;
				try {
					review_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("評價編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report_appraise/listAllReport_Appraise.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				/*************************** 2.開始查詢資料 *****************************************/
				Member_ReviewService member_reviewSvc = new Member_ReviewService(); // 呼叫回傳參數的建構子
				Member_ReviewVO member_reviewVO = member_reviewSvc.getOneMember_Review(review_no); // 呼叫Service內getOneEmp的方法
				if (member_reviewVO == null) {
					errorMsgs.add("查無資料！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report_appraise/listAllReport_Appraise.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("member_reviewVO", member_reviewVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/member_review/listOneMember_Review.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/report_appraise/listAllReport_Appraise.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOneForReviewFailed".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("review_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入評價編號才能查詢！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member_review/select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String review_no = null;
				try {
					review_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("評價編號格式不正確");
				}

				/*************************** 2.開始查詢資料 *****************************************/
				Member_ReviewService member_reviewSvc = new Member_ReviewService(); // 呼叫回傳參數的建構子
				Member_ReviewVO member_reviewVO = member_reviewSvc.getOneMember_Review(review_no); // 呼叫Service內getOneEmp的方法
				if (member_reviewVO == null) {
					errorMsgs.add("查無資料！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member_review/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("member_reviewVO", member_reviewVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/report_appraise/listAllReport_Appraise.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member_review/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllMember_Review.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String review_no = new String(req.getParameter("review_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				Member_ReviewService member_reviewSvc = new Member_ReviewService();
				Member_ReviewVO member_reviewVO = member_reviewSvc.getOneMember_Review(review_no); // 呼叫Service內getOneEmp的方法

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("member_reviewVO", member_reviewVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/member_review/update_member_review.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/member_review/listAllMember_Review.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_member_review_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String review_no = new String(req.getParameter("review_no").trim());

				String meal_order_no = req.getParameter("meal_order_no");
				if ("".equals(meal_order_no) || meal_order_no.trim().length() == 0) {
					errorMsgs.add("訂餐編號：請勿空白");
				}

				String mem_review_con = req.getParameter("mem_review_con").trim();
				if (mem_review_con == null || mem_review_con.trim().length() == 0) {
					errorMsgs.add("評價內容：請勿空白");
				}

				java.sql.Date review_date = null;
				try {
					review_date = java.sql.Date.valueOf(req.getParameter("review_date").trim());
				} catch (IllegalArgumentException e) {
					review_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Member_ReviewVO member_reviewVO = new Member_ReviewVO();
				member_reviewVO.setReview_no(review_no);
				member_reviewVO.setMeal_order_no(meal_order_no);
				member_reviewVO.setMem_review_con(mem_review_con);
				member_reviewVO.setReview_date(review_date);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("member_reviewVO", member_reviewVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/member_review/update_member_review.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				Member_ReviewService member_reviewSvc = new Member_ReviewService();
				member_reviewVO = member_reviewSvc.updateMember_Review(review_no, meal_order_no, mem_review_con,
						review_date); // 呼叫Service內updateEmp的方法

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("member_reviewVO", member_reviewVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/member_review/listAllMember_Review.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/member_review/update_member_review.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insertMemberReview".equals(action)) { // 來自addMember_Review.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String review_no = req.getParameter("review_no");
				
				String meal_order_no = req.getParameter("meal_order_no");
				if (meal_order_no == null || meal_order_no.trim().length() == 0) {
					errorMsgs.add("訂餐編號：請勿空白");
				}

				String mem_review_con = req.getParameter("mem_review_con").trim();
				if (mem_review_con == null || mem_review_con.trim().length() == 0) {
					errorMsgs.add("評價內容：請勿空白");
				}

				java.sql.Date review_date = null;
				try {
					review_date = java.sql.Date.valueOf(req.getParameter("review_date").trim());
				} catch (IllegalArgumentException e) {
					review_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期！");
				}

				Member_ReviewVO member_reviewVO = new Member_ReviewVO();
				member_reviewVO.setReview_no(review_no);
				member_reviewVO.setMeal_order_no(meal_order_no);
				member_reviewVO.setMem_review_con(mem_review_con);
				member_reviewVO.setReview_date(review_date);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("member_reviewVO", member_reviewVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/member_review/addMember_Review.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}				
				/*************************** 2.開始新增資料 ***************************************/
				Member_ReviewService member_reviewSvc = new Member_ReviewService();
				member_reviewVO = member_reviewSvc.addMember_Review(meal_order_no, mem_review_con, review_date); // 呼叫Service內addMember_Review的方法
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("member_reviewVO", member_reviewVO);
				String url = "/front-end/member_review/listOneMember_Review.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/member_review/addMember_Review.jsp");
				failureView.forward(req, res);
			}
		}
			
		if ("delete".equals(action)) { // 來自listAllMember_Review.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String review_no = new String(req.getParameter("review_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				Member_ReviewService member_reviewSvc = new Member_ReviewService();
				member_reviewSvc.deleteMember_Review(review_no); // 呼叫Service內deleteEmp的方法
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/member_review/listAllMember_Review.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/member_review/listAllMember_Review.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOneForReviewSuccess".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String review_no = new String(req.getParameter("review_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				Member_ReviewService member_reviewSvc = new Member_ReviewService();
				member_reviewSvc.deleteMember_Review(review_no); // 呼叫Service內deleteEmp的方法
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/report_appraise/listAllReport_Appraise.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/member_review/listAllMember_Review.jsp");
				failureView.forward(req, res);
			}
		}
	}
}