package com.food.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.food.model.*;

public class FoodServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("update".equals(action)) { // 來自update_food_input.jsp的請求

			List<String> updateErrorMsgs = new LinkedList<String>();
			req.setAttribute("updateErrorMsgs", updateErrorMsgs);

			int errorTime=0;
			String[] fd_no=req.getParameterValues("fd_no");
			String[] fd_name=req.getParameterValues("fd_name");
			String[] fd_stk_str=req.getParameterValues("fd_stk");
			String[] stk_ll_str=req.getParameterValues("stk_ll");
			String[] cal_str=req.getParameterValues("cal");
			String[] prot_str=req.getParameterValues("prot");
			String[] carb_str=req.getParameterValues("carb");
			String[] fat_str=req.getParameterValues("fat");

			FoodVO foodVO = new FoodVO();
			for(int i=0;i<fd_no.length;i++) {
				try {
					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
					//fd_no不需要檢查，並不是給使用者輸入，是事先寫好的
					
					String fd_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,10}$";
					if (fd_name[i] == null || fd_name[i].trim().length() == 0) {
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，食材名稱請勿空白");
					} else if (!fd_name[i].trim().matches(fd_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，食材名稱只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
					}
					
					Integer fd_isdel=1;//修改不會給他設定是否刪除		
					
					String fd_stkReg = "^[1-9][0-9]{0,4}$"; //只給輸入五位數
					if (fd_stk_str[i] == null || fd_stk_str[i].trim().length() == 0) {
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，庫存數量請勿空白");
					} else if (!fd_stk_str[i].trim().matches(fd_stkReg)) { 
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，庫存數量只能數字, 且長度必需在1到5之間");
					}
					Integer fd_stk = new Integer(fd_stk_str[i].trim()); //已經檢查了，直接轉型
														
					//String stk_llReg = "^[1-9][0-9]{0,4}$"; //只給輸入五位數
					if (stk_ll_str[i] == null || stk_ll_str[i].trim().length() == 0) {
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，庫存底線請勿空白");
					} else if (!stk_ll_str[i].trim().matches(fd_stkReg)) { 
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，庫存底線只能數字, 且長度必需在1到5之間");
					}
					Integer stk_ll = new Integer(fd_stk_str[i].trim()); //已經檢查了，直接轉型
					
					String calReg = "^[0-9]{1,5}[.]?[0-9]?$"; //只給輸入1~5位數+小數點+小數點後1位數
					if (cal_str[i] == null || cal_str[i].trim().length() == 0) {
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，熱量請勿空白");
					} else if (!cal_str[i].trim().matches(calReg)) { 
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，熱量只能數字, 且長度必需在1到5之間，小數點最多一位");
					}
					Double cal = new Double(cal_str[i].trim()); //已經檢查了，直接轉型
					
					if (prot_str[i] == null || prot_str[i].trim().length() == 0) {
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，蛋白質請勿空白");
					} else if (!prot_str[i].trim().matches(calReg)) { 
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，蛋白質只能數字, 且長度必需在1到5之間，小數點最多一位");
					}
					Double prot = new Double(prot_str[i].trim()); //已經檢查了，直接轉型
					
					if (carb_str[i] == null || carb_str[i].trim().length() == 0) {
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，碳水請勿空白");
					} else if (!carb_str[i].trim().matches(calReg)) { 
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，碳水只能數字, 且長度必需在1到5之間，小數點最多一位");
					}
					Double carb = new Double(carb_str[i].trim()); //已經檢查了，直接轉型
					
					if (fat_str[i] == null || fat_str[i].trim().length() == 0) {
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，脂肪請勿空白");
					} else if (!fat_str[i].trim().matches(calReg)) { 
						updateErrorMsgs.add("食材編號"+fd_no[i]+"修改錯誤，脂肪只能數字, 且長度必需在1到5之間，小數點最多一位");
					}
					Double fat = new Double(fat_str[i].trim()); //已經檢查了，直接轉型
					
					if(updateErrorMsgs.size()!=errorTime) {
						errorTime=updateErrorMsgs.size();
						continue;
					}
					
					foodVO.setFd_no(fd_no[i]);
					foodVO.setFd_name(fd_name[i]);
					foodVO.setFd_isdel(fd_isdel);
					foodVO.setFd_stk(fd_stk);
					foodVO.setStk_ll(stk_ll);
					foodVO.setCal(cal);
					foodVO.setProt(prot);
					foodVO.setCarb(carb);
					foodVO.setFat(fat);
					
					//req.setAttribute("foodVO", foodVO); // 含有輸入格式錯誤的foodVO物件,也存入req
					
					/*************************** 2.開始修改資料 *****************************************/
					FoodService fdSvc = new FoodService();
					foodVO = fdSvc.updateFood(fd_no[i], fd_name[i],fd_isdel,fd_stk,stk_ll,cal,prot,carb,fat);
					
					/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
					req.setAttribute("foodVO", foodVO); // 資料庫update成功後,正確的的foodVO物件,存入req
					String url = "/back-end/food/listAllFood.jsp";
	
					/*************************** 其他可能的錯誤處理 *************************************/
				} catch (Exception e) {
					updateErrorMsgs.add("修改資料失敗:" + e.getMessage());
				}
			}
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/food/listAllFood.jsp");
			failureView.forward(req, res);
		}

		if ("insert".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			int errorTime=0;
			String[] fd_name=req.getParameterValues("fd_name");
			String[] fd_stk_str=req.getParameterValues("fd_stk");
			String[] stk_ll_str=req.getParameterValues("stk_ll");
			String[] cal_str=req.getParameterValues("cal");
			String[] prot_str=req.getParameterValues("prot");
			String[] carb_str=req.getParameterValues("carb");
			String[] fat_str=req.getParameterValues("fat");
			
			Integer fd_stk=null;
			Integer stk_ll=null;
			Double cal=null;
			Double prot=null;
			Double carb=null;
			Double fat=null;
			
			FoodVO foodVO = new FoodVO();
			
			for(int i=0;i<fd_name.length;i++) {
				
				try {
					/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/					
					String fd_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,10}$";
					if (fd_name[i] == null || fd_name[i].trim().length() == 0) {
						errorMsgs.add("順序"+(i+1)+"新增失敗，食材名稱請勿空白");
					} else if (!fd_name[i].trim().matches(fd_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
						errorMsgs.add("順序"+(i+1)+"新增失敗，食材名稱只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
					}
					
					Integer fd_isdel=1;//修改不會給他設定是否刪除		

					String fd_stkReg = "^[1-9][0-9]{0,4}$"; //只給輸入五位數
					if (fd_stk_str[i] == null || fd_stk_str[i].trim().length() == 0) {
						errorMsgs.add("順序"+(i+1)+"新增失敗，庫存數量請勿空白");
					} else if (!fd_stk_str[i].trim().matches(fd_stkReg)) { 
						errorMsgs.add("順序"+(i+1)+"新增失敗，庫存數量只能數字, 且長度必需在1到5之間");
					} else {
						fd_stk = new Integer(fd_stk_str[i].trim()); //已經檢查了，直接轉型
					}
					
					if (stk_ll_str[i] == null || stk_ll_str[i].trim().length() == 0) {
						errorMsgs.add("順序"+(i+1)+"新增失敗，庫存底線請勿空白");
					} else if (!stk_ll_str[i].trim().matches(fd_stkReg)) { 
						errorMsgs.add("順序"+(i+1)+"新增失敗，庫存底線只能數字, 且長度必需在1到5之間");
					} else {
						stk_ll = new Integer(stk_ll_str[i].trim()); //已經檢查了，直接轉型
					}
					
					String calReg = "^[0-9]{5}[.]?[0-9]?$"; //只給輸入五位數+小數點+小數點後一位數
					if (cal_str[i] == null || cal_str[i].trim().length() == 0) {
						errorMsgs.add("順序"+(i+1)+"新增失敗，熱量請勿空白");
					} else if (!cal_str[i].trim().matches(calReg)) { 
						errorMsgs.add("順序"+(i+1)+"新增失敗，熱量只能數字, 且長度必需在1到5之間，小數點最多一位");
					} else {
						cal = new Double(cal_str[i].trim()); //已經檢查了，直接轉型
					}

					if (prot_str[i] == null || prot_str[i].trim().length() == 0) {
						errorMsgs.add("順序"+(i+1)+"新增失敗，蛋白質請勿空白");
					} else if (!prot_str[i].trim().matches(calReg)) { 
						errorMsgs.add("順序"+(i+1)+"新增失敗，蛋白質只能數字, 且長度必需在1到5之間，小數點最多一位");
					} else {
						prot = new Double(prot_str[i].trim()); //已經檢查了，直接轉型
					}

					if (carb_str[i] == null || carb_str[i].trim().length() == 0) {
						errorMsgs.add("順序"+(i+1)+"新增失敗，碳水請勿空白");
					} else if (!carb_str[i].trim().matches(calReg)) {
						errorMsgs.add("順序"+(i+1)+"新增失敗，碳水只能數字, 且長度必需在1到5之間，小數點最多一位");
					} else {
						carb = new Double(carb_str[i].trim()); //已經檢查了，直接轉型
					}

					if (fat_str[i] == null || fat_str[i].trim().length() == 0) {
						errorMsgs.add("順序"+(i+1)+"新增失敗，脂肪請勿空白");
					} else if (!fat_str[i].trim().matches(calReg)) { 
						errorMsgs.add("順序"+(i+1)+"新增失敗，脂肪只能數字, 且長度必需在1到5之間，小數點最多一位");
					} else {
						fat = new Double(fat_str[i].trim()); //已經檢查了，直接轉型
					}
					
					if(errorMsgs.size()!=errorTime) {
						errorTime=errorMsgs.size();
						continue;
					}
					
					foodVO.setFd_name(fd_name[i]);
					foodVO.setFd_isdel(fd_isdel);
					foodVO.setFd_stk(fd_stk);
					foodVO.setStk_ll(stk_ll);
					foodVO.setCal(cal);
					foodVO.setProt(prot);
					foodVO.setCarb(carb);
					foodVO.setFat(fat);

					req.setAttribute("foodVO", foodVO); // 含有輸入格式錯誤的foodVO物件,也存入req						
					/*************************** 開始新增資料 ***************************************/
					FoodService foodSvc = new FoodService();
					foodVO = foodSvc.addFood(fd_name[i],fd_isdel,fd_stk,stk_ll,cal,prot,carb,fat);
					/*************************** 其他可能的錯誤處理 **********************************/
				} catch (Exception e) {
					errorMsgs.add("第"+(i+1)+"筆的新增資料失敗:"+e.getMessage());
				}
			}
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/food/listAllFood.jsp");
			failureView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllFood.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Enumeration<String> fdnos=req.getParameterNames();
				String[] fd_no=req.getParameterValues("fd_no");
				/*************************** 2.開始刪除資料 ***************************************/
				FoodService foodSvc = new FoodService();
				for(int i=0;i<fd_no.length;i++) {
					foodSvc.deleteFood(fd_no[i]);
				}	

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/food/listAllFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/food/listAllFood.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
