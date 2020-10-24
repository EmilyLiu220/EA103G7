package com.mem.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import com.mem.model.MemService;
import com.mem.model.MemVO;

public class MemServlet extends HttpServlet {

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
				String str = req.getParameter("mem_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/mem/select_page_mem.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String mem_no = null;
				try {
					mem_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/mem/select_page_mem.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(mem_no);
				
				if (memVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/mem/select_page_mem.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				
				req.setAttribute("memVO", memVO); // 資料庫取出的empVO物件,存入req
				
				
				
				String url = "/back-end/mem/listOneMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mem/select_page_mem.jsp");
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
				String mem_name = req.getParameter("mem_name");
				String mem_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mem_name == null || mem_name.trim().length() == 0) {
					errorMsgs.add("會員姓名: 請勿空白");
				} else if(!mem_name.trim().matches(mem_nameReg)) { 
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String mem_act = req.getParameter("mem_act");
				String mem_actReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_act == null || mem_act.trim().length() == 0) {
					errorMsgs.add("會員帳號: 請勿空白");
				} else if(!mem_act.trim().matches(mem_actReg)) { 
					errorMsgs.add("會員帳號: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				MemService memSvc = new MemService();
				List<MemVO> list = memSvc.getAll();
				for (int i = 0; i < list.size(); i++) {
					if (mem_act.equals(list.get(i).getMem_act())) {
						errorMsgs.add("此帳號已被使用！");
						break;
					}
				}				
					
				String mem_psw1 = req.getParameter("mem_psw1");
				String mem_pswReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_psw1 == null || mem_psw1.trim().length() == 0) {
					errorMsgs.add("會員密碼: 請勿空白");
				} else if(!mem_psw1.trim().matches(mem_pswReg)) { 
					errorMsgs.add("會員密碼: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String mem_psw2 = req.getParameter("mem_psw2");
				if (!mem_psw1.equals(mem_psw2)) {
					errorMsgs.add("會員密碼與密碼確認必須一致！");
				}
				
				String mem_gen = req.getParameter("mem_gen");
				
				java.sql.Date mem_bir = java.sql.Date.valueOf(req.getParameter("mem_bir").trim());
				
				String mem_tel = req.getParameter("mem_tel");
				String mem_telReg = "^09[0-9]{8}$";
				if (mem_tel == null || mem_tel.trim().length() == 0) {
					errorMsgs.add("手機號碼: 請勿空白");
				} else if(!mem_tel.trim().matches(mem_telReg)) { 
					errorMsgs.add("手機號碼: 只能是數字,09開頭,且長度必需為10碼");
	            }
				
				String city = req.getParameter("city").trim();
				String town = req.getParameter("town").trim();
				String address = req.getParameter("address").trim();
				
				String[] addressArray = {city, town, address};
//				String admin_address = String.join(",",addressArray);//Java8
				StringBuilder sb = new StringBuilder();
				if(addressArray.length > 0) {
					for(int i = 0; i < addressArray.length; i++) {
						sb.append(addressArray[i]);
					}
				}
				String mem_adrs = sb.toString();
				if (mem_adrs == null) {
					mem_adrs = "";
				}
				
				String mem_mail = req.getParameter("mem_mail");
				if (mem_mail == null || mem_mail.trim().length() == 0) {
					errorMsgs.add("e-mail: 請勿空白");
				}
				
				List<MemVO> list2 = memSvc.getAll();
				for (int i = 0; i < list2.size(); i++) {
					if (mem_mail.equals(list2.get(i).getMem_mail())) {
						errorMsgs.add("此email已被使用！");
						break;
					}
				}
				
				MemVO memVO = new MemVO();
				memVO.setMem_name(mem_name);
				memVO.setMem_act(mem_act);
				memVO.setMem_psw(mem_psw1);
				memVO.setMem_gen(mem_gen);
				memVO.setMem_bir(mem_bir);
				memVO.setMem_tel(mem_tel);
				memVO.setMem_adrs(mem_adrs);
				memVO.setMem_mail(mem_mail);		
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的memVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mem/addMem.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				memVO = memSvc.addMem(mem_name, mem_act, mem_psw1, mem_gen, mem_bir, mem_tel, mem_adrs, mem_mail);
				
				System.out.println("333");
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("memVO", memVO);
				
				String url = "/front-end/mem/showMemInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listOneEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mem/addMem.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("check_info".equals(action)) { // 來自login_success.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String mem_no = new String(req.getParameter("mem_no"));
				
				/***************************2.開始查詢資料****************************************/
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(mem_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("memVO", memVO);         // 資料庫取出的empVO物件,存入req
				String url = "/front-end/mem/showMemInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mem/login_success_mem.jsp");
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
				String mem_no = new String(req.getParameter("mem_no"));
				
				/***************************2.開始查詢資料****************************************/
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(mem_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("memVO", memVO);         // 資料庫取出的empVO物件,存入req
				String url = "/front-end/mem/update_mem_info.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mem/update_mem_info.jsp");
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
				String mem_no = new String(req.getParameter("mem_no"));
				
				/***************************2.開始查詢資料****************************************/
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(mem_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("memVO", memVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/mem/update_mem_sts.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mem/update_mem_sts.jsp");
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
				String mem_no = req.getParameter("mem_no");
				
				String mem_name = req.getParameter("mem_name");
				String mem_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mem_name == null || mem_name.trim().length() == 0) {
					errorMsgs.add("會員姓名: 請勿空白");
				} else if(!mem_name.trim().matches(mem_nameReg)) { 
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }

				String mem_psw1 = req.getParameter("mem_psw1");
				String mem_pswReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if (mem_psw1 == null || mem_psw1.trim().length() == 0) {
					errorMsgs.add("會員密碼: 請勿空白");
				} else if(!mem_psw1.trim().matches(mem_pswReg)) { 
					errorMsgs.add("會員密碼: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String mem_psw2 = req.getParameter("mem_psw2");
				if (!mem_psw1.equals(mem_psw2)) {
					errorMsgs.add("會員密碼與密碼確認必須一致！");
				}
				
				System.out.println("psw1" + mem_psw1);
				
				String mem_gen = req.getParameter("mem_gen");
				
				System.out.println("gen" + mem_gen);
				
				java.sql.Date mem_bir = java.sql.Date.valueOf(req.getParameter("mem_bir").trim());
				if (mem_bir == null) {
					mem_bir = java.sql.Date.valueOf("2020-09-10");
				}
				
				System.out.println("bir" + mem_bir);
				
				String mem_tel = req.getParameter("mem_tel");
				String mem_telReg = "^09[0-9]{8}$";
				if (mem_tel == null || mem_tel.trim().length() == 0) {
					errorMsgs.add("手機號碼: 請勿空白");
				} else if(!mem_tel.trim().matches(mem_telReg)) { 
					errorMsgs.add("手機號碼: 只能是數字, 且長度必需為10碼");
	            }
				
				System.out.println("tel" + mem_tel);
				
				String city = req.getParameter("city").trim();
				String town = req.getParameter("town").trim();
				String address = req.getParameter("address").trim();
				
				String[] addressArray = {city, town, address};
//				String admin_address = String.join(",",addressArray);//Java8
				StringBuilder sb = new StringBuilder();
				if(addressArray.length > 0) {
					for(int i = 0; i < addressArray.length; i++) {
						sb.append(addressArray[i]);
					}
				}
				String mem_adrs = sb.toString();
				if (mem_adrs == null) {
					mem_adrs = "";
				}
				
				System.out.println("adrs" + mem_adrs);
				
				String mem_mail = req.getParameter("mem_mail");
				if (mem_mail == null || mem_mail.trim().length() == 0) {
					errorMsgs.add("e-mail: 請勿空白");
				}
				
				System.out.println("mail" + mem_mail);
				
				MemVO memVO = new MemVO();
				memVO.setMem_name(mem_name);
				memVO.setMem_psw(mem_psw1);
				memVO.setMem_gen(mem_gen);
				memVO.setMem_bir(mem_bir);
				memVO.setMem_tel(mem_tel);
				memVO.setMem_adrs(mem_adrs);
				memVO.setMem_mail(mem_mail);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mem/update_mem_info.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MemService memSvc = new MemService();
				memVO = memSvc.updateByMem(mem_name, mem_psw1, mem_gen, mem_bir, mem_tel, mem_adrs, mem_mail, mem_no);
				String mem_act = memSvc.getOneMem(mem_no).getMem_act();
				
				memVO.setMem_no(mem_no);
				memVO.setMem_act(mem_act);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("memVO", memVO); // 資料庫update成功後,正確的的empVO物件,存入req
				
				String url = "/front-end/mem/showMemInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mem/update_mem_info.jsp");
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
				String mem_no = req.getParameter("mem_no").trim();
				Integer mem_bns = new Integer(req.getParameter("mem_bns"));
				Integer mem_od_m = new Integer(req.getParameter("mem_od_m"));
				Integer mem_od_r = new Integer(req.getParameter("mem_od_r"));
				Integer mem_review = new Integer(req.getParameter("mem_review"));
				Integer mem_repo = new Integer(req.getParameter("mem_repo"));
				Integer mem_sts = new Integer(req.getParameter("mem_sts"));
				
				MemVO memVO = new MemVO();
				memVO.setMem_bns(mem_bns);
				memVO.setMem_od_m(mem_od_m);
				memVO.setMem_od_r(mem_od_r);
				memVO.setMem_review(mem_review);
				memVO.setMem_repo(mem_repo);
				memVO.setMem_sts(mem_sts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/mem/update_mem_sts.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MemService memSvc = new MemService();
				memSvc.updateByEmp(mem_bns, mem_od_m, mem_od_r, mem_review, mem_repo, mem_sts, mem_no);
				
				memVO = memSvc.getOneMem(mem_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("memVO", memVO); // 資料庫update成功後,正確的的empVO物件,存入req
				
				String url = "/back-end/mem/listOneMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/mem/update_mem_sts.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("forget_psw".equals(action)) {
					
			String mem_mail = req.getParameter("mem_mail");
			String mem_psw = RandomPsw.genAuthCode(8);
			
			MemVO memVO = new MemVO();
			memVO.setMem_mail(mem_mail);
			memVO.setMem_psw(mem_psw);
			
			MemService memSvc = new MemService();
			memVO = memSvc.forgetPsw(mem_psw, mem_mail);
			
			ForgetPswMail fpm = new ForgetPswMail();
			String messageText = "請使用此密碼進行登入：" + mem_psw + "\n"
					+ "點此連結進行登入，並修改密碼:" + "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/front-end/mem/update_mem_info.jsp";
			fpm.sendMail(mem_mail, "忘記密碼", messageText);
			
			res.sendRedirect(req.getContextPath() + "/front-end/mem/login_mem.jsp");
		}
		
		if ("logout".equals(action)) {
			
			req.getSession().invalidate();
	        res.sendRedirect(req.getContextPath() + "/front-end/front_home.jsp");
			
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
			    if (!lh.allowUser(account, password)) {          //【帳號 , 密碼無效時】
			    	errorMsgs.add("您的帳號或密碼無效！請重新輸入！");
			    	RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/mem/login_mem.jsp");
					failureView.forward(req, res);
			    } else {                                       //【帳號 , 密碼有效時, 才做以下工作】
			      
			      HttpSession session = req.getSession();
			      session.setAttribute("account", account);   //*工作1: 才在session內做已經登入過的標識
			      
			      MemService memSvc = new MemService();
			      String mem_no = memSvc.login(account).getMem_no();
			      
			      MemVO memVO = memSvc.getOneMem(mem_no);
			      
				  session.setAttribute("memVO2", memVO);
				  
			       try {                                                        
			         String location = (String) session.getAttribute("location");
			         if (location != null) {
			           session.removeAttribute("location");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
			           res.sendRedirect(location);            
			           return;
			         }
			       } catch (Exception ignored) { }
					
//			       String url = "/back-end/siderbar/siderbar.jsp";
//			       RequestDispatcher successView = req.getRequestDispatcher(url);
//			       successView.forward(req, res);
			       
			       res.sendRedirect(req.getContextPath() + "/front-end/mem/login_success_mem.jsp");  //*工作3: (-->如無來源網頁:則重導至backindex.jsp)
			    }
			    
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/mem/login_mem.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
	
}