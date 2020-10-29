package com.meal_set.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.*;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.meal_set.model.*;
import com.meal_set_consist.model.MealSetConService;
import com.meal_set_consist.model.MealSetConVO;

@MultipartConfig
@WebServlet("/meal_set/mealSet.do")
public class MealSetServlet extends HttpServlet {
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

				String mealSetName = req.getParameter("meal_set_name").trim();
				String rex = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";

				if (mealSetName == null || mealSetName.length() == 0) {
					errormsgs.put("mealSetName", "餐點名稱不可為空");
				} else if (!mealSetName.matches(rex)) {
					errormsgs.put("mealSetName", "只可以輸入中英文");
				}

				String mealSetInfo = req.getParameter("meal_set_info");

				if (mealSetInfo.trim() == null || mealSetInfo.trim().length() == 0) {
					errormsgs.put("mealSetInfo", "餐點描述不可為空");
				}

				byte[] mealSetImg = null;
				byte[] buf = null;
				try {

					Part part = req.getPart("meal_set_img");
					if (part.getSize() != 0) {
						InputStream in = part.getInputStream();
						buf = new byte[in.available()];
						in.read(buf);
						mealSetImg = buf;
//						in.close();
					} else {
						errormsgs.put("mealSetImg", "請上傳圖片啦");
					}

				} catch (Exception e) {
					errormsgs.put("mealSetImg", "圖片讀取失敗");
					System.out.println("失敗點1");
				}

				Integer mealSetPrice = null;
				try {
					mealSetPrice = new Integer(req.getParameter("meal_set_price").trim());
				} catch (NumberFormatException e) {
					mealSetPrice = 0;
					errormsgs.put("mealSetPrice", "價格不可為空");
				}

				Integer mealSetSts = new Integer(0);

				Integer mealSetCat = new Integer(50);

				MealSetVO mealSetVO = new MealSetVO();
				mealSetVO.setMeal_set_name(mealSetName);
				mealSetVO.setMeal_set_info(mealSetInfo);
				mealSetVO.setMeal_set_img(mealSetImg);
				mealSetVO.setMeal_set_price(mealSetPrice);
				mealSetVO.setMeal_set_sts(mealSetSts);
				mealSetVO.setCat_no(mealSetCat);

				String[] mealNo = req.getParameterValues("meal_no");
				
				boolean flag = false;
				for (int i = 0; i < mealNo.length; i++) {
					for (int j = i + 1; j < mealNo.length; j++) {
						if (mealNo[i].equals(mealNo[j])) {
							errormsgs.put("mealNo", "組成的餐點內容重複");
							flag = true;
							break;
						}
					}
					if (flag) {
						break;
					}
				}
				

				String[] qty = req.getParameterValues("meal_qty");

