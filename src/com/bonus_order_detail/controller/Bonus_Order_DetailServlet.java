package com.bonus_order_detail.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.bonus_order.model.Bonus_OrderService;
import com.bonus_order.model.Bonus_OrderVO;
import com.bonus_order_detail.model.*;

public class Bonus_Order_DetailServlet extends HttpServlet  { // 控制器Servlet收到請求後進入insert方法，再由Service呼叫DAO做事情

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
				
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("bo_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入紅利商品訂單編號才能查詢！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/bonus_order_detail/select_page.jsp");
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
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/bonus_order_detail/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				Bonus_Order_DetailService bonus_order_detailSvc = new Bonus_Order_DetailService(); // 呼叫回傳參數的建構子
				Bonus_Order_DetailVO bonus_order_detailVO = bonus_order_detailSvc.getOneBonus_Order_Detail(bo_no); // 呼叫Service內getOneEmp的方法
				if (bonus_order_detailVO == null) {
					errorMsgs.add("查無資料！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/bonus_order_detail/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("bonus_order_detailVO", bonus_order_detailVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/bonus_order_detail/listOneBonus_Order_Detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bonus_order_detail/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllMember_Review.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String bo_no = new String(req.getParameter("bo_no"));
				
				/***************************2.開始查詢資料****************************************/
				Bonus_Order_DetailService bonus_order_detailSvc = new Bonus_Order_DetailService();
				Bonus_Order_DetailVO bonus_order_detailVO = bonus_order_detailSvc.getOneBonus_Order_Detail(bo_no); // 呼叫Service內getOneEmp的方法
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("bonus_order_detailVO", bonus_order_detailVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/bonus_order_detail/update_bonus_order_detail_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bonus_order_detail/listAllBonus_Order_Detail.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_bonus_order_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String bo_no = new String(req.getParameter("bo_no").trim());
				
				String bns_no = req.getParameter("bns_no");
				
				if ("".equals(bns_no) || bns_no.trim().length() == 0) {
					errorMsgs.add("紅利商品編號：請勿空白");			
				}	
				
				Integer quantity = null;
				try {
					quantity = new Integer(req.getParameter("quantity").trim());
				} catch (NumberFormatException e) {
					quantity = 0;
					errorMsgs.add("數量請填數字！");
				}
				
				Bonus_Order_DetailVO bonus_order_detailVO = new Bonus_Order_DetailVO();
				bonus_order_detailVO.setBo_no(bo_no);
				bonus_order_detailVO.setBns_no(bns_no); 
				bonus_order_detailVO.setQuantity(quantity);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bonus_order_detailVO", bonus_order_detailVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/bonus_order_detail/update_bonus_order_detail_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Bonus_Order_DetailService bonus_order_detailSvc = new Bonus_Order_DetailService();
				bonus_order_detailVO = bonus_order_detailSvc.updateBonus_Order_Detail(bo_no, bns_no, quantity); // 呼叫Service內updateEmp的方法
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("bonus_order_detailVO", bonus_order_detailVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/bonus_order_detail/listOneBonus_Order_Detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bonus_order_detail/update_bonus_order_detail_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addMember_Review.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/			
				String bo_no = req.getParameter("bo_no");				
				if (bo_no == null || bo_no.trim().length() == 0) {
					errorMsgs.add("紅利商品訂單編號：請勿空白");
				}
				
				String bns_no = req.getParameter("bns_no");				
				if (bns_no == null || bns_no.trim().length() == 0) {
					errorMsgs.add("紅利商品編號：請勿空白");
				}
				
				Integer quantity = null;
				try {
					quantity = new Integer(req.getParameter("quantity").trim());
				} catch (NumberFormatException e) {
					quantity = 0;
					errorMsgs.add("數量請填數字！");
				}

				Bonus_Order_DetailVO bonus_order_detailVO = new Bonus_Order_DetailVO();
				bonus_order_detailVO.setBo_no(bo_no); 
				bonus_order_detailVO.setBns_no(bns_no); 
				bonus_order_detailVO.setQuantity(quantity);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bonus_order_detailVO", bonus_order_detailVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/bonus_order/addBonus_Order.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始新增資料***************************************/			
				Bonus_Order_DetailService bonus_order_detailSvc = new Bonus_Order_DetailService();
				bonus_order_detailVO = bonus_order_detailSvc.addBonus_Order_Detail(bo_no, bns_no, quantity); // 呼叫Service內addMember_Review的方法
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/bonus_order_detail/listAllBonus_Order_Detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bonus_order_detail/addBonus_Order_Detail.jsp");
				failureView.forward(req, res);
			}
		}
		
        
		if ("delete".equals(action)) { // 來自listAllMember_Review.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String bo_no = new String(req.getParameter("bo_no"));
				
				/***************************2.開始刪除資料***************************************/
				Bonus_Order_DetailService bonus_order_detailSvc = new Bonus_Order_DetailService();
				bonus_order_detailSvc.deleteBonus_Order_Detail(bo_no); // 呼叫Service內deleteEmp的方法
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/bonus_order_detail/listAllBonus_Order_Detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bonus_order_detail/listAllBonus_Order_Detail.jsp");
				failureView.forward(req, res);
			}
		}
	}
}