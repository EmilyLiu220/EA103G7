package com.front_inform.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.front_inform.model.*;
import com.mem.model.*;
import com.res_detail.model.*;
import com.res_order.model.*;

public class Front_informServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("insertAuthFI".equals(action)) { // 來自後台管理 mem 權限的請求(view 待完成)
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String mem_no = req.getParameter("mem_no");
				if(mem_no==null||(mem_no.trim().length()==0)) {
					errorMsgs.add("請輸入會員編號");
				}
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(mem_no);
				if (memVO == null) {
					errorMsgs.add("查無會員資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
					failureView.forward(req, res);
					return; // 此區塊代表有錯誤，會導回去，程式中斷
				}
				
				// 通知的內容
				List<String> info_conts = new ArrayList<String>();
				Front_InformService front_informSvc = new Front_InformService();
				
				// 取得該會員各種權限狀態 ( for 停權相關的一般通知 )
				String mem_od_m = req.getParameter("mem_od_m");
				String mem_od_r = req.getParameter("mem_od_r");
				String mem_review = req.getParameter("mem_review");
				String mem_repo = req.getParameter("mem_repo");
				String mem_sts = req.getParameter("mem_sts");
				
				if(mem_sts!=null) { // 停權的 checkbox 被勾選
					info_conts.add("提醒您，您將於 1 分鐘後被停權");
				}else { // 停權的 checkbox 未被勾選
					if(mem_od_m==null) {
						info_conts.add("提醒您，因您多次訂餐付款但皆未至本餐廳取餐，您的訂餐功能將於 3 天後恢復");
					}
					if(mem_od_r==null) {
						info_conts.add("提醒您，因您多次訂位且多次點按確認當天用餐按鈕，但皆未至本餐廳用餐，您的訂位功能將於 3 天後恢復");
					}
					if(mem_review==null) {
						info_conts.add("提醒您，因您有多則評價被檢舉成功，您的評價功能將於 14 天後恢復");
					}
					if(mem_repo==null) {
						info_conts.add("提醒您，因您檢舉多則評價，但評價內容多數未達不當言論之標準，您的檢舉功能將於 7 天後恢復");
					}
				}
				
				for(String info_cont : info_conts) {
					front_informSvc.addNormalFI(mem_no, info_cont);
				}
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("updateSts".equals(action)) { // 來自fiNmsg.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String mem_no = req.getParameter("mem_no");
				if(mem_no==null||(mem_no.trim().length()==0)) {
					errorMsgs.add("請輸入會員編號");
				}
				String info_no = req.getParameter("info_no");
				if(info_no==null||(info_no.trim().length()==0)) {
					errorMsgs.add("通知編號不正確");
				}
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(mem_no);
				if (memVO == null) {
					errorMsgs.add("查無會員資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
					failureView.forward(req, res);
					return; // 此區塊代表有錯誤，會導回去，程式中斷
				}
				
				Front_InformService fiSvc = new Front_InformService();
				// 取得該會員回應「需回覆之通知」的結果
				String checkYes = req.getParameter("checkYes");
				String checkNo = req.getParameter("checkNo");
				
				/********** 使用者點按後需去改動 Front_Inform 和 Res_Order 兩個 table**********/
				// 取得該 ResOrderVO 及其所有原值
				String res_no = fiSvc.getByInfoNo(info_no).getRes_no();
				ResOrderService resOrderSvc = new ResOrderService();
				ResOrderVO resOrderVO = resOrderSvc.getOneResOrder(res_no);
				// 透過 ResDetailService 取得 String[] seats_no 才能塞入 updateResOrder() 方法
				ResDetailService resDetailSvc = new ResDetailService();
				List<ResDetailVO> resDetailVOs = resDetailSvc.getAllResNO(res_no);
				List<String> seats_noList = new ArrayList<String>();
				for(ResDetailVO resDetailVO : resDetailVOs) {
					seats_noList.add(resDetailVO.getSeat_no());
				}
				String[] seats_no= new String[seats_noList.size()];
				seats_noList.toArray(seats_no);
				
				if(checkYes!=null) { // 勾選確定來吃
					boolean checked = fiSvc.updateSts(1, info_no);
					if(checked) {
						// 發送當日訂位確認通知後必須修改 Info_Sts 為 2 (已發送已確認)
						resOrderSvc.updateResOrder(res_no, resOrderVO.getMeal_order_no(), resOrderVO.getMem_no(),
								resOrderVO.getEmp_no(), resOrderVO.getRes_date(), resOrderVO.getPeople(), resOrderVO.getTime_peri_no(),
								new Integer(2), resOrderVO.getSeat_sts(), seats_no);
					}
				}
				if(checkNo!=null) { // 勾選不來吃
					boolean checked = fiSvc.updateSts(3, info_no);
					if(checked) {
						fiSvc.addROFI(mem_no, res_no, "您的訂位已取消");
						// 發送當日訂位確認通知後必須修改 Info_Sts 為 3 (會員已取消)
						resOrderSvc.updateResOrder(res_no, resOrderVO.getMeal_order_no(), resOrderVO.getMem_no(),
								resOrderVO.getEmp_no(), resOrderVO.getRes_date(), resOrderVO.getPeople(), resOrderVO.getTime_peri_no(),
								new Integer(3), resOrderVO.getSeat_sts(), seats_no);
					}
				}
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
				failureView.forward(req, res);
			}
		}
		
		// ajax
		if ("getMyInform".equals(action)) { // 會員以其身分登入網站頁面後要直接執行此動作一次

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				HttpSession session = req.getSession();
				MemVO memVO2 = (MemVO) session.getAttribute("memVO2");
				String mem_no = memVO2.getMem_no();
				if (mem_no == null || (mem_no.trim()).length() == 0) {
					errorMsgs.add("沒有會員編號");
				}
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(mem_no);
				if (memVO == null) {
					errorMsgs.add("查無會員資料");
				}
				if (!errorMsgs.isEmpty()) {
					System.out.println("getMyInformMsgs:"+ errorMsgs);
					return ;//程式中斷
				}
				Front_InformService front_informSvc = new Front_InformService();
				List<Front_InformVO> front_informVOs = front_informSvc.getMyInform(mem_no);
				session.setAttribute("front_informVOs", front_informVOs);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("getMyInform errorMsgs:"+ errorMsgs);

			}
			
		}
		