				if (!errormsgs.isEmpty()) {
					req.setAttribute("mealSetVO", mealSetVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_set/insertMealSet2.jsp");
					failureView.forward(req, res);
					return;
				}

				List<MealSetConVO> conList = new ArrayList<>();
				for (int i = 0; i < mealNo.length; i++) {
					MealSetConVO mealSetConVO = new MealSetConVO();
					mealSetConVO.setMeal_no(mealNo[i]);
					mealSetConVO.setMeal_qty(Integer.parseInt(qty[i]));
					conList.add(mealSetConVO);
				}

				MealSetService mealSetSrv = new MealSetService();
				mealSetVO = mealSetSrv.addMealSet(mealSetName, mealSetInfo, mealSetImg, mealSetPrice, mealSetSts,
						mealSetCat, conList);

				req.setAttribute("meaSetlVO", mealSetVO);
				String url = "/back-end/meal_set/listAllMealSet2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); //
				successView.forward(req, res);

			} catch (Exception e) {
				errormsgs.put("exception", "無法取得修改資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_set/insertMealSet2.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			Map<String, String> errormsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errormsgs", errormsgs);

			try {
				String mealSetNo = req.getParameter("meal_set_no");

				String mealSetName = req.getParameter("meal_set_name");
				String rgx = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";

				if (mealSetName.trim() == null || mealSetName.trim().length() == 0) {
					errormsgs.put("mealSetName", "餐點名稱不可為空");
				} else if (!(mealSetName.trim()).matches(rgx)) {
					errormsgs.put("mealSetName", "餐點名稱只可輸入中英文");
				}

				String mealSetInfo = req.getParameter("meal_set_info");

				if (mealSetInfo.trim() == null || mealSetInfo.trim().length() == 0) {
					errormsgs.put("mealSetInfo", "餐點描述不可為空");
					mealSetInfo = "";
				}

				byte[] mealSetImg = null;
				byte[] buf = null;
//				InputStream mealImg =null;

				try {
					Part part = req.getPart("meal_set_img");

					if (part.getSize() != 0) {
						InputStream in = part.getInputStream();
						buf = new byte[in.available()];
						in.read(buf);
						mealSetImg = buf;
//						in.close();
					} else {
						MealSetService mealSetSrv = new MealSetService();
						MealSetVO mealSetVO = mealSetSrv.searchByNo(mealSetNo);
						mealSetImg = mealSetVO.getMeal_set_img();
					}

				} catch (Exception e) {

					errormsgs.put("mealSetPrice", "圖片讀取失敗");
					System.out.println("失敗點2");
				}

				Integer mealSetPrice = null;
				try {
					mealSetPrice = new Integer(req.getParameter("meal_set_price").trim());
				} catch (NumberFormatException e) {
					MealSetService mealSetSrv = new MealSetService();
					MealSetVO mealSetVO = mealSetSrv.searchByNo(mealSetNo);
					mealSetPrice = mealSetVO.getMeal_set_price();
					errormsgs.put("mealSetPrice", "價格不可為空");
				}

				Integer mealSetSts = new Integer(req.getParameter("meal_set_sts"));

				Integer mealSetCat = new Integer(50);

				MealSetVO mealSetVO = new MealSetVO();
				mealSetVO.setMeal_set_no(mealSetNo);
				mealSetVO.setMeal_set_name(mealSetName);
				mealSetVO.setMeal_set_info(mealSetInfo);
				mealSetVO.setMeal_set_img(mealSetImg);
				mealSetVO.setMeal_set_price(mealSetPrice);
				mealSetVO.setMeal_set_sts(mealSetSts);
				mealSetVO.setCat_no(mealSetCat);
				
				String[] mealNo = req.getParameterValues("meal_no");
				boolean flag = false;
				for (int i = 0; i < mealNo.length; i++) {
					for (int j = i + 1; j < mealNo.length; j++) {
						if (mealNo[i].equals(mealNo[j])) {
							errormsgs.put("mealNo", "組成的餐點內容重複");
							flag = true;
							break;
						}
					}
					if (flag) {
						break;
					}
				}
				
				String[] qty = req.getParameterValues("meal_qty");

				if (!errormsgs.isEmpty()) {
					req.setAttribute("mealSetVO", mealSetVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_set/updateMealSet2.jsp");
					failureView.forward(req, res);
					return;
				}
				

				List<MealSetConVO> conList = new ArrayList<>();
				for (int i = 0; i < mealNo.length; i++) {
					MealSetConVO mealSetConVO = new MealSetConVO();
					mealSetConVO.setMeal_no(mealNo[i]);
					mealSetConVO.setMeal_qty(Integer.parseInt(qty[i]));
					conList.add(mealSetConVO);
				}
				MealSetConService mealSetConSrv = new MealSetConService();
				mealSetConSrv.delete(mealSetNo);

				MealSetService mealSetSrv = new MealSetService();
				mealSetVO = mealSetSrv.updateMealSet(mealSetNo, mealSetName, mealSetInfo, mealSetImg, mealSetPrice,
						mealSetSts, mealSetCat, conList);

				req.setAttribute("mealSetVO", mealSetVO);
				String url = "/back-end/meal_set/listAllMealSet2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���view
				successView.forward(req, res);

			} catch (Exception e) {
				errormsgs.put("exception", "�L�k���o���:" + e.getMessage());
				System.out.println("�����I2");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_set/updateMealSet2.jsp");
				failureView.forward(req, res);
			}

		}
		if ("getOneupdate".equals(action)) {

			Map<String, String> errormsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errormsgs", errormsgs);

			try {
				String mealSetNo = req.getParameter("meal_set_no");

				MealSetService mealSetSrv = new MealSetService();
				MealSetVO mealSetVO = mealSetSrv.searchByNo(mealSetNo);

				req.setAttribute("mealSetVO", mealSetVO);
				String url = "/back-end/meal_set/updateMealSet2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errormsgs.put("exception", "�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_set/listAllMealSet2.jsp");
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
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_set/listAllMealSet.jsp");
					failureView.forward(req, res);
					return;
				}

				MealSetService mealSetSrv = new MealSetService();
				MealSetVO mealSetVO = mealSetSrv.searchByNo(keyWord);
				if (mealSetVO.getMeal_set_no() == null) {
					errormsgs.put("search", "查無此資料");
				}
				if (!errormsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_set/listAllMealSet.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("mealSetVO", mealSetVO);
				String url = "/back-end/meal_set/listAllMealSet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errormsgs.put("exception", "無法取得查詢資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/meal_set/listAllMealSet.jsp");
				failureView.forward(req, res);
			}

		}

	}

}
