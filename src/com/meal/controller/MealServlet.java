package com.meal.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.meal.model.*;
import com.meal_part.model.Meal_partVO;

@MultipartConfig
public class MealServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if ("insert".equals(action)) {
			Map<String, String> errormsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errormsgs", errormsgs);
			try {

				String mealName = req.getParameter("meal_name").trim();
				String rex = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";

				if (mealName == null || mealName.length() == 0) {
					errormsgs.put("mealName", "---餐點名稱不可為空---");
				} else if (!mealName.matches(rex)) {
					errormsgs.put("mealName", "---只可以輸入中英文---");
				}

				String mealInfo = req.getParameter("meal_info");

				if (mealInfo.trim() == null || mealInfo.trim().length() == 0) {
					errormsgs.put("mealInfo", "---餐點描述不可為空---");
				}

				byte[] mealImg = null;
				byte[] buf = null;
				try {
					Part part = req.getPart("meal_img");
					if (part.getSize() != 0) {
						InputStream in = part.getInputStream();
						buf = new byte[in.available()];
						in.read(buf);
						mealImg = buf;
//						in.close();
					}

				} catch (Exception e) {
					errormsgs.put("mealImg", "---圖片讀取失敗---");
					System.out.println("失敗點1");
				}

				Integer mealPrice = null;
				try {
					mealPrice = new Integer(req.getParameter("meal_price").trim());
					String rgx = "^[\\d]{1,5}$";
					if (!(req.getParameter("meal_price").trim()).matches(rgx)) {
						errormsgs.put("mealPrice", "---價格不可超過5位數---");
					}
				} catch (NumberFormatException e) {
					mealPrice = 0;
					errormsgs.put("mealPrice", "---價格不可為空---");
				}

				Integer mealSts = new Integer(0);

				Integer mealCat = new Integer(req.getParameter("cat_no"));

				MealVO mealVO = new MealVO();
				mealVO.setMeal_name(mealName);
				mealVO.setMeal_info(mealInfo);
				mealVO.setMeal_img(mealImg);
				mealVO.setMeal_price(mealPrice);
				mealVO.setMeal_sts(mealSts);
				mealVO.setCat_no(mealCat);

				String[] foodsNo = req.getParameterValues("fd_no");

				boolean flag = false;
				for (int i = 0; i < foodsNo.length; i++) {
					for (int j = i + 1; j < foodsNo.length; j++) {
						if (foodsNo[i].equals(foodsNo[j])) {
							errormsgs.put("foodsNo", "組成的食材內容重複");
							flag = true;
							break;
						}
					}
					if (flag) {
						break;
					}
				}
				String[] foodsGw = req.getParameterValues("fd_gw");
				for (int i = 0; i < foodsGw.length; i++) {
					String rgx = "^[0-9]*$";
					if (!(foodsGw[i].matches(rgx))) {
						errormsgs.put("foodsGw", "組成的食材數量異常");
					}
				}

				if (!errormsgs.isEmpty()) {
					req.setAttribute("mealVO", mealVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/insertMeal2.jsp");
					failureView.forward(req, res);
					return;
				}

				List<Meal_partVO> partList = new ArrayList<>();
				for (int i = 0; i < foodsNo.length; i++) {
					Meal_partVO partVO = new Meal_partVO();
					partVO.setFd_no(foodsNo[i]);
					partVO.setFd_gw(new Double(foodsGw[i]));
					partList.add(partVO);
				}

				MealService mealSrv = new MealService();
				mealVO = mealSrv.addMeal(mealName, mealInfo, mealImg, mealPrice, mealSts, mealCat, partList);

				req.setAttribute("mealVO", mealVO);
				String url = "/back-end/meal/listAllMeal2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); //
				successView.forward(req, res);

			} catch (Exception e) {
				errormsgs.put("exception", "無法取得修改資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/insertMeal2.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			Map<String, String> errormsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errormsgs", errormsgs);

//			try {
			String mealNo = req.getParameter("meal_no");

			String mealName = req.getParameter("meal_name");
			String rgx = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";

			if (mealName.trim() == null || mealName.trim().length() == 0) {
				errormsgs.put("mealName", "---餐點名稱不可為空---");
			} else if (!(mealName.trim()).matches(rgx)) {
				errormsgs.put("mealName", "---餐點名稱只可輸入中英文---");
			}

			String mealInfo = req.getParameter("meal_info");

			if (mealInfo.trim() == null || mealInfo.trim().length() == 0) {
				errormsgs.put("mealInfo", "---餐點描述不可為空---");
				mealInfo = "";
			}

			byte[] mealImg = null;
			byte[] buf = null;
//				InputStream mealImg =null;

			try {
				Part part = req.getPart("meal_img");

				if (part.getSize() != 0) {
					InputStream in = part.getInputStream();
					buf = new byte[in.available()];
					in.read(buf);
					mealImg = buf;
//						in.close();
				} else {
					MealService mealSrv = new MealService();
					MealVO mealVO = mealSrv.searchByNo(mealNo);
					mealImg = mealVO.getMeal_img();
				}

			} catch (Exception e) {

				errormsgs.put("mealPrice", "---圖片讀取失敗---");
				System.out.println("失敗點2");
			}

			Integer mealPrice = null;
			try {
				mealPrice = new Integer(req.getParameter("meal_price").trim());
			} catch (NumberFormatException e) {
				MealService mealSrv = new MealService();
				MealVO mealVO = mealSrv.searchByNo(mealNo);
				mealPrice = mealVO.getMeal_price();
				errormsgs.put("mealPrice", "---價格不可為空或輸入數字有誤---");
			}

			Integer mealSts = new Integer(req.getParameter("meal_sts"));

			Integer mealCat = new Integer(req.getParameter("cat_no"));

			MealVO mealVO = new MealVO();
			mealVO.setMeal_no(mealNo);
			mealVO.setMeal_name(mealName);
			mealVO.setMeal_info(mealInfo);
			mealVO.setMeal_img(mealImg);
			mealVO.setMeal_price(mealPrice);
			mealVO.setMeal_sts(mealSts);
			mealVO.setCat_no(mealCat);

			String[] foodsNo = req.getParameterValues("fd_no");
			boolean flag = false;
			if (foodsNo != null || foodsNo.length > 0) {
				for (int i = 0; i < foodsNo.length; i++) {
					for (int j = i + 1; j < foodsNo.length; j++) {
						if (foodsNo[i].equals(foodsNo[j])) {
							errormsgs.put("foodsNo", "組成的食材內容重複");
							flag = true;
							break;
						}
					}
					if (flag) {
						break;
					}
				}
			} else {
				errormsgs.put("foodsNo", "組成食材不可為空");
			}

			String[] foodsGw = req.getParameterValues("fd_gw");
			
				for (int i = 0; i < foodsGw.length; i++) {
					String rex = "^[0-9]+$";
					if (!(foodsGw[i].toString().matches(rex))) {
						errormsgs.put("foodsGw", "組成的食材數量異常");
					}
				}

			if (!errormsgs.isEmpty()) {
				req.setAttribute("mealVO", mealVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/updateMeal2.jsp");
				failureView.forward(req, res);
				return;
			}

			List<Meal_partVO> partList = new ArrayList<>();
			for (int i = 0; i < foodsNo.length; i++) {
				Meal_partVO partVO = new Meal_partVO();
				partVO.setFd_no(foodsNo[i]);
				partVO.setFd_gw(new Double(foodsGw[i]));
				partList.add(partVO);
			}

			MealService mealSrv = new MealService();
			mealVO = mealSrv.updateMeal(mealNo, mealName, mealInfo, mealImg, mealPrice, mealSts, mealCat, partList);

			req.setAttribute("mealVO", mealVO);
			String url = "/back-end/meal/listAllMeal2.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // ���view
			successView.forward(req, res);

//			} catch (Exception e) {
//				errormsgs.put("exception", "�L�k���o���:" + e.getMessage());
//				System.out.println("�����I2");
//				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/updateMeal2.jsp");
//				failureView.forward(req, res);
//			}

		}

		if ("getOneupdate".equals(action)) {

			Map<String, String> errormsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errormsgs", errormsgs);

			try {
				String mealNo = req.getParameter("meal_no");

				MealService mealSrv = new MealService();
				MealVO mealVO = mealSrv.searchByNo(mealNo);

				req.setAttribute("mealVO", mealVO);
				String url = "/back-end/meal/updateMeal2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errormsgs.put("exception", "無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/listAllMeal2.jsp");
				failureView.forward(req, res);
			}
		}

		if ("search".equals(action)) {

			Map<String, String> errormsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errormsgs", errormsgs);

			try {
				String keyWord = req.getParameter("search").trim();
				String rgx = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]$";
				if (keyWord == null || keyWord.length() == 0) {
					errormsgs.put("search", "搜尋欄不可空白");
				} else if (!keyWord.matches(rgx)) {
					errormsgs.put("search", "搜尋欄只可輸入中,英文字");
				}
				if (!errormsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/listAllMeal.jsp");
					failureView.forward(req, res);
					return;
				}

				MealService mealSrv = new MealService();
				MealVO mealVO = mealSrv.searchByNo(keyWord);
				if (mealVO.getMeal_no() == null) {
					errormsgs.put("search", "查無此資料");
				}
				if (!errormsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/listAllMeal.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("mealVO", mealVO);
				String url = "/back-end/meal/listAllMeal.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errormsgs.put("exception", "無法取得查詢資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal/listAllMeal.jsp");
				failureView.forward(req, res);
			}

		}

	}

}
