package com.news.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.news.model.NewsService;
import com.news.model.NewsVO;

public class NewsServlet extends HttpServlet {
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
				String str = req.getParameter("news_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入news_no");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/news/select_news.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String news_no = null;
				try {
					news_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/news/select_news.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 2.開始查詢資料 *****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(news_no);
				if (newsVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/news/select_news.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("newsVO", newsVO); // 資料庫取出的newsVO物件,存入req
				String url = "/back-end/news/listOneNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneNews.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/news/select_news.jsp");
				failureView.forward(req, res);
			}
		}
		// 1修改
		if ("getOne_For_Update".equals(action)) { // 來自listAllnews.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String news_no = new String(req.getParameter("news_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(news_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("newsVO", newsVO); // 資料庫取出的newsVO物件,存入req
				String url = "/back-end/news/update_news_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_news_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/news/listAllnews.jsp");
				failureView.forward(req, res);

			}
		}

		// 修改
		if ("update".equals(action)) { // 來自update_news_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String news_no = req.getParameter("news_no").trim();

				String emp_no = req.getParameter("emp_no").trim();
//				String emp_noReg = "^[(EMP)[0-9]{4}]$";
				
				if (emp_no == null || emp_no.trim().length() == 0) {
					errorMsgs.add("emp_no: 請勿空白");
				}
//				else if (!emp_no.trim().matches(emp_noReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("emp_no: 只能是EMP_ , 且長度必需在4");
//				}

				String news_cont = req.getParameter("news_cont").trim();
				if (news_cont == null || news_cont.trim().length() == 0) {
					errorMsgs.add("最新消息，請勿空白");
				}

				java.sql.Date news_date = null;
				try {
					news_date = java.sql.Date.valueOf(req.getParameter("news_date").trim());
				} catch (IllegalArgumentException e) {
					news_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				NewsVO newsVO = new NewsVO();
				newsVO.setNews_no(news_no);
				newsVO.setEmp_no(emp_no);
				newsVO.setNews_cont(news_cont);
				newsVO.setNews_date(news_date);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); // 含有輸入格式錯誤的newsVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/news/update_news_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.updateNews(news_no, emp_no, news_cont, news_date);
			
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("newsVO", newsVO); // 資料庫update成功後,正確的的newsVO物件,存入req
				String url = "/back-end/news/listOneNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneNews.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/news/update_news_input.jsp");
				failureView.forward(req, res);
			}

		}

		// insert

		if ("insert".equals(action)) { // 來自addNews.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String emp_no = req.getParameter("emp_no");
//				String emp_noReg = "^[(EMP)[0-9]{4}]$";
				if (emp_no == null || emp_no.trim().length() == 0) {
					errorMsgs.add("emp_no: 請勿空白");
				}
//				else if (!emp_no.trim().matches(emp_noReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("emp_no: 只能是EMP , 且長度必需在4");
//				}

				String news_cont = req.getParameter("news_cont").trim();
				if (news_cont == null || news_cont.trim().length() == 0) {
					errorMsgs.add("消息內容，請勿空白");
				}

				java.sql.Date news_date = null;
				try {
					news_date = java.sql.Date.valueOf(req.getParameter("news_date").trim());
				} catch (IllegalArgumentException e) {
					news_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

//				----------------------
				
				//抓取登入員工
//				HttpSession session = req.getSession();
//				EmpVO empVO = (EmpVO)session.getAttribute("EmpVO");
//				String emp_no = empVO.getEmp_no();
				
//				------------------------
				NewsVO newsVO = new NewsVO();
				newsVO.setEmp_no(emp_no);
				newsVO.setNews_cont(news_cont);
				newsVO.setNews_date(news_date);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); // 含有輸入格式錯誤的newsVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/news/addNews.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始修改資料 *****************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.addNews(emp_no, news_cont, news_date);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/news/listAllnews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllnews.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/news/addNews.jsp");
				failureView.forward(req, res);
			}
		}

		// delete

		if ("delete".equals(action)) { // 來自listAllnews.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String news_no = new String(req.getParameter("news_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				NewsService newsSvc = new NewsService();
				newsSvc.deleteNews(news_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/news/listAllnews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/news/listAllnews.jsp");
				failureView.forward(req, res);
			}
		}
	}
}