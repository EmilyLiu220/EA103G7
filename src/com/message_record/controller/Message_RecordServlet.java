package com.message_record.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.front_inform.model.Front_InformService;
import com.front_inform.model.Front_InformVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.message_record.model.Message_RecordService;
import com.message_record.model.Message_RecordVO;

public class Message_RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("memGetMsg".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1. 取得會員參數 *****************************************/
				String mem_no = req.getParameter("mem_no");
				if (mem_no == null || (mem_no.trim()).length() == 0) {
					errorMsgs.add("請先登入頁面");
				}
//				MemService memSvc = new MemService();
//				MemVO memVO = memSvc.getOneMem(mem_no);
//				if (memVO == null) {
//					errorMsgs.add("查無會員資料");
//				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/*************************** 2. 開始查詢資料 *****************************************/
				Message_RecordService msgSvc = new Message_RecordService();
				List<Message_RecordVO> msgVOs = msgSvc.getMsgByMem(mem_no);

				if(msgVOs.isEmpty()) {
					PrintWriter out = new PrintWriter(System.out);
					out.println("您尚無對話資料");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("msgVOs", msgVOs);
				HttpSession session = req.getSession();
				session.setAttribute("msgVOs", msgVOs);
				
				String url = "/front-end/message_record/front_msg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("查無資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
				failureView.forward(req, res);
			}

		}
		
		if ("empGetMsg".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/************************* 1. 取得員工參數 *************************/
				String emp_no = req.getParameter("emp_no");
				if (emp_no == null || (emp_no.trim()).length() == 0) {
					errorMsgs.add("請先登入頁面");
				}
//				EmpService empSvc = new EmpService();
//				EmpVO empVO = empSvc.getOneEmp(emp_no);
//				if (empVO == null) {
//					errorMsgs.add("查無員工資料");
//				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/*************************** 2. 開始查詢資料 *****************************************/
				Message_RecordService msgSvc = new Message_RecordService();
				List<Message_RecordVO> msgVOs = msgSvc.getMsgByEmp(emp_no);

				if(msgVOs.isEmpty()) {
					PrintWriter out = new PrintWriter(System.out);
					out.println("您尚無對話資料");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("msgVOs", msgVOs);
				HttpSession session = req.getSession();
				session.setAttribute("msgVOs", msgVOs);
				
				String url = "/front-end/message_record/front_msg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("查無資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
				failureView.forward(req, res);
			}

		}
		
		if ("insertMsg".equals(action)) { // Q:客服是否需要登入才能傳送訊息? -> 個人覺得不用...
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String mem_no = req.getParameter("mem_no").trim();
				// 顧客未登入頁面傳來的訊息
				if(mem_no == null || (mem_no.trim()).length() == 0) {
					String msg_cont = req.getParameter("msg_cont");
				}
				// 會員登入後傳的訊息
				if(mem_no != null && (mem_no.trim()).length() != 0) {
					
				}
			} catch (Exception e) {
				
			}

		}
		
		if ("updateMemReadSts".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
			} catch (Exception e) {
				
			}

		}

		if ("updateEmpReadSts".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
			} catch (Exception e) {
				
			}

		}
		
	}

}
