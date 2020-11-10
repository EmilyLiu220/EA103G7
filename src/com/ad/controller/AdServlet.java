package com.ad.controller;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.ad.model.AdService;
import com.ad.model.AdVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class AdServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		AdService addSvc = new AdService();

		String add_no = req.getParameter("add_no");
		if (add_no != null) {
			try {
				ServletOutputStream out = res.getOutputStream();
				out.write(addSvc.getOneAd(add_no).getAd_img());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String action = req.getParameter("action");
		//==========================

		// insert
		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String emp_no = req.getParameter("emp_no");

				if (emp_no == null || (emp_no.trim()).length() == 0) {
					errorMsgs.add("員工編號: 請勿空白");
				}

				String ad_title = req.getParameter("ad_title").trim();

				if (ad_title == null || ad_title.trim().length() == 0) {
					errorMsgs.add("廣告標題: 請勿空白");
				}
				String ad_cont = req.getParameter("ad_cont").trim();

				if (ad_cont == null || ad_cont.trim().length() == 0) {
					errorMsgs.add("廣告內容: 請勿空白");
				}

				java.sql.Date ad_add_date = null;

				try {
					ad_add_date = java.sql.Date.valueOf(req.getParameter("ad_add_date").trim());
				} catch (IllegalArgumentException e) {
					ad_add_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入上架日期!");
				}

				java.sql.Date ad_re_date = null;

				try {
					ad_re_date = java.sql.Date.valueOf(req.getParameter("ad_re_date").trim());
				} catch (IllegalArgumentException e) {
					ad_re_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入下架日期!");
				}

				Integer ad_sts = new Integer(0);
				
//			          抓取登入員工
//				HttpSession session = req.getSession();
//				EmpVO empVO = (EmpVO)session.getAttribute("EmpVO");
//				String emp_no = empVO.getEmp_no();

				AdVO adVO = new AdVO();
				adVO.setEmp_no(emp_no);
				adVO.setAd_title(ad_title);
				adVO.setAd_cont(ad_cont);
				adVO.setAd_add_date(ad_add_date);
				adVO.setAd_re_date(ad_re_date);
				

				Part ad_img = req.getPart("ad_img");
				byte[] img = null;

				if (ad_img.getSize() != 0) {
					if (ad_img.getContentType() != null) {
						InputStream is = ad_img.getInputStream();
						img = new byte[is.available()];
						is.read(img);
						is.close();
					}
				} else {
					errorMsgs.add("廣告圖片: 請勿空白");
				}
//					else {
//					AdService adSvc= new AdService();
//					AdVO adVO= adSvc.get_One_AD(ad_no);
//					img =ad.getAd_img;
//				}	
				adVO.setAd_sts(ad_sts);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adVO", adVO); // 含有輸入格式錯誤的adVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/addAd.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始修改資料 *****************************************/
				AdService adSvc = new AdService();
				adVO = adSvc.addAd(emp_no, ad_title, ad_cont, ad_add_date, ad_re_date, img,ad_sts);
//				MemService memSvc = new MemService();
//				while (next()) {
//					MemVO	MemVO = new MemVO();
//				}
//				
//				Inform_SetService in_set = new Inform_SetService();
//				in_set.addAd();
				/*******************************************************************/
				
//				AdWS adWS =new AdWS();
//				adWS.onMessage(adVO);
				
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/ad/listAllAd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllAd.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/addAd.jsp");
				failureView.forward(req, res);
			}
		}
		// delete
		if ("delete".equals(action)) { // 來自listAllAd.jsp

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String ad_no = new String(req.getParameter("ad_no"));
				/*************************** 2.開始刪除資料 ***************************************/
				AdService adSvc = new AdService();
				adSvc.deleteAd(ad_no);
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/ad/listAllAd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/listAllAd.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("ad_no");
				System.out.println(str);
//				if (str == null || str.trim().length() == 0 ){
//					errorMsgs.add("請輸入ad_no");
//				}
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("廣告編號: 請勿空白");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/select_ad.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String ad_no = null;
				try {
					ad_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/select_ad.jsp");
					failureView.forward(req, res);
					return;// 程式中斷

				}
				/*************************** 2.開始查詢資料 *****************************************/
				AdService adSvc = new AdService();
				AdVO adVO = adSvc.getOneAd(ad_no);
				if (adVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/select_ad.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("adVO", adVO);
				String url = "/back-end/ad/listOneAd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/select_ad.jsp");
				failureView.forward(req, res);
			}
		}

		// update

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String ad_no = new String(req.getParameter("ad_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				AdService adSvc = new AdService();
				AdVO adVO = adSvc.getOneAd(ad_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("adVO", adVO);
				String url = "/back-end/ad/update_ad_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_news_input.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/listAllAd.jsp");
				failureView.forward(req, res);
			}
		}

		// update

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ad_no = req.getParameter("ad_no").trim();
//				System.out.println(ad_no);
//				String emp_no = req.getParameter("emp_no").trim();
//				if (emp_no == null || emp_no.trim().length() == 0) {
//					errorMsgs.add("emp_no: 請勿空白");
//				}
				
				
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
					
					EmpService empSvc = new EmpService();
					EmpVO empVO = empSvc.getOneEmp(emp_no.toUpperCase());
					if (empVO == null) {
						errorMsgs.add("查無員工");
					}
					
				}else {
					errorMsgs.add("員工編號: 請勿空白");
				}
				
				
