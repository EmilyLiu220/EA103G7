package com.bonus_order.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.bonus_order.model.*;

public class Bonus_OrderServlet extends HttpServlet { // 控制器Servlet收到請求後進入insert方法，再由Service呼叫DAO做事情

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("bo_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入紅利商品訂單編號才能查詢！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus_order/select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String bo_no = null;
				try {
					bo_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("紅利商品訂單編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus_order/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				Bonus_OrderService bonus_orderSvc = new Bonus_OrderService(); // 呼叫回傳參數的建構子
				Bonus_OrderVO bonus_orderVO = bonus_orderSvc.getOneBonus_Order(bo_no); // 呼叫Service內getOneEmp的方法
				if (bonus_orderVO == null) {
					errorMsgs.add("查無資料！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus_order/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("bonus_orderVO", bonus_orderVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/bonus_order/listOneBonus_Order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus_order/select_page.jsp");
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
				String bo_no = new String(req.getParameter("bo_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				Bonus_OrderService bonus_orderSvc = new Bonus_OrderService();
				Bonus_OrderVO bonus_orderVO = bonus_orderSvc.getOneBonus_Order(bo_no); // 呼叫Service內getOneEmp的方法

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("bonus_orderVO", bonus_orderVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/bonus_order/update_bonus_order_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bonus_order/listAllBonus_Order.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_bonus_order_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String bo_no = new String(req.getParameter("bo_no").trim());

				String mem_no = req.getParameter("mem_no");

				if ("".equals(mem_no) || mem_no.trim().length() == 0) {
					errorMsgs.add("會員編號：請勿空白");
				}

				java.sql.Date bo_date = null;
				try {
					bo_date = java.sql.Date.valueOf(req.getParameter("bo_date").trim());
				} catch (IllegalArgumentException e) {
					bo_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				String promo_code = req.getParameter("promo_code");

				if ("".equals(promo_code) || promo_code.trim().length() == 0) {
					errorMsgs.add("優惠代碼：請勿空白");
				}

				Bonus_OrderVO bonus_orderVO = new Bonus_OrderVO();
				bonus_orderVO.setBo_no(bo_no);
				bonus_orderVO.setMem_no(mem_no);
				bonus_orderVO.setBo_date(bo_date);
				bonus_orderVO.setPromo_code(promo_code);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bonus_orderVO", bonus_orderVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/bonus_order/update_bonus_order_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				Bonus_OrderService bonus_orderSvc = new Bonus_OrderService();
				bonus_orderVO = bonus_orderSvc.updateBonus_Order(bo_no, mem_no, bo_date, promo_code); // 呼叫Service內updateEmp的方法

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("bonus_orderVO", bonus_orderVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/bonus_order/listOneBonus_Order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bonus_order/update_bonus_order_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addMember_Review.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String mem_no = req.getParameter("mem_no");
				if (mem_no == null || mem_no.trim().length() == 0) {
					errorMsgs.add("會員編號：請勿空白");
				}

				String promo_code = req.getParameter("promo_code");
				String promo_codeReg = "^[(a-zA-Z0-9)]{10}$";
				if (promo_code == null || promo_code.trim().length() == 0) {
					errorMsgs.add("優惠代碼: 請勿空白");
				} else if (!promo_code.trim().matches(promo_codeReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("優惠代碼: 只能是英文字母和數字，且長度必須為10");
				}

				java.sql.Date bo_date = null;
				try {
					bo_date = java.sql.Date.valueOf(req.getParameter("bo_date").trim());
				} catch (IllegalArgumentException e) {
					bo_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Bonus_OrderVO bonus_orderVO = new Bonus_OrderVO();
				bonus_orderVO.setMem_no(mem_no);
				bonus_orderVO.setBo_date(bo_date);
				bonus_orderVO.setPromo_code(promo_code);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bonus_orderVO", bonus_orderVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/bonus_order/addBonus_Order.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				Bonus_OrderService bonus_orderSvc = new Bonus_OrderService();
				bonus_orderVO = bonus_orderSvc.addBonus_Order(mem_no, bo_date, promo_code, null); // 呼叫Service內addMember_Review的方法

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/bonus_order/listAllBonus_Order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus_order/addBonus_Order.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllMember_Review.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String bo_no = new String(req.getParameter("bo_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				Bonus_OrderService bonus_orderSvc = new Bonus_OrderService();
				bonus_orderSvc.deleteBonus_Order(bo_no); // 呼叫Service內deleteEmp的方法
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/bonus_order/listAllBonus_Order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bonus_order/listAllBonus_Order.jsp");
				failureView.forward(req, res);
			}
		}
	}
}