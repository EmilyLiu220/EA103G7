package com.wait_seat.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.meal.model.MealDAO;
import com.wait_seat.model.*;

public class Wait_seatServlet extends HttpServlet {

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
			String mem_no=null;
			String n_mem_name=null;
			boolean memIsNull=false; //因為未有其他情況產生錯誤訊息
			boolean n_mem_nameIsNull=false;
			String n_mem_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z)]{2,10}$";//檢驗中文、英文
			String mem_noReg = "^MEM[0-9]{4}$";
			
			String wait_seat_no=req.getParameter("wait_seat_no");
			
			if((mem_no=req.getParameter("mem_no").trim())==null || mem_no.length()==0) {
				memIsNull=true;
			}
			if((n_mem_name=req.getParameter("n_mem_name").trim())==null || n_mem_name.length()==0) {
				n_mem_nameIsNull=true;
			}
			
			String phone_m=req.getParameter("phone_m");

			Wait_seatVO wait_seatVO = new Wait_seatVO();
				try {
					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
					//wait_seat_no不需要檢查，並不是給使用者輸入，是事先寫好的

					
					if((memIsNull==true && n_mem_nameIsNull==true) || (memIsNull==false && n_mem_nameIsNull==false)) {
						updateErrorMsgs.add("候位編號"+wait_seat_no+"修改錯誤，會員編號或非會員候位姓名最多只能填寫一個");
					}
					if(memIsNull==true && n_mem_nameIsNull==false) {
						if(!n_mem_name.trim().matches(n_mem_nameReg)){
							updateErrorMsgs.add("候位編號"+wait_seat_no+"修改錯誤，非會員候位姓名只能是中、英文且長度2~10");
						}
					}
					if(memIsNull==false && n_mem_nameIsNull==true) {
						if(!mem_no.trim().matches(mem_noReg)){
							updateErrorMsgs.add("候位編號"+wait_seat_no+"修改錯誤，會員編號只能是MEM開頭，後面數字從0000-9999");
						}
					}
					
					String phone_mReg = "^09[0-9]{8}$";
					if (phone_m == null || phone_m.trim().length() == 0) {
						updateErrorMsgs.add("候位編號"+wait_seat_no+"修改錯誤，行動電話請勿空白");
					} else if (!phone_m.trim().matches(phone_mReg)) { 
						updateErrorMsgs.add("候位編號"+wait_seat_no+"修改錯誤，行動電話只能是數字, 且長度為10碼中間沒有其他符號");
					} 
					
					if (!updateErrorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/wait_seat/listAllWait_seat.jsp");
						failureView.forward(req, res);
						return;
					}

					wait_seatVO.setWait_seat_no(wait_seat_no);
					wait_seatVO.setPhone_m(phone_m);
					
					/*************************** 2.開始修改資料 *****************************************/
					Wait_seatService wait_seatSvc = new Wait_seatService();
					wait_seatVO = wait_seatSvc.updateWait_seat(wait_seat_no, mem_no, n_mem_name, phone_m);
					
					/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
					req.setAttribute("wait_seatVO", wait_seatVO); // 資料庫update成功後,正確的的foodVO物件,存入req
					String url = "/back-end/wait_seat/listAllWait_seat.jsp";
	
					/*************************** 其他可能的錯誤處理 *************************************/
				} catch (Exception e) {
					updateErrorMsgs.add("修改資料失敗:" + e.getMessage());
				}
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/wait_seat/listAllWait_seat.jsp");
			failureView.forward(req, res);
		}
		
		if ("insert".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			int errorTime=0;
			String mem_no=null;
			String n_mem_name=null;
			boolean memIsNull=false; //因為未有其他情況產生錯誤訊息
			boolean n_mem_nameIsNull=false;
			String n_mem_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z)]{2,10}$";//檢驗中文、英文
			String mem_noReg = "^MEM[0-9]{4}$";
			if((mem_no=req.getParameter("mem_no").trim())==null || mem_no.length()==0) {
				memIsNull=true;
			}
			if((n_mem_name=req.getParameter("n_mem_name").trim())==null || n_mem_name.length()==0) {
				n_mem_nameIsNull=true;
			}		
			
			String phone_m=req.getParameter("phone_m");

			Wait_seatVO wait_seatVO = new Wait_seatVO();
				try {
					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
					//wait_seat_no不需要檢查，並不是給使用者輸入，是事先寫好的

					
					if((memIsNull==true && n_mem_nameIsNull==true) || (memIsNull==false && n_mem_nameIsNull==false)) {
						errorMsgs.add("修改錯誤，會員編號或非會員候位姓名最多只能填寫一個");
					}
					if(memIsNull==true && n_mem_nameIsNull==false) {
						if(!n_mem_name.trim().matches(n_mem_nameReg)){
							errorMsgs.add("修改錯誤，非會員候位姓名只能是中、英文且長度2~10");
						}
					}
					if(memIsNull==false && n_mem_nameIsNull==true) {
						if(!mem_no.trim().matches(mem_noReg)){
							errorMsgs.add("修改錯誤，會員編號只能是MEM開頭，後面數字從0000-9999");
						}
					}
					
					String phone_mReg = "^09[0-9]{8}$";
					if (phone_m == null || phone_m.trim().length() == 0) {
						errorMsgs.add("修改錯誤，行動電話請勿空白");
					} else if (!phone_m.trim().matches(phone_mReg)) { 
						errorMsgs.add("修改錯誤，行動電話只能是數字, 且長度為10碼中間沒有其他符號");
					} 								
					
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/wait_seat/listAllWait_seat.jsp");
						failureView.forward(req, res);
						return;
					}

					wait_seatVO.setPhone_m(phone_m);
					
					/*************************** 2.開始修改資料 *****************************************/
					Wait_seatService wait_seatSvc = new Wait_seatService();
					wait_seatVO = wait_seatSvc.addWait_seat(mem_no, n_mem_name, phone_m);
					
					/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
					req.setAttribute("wait_seatVO", wait_seatVO); // 資料庫update成功後,正確的的foodVO物件,存入req
					String url = "/back-end/wait_seat/listAllWait_seat.jsp";
	
					/*************************** 其他可能的錯誤處理 *************************************/
				} catch (Exception e) {
					errorMsgs.add("修改資料失敗:" + e.getMessage());
				}
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/wait_seat/listAllWait_seat.jsp");
			failureView.forward(req, res);
		}
		if ("delete".equals(action)) { // 來自listAllWait_seat.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String[] wait_seat_no=req.getParameterValues("wait_seat_no");
				/*************************** 2.開始刪除資料 ***************************************/
				Wait_seatService wait_seatSvc = new Wait_seatService();
				for(int i=0;i<wait_seat_no.length;i++) {
					wait_seatSvc.deleteWait_seat(wait_seat_no[i]);
				}	

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/wait_seat/listAllWait_seat.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/wait_seat/listAllWait_seat.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getAll".equals(action)) {
			Wait_seatDAO WSDao = new Wait_seatDAO();
			List<Wait_seatVO> list = WSDao.getAllForUser();
//			Map<String,String> map = new HashMap<>();
//			if (list!=null) {
//				for(Wait_seatVO VO:list) {
//					map.put("wait_seat_no",VO.getWait_seat_no());
//					map.put("phone_m",VO.getPhone_m());
//				}
//			}
			res.setContentType("application/json; charset=utf-8");
			PrintWriter out = res.getWriter();
//			JSONObject jsonObject = new JSONObject(list);
			Gson gson = new Gson();   
			String str = gson.toJson(list); 
			out.print(str);
		}
	}
}
