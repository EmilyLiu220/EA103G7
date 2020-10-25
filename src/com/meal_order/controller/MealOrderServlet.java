package com.meal_order.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.meal.model.*;
import com.meal_order.model.*;
import com.meal_order_detail.model.MealOrderDetailService;
import com.meal_order_detail.model.MealOrderDetailVO;
import com.meal_set.model.*;

@WebServlet("/MealOrderServlet.do")
public class MealOrderServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		String action = req.getParameter("action");
		Vector<MealVO> mealList = (Vector) session.getAttribute("mealList");
		Vector<MealSetVO> setList = (Vector) session.getAttribute("setList");
		Vector<MealVO> rsvMealList = (Vector<MealVO>) session.getAttribute("rsvMealList");
		Vector<MealSetVO> rsvSetList = (Vector<MealSetVO>) session.getAttribute("rsvSetList");
		Map<String, String> errormsgs = new HashMap<>();

		if ("checkout".equals(action)) {
			String memNo = null;
			String empNo = (String) session.getAttribute("emp_no");
			String resNo = req.getParameter("res_no");
			if (session.getAttribute("mem_no") != null) {
				memNo = (String) session.getAttribute("mem_no");
			}

			Integer mealOrderSts = new Integer(1);
			Integer notiSts = new Integer(0);
			Integer paySts = (resNo != null ? new Integer(0) : new Integer(1));
			Integer amount = new Integer(req.getParameter("amount"));
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Timestamp pickupTime = null;
			System.out.println(req.getParameter("pickup_time"));
			if (req.getParameter("res_no") == null) {

				try {
					pickupTime = new Timestamp (ft.parse(req.getParameter("pickup_time")).getTime());
				} catch (IllegalArgumentException | ParseException e) {
					long min = 60 * 30 * 1000;
					pickupTime = new Timestamp(System.currentTimeMillis() + min);
				}
			}
			List<MealOrderDetailVO> detailList = new ArrayList<>();
			if (mealList != null) {
				for (MealVO mealVO : mealList) {
					MealOrderDetailVO mealOrderDetailVO = new MealOrderDetailVO();
					mealOrderDetailVO.setMeal_no(mealVO.getMeal_no());
					mealOrderDetailVO.setQty(mealVO.getMeal_qty());
					mealOrderDetailVO.setDetail_amount(mealVO.getMeal_price() * mealVO.getMeal_qty());
					mealOrderDetailVO.setMeal_name(mealVO.getMeal_name());
					detailList.add(mealOrderDetailVO);
				}
			}
			if (setList != null) {
				for (MealSetVO mealSetVO : setList) {
					MealOrderDetailVO mealOrderDetailVO = new MealOrderDetailVO();
					mealOrderDetailVO.setMeal_set_no(mealSetVO.getMeal_set_no());
					mealOrderDetailVO.setQty(mealSetVO.getMeal_set_qty());
					mealOrderDetailVO.setDetail_amount(mealSetVO.getMeal_set_price() * mealSetVO.getMeal_set_qty());
					mealOrderDetailVO.setMeal_name(mealSetVO.getMeal_set_name());
					detailList.add(mealOrderDetailVO);
				}
			}

			MealOrderVO mealOrderVO = new MealOrderVO();
			mealOrderVO.setMem_no(memNo);
			mealOrderVO.setEmp_no(empNo);
			mealOrderVO.setMeal_order_sts(mealOrderSts);
			mealOrderVO.setNoti_sts(notiSts);
			mealOrderVO.setPay_sts(paySts);
			mealOrderVO.setAmount(amount);
//			mealOrderVO.setOrder_time(orderTime);
			mealOrderVO.setPickup_time(pickupTime);

			if (true) {
//				(結帳 錯誤驗證?)
			}
			if (mealList != null) {
				mealList.removeAllElements();
			}
			if (setList != null) {
				setList.removeAllElements();
			}
			MealOrderService mealOrderSrv = new MealOrderService();
			mealOrderVO = (MealOrderVO) (mealOrderSrv.addOrder(memNo, empNo, mealOrderSts, amount, notiSts, paySts,
					pickupTime, detailList)).get("mealOrderVO");

			MealOrderWebSocket webSocket = new MealOrderWebSocket();
			Gson gson = new Gson();
			Map<String, Object> pushMsg = new HashMap<>();
			if (pickupTime.getMonth() == new Date().getMonth() && pickupTime.getDate() == new Date().getDate()) {
				pushMsg.put("reload","reload");
			}
			pushMsg.put("action", "insert");
			pushMsg.put("mealOrderVO", mealOrderVO);
			String jsonMap = gson.toJson(pushMsg);
			webSocket.onMessage(jsonMap);

			req.setAttribute("amount", amount);
			req.setAttribute("mealOrderVO", mealOrderVO);
			String url = "front-end/shopping/mealOrderOne.jsp";
			RequestDispatcher success = req.getRequestDispatcher(url);
			success.forward(req, res);

		}

		if ("rsvCheckout".equals(action)) {
			String memNo = null;
			String empNo = (String) session.getAttribute("emp_no");
			String resNo = req.getParameter("res_no");
			if (session.getAttribute("mem_no") != null) {
				memNo = (String) session.getAttribute("mem_no");
			}

			Integer mealOrderSts = new Integer(1);
			Integer notiSts = new Integer(0);
			Integer paySts = resNo != null ? new Integer(0) : new Integer(1);
			Integer amount = new Integer(req.getParameter("amount"));
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Timestamp pickupTime = null;
			List<MealOrderDetailVO> detailList = new ArrayList<>();
			if (rsvMealList != null) {
				for (MealVO mealVO : rsvMealList) {
					MealOrderDetailVO mealOrderDetailVO = new MealOrderDetailVO();
					mealOrderDetailVO.setMeal_no(mealVO.getMeal_no());
					mealOrderDetailVO.setQty(mealVO.getMeal_qty());
					mealOrderDetailVO.setDetail_amount(mealVO.getMeal_price() * mealVO.getMeal_qty());
					mealOrderDetailVO.setMeal_name(mealVO.getMeal_name());
					detailList.add(mealOrderDetailVO);
				}
			}
			if (rsvSetList != null) {
				for (MealSetVO mealSetVO : rsvSetList) {
					MealOrderDetailVO mealOrderDetailVO = new MealOrderDetailVO();
					mealOrderDetailVO.setMeal_set_no(mealSetVO.getMeal_set_no());
					mealOrderDetailVO.setQty(mealSetVO.getMeal_set_qty());
					mealOrderDetailVO.setDetail_amount(mealSetVO.getMeal_set_price() * mealSetVO.getMeal_set_qty());
					mealOrderDetailVO.setMeal_name(mealSetVO.getMeal_set_name());
					detailList.add(mealOrderDetailVO);
				}
			}

			MealOrderVO mealOrderVO = new MealOrderVO();
			mealOrderVO.setMem_no(memNo);
			mealOrderVO.setEmp_no(empNo);
			mealOrderVO.setMeal_order_sts(mealOrderSts);
			mealOrderVO.setNoti_sts(notiSts);
			mealOrderVO.setPay_sts(paySts);
			mealOrderVO.setAmount(amount);
//			mealOrderVO.setOrder_time(orderTime);
			mealOrderVO.setPickup_time(pickupTime);

			MealOrderService mealOrderSrv = new MealOrderService();
			mealOrderVO = (MealOrderVO) (mealOrderSrv.addOrder(memNo, empNo, mealOrderSts, amount, notiSts, paySts,
					pickupTime, detailList)).get("mealOrderVO");
			req.setAttribute("res_no", resNo);
			req.setAttribute("amount", amount);
			req.setAttribute("mealOrderVO", mealOrderVO);
			String url = "front-end/shopping/mealOrderOne.jsp";
			RequestDispatcher success = req.getRequestDispatcher(url);
			success.forward(req, res);

		}

		if ("cancel".equals(action)) {

			String mealOrderNo = (String) req.getParameter("meal_order_no");

			MealOrderService mealOrderSrv = new MealOrderService();
			MealOrderVO mealOrderVO = mealOrderSrv.searchByOrderNo(mealOrderNo);
			if (mealOrderVO.getMeal_order_sts() >= 2) {
				errormsgs.put("orderUpdate", "已派工，無法取消訂單!");
				req.setAttribute("errormsgs", errormsgs);
				req.setAttribute("mealOrderVO", mealOrderVO);
				String url = "front-end/shopping/mealOrderOne.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
			} else {
				mealOrderSrv.updateOrderSts(mealOrderNo, 0, mealOrderVO.getNoti_sts(), mealOrderVO.getPay_sts());
				mealOrderVO.setMeal_order_sts(0);
				req.setAttribute("mealOrderVO", mealOrderVO);
				String url = "front-end/shopping/mealOrderOne.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			String reqURL = req.getParameter("reqURL");
			String whichPage = req.getParameter("whichPage");
			String returnPath = reqURL;

			String mealOrderNo = (String) req.getParameter("meal_order_no");
			Integer mealOrderSts = new Integer(req.getParameter("meal_order_sts"));
			Integer notiSts = new Integer(req.getParameter("noti_sts"));
			Integer paySts = new Integer(req.getParameter("pay_sts"));
			MealOrderService mealOrderSrv = new MealOrderService();

			mealOrderSrv.updateOrderSts(mealOrderNo, mealOrderSts, notiSts, paySts);
			MealOrderVO mealOrderVO = mealOrderSrv.searchByOrderNo(mealOrderNo);
			MealOrderWebSocket webSocket = new MealOrderWebSocket();
			Gson gson = new Gson();
			MealOrderDetailService detailSrv = new MealOrderDetailService();
			List<MealOrderDetailVO> detailList = detailSrv.searchByOrderNo(mealOrderNo);

			Map<String, Object> pushMsg = new HashMap<>();
			pushMsg.put("mealOrderVO", mealOrderVO);
			pushMsg.put("action", "update");
			pushMsg.put("detailList", detailList);
			String jsonMap = gson.toJson(pushMsg);
			webSocket.onMessage(jsonMap);

			if (whichPage != null) {
				returnPath = reqURL + "?whichPage=" + whichPage;
			}
			req.setAttribute("returnPath", returnPath);
			req.setAttribute("mealOrderVO", mealOrderVO);
			String url = returnPath;
			RequestDispatcher success = req.getRequestDispatcher(url);
			success.forward(req, res);

		}

		if ("prepared".equals(action)) {

			String mealOrderNo = (String) req.getParameter("meal_order_no");
			Integer mealOrderSts = new Integer(3);
			Integer notiSts = new Integer(req.getParameter("noti_sts"));
			Integer paySts = new Integer(req.getParameter("pay_sts"));
			MealOrderService mealOrderSrv = new MealOrderService();
			MealOrderVO mealOrderVO = mealOrderSrv.searchByOrderNo(mealOrderNo);
			mealOrderSrv.updateOrderSts(mealOrderNo, mealOrderSts, notiSts, paySts);

		}

		if ("search".equals(action)) {
//			System.out.println(req.getParameter("queryString"));
//			System.out.println(req.getRequestURI());
//			System.out.println(req.getRequestURL());
			String reqURL = req.getParameter("reqURL");
			String whichPage = null;
			if (req.getParameter("whichPage") != null) {
				whichPage = req.getParameter("whichPage");
			}
			String returnPath = reqURL;
			String mealOrderNo = req.getParameter("meal_order_no");

			MealOrderService mealOrderSrv = new MealOrderService();
			MealOrderVO mealOrderVO = mealOrderSrv.searchByOrderNo(mealOrderNo);
			if (whichPage != null) {
				returnPath = reqURL + "?whichPage=" + whichPage;
			}
			if ((reqURL == null || req.getParameter("queryString") != null)
					&& !(req.getParameter("queryString").equals("null"))) {
				returnPath = req.getServletPath() + "?whichPage=" + whichPage + "&action="
						+ req.getParameter("queryString");
			}
			req.setAttribute("returnPath", returnPath);
			req.setAttribute("mealOrderVO", mealOrderVO);
			String url = "/back-end/mealOrder/listOneOrder.jsp";
			RequestDispatcher success = req.getRequestDispatcher(url);
			success.forward(req, res);
		}
		if ("asignQuery".equals(action)) {
			Map<String, String[]> map = (HashMap) session.getAttribute("asignMap");
			if (req.getParameter("whichPage") == null) {
				Map<String, String[]> map2 = new HashMap<>(req.getParameterMap());
				session.setAttribute("asignMap", map2);
				map = map2;
			}
			MealOrderService mealOrderSrv = new MealOrderService();
			List<MealOrderVO> orderList = mealOrderSrv.getAll(map);
//			System.out.println(req.getRequestURI());
//			System.out.println(req.getRequestURL());
			req.setAttribute("action", action);
			req.setAttribute("orderList", orderList);
			req.getRequestDispatcher("/back-end/mealOrder/asignOrder.jsp").forward(req, res);

		}

		if ("queryAll".equals(action)) {

			Map<String, String[]> map = (HashMap) session.getAttribute("queryAllMap");
//			if (map == null || map.isEmpty()) {
			if (req.getParameter("whichPage") == null) {
				Map<String, String[]> map2 = new HashMap<>(req.getParameterMap());
				session.setAttribute("queryAllMap", map2);
				map = map2;

			}
//			}
			MealOrderService mealOrderSrv = new MealOrderService();
			List<MealOrderVO> orderList = mealOrderSrv.getAll(map);
//			System.out.println(req.getRequestURI());
//			System.out.println(req.getRequestURL());
			req.setAttribute("action", action);
			req.setAttribute("orderList", orderList);
			req.getRequestDispatcher("/back-end/mealOrder/listQueryOrder2.jsp").forward(req, res);

		}

	}
}
