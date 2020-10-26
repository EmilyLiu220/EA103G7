package com.wait_seat.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

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
			
			try {
				mem_no=req.getParameter("mem_no");
			}catch(NullPointerException e){
				memIsNull=true;
			}
			
			try {
				n_mem_name=req.getParameter("n_mem_name");
			}catch(NullPointerException e){
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
					
					if(updateErrorMsgs.size()!=errorTime) {
						errorTime=updateErrorMsgs.size();
						//continue;
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
		
		if ("insert".equals(action)) { // 來自update_food_input.jsp的請求

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
			System.out.println("mem_no="+mem_no+" "+memIsNull);
			System.out.println("n_mem_name="+n_mem_name+" "+n_mem_nameIsNull);			
			
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

//		if ("insert".equals(action)) { 
//			List<String> errorMsgs = new LinkedList<String>();
//			
//			req.setAttribute("errorMsgs", errorMsgs);
//			int errorTime=0;
//			String[] mem_no=req.getParameterValues("mem_no");
//			String[] n_mem_name=req.getParameterValues("n_mem_name");
//			String[] phone_m=req.getParameterValues("phone_m");
//			
//			Wait_seatVO wait_seatVO = new Wait_seatVO();
//						
//			for(int i=0;i<phone_m.length;i++) {
//				
//				try {
//					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//					//wait_seat_no不需要檢查，並不是給使用者輸入，是事先寫好的
//
//					String n_mem_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z)]{2,10}$";//檢驗中文、英文
//					String mem_noReg = "^MEM[0-9]{4}$";
//					
//					if ((mem_no[i] == null || mem_no[i].trim().length() == 0) && (n_mem_name[i] == null || n_mem_name[i].trim().length() == 0)) {
//						errorMsgs.add("順序"+(i+1)+"新增失敗，會員編號或非會員候位姓名至少要填寫一個"); //沒填會員編號也沒填非會員候位姓名
//					} else if((mem_no[i] != null || mem_no[i].trim().length() != 0) && (n_mem_name[i] != null || n_mem_name[i].trim().length() != 0) && mem_no[i].trim().matches(mem_noReg) && n_mem_name[i].trim().matches(n_mem_nameReg) ){
//						//都填寫正確，但填了兩樣，叫他重填
//						errorMsgs.add("順序"+(i+1)+"新增失敗，會員與非會員的資料都有填寫，請選擇其中一項填寫"); 
//					} else {
//						if((mem_no[i] != null || mem_no[i].trim().length() != 0) && !mem_no[i].trim().matches(mem_noReg)) {
//							errorMsgs.add("順序"+(i+1)+"新增失敗，會員編號必須是MEM開頭，後面填寫數字從0000-9999"); //有填寫但填寫錯誤 
//						}else {
//							wait_seatVO.setMem_no(mem_no[i]); //都輸入正確
//						}
//						
//						if((n_mem_name[i] != null || n_mem_name[i].trim().length() != 0) && !n_mem_name[i].trim().matches(n_mem_nameReg)) {
//							errorMsgs.add("順序"+(i+1)+"新增失敗，非會員候位姓名，後面只能填寫中文或英文長度2~10個字"); //有填寫但填寫錯誤 
//						}else {
//							wait_seatVO.setN_mem_name(n_mem_name[i]); //都輸入正確
//						}
//					}
//					
//					String phone_mReg = "^09[0-9]{8}$";
//					if (phone_m[i] == null || phone_m[i].trim().length() == 0) {
//						errorMsgs.add("順序"+(i+1)+"新增失敗，庫存數量請勿空白");
//					} else if (!phone_m[i].trim().matches(phone_mReg)) { 
//						errorMsgs.add("順序"+(i+1)+"新增失敗，庫存數量只能數字, 且長度必需在1到5之間");
//					} 								
//					
//					if(errorMsgs.size()!=errorTime) {
//						errorTime=errorMsgs.size();
//						continue;
//					}
//
//					wait_seatVO.setPhone_m(phone_m[i]);
//					/*************************** 開始新增資料 ***************************************/
//					Wait_seatService wait_seatSvc = new Wait_seatService();
//					wait_seatVO = wait_seatSvc.addWait_seat(mem_no[i], n_mem_name[i], phone_m[i]);
//					/*************************** 其他可能的錯誤處理 **********************************/
//				} catch (Exception e) {
//					errorMsgs.add("第"+(i+1)+"筆的新增資料失敗:"+e.getMessage());
//				}
//			}
//			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/wait_seat/listAllWait_seat.jsp");
//			failureView.forward(req, res);
//		}

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
	}
}
