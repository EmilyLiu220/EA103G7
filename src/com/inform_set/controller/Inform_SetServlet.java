package com.inform_set.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.emp.model.*;
import com.front_inform.model.*;
import com.inform_set.model.*;

public class Inform_SetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("getOneIsForDisplay".equals(action)) { // 來自 select_is.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String is_no = req.getParameter("is_no").trim().toUpperCase();
				String is_noReg = "I{1}S{1}[\\d]{4}";
				Pattern pat = Pattern.compile(is_noReg);
				Matcher matcher = pat.matcher(is_no.trim());
				if(is_no==null||(is_no.length()==0)) {
					errorMsgs.add("請輸入通知設定編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				if(!matcher.find()) {
					errorMsgs.add("通知設定編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				Inform_SetService isSvc = new Inform_SetService();
				Inform_SetVO isVO = isSvc.getOneIs(is_no);
				if (isVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("isVO", isVO); // 資料庫取出的isVO物件,存入req
				String url = "/back-end/inform_set/listOne_is.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 listOne_is.jsp
				successView.forward(req, res);
				
			}catch(Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		if("getIsForDisplayByEmp".equals(action)) { // 來自 select_is.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String emp_no = req.getParameter("emp_no").trim().toUpperCase();
				String emp_noReg = "E{1}M{1}P{1}[\\d]{4}";
				Pattern pat = Pattern.compile(emp_noReg);
				Matcher matcher = pat.matcher(emp_no.trim());
				if(emp_no==null||(emp_no.length()==0)) {
					errorMsgs.add("請輸入員工編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				if(!matcher.find()) {
					errorMsgs.add("員工編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_no);
				if (empVO == null) {
					errorMsgs.add("查無員工資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				Inform_SetService isSvc = new Inform_SetService();
				List<Inform_SetVO> isVOs = isSvc.getIsByEmp(emp_no);
				if (isVOs == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("isVOs", isVOs); // 資料庫取出的 isVOs ,存入req
//				HttpSession session = req.getSession();
//				session.setAttribute("isVOs", isVOs);
				String url = "/back-end/inform_set/listByComplex_is.jsp";//"/back-end/inform_set/listByEmp_is.jsp"
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 listByComplex_is.jsp
				successView.forward(req, res);
				
			} catch(Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		if("getIsForDisplayByCont".equals(action)) { // 來自 select_is.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String is_cont = req.getParameter("is_cont").trim();
				if (is_cont == null || is_cont.length() == 0) {
					errorMsgs.add("請確實輸入欲查詢之通知內容關鍵字");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String is_contReg="[`~!@#$%^&*()+=|{}':;'\",\\[\\].?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
				Pattern pat = Pattern.compile(is_contReg); 
				Matcher matcher = pat.matcher(is_cont);
				if(matcher.find()) {
					errorMsgs.add("請輸入非特殊符號之關鍵字");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				Inform_SetService isSvc = new Inform_SetService();
				List<Inform_SetVO> isVOs = new ArrayList<Inform_SetVO>();
				if(is_cont.contains(" ")) {
					String[] many = is_cont.split(" ");
					for(String aSearch : many) { // isSvc.getIsByCont(aSearch) 回傳 List<Inform_SetVO>
						for(Inform_SetVO isVO : isSvc.getIsByCont(aSearch) ) {
							isVOs.add(isVO);
						}
					}
					if(isVOs.isEmpty()) {
						errorMsgs.add("查無資料");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
						failureView.forward(req, res);
						return;
					}
					
					Set<Inform_SetVO> temp= new HashSet<Inform_SetVO>();
					temp.addAll(isVOs);      
					isVOs = new ArrayList<Inform_SetVO>();
					isVOs.addAll(temp);
					
					Collections.sort(isVOs);
					
					if (isVOs.isEmpty()) {
						errorMsgs.add("查無資料");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
						failureView.forward(req, res);
						return;
					}
					
				} else {
					isVOs = isSvc.getIsByCont(is_cont);
					if (isVOs.isEmpty()) {
						errorMsgs.add("查無資料");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
						failureView.forward(req, res);
						return;
					}
				}
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("isVOs", isVOs); // 資料庫取出的 isVOs ,存入req
//				HttpSession session = req.getSession();
//				session.setAttribute("isVOs", isVOs);
				String url = "/back-end/inform_set/listByComplex_is.jsp";//"/back-end/inform_set/listByCont_is.jsp"
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 listByComplex_is.jsp
				successView.forward(req, res);
				
			} catch(Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		if("getIsForDisplayByDate".equals(action)) { // 來自 select_is.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數****************************************/
				// 起始日期
				String startDate = req.getParameter("is_date_startDate").trim();
				if (startDate == null || startDate.length() == 0) {
					errorMsgs.add("請輸入起始日期");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				// 修改傳入字串，將字串由 mm/dd/yyyy 改為 yyyy-mm-dd
				String[] startDateArr = startDate.split("/");
				startDate = startDateArr[2]+'-'+startDateArr[0]+'-'+startDateArr[1];
				
				// 結束日期
				String stopDate = req.getParameter("is_date_stopDate").trim();
				if (stopDate == null || stopDate.length() == 0) {
					errorMsgs.add("請輸入結束日期");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				// 修改傳入字串，將字串由 mm/dd/yyyy 改為 yyyy-mm-dd
				String[] stopDateArr = stopDate.split("/");
				stopDate = stopDateArr[2]+'-'+stopDateArr[0]+'-'+stopDateArr[1];
				
				/***************************2.參數轉型、開始查詢資料****************************************/
				Inform_SetService isSvc = new Inform_SetService();
				List<Inform_SetVO> isVOs = isSvc.getIsByDate(startDate, stopDate);
				if (isVOs.isEmpty()) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("isVOs", isVOs); // 資料庫取出的 isVOs ,存入req
//				HttpSession session = req.getSession();
//				session.setAttribute("isVOs", isVOs);
				String url = "/back-end/inform_set/listByComplex_is.jsp";//"/back-end/inform_set/listByDate_is.jsp"
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 listByComplex_is.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch(Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		
		if("getIsForDisplayByComplex".equals(action)) { // 來自 select_is.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數，並依據參數查詢資料****************************************/
				/************************************* 通知編號 is_no ( String ) ********************************/
				// 判斷是否輸入通知編號
				String is_no = req.getParameter("is_no").trim();
				if ( is_no != null && is_no.length() != 0) {
					is_no = is_no.toUpperCase();
					String is_noReg = "I{1}S{1}[\\d]{4}";
					Pattern patIs_no = Pattern.compile(is_noReg);
					Matcher matcherIs_no = patIs_no.matcher(is_no.trim());
					if(!matcherIs_no.find()) {
						errorMsgs.add("通知設定編號格式不正確");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
						failureView.forward(req, res);
						return;
					}
				}
				

				/**************************************** 得員工編號 emp_no ( String ) ****************************************/
				// 取得員工編號參數
				String emp_no = req.getParameter("emp_no").trim();
				// 判斷是否真的有輸入員工編號
				// 有輸入員工編號
				if(emp_no != null && emp_no.length() != 0) { 
					emp_no = emp_no.toUpperCase();
					String emp_noReg = "E{1}M{1}P{1}[\\d]{4}";
					Pattern patEmp = Pattern.compile(emp_noReg);
					Matcher matcherEmp = patEmp.matcher(emp_no.trim());
					if(!matcherEmp.find()) {
						errorMsgs.add("員工編號格式不正確");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
						failureView.forward(req, res);
						return;
					}
					EmpService empSvc = new EmpService();
					EmpVO empVO = empSvc.getOneEmp(emp_no);
					if (empVO == null) {
						errorMsgs.add("查無員工");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
						failureView.forward(req, res);
						return;
					}
				}
				
				/*********************************** 得通知內容 is_cont ( String 或  String[] ) ***********************************/	
				// 取得模糊查詢的參數
				String tempIs_cont = req.getParameter("is_cont").trim();
				String[] manyIs_cont;
				String[] is_cont = null;
				// 有輸入模糊查詢
				if (tempIs_cont != null && tempIs_cont.length() != 0) {
					String is_contReg="[`~!@#$%^&*()+=|{}':;'\",\\[\\].?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
					Pattern patCont = Pattern.compile(is_contReg); 
					Matcher matcherCont = patCont.matcher(tempIs_cont);
					if(matcherCont.find()) {
						errorMsgs.add("請輸入非特殊符號之關鍵字");
					}
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
						failureView.forward(req, res);
						return;
					} // 輸入模糊查詢後，若上述錯誤狀況未發生，則進入下方條件判斷流程
					// 模糊查詢之內容若含有空白格，則需切割分段
					if(tempIs_cont.contains(" ")) {
						manyIs_cont = tempIs_cont.split(" ");
						is_cont = manyIs_cont;
					} else {
						is_cont = new String[1];
						is_cont[0] = tempIs_cont;
					}
				}
				
				/****************************** 起始及結束日期 startDate 和 stopDate ( String ) ******************************/
				// 判斷是否輸入日期
				// 起始日期
				String startDate = req.getParameter("is_date_startDate").trim();
				if ("".equals(startDate)) {
					startDate = "01/01/1970";
				}
				if (startDate != null && !"".equals(startDate)) {
					// 修改傳入字串，將字串由 mm/dd/yyyy 改為 yyyy-mm-dd
					String[] startDateArr = startDate.split("/");
					startDate = startDateArr[2]+'-'+startDateArr[0]+'-'+startDateArr[1];
				}
				// 結束日期
				String stopDate = req.getParameter("is_date_stopDate").trim();
				java.sql.Date is_date_stopDate = null;
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				
				// 若都沒填，直接重導向到 listAll_is.jsp
				if("".equals(is_no) && "".equals(emp_no) && "".equals(tempIs_cont) && "1970-01-01".equals(startDate) && "".equals(stopDate)) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/listAll_is.jsp");
					failureView.forward(req, res);
					return;
				}
				
				if ("".equals(stopDate)) {
					is_date_stopDate = new java.sql.Date(System.currentTimeMillis()+63072000000L);
					stopDate = sdf.format(is_date_stopDate);
				}
				if (stopDate != null && !"".equals(stopDate)) {
					// 修改傳入字串，將字串由 mm/dd/yyyy 改為 yyyy-mm-dd
					String[] stopDateArr = stopDate.split("/");
					stopDate = stopDateArr[2]+'-'+stopDateArr[0]+'-'+stopDateArr[1];
				}
				/******************************** 2.開始查詢資料，進行可能的錯誤處理 *******************************/
				Inform_SetService isSvc = new Inform_SetService();
				List<Inform_SetVO> isVOs = isSvc.getIsByComplex(is_no, emp_no, is_cont, startDate, stopDate);
				if (isVOs.isEmpty() || isVOs == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
					failureView.forward(req, res);
					return;
				}
				/******************************** 3.查詢完成,準備轉交(Send the Success view) *****************/
				req.setAttribute("isVOs", isVOs); // 資料庫取出的 isVOs ,存入req
//				HttpSession session = req.getSession();
//				session.setAttribute("isVOs", isVOs);
				String url = "/back-end/inform_set/listByComplex_is.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 listByComplex_is.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch(Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("getOneIsForUpdate".equals(action)) { 
			// 來自 listAll_is.jsp、listByCont_is.jsp、listByComplex_is.jsp、listByDate_is.jsp、listByEmp_is.jsp、listOne_is.jsp 的請求
			// Q：如何在發生錯誤時及成功轉交時，跳回來源頁面? 使用 form 表單提交的頁面，有辦法跳回去嗎?
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String is_no = req.getParameter("is_no").trim();
				
				/***************************2.開始查詢資料，進行可能的錯誤處理****************************************/
				Inform_SetService isSvc = new Inform_SetService();
				Inform_SetVO isVO = isSvc.getOneIs(is_no);
				java.util.Date nowTime = new java.util.Date();
				if(isVO.getIs_date().before(nowTime)) {
					errorMsgs.add("通知編號 " + is_no + " 時效已過期，無法予以修改");
				}
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("isVO", isVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/listOne_is.jsp");
					failureView.forward(req, res);
					return;
				}
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("isVO", isVO);
				String url = "/back-end/inform_set/update_is.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_is.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch(Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("updateIs".equals(action)) { // 來自update_is.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String is_no = req.getParameter("is_no").trim();
				
				String emp_no = req.getParameter("emp_no").trim().toUpperCase();
				String emp_noReg = "E{1}M{1}P{1}[\\d]{4}";
				Pattern pat = Pattern.compile(emp_noReg);
				Matcher matcher = pat.matcher(emp_no.trim());
				if(emp_no==null||(emp_no.length()==0)) {
					errorMsgs.add("請輸入員工編號");
				}
				if(!matcher.find()) {
					errorMsgs.add("員工編號格式不正確");
				}

				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_no);
				if (empVO == null) {
					errorMsgs.add("查無員工資料");
				}
				
				String is_cont = req.getParameter("is_cont").trim();
				if (is_cont == null || is_cont.length() == 0) {
					errorMsgs.add("通知設定內容請勿空白");
				}
				if (is_cont.length() < 5) {
					errorMsgs.add("通知設定內容長度須至少 5 個字元以上");
	            }
				
				java.sql.Date is_date = null;
				try {
					is_date = java.sql.Date.valueOf(req.getParameter("is_date").trim());
				} catch (IllegalArgumentException e) {
					is_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				// 判斷是否小於今日
				java.util.Date today = new java.util.Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String todayStr = sdf.format(today);
				java.util.Date sqlToday = java.sql.Date.valueOf(todayStr);
				if(is_date.before(sqlToday)){
					errorMsgs.add("請勿輸入過去日期!");
				}
				
				Inform_SetVO isVO = new Inform_SetVO();
				isVO.setIs_no(is_no);
				isVO.setEmp_no(emp_no);
				isVO.setIs_cont(is_cont);
				isVO.setIs_date(is_date);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("isVO", isVO); // 含有輸入格式錯誤的isVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/update_is.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				Inform_SetService isSvc = new Inform_SetService();
				isVO = isSvc.updateIs(is_no, emp_no, is_cont, is_date);
				
				// 若使用者輸入的時間為今日，則會直接執行由 Inform_Set table 輸入至 Front_Inform table 的動作
				if(todayStr.equals(is_date.toString())) {
					Front_InformService fiSvc = new Front_InformService();
					fiSvc.addISToAll(isVO.getIs_no());
				}
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("isVO", isVO); // 資料庫update成功後,正確的的isVO物件,存入req
//				HttpSession session = req.getSession();
//				session.setAttribute("isVO", isVO);
				String url = "/back-end/inform_set/listOne_is.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOne_is.jsp
				successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/update_is.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("insertIs".equals(action)) { // 來自add_is.jsp的請求  

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String emp_no = req.getParameter("emp_no").trim().toUpperCase();
				if(emp_no==null||(emp_no.length()==0)) {
					errorMsgs.add("請輸入員工編號");
				}
				
				String emp_noReg = "E{1}M{1}P{1}[\\d]{4}";
				Pattern pat = Pattern.compile(emp_noReg);
				Matcher matcher = pat.matcher(emp_no.trim());
				if(!matcher.find()) {
					errorMsgs.add("員工編號格式不正確");
				}
				
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(emp_no.toUpperCase());
				if (empVO == null) {
					errorMsgs.add("查無員工資料");
				}
				
				String is_cont = req.getParameter("is_cont").trim();
				if (is_cont == null || is_cont.length() == 0) {
					errorMsgs.add("通知設定內容請勿空白");
				}
				if (is_cont.length() < 5) {
					errorMsgs.add("通知設定內容長度須至少 5 個字元以上");
	            }
				
				java.sql.Date is_date = null;
				try {
					is_date = java.sql.Date.valueOf(req.getParameter("is_date").trim());
				} catch (IllegalArgumentException e) {
					is_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				// 判斷是否小於今日
				java.util.Date today = new java.util.Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String todayStr = sdf.format(today);
				java.util.Date sqlToday = java.sql.Date.valueOf(todayStr);
				if(is_date.before(sqlToday)){
					errorMsgs.add("請勿輸入過去日期!");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/add_is.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Inform_SetVO isVO = new Inform_SetVO();
				isVO.setEmp_no(emp_no);
				isVO.setIs_cont(is_cont);
				isVO.setIs_date(is_date);
				
				/***************************2.開始新增資料***************************************/
				Inform_SetService isSvc = new Inform_SetService();
				isVO = isSvc.addIs(emp_no, is_cont, is_date);
				
				// 若使用者輸入的時間為今日，則會直接執行由 Inform_Set table 輸入至 Front_Inform table 的動作
				if(todayStr.equals(is_date.toString())) {
					Front_InformService fiSvc = new Front_InformService();
					fiSvc.addISToAll(isVO.getIs_no());
				}
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/inform_set/listAll_is.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAll_is.jsp
				successView.forward(req, res);	
				
			}catch(Exception e) {
				errorMsgs.add("新增資料失敗：" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/add_is.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		if("deleteIs".equals(action)) { 
			// 來自 listAll_is.jsp、listByCont_is.jsp、listByComplex_is.jsp、listByEmp_is.jsp、listByDate_is.jsp、listOne_is.jsp 的請求
			// Q：如何在發生錯誤時及成功轉交時，跳回來源頁面? 使用 form 表單提交的頁面，有辦法跳回去嗎?
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數***************************************/
				String is_no = new String(req.getParameter("is_no").trim());
				
				/***************************2.確認是否過期***************************************/
				Inform_SetService isSvc = new Inform_SetService();
				Inform_SetVO isVO = isSvc.getOneIs(is_no);
				java.util.Date nowTime = new java.util.Date();
				if(isVO.getIs_date().before(nowTime)) {
					errorMsgs.add("通知編號 " + is_no + " 時效已過期，無法予以刪除");
				}
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("isVO", isVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/listOne_is.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				isSvc.deleteIs(is_no);
				String url = "/back-end/inform_set/listAll_is.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

			/***************************其他可能的錯誤處理**********************************/
			}catch(Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/inform_set/select_is.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
