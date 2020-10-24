package com.front_inform.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.front_inform.model.Front_InformService;
import com.front_inform.model.Front_InformVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;

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
				
				Front_InformService front_informSvc = new Front_InformService();
				// 取得該會員回應「需回覆之通知」的結果
				String checkYes = req.getParameter("checkYes");
				String checkNo = req.getParameter("checkNo");
				
				if(checkYes!=null) { // 勾選確定來吃
					front_informSvc.updateSts(1, info_no);;
				}
				if(checkNo!=null) { // 勾選不來吃
					front_informSvc.updateSts(3, info_no);;
				}
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getMyInform".equals(action)) { // 會員以其身分登入網站頁面後要直接執行此動作一次

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
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Front_InformService front_informSvc = new Front_InformService();
				List<Front_InformVO> front_informVOs = front_informSvc.getMyInform(mem_no);

				req.setAttribute("front_informVOs", front_informVOs);
				HttpSession session = req.getSession();
				session.setAttribute("front_informVOs", front_informVOs);
				String url = "/front-end/front_inform/fiNmsg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);		
				
			} catch (Exception e) {
				errorMsgs.add("查無資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		if("updateReadSts".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String mem_no = req.getParameter("mem_no");
				if(mem_no==null || mem_no=="" ||(mem_no.trim().length()==0)) {
					errorMsgs.add("請先登入頁面，或確實在 URL 上輸入 mem_no");
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
				
				Front_InformService front_informSvc = new Front_InformService();
				front_informSvc.updateReadSts(mem_no);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/front_inform/errorPage.jsp");
				failureView.forward(req, res);
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
