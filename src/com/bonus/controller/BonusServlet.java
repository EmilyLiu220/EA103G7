package com.bonus.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.bonus.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class BonusServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		BonusService addSvc = new BonusService();

		String bonus_img = req.getParameter("bonus_img");
		if (bonus_img != null) {
			try {
				ServletOutputStream out = res.getOutputStream();
				out.write(addSvc.getOneBonus(bonus_img).getBns_img());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("bns_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入紅利商品編號才能查詢！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus/select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				String bns_no = null;
				try {
					bns_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("紅利商品編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				BonusService bonusSvc = new BonusService(); // 呼叫回傳參數的建構子
				BonusVO bonusVO = bonusSvc.getOneBonus(bns_no); // 呼叫Service內getOneEmp的方法
				if (bonusVO == null) {
					errorMsgs.add("查無資料！");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/member_review/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("bonusVO", bonusVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/bonus/listOneBonus.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllBonus.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String bns_no = new String(req.getParameter("bns_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				BonusService bonusSvc = new BonusService();
				BonusVO bonusVO = bonusSvc.getOneBonus(bns_no); // 呼叫Service內getOneEmp的方法

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("bonusVO", bonusVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/bonus/update_bonus_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus/listAllBonus.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_member_review_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String bns_no = new String(req.getParameter("bns_no").trim());

				String bns_name = req.getParameter("bns_name");

				if ("".equals(bns_name) || bns_name.trim().length() == 0) {
					errorMsgs.add("紅利商品名稱：請勿空白");

				}

				Integer bns_price = null;
				try {
					bns_price = new Integer(req.getParameter("bns_price").trim());
				} catch (NumberFormatException e) {
					bns_price = 0;
					errorMsgs.add("紅利商品價格請填數字！");
				}

				Integer bns_stks = null;
				try {
					bns_stks = new Integer(req.getParameter("bns_stks").trim());
				} catch (NumberFormatException e) {
					bns_stks = 0;
					errorMsgs.add("庫存量請填數字！");
				}

				java.sql.Date bns_date = null;
				try {
					bns_date = java.sql.Date.valueOf(req.getParameter("bns_date").trim());
				} catch (IllegalArgumentException e) {
					bns_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer bns_sts = null;
				try {
					bns_sts = new Integer(req.getParameter("bns_sts").trim());
				} catch (NumberFormatException e) {
					bns_sts = 0;
					errorMsgs.add("紅利商品狀態請填數字！");
				}

				Part bns_img = req.getPart("bns_img");
				byte[] img = null;

				if (bns_img.getSize() != 0) {
					if (bns_img.getContentType() != null) {
						InputStream is = bns_img.getInputStream();
						img = new byte[is.available()];
						is.read(img);
						is.close();
					}
				} else {
					errorMsgs.add("紅利商品圖片：請上傳圖片");
				}
				System.out.println(bns_img.getName());

				BonusVO bonusVO = new BonusVO();
				bonusVO.setBns_no(bns_no);
				bonusVO.setBns_name(bns_name);
				bonusVO.setBns_price(bns_price);
				bonusVO.setBns_stks(bns_stks);
				bonusVO.setBns_date(bns_date);
				bonusVO.setBns_sts(bns_sts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bonusVO", bonusVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus/update_bonus_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				BonusService bonusSvc = new BonusService();
				bonusVO = bonusSvc.updateBonus(bns_no, bns_name, bns_price, bns_stks, bns_date, bns_sts, img); // 呼叫Service內updateEmp的方法

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("bonusVO", bonusVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/bonus/listOneBonus.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus/update_bonus_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addMember_Review.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String bns_name = req.getParameter("bns_name");
				if (bns_name == null || bns_name.trim().length() == 0) {
					errorMsgs.add("紅利商品名稱：請勿空白");
				}

				Integer bns_price = null;
				try {
					bns_price = new Integer(req.getParameter("bns_price").trim());
				} catch (NumberFormatException e) {
					bns_price = 0;
					errorMsgs.add("紅利商品價格請填數字！");
				}

				Integer bns_stks = null;
				try {
					bns_stks = new Integer(req.getParameter("bns_stks").trim());
				} catch (NumberFormatException e) {
					bns_stks = 0;
					errorMsgs.add("庫存量請填數字！");
				}

				java.sql.Date bns_date = null;
				try {
					bns_date = java.sql.Date.valueOf(req.getParameter("bns_date").trim());
				} catch (IllegalArgumentException e) {
					bns_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入有效日期！");
				}

				Integer bns_sts = null;
				try {
					bns_sts = new Integer(req.getParameter("bns_sts").trim());
				} catch (NumberFormatException e) {
					bns_sts = 0;
					errorMsgs.add("紅利商品狀態請填數字！");
				}
				System.out.println(1);
				Part bns_img = req.getPart("bns_img");
				byte[] img = null;

				if (bns_img.getSize() != 0) {
					if (bns_img.getContentType() != null) {
						InputStream is = bns_img.getInputStream();
						img = new byte[is.available()];
						is.read(img);
						is.close();
					}
				} else {
					errorMsgs.add("bns_img: 請勿空白");
				}
				System.out.println(2);
//				System.out.println(bns_img.getName());

				BonusVO bonusVO = new BonusVO();
				bonusVO.setBns_name(bns_name);
				bonusVO.setBns_price(bns_price);
				bonusVO.setBns_stks(bns_stks);
				bonusVO.setBns_date(bns_date);
				bonusVO.setBns_sts(bns_sts);
				System.out.println(3);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bonusVO", bonusVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus/addBonus.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始新增資料 ***************************************/
				BonusService bonusSvc = new BonusService();
				bonusVO = bonusSvc.addBonus(bns_name, bns_price, bns_stks, bns_date, bns_sts, img); // 呼叫Service內addMember_Review的方法

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/bonus/listAllBonus.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus/addBonus.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllMember_Review.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String bns_no = new String(req.getParameter("bns_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				BonusService bonusSvc = new BonusService();
				bonusSvc.deleteBonus(bns_no); // 呼叫Service內deleteEmp的方法
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/bonus/listAllBonus.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bonus/listAllBonus.jsp");
				failureView.forward(req, res);
			}
		}
	}
}