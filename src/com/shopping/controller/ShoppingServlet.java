package com.shopping.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.meal.model.MealDAO;
import com.meal.model.MealVO;
import com.meal_set.model.MealSetDAO;
import com.meal_set.model.MealSetVO;


@WebServlet("/Shopping.do")
public class ShoppingServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String action = req.getParameter("action");

		Map<String, String> errormsgs = new HashMap<>();

		Vector<MealVO> mealList = (Vector<MealVO>) session.getAttribute("mealList");
		Vector<MealSetVO> setList = (Vector<MealSetVO>) session.getAttribute("setList");
		Vector<MealVO> rsvMealList = (Vector<MealVO>) session.getAttribute("rsvMealList");
		Vector<MealSetVO> rsvSetList = (Vector<MealSetVO>) session.getAttribute("rsvSetList");

		if ("checkout".equals(action)) {

			if ((mealList == null || mealList.size() == 0) && (setList == null || setList.size() == 0)) {
				System.out.println((mealList == null));
				System.out.println((setList == null));
				errormsgs.put("cartEmpty", "購物車是空的!!");
				req.setAttribute("errormsgs", errormsgs);
				String url = "/front-end/shopping/mealMenu2.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
			} else {

				Integer amount = 0;
				if (mealList != null && mealList.size() != 0) {
					for (MealVO mealVO : mealList) {
						amount += mealVO.getMeal_price() * mealVO.getMeal_qty();
					}
				}
				if (setList != null && setList.size() != 0) {
					for (MealSetVO mealSetVO : setList) {
						amount += mealSetVO.getMeal_set_price() * mealSetVO.getMeal_set_qty();
					}
				}

				req.setAttribute("amount", amount);
				String url = "/front-end/shopping/checkout.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
			}

		}

		if ("rsvCheckout".equals(action)) {
			String resNo = req.getParameter("res_no");
			if ((rsvMealList == null || rsvMealList.size() == 0) && (rsvSetList == null || rsvSetList.size() == 0)) {
//				System.out.println((rsvMealList == null));
//				System.out.println((rsvSetList == null));
				errormsgs.put("rsvCartEmpty", "購物車是空的!!");
				req.setAttribute("errormsgs", errormsgs);
				req.setAttribute("res_no", resNo);
				String url = "/front-end/shopping/mealMenu2.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
			} else {

				Integer amount = 0;
				if (rsvMealList != null && rsvMealList.size() != 0) {
					for (MealVO mealVO : rsvMealList) {
						amount += mealVO.getMeal_price() * mealVO.getMeal_qty();
					}
				}
				if (rsvSetList != null && rsvSetList.size() != 0) {
					for (MealSetVO mealSetVO : rsvSetList) {
						amount += mealSetVO.getMeal_set_price() * mealSetVO.getMeal_set_qty();
					}
				}
				
				req.setAttribute("res_no", resNo);
				req.setAttribute("amount", amount);
				String url = "/front-end/shopping/checkout.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
			}

		}

		if ("addMeal".equals(action)) {
			String mealNo = req.getParameter("meal_no");
//			String mealName = req.getParameter("meal_name");
//			Integer mealPrice = new Integer(req.getParameter("meal_price"));
			Integer mealQty = new Integer(req.getParameter("meal_qty"));
			MealDAO mealDAO = new MealDAO();
//			MealVO newMealVO = new MealVO();
			MealVO newMealVO = mealDAO.searchByNo(mealNo);
//			newMealVO.setMeal_no(mealNo);
//			newMealVO.setMeal_name(mealName);
//			newMealVO.setMeal_price(mealPrice);
			newMealVO.setMeal_qty(mealQty);
			Map<String, String> map = new HashMap<>();

			int eleindex = 0;
			boolean match = false;
			if (mealList == null) {
				mealList = new Vector<MealVO>();
				mealList.add(newMealVO);
				map.put("mealNo", newMealVO.getMeal_no());
				map.put("mealName", newMealVO.getMeal_name());
				map.put("mealQty", newMealVO.getMeal_qty().toString());
				map.put("mealPrice", newMealVO.getMeal_price().toString());
				map.put("amount", Integer.toString((newMealVO.getMeal_qty() * newMealVO.getMeal_price())));
			} else {
				for (int i = 0; i < mealList.size(); i++) {
					MealVO mealVO = mealList.get(i);

					if (mealVO.getMeal_no().equals((newMealVO.getMeal_no()))) {
						int qty = mealVO.getMeal_qty() + newMealVO.getMeal_qty();
						map.put("mealNo", mealVO.getMeal_no());
						map.put("mealQty", Integer.toString(qty));
						map.put("mealPrice",Integer.toString(mealVO.getMeal_price()));
						mealVO.setMeal_qty(qty);
						mealList.setElementAt(mealVO, i);
						eleindex = i;
						match = true;
					}

				}
				if (!match) {
					mealList.add(newMealVO);
					map.put("mealNo", newMealVO.getMeal_no());
					map.put("mealName", newMealVO.getMeal_name());
					map.put("mealQty", newMealVO.getMeal_qty().toString());
					map.put("mealPrice", newMealVO.getMeal_price().toString());
					map.put("amount", Integer.toString((newMealVO.getMeal_qty() * newMealVO.getMeal_price())));
				}
			}
			int matchflag;
			if (match) {
				matchflag = 1;
			} else {
				matchflag = 0;
			}
			map.put("eleindex", Integer.toString(eleindex));
			map.put("match", Integer.toString(matchflag));

			res.setContentType("application/json; charset=utf-8");
			PrintWriter out = res.getWriter();
			JSONObject jsonObject = new JSONObject(map);
			out.print(jsonObject);

			session.setAttribute("mealList", mealList);
//			String url = "/front-end/shopping/mealMenu2.jsp";
//			RequestDispatcher success = req.getRequestDispatcher(url);
//			success.forward(req, res);

		}

		if ("addRsvMeal".equals(action)) {
			String mealNo = req.getParameter("meal_no");
//			String mealName = req.getParameter("meal_name");
//			Integer mealPrice = new Integer(req.getParameter("meal_price"));
			Integer mealQty = new Integer(req.getParameter("meal_qty"));
			MealDAO mealDAO = new MealDAO();
//			MealVO newMealVO = new MealVO();
			MealVO newMealVO = mealDAO.searchByNo(mealNo);
//			newMealVO.setMeal_no(mealNo);
//			newMealVO.setMeal_name(mealName);
//			newMealVO.setMeal_price(mealPrice);
			newMealVO.setMeal_qty(mealQty);
			Map<String, String> map = new HashMap<>();

			int eleindex = 0;
			boolean match = false;
			if (rsvMealList == null) {
				rsvMealList = new Vector<MealVO>();
				rsvMealList.add(newMealVO);
				map.put("mealNo", newMealVO.getMeal_no());
				map.put("mealName", newMealVO.getMeal_name());
				map.put("mealQty", newMealVO.getMeal_qty().toString());
				map.put("mealPrice", newMealVO.getMeal_price().toString());
				map.put("amount", Integer.toString((newMealVO.getMeal_qty() * newMealVO.getMeal_price())));
			} else {
				for (int i = 0; i < rsvMealList.size(); i++) {
					MealVO mealVO = rsvMealList.get(i);

					if (mealVO.getMeal_no().equals((newMealVO.getMeal_no()))) {
						int qty = mealVO.getMeal_qty() + newMealVO.getMeal_qty();
						map.put("mealNo", mealVO.getMeal_no());
						map.put("mealQty", Integer.toString(qty));
						map.put("mealPrice",Integer.toString(mealVO.getMeal_price()));
						mealVO.setMeal_qty(qty);
						rsvMealList.setElementAt(mealVO, i);
						eleindex = i;
						match = true;
					}

				}
				if (!match) {
					rsvMealList.add(newMealVO);
					map.put("mealNo", newMealVO.getMeal_no());
					map.put("mealName", newMealVO.getMeal_name());
					map.put("mealQty", newMealVO.getMeal_qty().toString());
					map.put("mealPrice", newMealVO.getMeal_price().toString());
					map.put("amount", Integer.toString((newMealVO.getMeal_qty() * newMealVO.getMeal_price())));
				}
			}
			int matchflag;
			if (match) {
				matchflag = 1;
			} else {
				matchflag = 0;
			}
			map.put("eleindex", Integer.toString(eleindex));
			map.put("match", Integer.toString(matchflag));

			res.setContentType("application/json; charset=utf-8");
			PrintWriter out = res.getWriter();
			JSONObject jsonObject = new JSONObject(map);
			out.print(jsonObject);

			session.setAttribute("rsvMealList", rsvMealList);
//			String url = "/front-end/shopping/mealMenu2.jsp";
//			RequestDispatcher success = req.getRequestDispatcher(url);
//			success.forward(req, res);

		}

		if ("addSet".equals(action)) {
			String mealSetNo = req.getParameter("meal_set_no");
//			String mealSetName = req.getParameter("meal_set_name");
//			Integer mealSetPrice = Integer.parseInt(req.getParameter("meal_set_price"));
			Integer mealSetQty = new Integer(req.getParameter("meal_set_qty"));
			MealSetDAO mealSetDAO = new MealSetDAO();
			MealSetVO newMealSetVO = mealSetDAO.searchByNo(mealSetNo);

//			newMealSetVO.setMeal_set_no(mealSetNo);
//			newMealSetVO.setMeal_set_name(mealSetName);
//			newMealSetVO.setMeal_set_price(mealSetPrice);
			newMealSetVO.setMeal_set_qty(mealSetQty);
			Map<String, String> map = new HashMap<>();

			int eleindex = 0;
			boolean match = false;
			if (setList == null) {
				setList = new Vector<MealSetVO>();
				setList.add(newMealSetVO);
				map.put("mealSetNo", newMealSetVO.getMeal_set_no());
				map.put("mealSetName", newMealSetVO.getMeal_set_name());
				map.put("mealSetQty", newMealSetVO.getMeal_set_qty().toString());
				map.put("mealSetPrice", newMealSetVO.getMeal_set_price().toString());
				map.put("amount",
						Integer.toString((newMealSetVO.getMeal_set_qty() * newMealSetVO.getMeal_set_price())));
			} else {
				for (int i = 0; i < setList.size(); i++) {
					MealSetVO mealSetVO = setList.get(i);

					if (mealSetVO.getMeal_set_no().equals((newMealSetVO.getMeal_set_no()))) {
						int qty = mealSetVO.getMeal_set_qty() + newMealSetVO.getMeal_set_qty();
						map.put("mealSetNo", mealSetVO.getMeal_set_no());
						map.put("mealSetQty", Integer.toString(qty));
						map.put("mealSetPrice",Integer.toString(mealSetVO.getMeal_set_price()));
						mealSetVO.setMeal_set_qty(qty);
						setList.setElementAt(mealSetVO, i);
						eleindex = i;
						match = true;
					}

				}
				if (!match) {
					setList.add(newMealSetVO);
					map.put("mealSetNo", newMealSetVO.getMeal_set_no());
					map.put("mealSetName", newMealSetVO.getMeal_set_name());
					map.put("mealSetQty", newMealSetVO.getMeal_set_qty().toString());
					map.put("mealSetPrice", newMealSetVO.getMeal_set_price().toString());
					map.put("amount",
							Integer.toString((newMealSetVO.getMeal_set_qty() * newMealSetVO.getMeal_set_price())));

				}
			}
			int matchflag;
			if (match) {
				matchflag = 1;
			} else {
				matchflag = 0;
			}
			map.put("eleindex", Integer.toString(eleindex));
			map.put("match", Integer.toString(matchflag));
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			JSONObject jsonObject = new JSONObject(map);
			out.print(jsonObject);

			session.setAttribute("setList", setList);
//			String url = "/front-end/shopping/mealMenu2.jsp";
//			RequestDispatcher success = req.getRequestDispatcher(url);
//			success.forward(req, res);

		}

		if ("addRsvSet".equals(action)) {
			String mealSetNo = req.getParameter("meal_set_no");
//			String mealSetName = req.getParameter("meal_set_name");
//			Integer mealSetPrice = Integer.parseInt(req.getParameter("meal_set_price"));
			Integer mealSetQty = new Integer(req.getParameter("meal_set_qty"));
			MealSetDAO mealSetDAO = new MealSetDAO();
			MealSetVO newMealSetVO = mealSetDAO.searchByNo(mealSetNo);

//			newMealSetVO.setMeal_set_no(mealSetNo);
//			newMealSetVO.setMeal_set_name(mealSetName);
//			newMealSetVO.setMeal_set_price(mealSetPrice);
			newMealSetVO.setMeal_set_qty(mealSetQty);
			Map<String, String> map = new HashMap<>();

			int eleindex = 0;
			boolean match = false;
			if (rsvSetList == null) {
				rsvSetList = new Vector<MealSetVO>();
				rsvSetList.add(newMealSetVO);
				map.put("mealSetNo", newMealSetVO.getMeal_set_no());
				map.put("mealSetName", newMealSetVO.getMeal_set_name());
				map.put("mealSetQty", newMealSetVO.getMeal_set_qty().toString());
				map.put("mealSetPrice", newMealSetVO.getMeal_set_price().toString());
				map.put("amount",
						Integer.toString((newMealSetVO.getMeal_set_qty() * newMealSetVO.getMeal_set_price())));
			} else {
				for (int i = 0; i < rsvSetList.size(); i++) {
					MealSetVO mealSetVO = rsvSetList.get(i);

					if (mealSetVO.getMeal_set_no().equals((newMealSetVO.getMeal_set_no()))) {
						int qty = mealSetVO.getMeal_set_qty() + newMealSetVO.getMeal_set_qty();
						map.put("mealSetNo", mealSetVO.getMeal_set_no());
						map.put("mealSetQty", Integer.toString(qty));
						map.put("mealSetPrice",Integer.toString(mealSetVO.getMeal_set_price()));
						mealSetVO.setMeal_set_qty(qty);
						rsvSetList.setElementAt(mealSetVO, i);
						eleindex = i;
						match = true;
					}

				}
				if (!match) {
					rsvSetList.add(newMealSetVO);
					map.put("mealSetNo", newMealSetVO.getMeal_set_no());
					map.put("mealSetName", newMealSetVO.getMeal_set_name());
					map.put("mealSetQty", newMealSetVO.getMeal_set_qty().toString());
					map.put("mealSetPrice", newMealSetVO.getMeal_set_price().toString());
					map.put("amount",
							Integer.toString((newMealSetVO.getMeal_set_qty() * newMealSetVO.getMeal_set_price())));

				}
			}
			int matchflag;
			if (match) {
				matchflag = 1;
			} else {
				matchflag = 0;
			}
			map.put("eleindex", Integer.toString(eleindex));
			map.put("match", Integer.toString(matchflag));
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			JSONObject jsonObject = new JSONObject(map);
			out.print(jsonObject);

			session.setAttribute("rsvSetList", rsvSetList);
//			String url = "/front-end/shopping/mealMenu2.jsp";
//			RequestDispatcher success = req.getRequestDispatcher(url);
//			success.forward(req, res);

		}

		if ("removeMeal".equals(action)) {
			String mealNo = req.getParameter("meal_no");
//			Integer index = new Integer(req.getParameter("deleteMeal"));
			Map<String, String> map = new HashMap();

			int eleindex = 1;
			for (int i = 0; i < mealList.size(); i++) {
				MealVO mealVO = mealList.get(i);

				if (mealVO.getMeal_no().equals(mealNo)) {
					mealVO.setMeal_qty(mealVO.getMeal_qty() - 1);
					map.put("mealQty", Integer.toString(mealVO.getMeal_qty()));
					map.put("mealNo", mealVO.getMeal_no());
					map.put("mealPrice",Integer.toString(mealVO.getMeal_price()));
					if (mealVO.getMeal_qty() <= 0) {
						mealList.removeElementAt(i);
						eleindex = 0;
					} else
						mealList.setElementAt(mealVO, i);
				}
			}
			map.put("eleindex", Integer.toString(eleindex));

			res.setContentType("application/json; charset=utf-8");
			PrintWriter out = res.getWriter();
			JSONObject jsonObject = new JSONObject(map);
			out.print(jsonObject);

			session.setAttribute("mealList", mealList);
//			String url = "/front-end/shopping/mealMenu2.jsp";
//			RequestDispatcher success = req.getRequestDispatcher(url);
//			success.forward(req, res);

		}

		if ("removeRsvMeal".equals(action)) {
			String mealNo = req.getParameter("meal_no");
//			Integer index = new Integer(req.getParameter("deleteMeal"));
			Map<String, String> map = new HashMap();

			int eleindex = 1;
			for (int i = 0; i < rsvMealList.size(); i++) {
				MealVO mealVO = rsvMealList.get(i);

				if (mealVO.getMeal_no().equals(mealNo)) {
					mealVO.setMeal_qty(mealVO.getMeal_qty() - 1);
					map.put("mealQty", Integer.toString(mealVO.getMeal_qty()));
					map.put("mealNo", mealVO.getMeal_no());
					map.put("mealPrice",Integer.toString(mealVO.getMeal_price()));
					if (mealVO.getMeal_qty() <= 0) {
						rsvMealList.removeElementAt(i);
						eleindex = 0;
					} else
						rsvMealList.setElementAt(mealVO, i);
				}
			}
			map.put("eleindex", Integer.toString(eleindex));

			res.setContentType("application/json; charset=utf-8");
			PrintWriter out = res.getWriter();
			JSONObject jsonObject = new JSONObject(map);
			out.print(jsonObject);

			session.setAttribute("rsvMealList", rsvMealList);
//			String url = "/front-end/shopping/mealMenu2.jsp";
//			RequestDispatcher success = req.getRequestDispatcher(url);
//			success.forward(req, res);

		}

		if ("removeSet".equals(action)) {
			String mealSetNo = req.getParameter("meal_set_no");
//			Integer index = new Integer(req.getParameter("deleteSet"));
			Map<String, String> map = new HashMap();
			int eleindex = 1;
			for (int i = 0; i < setList.size(); i++) {
				MealSetVO mealSetVO = setList.get(i);

				if (mealSetVO.getMeal_set_no().equals(mealSetNo)) {
					mealSetVO.setMeal_set_qty(mealSetVO.getMeal_set_qty() - 1);
					map.put("mealSetQty", Integer.toString(mealSetVO.getMeal_set_qty()));
					map.put("mealSetNo", mealSetVO.getMeal_set_no());
					map.put("mealSetPrice",Integer.toString(mealSetVO.getMeal_set_price()));
					if (mealSetVO.getMeal_set_qty() <= 0) {
						setList.removeElementAt(i);
						eleindex = 0;
					} else
						setList.setElementAt(mealSetVO, i);
				}
			}
			map.put("eleindex", Integer.toString(eleindex));

			res.setContentType("application/json; charset=utf-8");
			PrintWriter out = res.getWriter();
			JSONObject jsonObject = new JSONObject(map);
			out.print(jsonObject);

			session.setAttribute("setList", setList);
//			String url = "/front-end/shopping/mealMenu2.jsp";
//			RequestDispatcher success = req.getRequestDispatcher(url);
//			success.forward(req, res);

		}

		if ("removeRsvSet".equals(action)) {
			String mealSetNo = req.getParameter("meal_set_no");
//			Integer index = new Integer(req.getParameter("deleteSet"));
			Map<String, String> map = new HashMap();
			int eleindex = 1;
			for (int i = 0; i < rsvSetList.size(); i++) {
				MealSetVO mealSetVO = rsvSetList.get(i);

				if (mealSetVO.getMeal_set_no().equals(mealSetNo)) {
					mealSetVO.setMeal_set_qty(mealSetVO.getMeal_set_qty() - 1);
					map.put("mealSetQty", Integer.toString(mealSetVO.getMeal_set_qty()));
					map.put("mealSetNo", mealSetVO.getMeal_set_no());
					map.put("mealSetPrice",Integer.toString(mealSetVO.getMeal_set_price()));
					if (mealSetVO.getMeal_set_qty() <= 0) {
						rsvSetList.removeElementAt(i);
						eleindex = 0;
					} else
						rsvSetList.setElementAt(mealSetVO, i);
				}
			}
			map.put("eleindex", Integer.toString(eleindex));

			res.setContentType("application/json; charset=utf-8");
			PrintWriter out = res.getWriter();
			JSONObject jsonObject = new JSONObject(map);
			out.print(jsonObject);

			session.setAttribute("rsvSetList", rsvSetList);
//			String url = "/front-end/shopping/mealMenu2.jsp";
//			RequestDispatcher success = req.getRequestDispatcher(url);
//			success.forward(req, res);

		}

	}

}
