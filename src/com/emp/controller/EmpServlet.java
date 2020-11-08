package com.emp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.emp_auth.model.Emp_authDAO;
import com.emp_auth.model.Emp_authService;
import com.emp_auth.model.Emp_authVO;
import com.fun_auth.model.Fun_authService;
import com.fun_auth.model.Fun_authVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;

public class EmpServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
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
				String str = req.getParameter("emp_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String emp_no = null;
				try {
					emp_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_no);
				
				Emp_authService emp_authService = new Emp_authService();
				List<Emp_authVO> list = emp_authService.getOneEmp_auth(emp_no);
				
				if (empVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("empVO", empVO); // 資料庫取出的empVO物件,存入req
				
				req.setAttribute("emp_authVO", list);
				
				// 為了得到權限名稱
				List<Fun_authVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					Fun_authVO Fun_authVO = new Fun_authService().getOneFun(list.get(i).getFun_no());
					list2.add(Fun_authVO);
				}
				req.setAttribute("fun_authVO", list2);
				
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Display_ByName".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				String emp_name = req.getParameter("emp_name");
				if (emp_name == null || (emp_name.trim()).length() == 0) {
					errorMsgs.add("請輸入員工姓名");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String emp_no = null;
				
				EmpService empSvc = new EmpService();
				List<EmpVO> list_e = empSvc.getAll();
				for (int i = 0; i < list_e.size(); i++) {
					if (emp_name.equals(list_e.get(i).getEmp_name())) {
						emp_no = list_e.get(i).getEmp_no();
						break;
					}
				}
				
				System.out.println(emp_no);
				/***************************2.開始查詢資料*****************************************/
				EmpVO empVO = empSvc.getOneEmp(emp_no);
				
				Emp_authService emp_authService = new Emp_authService();
				List<Emp_authVO> list = emp_authService.getOneEmp_auth(emp_no);
				
				if (empVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("empVO", empVO); // 資料庫取出的empVO物件,存入req
				
				req.setAttribute("emp_authVO", list);
				
				// 為了得到權限名稱
				List<Fun_authVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					Fun_authVO Fun_authVO = new Fun_authService().getOneFun(list.get(i).getFun_no());
					list2.add(Fun_authVO);
				}
				req.setAttribute("fun_authVO", list2);
				
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String emp_name = req.getParameter("emp_name");
				String emp_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!emp_name.trim().matches(emp_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }

				EmpVO empVO = new EmpVO();
				empVO.setEmp_name(emp_name);
				
//				String emp_psw = genAuthCode(8);
//				empVO.setEmp_psw(emp_psw);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/addEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO2 = empSvc.addEmp(emp_name);
				
				String emp_no = empVO2.getEmp_no();
				
//				empSvc.updateByEmp(emp_psw, emp_name, emp_no);
				
				String fun_no[] = req.getParameterValues("fun_no[]");
				
				Emp_authVO emp_authVO = new Emp_authVO();
				for (int i = 0; i < fun_no.length; i++) {
					emp_authVO.setEmp_no(emp_no);
					emp_authVO.setFun_no(fun_no[i]);
				}
				
				Emp_authService emp_authSvc = new Emp_authService();
				
				for (int i = 0; i < fun_no.length; i++) {
					emp_authVO = emp_authSvc.addEmp_auth(fun_no[i], emp_no);
				}
				
				EmpVO empVO3 = empSvc.getOneEmp(emp_no);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				List<Emp_authVO> list = new Emp_authService().getOneEmp_auth(emp_no);
				req.setAttribute("emp_authVO", list); // 資料庫update成功後,正確的的empVO物件,存入req
				
				req.setAttribute("empVO", empVO3);
				
				// 為了得到權限名稱
				List<Fun_authVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					Fun_authVO fun_authVO = new Fun_authService().getOneFun(list.get(i).getFun_no());
					list2.add(fun_authVO);
				}
				req.setAttribute("fun_authVO", list2);
				
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listOneEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/addEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("Update_info".equals(action)) { // 來自login_success.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String emp_no = req.getParameter("emp_no").trim();
				
				/***************************2.開始查詢資料****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("empVO", empVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/emp/update_emp_info.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/update_emp_info.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("Update_sts".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String emp_no = new String(req.getParameter("emp_no"));
				
				/***************************2.開始查詢資料****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("empVO", empVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/emp/update_emp_sts.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("Update_auth".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String emp_no = new String(req.getParameter("emp_no"));
				
				/***************************2.開始查詢資料****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_no);
				
				Emp_authService emp_authService = new Emp_authService();
				List<Emp_authVO> list = emp_authService.getOneEmp_auth(emp_no);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("emp_authVO", list);
				
				req.setAttribute("empVO", empVO);         // 資料庫取出的empVO物件,存入req
				
				// 為了得到權限名稱
				List<Fun_authVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					Fun_authVO Fun_authVO = new Fun_authService().getOneFun(list.get(i).getFun_no());
					list2.add(Fun_authVO);
				}
				req.setAttribute("fun_authVO", list2);
				
				String url = "/back-end/emp/update_emp_auth.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update_i".equals(action)) { // 來自update_emp_info.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String emp_no = new String(req.getParameter("emp_no").trim());
				String emp_name = new String(req.getParameter("emp_name").trim());
				String emp_psw1 = new String(req.getParameter("emp_psw1").trim());
				String emp_psw2 = new String(req.getParameter("emp_psw2").trim());
				
				EmpVO empVO = new EmpVO();
				empVO.setEmp_no(emp_no);
				empVO.setEmp_name(emp_name);
				
				if (!emp_psw1.equals(emp_psw2)) {
					req.setAttribute("empVO", empVO);
					errorMsgs.add("您的密碼與密碼確認必須一致！請重新輸入！");
			    	RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/update_emp_info.jsp");
					failureView.forward(req, res);
					return;
				}
				
				empVO.setEmp_psw(emp_psw1);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/update_emp_info.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				EmpService empSvc = new EmpService();
				empVO = empSvc.updateByEmp(emp_psw1, emp_name, emp_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				List<Emp_authVO> list = new Emp_authService().getOneEmp_auth(emp_no);
				req.setAttribute("emp_authVO", list);
				
				req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
				
				// 為了得到權限名稱
				List<Fun_authVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					Fun_authVO Fun_authVO = new Fun_authService().getOneFun(list.get(i).getFun_no());
					list2.add(Fun_authVO);
				}
				req.setAttribute("fun_authVO", list2);
				
				String url = "/back-end/backindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/update_emp_info.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update_s".equals(action)) { // 來自update_emp_sts.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String fun_no[] = req.getParameterValues("fun_no[]");
				
				Emp_authVO emp_authVO = new Emp_authVO();
				
				String emp_no = new String(req.getParameter("emp_no").trim());
				String emp_name = new String(req.getParameter("emp_name").trim());
				Integer emp_sts = new Integer(req.getParameter("emp_sts").trim());
				
				EmpVO empVO = new EmpVO();
				empVO.setEmp_no(emp_no);
				empVO.setEmp_sts(emp_sts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/update_emp_sts.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				EmpService empSvc = new EmpService();
				empVO = empSvc.updateBySv(emp_sts, emp_no);
				
				Emp_authService emp_authSvc = new Emp_authService();
				if (emp_sts == 0) {
					emp_authSvc.deleteEmp_auth(emp_no);
				}
				
				empVO.setEmp_name(emp_name);
				
				for (int i = 0; i < fun_no.length; i++) {
					emp_authVO.setFun_no(fun_no[i]);
				}
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				List<Emp_authVO> list = new Emp_authService().getOneEmp_auth(emp_no);
				req.setAttribute("emp_authVO", list);
				
				req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
				
				
				// 為了得到權限名稱
				List<Fun_authVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					Fun_authVO Fun_authVO = new Fun_authService().getOneFun(list.get(i).getFun_no());
					list2.add(Fun_authVO);
				}
				req.setAttribute("fun_authVO", list2);
				
				
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/update_emp_sts.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update_a".equals(action)) { // 來自update_emp_auth.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String emp_name = new String(req.getParameter("emp_name").trim());
				Integer emp_sts = new Integer(req.getParameter("emp_sts").trim());
				
				EmpVO empVO = new EmpVO();
				
				String emp_no = new String(req.getParameter("emp_no").trim());
				String fun_no[] = req.getParameterValues("fun_no[]");
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/update_emp_auth.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Emp_authService emp_authSvc = new Emp_authService();
				
				if (fun_no == null) {
					emp_authSvc.deleteEmp_auth(emp_no);
				} else {
					emp_authSvc.deleteEmp_auth(emp_no);
					
					Emp_authVO emp_authVO = new Emp_authVO();
					
					for (int i = 0; i < fun_no.length; i++) {
						emp_authVO.setEmp_no(emp_no);
						emp_authVO.setFun_no(fun_no[i]);
					}
					
					for (int i = 0; i < fun_no.length; i++) {
						emp_authVO = emp_authSvc.addEmp_auth(fun_no[i], emp_no);
					}	
				}
				
				empVO.setEmp_no(emp_no);
				empVO.setEmp_name(emp_name);
				empVO.setEmp_sts(emp_sts);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				List<Emp_authVO> list = new Emp_authService().getOneEmp_auth(emp_no);
				req.setAttribute("emp_authVO", list); // 資料庫update成功後,正確的的empVO物件,存入req
				
				req.setAttribute("empVO", empVO);
						
				// 為了得到權限名稱
				List<Fun_authVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					Fun_authVO fun_authVO = new Fun_authService().getOneFun(list.get(i).getFun_no());
					list2.add(fun_authVO);
				}
				req.setAttribute("fun_authVO", list2);
				
				
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/update_emp_auth.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete_emp_auth".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String emp_no = new String(req.getParameter("emp_no"));
				String emp_name = new String(req.getParameter("emp_name"));
				Integer emp_sts = new Integer(req.getParameter("emp_sts").trim());
				
				EmpVO empVO = new EmpVO();
				empVO.setEmp_no(emp_no);
				empVO.setEmp_name(emp_name);
				empVO.setEmp_sts(emp_sts);
				
				/***************************2.開始刪除資料***************************************/
				Emp_authService emp_authSvc = new Emp_authService();
				emp_authSvc.deleteEmp_auth(emp_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				req.setAttribute("empVO", empVO);
				
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("logout".equals(action)) {
			
			HttpSession session_e = req.getSession();
			session_e.removeAttribute("account_e");
			session_e.removeAttribute("empVO2");
			session_e.removeAttribute("emp_authVO2");
			session_e.removeAttribute("fun_authVO2");
			
//			req.getSession().invalidate();
	        res.sendRedirect(req.getContextPath() + "/back-end/backindex.jsp");
			
		}
		
		if ("login".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String account = new String(req.getParameter("account"));
				String password = new String(req.getParameter("password"));
				
				LoginHandler lh = new LoginHandler();
				// 【檢查該帳號 , 密碼是否有效】
			    if (lh.allowUser(account, password) == 0) {          //【帳號 , 密碼無效時】
			    	errorMsgs.add("您的帳號或密碼無效！請重新輸入！");
			    	RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/login.jsp");
					failureView.forward(req, res);
			    } else if (lh.allowUser(account, password) == 1) {
			    	errorMsgs.add("您已離職，故無法登入後台系統！");
			    	RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/emp/login.jsp");
					failureView.forward(req, res);
			    } else {                                       //【帳號 , 密碼有效時, 才做以下工作】
			      
			      HttpSession session = req.getSession();
			      session.setAttribute("account_e", account);   //*工作1: 才在session內做已經登入過的標識
			      
			      EmpService empSvc = new EmpService();
			      EmpVO empVO = empSvc.getOneEmp(account);
			      
			      Emp_authService emp_authSvc = new Emp_authService();
				  List<Emp_authVO> list = emp_authSvc.getOneEmp_auth(account);
			      
				// 為了得到權限名稱
				  List<Fun_authVO> list2 = new ArrayList<>();
				  for (int i = 0; i < list.size(); i++) {
					  Fun_authVO Fun_authVO = new Fun_authService().getOneFun(list.get(i).getFun_no());
					  list2.add(Fun_authVO);
				  }
				  
				  session.setAttribute("empVO2", empVO);
				  session.setAttribute("emp_authVO2", list);
				  session.setAttribute("fun_authVO2", list2);
				  
			       try {                                                        
			         String location = (String) session.getAttribute("location_e");
			         if (location != null) {
			           session.removeAttribute("location_e");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
			           res.sendRedirect(location);            
			           return;
			         }
			       } catch (Exception ignored) { }
					
//			       String url = "/back-end/siderbar/siderbar.jsp";
//			       RequestDispatcher successView = req.getRequestDispatcher(url);
//			       successView.forward(req, res);
			       
			       res.sendRedirect(req.getContextPath() + "/back-end/backindex.jsp");  //*工作3: (-->如無來源網頁:則重導至backindex.jsp)
			    }
			    
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/emp/login.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
	
}
