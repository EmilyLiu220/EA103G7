package com.meal_part.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.meal_part.model.*;

public class Meal_partServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("update".equals(action)) { // 來自update_meal_part_input.jsp的請求

			List<String> updateErrorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("updateErrorMsgs", updateErrorMsgs);

			int errorTime=0;
			String meal_no=req.getParameter("meal_no");
			String[] fd_no=req.getParameterValues("fd_no");
			String[] fd_gw_str=req.getParameterValues("fd_gw");
			//以目前來講修改一次只能修改一個
			Meal_partVO meal_partVO = new Meal_partVO();
			for(int i=0;i<fd_no.length;i++) {
				try {
					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
									
					
					String meal_partnoReg = "^MEAL[0-9]{4}$";
					if (meal_no == null || meal_no.trim().length() == 0) {
						updateErrorMsgs.add("餐點編號"+meal_no+"修改錯誤，餐點編號請勿空白");
					} else if (!meal_no.trim().matches(meal_partnoReg)) { // 以下練習正則(規)表示式(regular-expression)
						updateErrorMsgs.add("餐點編號"+meal_no+"修改錯誤，餐點編號只能是MEAL加上長度4的數字，從0000到9999");
					}
					
					String fd_noReg = "^FD[0-9]{4}$";
					if (fd_no[i] == null || fd_no[i].trim().length() == 0) {
						updateErrorMsgs.add("餐點編號"+meal_no+"修改錯誤，食材編號請勿空白");
					} else if (!fd_no[i].trim().matches(fd_noReg)) { // 以下練習正則(規)表示式(regular-expression)
						updateErrorMsgs.add("餐點編號"+meal_no+"修改錯誤，食材編號只能是MEAL加上長度4的數字，從0000到9999");
					}
					String fd_gwReg = "[1-9][0-9]{0,7}[.]?[0-9]?";
					if (fd_gw_str[i] == null || fd_gw_str[i].trim().length() == 0) {
						updateErrorMsgs.add("第"+(i+1)+"筆的庫存請勿空白");
					} else if (!fd_gw_str[i].trim().matches(fd_gwReg)) { 
						updateErrorMsgs.add("第"+(i+1)+"筆的庫存必須為數字 , 且開頭不得為0，長度必需在1到10之間");
					}
					Double[] fd_gw=new Double[fd_gw_str.length];
					try {
						fd_gw[i] = new Double(fd_gw_str[i]);
					} catch (NumberFormatException e) {
						fd_gw[i] = 0.0;  //隨便設一個數字給他回去
					}
					
		
					if(updateErrorMsgs.size()!=errorTime) {
						errorTime=updateErrorMsgs.size();
						continue;
					}
					
					meal_partVO.setMeal_no(meal_no);
					meal_partVO.setFd_no(fd_no[i]);
					meal_partVO.setFd_gw(fd_gw[i]);
					
					req.setAttribute("meal_partVO", meal_partVO); // 含有輸入格式錯誤的meal_partVO物件,也存入req
					
					/*************************** 2.開始修改資料 *****************************************/
					Meal_partService fdSvc = new Meal_partService();
					meal_partVO = fdSvc.updateMeal_part(meal_no, fd_no[i], fd_gw[i]);
					
					/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
					req.setAttribute("meal_partVO", meal_partVO); // 資料庫update成功後,正確的的meal_partVO物件,存入req
					String url = "/back-end/meal_part/listAllmeal_part.jsp";
	
					/*************************** 其他可能的錯誤處理 *************************************/
				} catch (Exception e) {
					updateErrorMsgs.add("修改資料失敗:" + e.getMessage());
				}
			}
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_part/listAllMeal_part.jsp");
			failureView.forward(req, res);
		}

		if ("insert".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			int errorTime=0;
			String meal_no=req.getParameter("meal_no");
			String[] fd_no=req.getParameterValues("fd_no");
			String[] fd_gw_str=req.getParameterValues("fd_gw");
			Meal_partVO meal_partVO = new Meal_partVO();
			//新增一次只能新增一道菜
			for(int i=0;i<fd_no.length;i++) {
				try {
					/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/					
					String meal_partnoReg = "^MEAL[0-9]{4}$";
					if (meal_no == null || meal_no.trim().length() == 0) {
						errorMsgs.add("餐點編號"+meal_no+"修改錯誤，餐點編號請勿空白");
					} else if (!meal_no.trim().matches(meal_partnoReg)) { // 以下練習正則(規)表示式(regular-expression)
						errorMsgs.add("餐點編號"+meal_no+"修改錯誤，餐點編號只能是MEAL加上長度4的數字，從0000到9999");
					}
					
					String fd_noReg = "^FD[0-9]{4}$";
					if (fd_no[i] == null || fd_no[i].trim().length() == 0) {
						errorMsgs.add("餐點編號"+meal_no+"修改錯誤，食材編號請勿空白");
					} else if (!fd_no[i].trim().matches(fd_noReg)) { // 以下練習正則(規)表示式(regular-expression)
						errorMsgs.add("餐點編號"+meal_no+"修改錯誤，食材編號只能是MEAL加上長度4的數字，從0000到9999");
					}
					System.out.println(meal_no);
					System.out.println(fd_no[i]);
					String fd_gwReg = "[0-9]{1,9}";
					if (fd_gw_str[i] == null || fd_gw_str[i].trim().length() == 0) {
						errorMsgs.add("第"+(i+1)+"筆的庫存請勿空白");
					} else if (!fd_gw_str[i].trim().matches(fd_gwReg)) { 
						errorMsgs.add("第"+(i+1)+"筆的庫存必須為數字 , 且開頭不得為0，長度必需在1到10之間");
					}					
					Double[] fd_gw=new Double[fd_gw_str.length];
					try {
						fd_gw[i] = new Double(fd_gw_str[i]);
					} catch (NumberFormatException e) {
						fd_gw[i] = 0.0;  //隨便設一個數字給他回去
					}	

					if(errorMsgs.size()!=errorTime) {
						errorTime=errorMsgs.size();
						continue;
					}
					
					meal_partVO.setMeal_no(meal_no);
					meal_partVO.setFd_no(fd_no[i]);
					meal_partVO.setFd_gw(fd_gw[i]);

					req.setAttribute("meal_partVO", meal_partVO); // 含有輸入格式錯誤的meal_partVO物件,也存入req						
					/*************************** 開始新增資料 ***************************************/
					Meal_partService meal_partSvc = new Meal_partService();
					meal_partVO = meal_partSvc.addMeal_part(meal_no, fd_no[i], fd_gw[i]);
					/*************************** 其他可能的錯誤處理 **********************************/
				} catch (Exception e) {
					errorMsgs.add("第"+(i+1)+"筆的新增資料失敗:"+e.getMessage());
				}
			}
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_part/listAllMeal_part.jsp");
			failureView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllmeal_part.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String[] meal_no=req.getParameterValues("meal_no");
				String[] fd_no=req.getParameterValues("fd_no");
				
				/*************************** 2.開始刪除資料 ***************************************/
				Meal_partService meal_partSvc = new Meal_partService();
				for(int i=0;i<fd_no.length;i++) {
					meal_partSvc.deleteMeal_part(meal_no[i], fd_no[i]);
				}	

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/meal_part/listAllMeal_part.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_part/listAllMeal_part.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
