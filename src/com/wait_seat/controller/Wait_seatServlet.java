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

			Wait_seatService WSsvc=new Wait_seatService();
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
			Integer wait_n=Integer.valueOf(req.getParameter("wait_n"));
			Integer delay=Integer.valueOf(req.getParameter("delay"));
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
					
					Wait_seatVO wait_seatVO = new Wait_seatVO();
					
					wait_seatVO.setWait_seat_no(wait_seat_no);
					wait_seatVO.setMem_no(mem_no);
					wait_seatVO.setN_mem_name(n_mem_name);
					wait_seatVO.setPhone_m(phone_m);
					wait_seatVO.setDelay(delay);
					wait_seatVO.setWait_n(wait_n);
					
					if (!updateErrorMsgs.isEmpty()) {
						req.setAttribute("wait_seatVO", wait_seatVO);
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/wait_seat/getOneWait_seat.jsp");
						failureView.forward(req, res);
						return;
					}
					/*************************** 2.開始修改資料 *****************************************/
					Wait_seatService wait_seatSvc = new Wait_seatService();
					wait_seatVO = wait_seatSvc.updateWait_seat(wait_seat_no, mem_no, n_mem_name, phone_m,Integer.valueOf(WSsvc.getOneWait_seat(wait_seat_no).getDelay()),Integer.valueOf(WSsvc.getOneWait_seat(wait_seat_no).getWait_n()));
					
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
					wait_seatVO = wait_seatSvc.addWait_seat(mem_no, n_mem_name, phone_m,0);
					
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
				List<Wait_seatVO> list = wait_seatSvc.getAll();
				for(int i=0;i<list.size();i++) {
					wait_seatSvc.updateWait_seat(list.get(i).getWait_seat_no(), list.get(i).getMem_no(), list.get(i).getN_mem_name(), list.get(i).getPhone_m(), list.get(i).getDelay(), i+1);
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
			res.setContentType("application/json; charset=utf-8");
			PrintWriter out = res.getWriter();
			Gson gson = new Gson();   
			String str = gson.toJson(list); 
			out.print(str);
		}
		if ("getOne".equals(action)) { // 來自update_food_input.jsp的請求
			String wait_seat_no=req.getParameter("wait_seat_no");
			Wait_seatService WSsvc = new Wait_seatService();
			Wait_seatVO WSVO = WSsvc.getOneWait_seat(wait_seat_no);
			req.setAttribute("wait_seatVO", WSVO);
			String url = "/back-end/wait_seat/getOneWait_seat.jsp";
			RequestDispatcher View = req.getRequestDispatcher(url);
			View.forward(req, res);
		}
		if ("delay_update".equals(action)) { 
			Wait_seatService WSsvc=new Wait_seatService();
			List<Wait_seatVO> newlist=WSsvc.getAll();
			int size=newlist.size();
			Wait_seatVO[] WSVOs=null;
			int[] temp;
//			if(size==1 || newlist.get(0).getDelay()==1) { //等待只有一位，未到次數已經有一次了，刪掉它
			if(newlist.get(0).getDelay()==1) { //未到次數已經有一次了，刪掉它
				Wait_seatVO VO=newlist.get(0);
				WSsvc.deleteWait_seat(VO.getWait_seat_no());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/wait_seat/listAllWait_seat.jsp");
				failureView.forward(req, res);
				return;
			}
			if(size==1) {
				WSsvc.updateWait_seat(WSsvc.getAll().get(0).getWait_seat_no(),
									  WSsvc.getAll().get(0).getMem_no(),
									  WSsvc.getAll().get(0).getN_mem_name(),
									  WSsvc.getAll().get(0).getPhone_m(),
									  WSsvc.getAll().get(0).getDelay()+1,
									  WSsvc.getAll().get(0).getWait_n()
						);
			}else if(size<6) {
				
			}else {
				size=6;
			}
			WSVOs=new Wait_seatVO[size];
			for(int i=0;i<size;i++) {
				WSVOs[i]=newlist.get(i);
			}
			temp=new int[size];
			for(int i=0;i<size;i++) {
				temp[i]=WSVOs[i].getWait_n();
			}
			for(int i=0;i<size-1;i++) {
//				WSVOs[i+1].setWait_n(temp[i]);
				WSsvc.updateWait_seat(WSVOs[i+1].getWait_seat_no(),
									  WSVOs[i+1].getMem_no(), 
									  WSVOs[i+1].getN_mem_name(), 
									  WSVOs[i+1].getPhone_m(),
									  WSVOs[i+1].getDelay(),
									  temp[i]);
//				System.out.println("第"+(i+1)+"個的等待改成第"+i+"個  "+temp[i]);
			}
			WSsvc.updateWait_seat(
					WSVOs[0].getWait_seat_no(),
					WSVOs[0].getMem_no(), 
					WSVOs[0].getN_mem_name(), 
					WSVOs[0].getPhone_m(),
					(WSVOs[0].getDelay()+1),
					temp[size-1]
					);
			
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/wait_seat/listAllWait_seat.jsp");
			failureView.forward(req, res);
		}
		
		
	}
}