		// ajax
		if("updateReadSts".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			try {
				String mem_no = req.getParameter("mem_no");
				if(mem_no==null || mem_no=="" ||(mem_no.trim().length()==0)) {
					errorMsgs.add("尚未登入");
				}
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(mem_no);
				if (memVO == null) {
					errorMsgs.add("查無會員資料");
				}
				if (!errorMsgs.isEmpty()) {
					out.write("error");
					out.flush();
					out.close();
					return; // 此區塊代表有錯誤，會導回去，程式中斷
				}
				
				Front_InformService front_informSvc = new Front_InformService();
				front_informSvc.updateReadSts(mem_no);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("errorMsgs:"+ errorMsgs);
				out.write("error");
				out.flush();
				out.close();
			}
			
		}
		
		if("empGetInform".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String mem_no = req.getParameter("mem_no");
				if (mem_no == null || (mem_no.trim()).length() == 0) {
					errorMsgs.add("請先登入頁面，或確實在 URL 上輸入 mem_no");
				}
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(mem_no);
				if (memVO == null) {
					errorMsgs.add("查無會員資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/front_inform/errorPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Front_InformService front_informSvc = new Front_InformService();
				List<Front_InformVO> front_informVOs = front_informSvc.getMyInform(mem_no);

				req.setAttribute("front_informVOs", front_informVOs);
				HttpSession session = req.getSession();
				session.setAttribute("front_informVOs", front_informVOs);
				String url = "/back-end/front_inform/empCheckInform.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);		
				
			} catch (Exception e) {
				errorMsgs.add("查無資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/front_inform/errorPage.jsp");
				failureView.forward(req, res);
			}
		}
	}
	
}