//				System.out.println(emp_no);
				String ad_title = req.getParameter("ad_title").trim();
				if (ad_title == null || ad_title.trim().length() == 0) {
					errorMsgs.add("活動標題: 請勿空白");
				}
//				System.out.println(ad_title);
				String ad_cont = req.getParameter("ad_cont").trim();
				if (ad_cont == null || ad_cont.trim().length() == 0) {
					errorMsgs.add("活動內容: 請勿空白");
				}

				java.sql.Date ad_add_date = null;
				try {
					ad_add_date = java.sql.Date.valueOf(req.getParameter("ad_add_date").trim());
				} catch (IllegalArgumentException e) {
					ad_add_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入上架日期!");
				}
//				System.out.println(ad_add_date);
				java.sql.Date ad_re_date = null;
				try {
					ad_re_date = java.sql.Date.valueOf(req.getParameter("ad_re_date").trim());
				} catch (IllegalArgumentException e) {
					ad_re_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入下架日期!");
				}
//				System.out.println(ad_re_date);

				Part ad_img = req.getPart("ad_img");

				byte[] img = null;
				if (ad_img.getSize() != 0) {
					if (ad_img.getContentType() != null) {
						InputStream is = ad_img.getInputStream();
						img = new byte[is.available()];
						is.read(img);
						is.close();
					}
				} else {
					AdService Adsvc = new AdService();
					AdVO adVO = Adsvc.getOneAd(ad_no);
					img = adVO.getAd_img();
				}

				System.out.println(ad_img.getName());

				Integer ad_sts =  new Integer(req.getParameter("ad_sts"));
				
				AdVO adVO = new AdVO();
				adVO.setAd_no(ad_no);
				adVO.setEmp_no(emp_no);
				adVO.setAd_title(ad_title);
				adVO.setAd_cont(ad_cont);
				adVO.setAd_add_date(ad_add_date);
				adVO.setAd_re_date(ad_re_date);
				adVO.setAd_sts(ad_sts);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adVO", adVO); // 含有輸入格式錯誤的adVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/update_ad_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				AdService adSvc = new AdService();
				adVO = adSvc.updateAd(ad_no, emp_no, ad_title, ad_cont, ad_add_date, ad_re_date, img,ad_sts);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("adVO", adVO); // 資料庫update成功後,正確的的adVO物件,存入req
				String url = "/back-end/ad/listOneAd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneAd.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/update_ad_input.jsp");
				failureView.forward(req, res);
			}
		}

		/********************************************************************/
		if ("getADByEmpNo".equals(action)) {
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				HttpSession session = req.getSession();
				String str = req.getParameter("emp_no");

				/*************************** 2.開始查詢資料 *****************************************/
//				AdService adSvc = new AdService();
//				List<AdVO> list = adSvc.getadno(str);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				session.setAttribute("str", str);
				String url = "/back-end/ad/listEmpNoAd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/ad/select_ad.jsp");
				failureView.forward(req, res);
			}
		}
		/********************************************************************/
//		if ("upad".equals(action)) {
//			PrintWriter out = res.getWriter();
//			try {
//				/*************************** 1.接收請求參數 **********************/
//				String ad_no = req.getParameter("ad_no").trim();
//			
//				String str = req.getParameter("emp_no");
//
//				/*************************** 2.開始查詢資料 *****************************************/
//				AdService adSvc = new AdService();
//				AdVO adVO = adSvc.getOneAd(ad_no);
//				java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
//				java.sql.Date todayTemp = java.sql.Date.valueOf(today.toString());
//				java.sql.Date adUpTemp = java.sql.Date.valueOf(adVO.getAd_add_date().toString());
//				java.sql.Date adDownTemp = java.sql.Date.valueOf(adVO.getAd_re_date().toString());
//
//				/*************************** 3.開始修改資料 *****************************************/
////        	String status;
//				if (adUpTemp.after(todayTemp)) {
//					// 提前上架
////        		status = "0";
//					adSvc.upad(adVO.getAd_no(), str,  today, adVO.getAd_re_date(),new Integer(1));
//				} else if (adDownTemp.before(todayTemp)) {
//					// 已為下架廣告，請先修改下架時間
////        		status = "2";
//				} else {
//					// 目前已是上架中
////        		status = "1";
//				}
//
//				/*************************** 4.修改完成,準備轉交(Send the Success view) *************/
////        	out.write(status);
//				/*************************** 其他可能的錯誤處理 *************************************/
//			} catch (Exception e) {
//				out.write("error: " + e.getMessage());
//			}
//		}
		/********************************************************************/
//		if("downad".equals(action)) {
//			PrintWriter out = res.getWriter();
//			try {
//				/*************************** 1.接收請求參數 **********************/
//				String ad_no = req.getParameter("ad_no").trim();
//				
//				String str = req.getParameter("emp_no");
//
//				/*************************** 2.開始查詢資料 *****************************************/
//				AdService adSvc = new AdService();
//				AdVO adVO = adSvc.getOneAd(ad_no);
//				java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
//				java.sql.Date todayTemp = java.sql.Date.valueOf(today.toString());
//				java.sql.Date adUpTemp = java.sql.Date.valueOf(adVO.getAd_add_date().toString());
//				java.sql.Date adDownTemp = java.sql.Date.valueOf(adVO.getAd_re_date().toString());
//
//				/*************************** 3.開始修改資料 *****************************************/
//            	
//            	if(adUpTemp.after(todayTemp)) {
//            		//還未上架,要下架
////            		status = "0";
//            		adSvc.downad(adVO.getAd_no(), str, today, today, new Integer(2));
//            	} else if(adDownTemp.before(todayTemp)) {
//            		//已是下架
////            		status = "2";
//            	} else {
//            		//上架中要下架
////            		status = "1";
//            		adSvc.downad(adVO.getAd_no(), str, adVO.getAd_add_date(), today, new Integer(2));
//            	}
//		}catch (Exception e) {
//			out.write("error: " + e.getMessage());
//		}
//		
//		}
		
		/*************************** 前台頁面 *************************************/
		if ("getFrontOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			String str = req.getParameter("ad_no");

			String ad_no = null;
			ad_no = new String(str);
			/*************************** 2.開始查詢資料 *****************************************/
			AdService adSvc = new AdService();
			AdVO adVO = adSvc.getOneAd(ad_no);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("adVO", adVO);
			String url = "/front-end/front/front_ad_one.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
	}
}