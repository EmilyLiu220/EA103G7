package com.meal_order.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

import org.json.JSONArray;

import com.front_inform.model.*;
import com.google.gson.Gson;
import com.meal.model.*;
import com.meal_order.model.*;
import com.meal_order_detail.model.MealOrderDetailService;
import com.meal_order_detail.model.MealOrderDetailVO;
import com.meal_set.model.*;
import com.mem.model.MemVO;
import com.res_order.model.ResOrderService;
import com.res_order.model.ResOrderVO;

@WebServlet("/MealOrderServlet.do")
public class MealOrderServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		String action = req.getParameter("action");
		String reqURL = req.getParameter("reqURL");
		String whichPage = req.getParameter("whichPage");
		String returnPath = reqURL;
		if (whichPage != null) {
			returnPath = reqURL + "?whichPage=" + whichPage;
		}

		if ((reqURL == null || req.getParameter("queryString") != null)
				&& !("null".equals(req.getParameter("queryString")))) {
			returnPath = req.getServletPath() + "?whichPage=" + whichPage + "&action="
					+ req.getParameter("queryString");
		}
		Vector<MealVO> mealList = (Vector) session.getAttribute("mealList");
		Vector<MealSetVO> setList = (Vector) session.getAttribute("setList");
		Vector<MealVO> rsvMealList = (Vector<MealVO>) session.getAttribute("rsvMealList");
		Vector<MealSetVO> rsvSetList = (Vector<MealSetVO>) session.getAttribute("rsvSetList");
		Map<String, String> errormsgs = new HashMap<>();

		if ("checkout".equals(action)) {
			MemVO memVO2 = (MemVO) session.getAttribute("memVO2");
			String memNo = memVO2.getMem_no();
			String empNo = (String) session.getAttribute("emp_no");
			String resNo = req.getParameter("res_no");

			Integer mealOrderSts = new Integer(1);
			Integer notiSts = new Integer(0);
			Integer paySts = (resNo != null ? new Integer(0) : new Integer(1));
			Integer amount = new Integer(req.getParameter("amount"));
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Timestamp pickupTime = null;
//			System.out.println(req.getParameter("pickup_time"));
			if (req.getParameter("res_no") == null) {

				try {
					pickupTime = new Timestamp(ft.parse(req.getParameter("pickup_time")).getTime());
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
				pushMsg.put("reload", "asignOrder");
			}
			pushMsg.put("action", "insert");
			pushMsg.put("mealOrderVO", mealOrderVO);
			String jsonMap = gson.toJson(pushMsg);
			webSocket.onMessage(jsonMap);
			// 加入訂餐完成通知
			Front_InformService fiSvc = new Front_InformService();
			fiSvc.addNormalFI(memNo, "訂餐成功，點選查看訂餐訂單");

			req.setAttribute("amount", amount);
			req.setAttribute("mealOrderVO", mealOrderVO);
			String url = "front-end/shopping/mealOrderOne.jsp";
			RequestDispatcher success = req.getRequestDispatcher(url);
			success.forward(req, res);

		}

		if ("rsvCheckout".equals(action)) {
			MemVO memVO2 = (MemVO) session.getAttribute("memVO2");
			String memNo = memVO2.getMem_no();
			String empNo = (String) session.getAttribute("emp_no");
			String resNo = req.getParameter("res_no");
//			if (session.getAttribute("mem_no") != null) {
//				memNo = (String) session.getAttribute("mem_no");
//			}

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

			if (rsvMealList != null) {
				rsvMealList.removeAllElements();
			}
			if (rsvSetList != null) {
				rsvSetList.removeAllElements();
			}

			// 有訂餐的話，訂位訂單新增訂餐編號
			ResOrderService resOrderSvc = new ResOrderService();
			ResOrderVO resOrderVO = resOrderSvc.getOneResOrder(resNo);
			resOrderSvc.updateResOrder(resNo, mealOrderVO.getMeal_order_no(), resOrderVO.getMem_no(),
					resOrderVO.getEmp_no(), resOrderVO.getRes_date(), resOrderVO.getPeople(),
					resOrderVO.getTime_peri_no(), resOrderVO.getInfo_sts(), resOrderVO.getSeat_sts(), null);

			// 加入訂餐完成通知
			Front_InformService fiSvc = new Front_InformService();
			fiSvc.addNormalFI(mealOrderVO.getMem_no(), "訂餐成功，點選查看訂餐訂單");

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
			ResOrderService resOrderSvc = new ResOrderService();
			ResOrderVO resOrderVO = resOrderSvc.findByMealOrderNO(mealOrderVO.getMeal_order_no());
			
			if (resOrderVO != null) {
				resOrderSvc.updateResOrder(resOrderVO.getRes_no(), null, resOrderVO.getMem_no(), resOrderVO.getEmp_no(),
						resOrderVO.getRes_date(), resOrderVO.getPeople(), resOrderVO.getTime_peri_no(),
						resOrderVO.getInfo_sts(), resOrderVO.getSeat_sts(), null);
			}

			if (mealOrderVO.getMeal_order_sts() >= 2 || mealOrderVO.getMeal_order_sts() == 0) {
				errormsgs.put("orderUpdate", "餐點已派工或已取消，無法取消訂單!");
				req.setAttribute("errormsgs", errormsgs);
				req.setAttribute("mealOrderVO", mealOrderVO);
//				String url = "front-end/shopping/mealOrderOne.jsp";
				String url = returnPath;
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
			} else {
				Front_InformService fiSvc = new Front_InformService();
				fiSvc.addNormalFI(mealOrderVO.getMem_no(), "您的訂餐已取消");
				mealOrderSrv.updateOrderSts(mealOrderNo, 0, mealOrderVO.getNoti_sts(), mealOrderVO.getPay_sts());
				mealOrderVO.setMeal_order_sts(0);
				req.setAttribute("mealOrderVO", mealOrderVO);
//				String url = "front-end/shopping/mealOrderOne.jsp";
				String url = returnPath;
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
			}
		}

		if ("update".equals(action)) {

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
			if (mealOrderVO.getMeal_order_sts() == 2) {
				pushMsg.put("action", "prepared");
			}
			pushMsg.put("detailList", detailList);
			String jsonMap = gson.toJson(pushMsg);
			webSocket.onMessage(jsonMap);

			req.setAttribute("action", action);
			req.setAttribute("returnPath", returnPath);
			req.setAttribute("mealOrderVO", mealOrderVO);
			String url = returnPath;
			if ("update".equals(req.getParameter("queryString"))) {
				req.getRequestDispatcher("/back-end/mealOrder/asignOrder.jsp").forward(req, res);
			} else {
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
			}

		}

		if ("prepared".equals(action)) {

			String mealOrderNo = (String) req.getParameter("meal_order_no");
			Integer mealOrderSts = new Integer(3);
			Integer notiSts = new Integer(1); // 原 req.getParameter("noti_sts")
			Integer paySts = new Integer(req.getParameter("pay_sts"));
			MealOrderService mealOrderSrv = new MealOrderService();
			MealOrderVO mealOrderVO = mealOrderSrv.searchByOrderNo(mealOrderNo);
			Front_InformService fiSvc = new Front_InformService();
			fiSvc.addNormalFI(mealOrderVO.getMem_no(), "餐點已完成，請至本餐廳取餐(點選可查看訂單)");
			mealOrderSrv.updateOrderSts(mealOrderNo, mealOrderSts, notiSts, paySts);

			MealOrderWebSocket webSocket = new MealOrderWebSocket();
			Gson gson = new Gson();
			Map<String, Object> pushMsg = new HashMap<>();
			pushMsg.put("reload", "orderDone");
			String jsonMap = gson.toJson(pushMsg);
			webSocket.onMessage(jsonMap);
		}

		if ("search".equals(action)) {
			String mealOrderNo = req.getParameter("meal_order_no");

			MealOrderService mealOrderSrv = new MealOrderService();
			MealOrderVO mealOrderVO = mealOrderSrv.searchByOrderNo(mealOrderNo);
			req.setAttribute("returnPath", returnPath);
			req.setAttribute("mealOrderVO", mealOrderVO);
			String url = "/back-end/mealOrder/listOneOrder.jsp";
			RequestDispatcher success = req.getRequestDispatcher(url);
			success.forward(req, res);
		}

		if ("memOrder".equals(action)) {
			String mealOrderNo = req.getParameter("meal_order_no");

			MealOrderService mealOrderSrv = new MealOrderService();
			MealOrderVO mealOrderVO = mealOrderSrv.searchByOrderNo(mealOrderNo);
			req.setAttribute("returnPath", returnPath);
			req.setAttribute("mealOrderVO", mealOrderVO);
			String url = "/front-end/shopping/mealOrderOne.jsp";
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
			req.setAttribute("action", action);
			req.setAttribute("orderList", orderList);
			req.getRequestDispatcher("/back-end/mealOrder/asignOrder.jsp").forward(req, res);

		}

		if ("queryAll".equals(action)) {

			Map<String, String[]> map = (HashMap) session.getAttribute("queryAllMap");
			if (req.getParameter("whichPage") == null) {
				Map<String, String[]> map2 = new HashMap<>(req.getParameterMap());
				session.setAttribute("queryAllMap", map2);
				map = map2;

			}
			MealOrderService mealOrderSrv = new MealOrderService();
			List<MealOrderVO> orderList = mealOrderSrv.getAll(map);
			req.setAttribute("action", action);
			req.setAttribute("orderList", orderList);
			req.getRequestDispatcher("/back-end/mealOrder/listQueryOrder2.jsp").forward(req, res);

		}

		if ("orderChart".equals(action)) {
			Map<String, String[]> map = (HashMap) session.getAttribute("orderChart");
			if (req.getParameter("whichPage") == null) {
				Map<String, String[]> map2 = new HashMap<>(req.getParameterMap());

				JSONArray orderArr = new JSONArray(map2.get("order_time")[0]);
				JSONArray pickArr = new JSONArray(map2.get("pickup_time")[0]);
				String[] orderStrArray = (String[]) orderArr.toList().toArray(new String[orderArr.length()]);
				String[] pickStrArray = (String[]) pickArr.toList().toArray(new String[pickArr.length()]);
				map2.put("order_time", orderStrArray);
				map2.put("pickup_time", pickStrArray);

//				System.out.println("stringArray.length=" + map2.get("order_time").length);

				session.setAttribute("orderChart", map2);
				map = map2;
			}
			MealOrderService mealOrderSrv = new MealOrderService();
			List<MealOrderVO> orderList = mealOrderSrv.getAll(map);
			Map<String, Object> listMap = new HashMap<>();
			int ranNumPeople = (int) ((Math.random() * 2 + 1) * orderList.size());
			listMap.put("orderList", new Integer(orderList.size()));
			listMap.put("ranNumPeople", ranNumPeople);
			MealOrderDetailService detailSrv = new MealOrderDetailService();
			MealService mealSrv = new MealService();
			MealSetService mealSetSrv = new MealSetService();

			Map<String, Object> mealMap = mealSrv.getAll().stream()
					.collect(Collectors.toMap(MealVO::getMeal_no, m -> m));
			Map<String, Object> mealSetMap = mealSetSrv.getAll().stream()
					.collect(Collectors.toMap(MealSetVO::getMeal_set_no, ms -> ms));
			Set<String> mealKeys = mealMap.keySet();
			Set<String> mealSetKeys = mealSetMap.keySet();

			for (String key : mealKeys) {
				((MealVO) mealMap.get(key)).setMeal_qty(0);
				((MealVO) mealMap.get(key)).setMeal_img(null);
			}
			for (String key : mealSetKeys) {
				((MealSetVO) mealSetMap.get(key)).setMeal_set_qty(0);
				((MealSetVO) mealSetMap.get(key)).setMeal_set_img(null);
			}

			for (MealOrderVO mealOrderVO : orderList) {
				for (MealOrderDetailVO detailVO : detailSrv.searchByOrderNo(mealOrderVO.getMeal_order_no())) {
					for (String key : mealKeys) {
						if (key.equals(detailVO.getMeal_no())) {
							((MealVO) mealMap.get(key))
									.setMeal_qty(((MealVO) mealMap.get(key)).getMeal_qty() + detailVO.getQty());
						}
					}
					for (String key : mealSetKeys) {
						if (key.equals(detailVO.getMeal_set_no())) {
							((MealSetVO) mealSetMap.get(detailVO.getMeal_set_no())).setMeal_set_qty(
									((MealSetVO) mealSetMap.get(detailVO.getMeal_set_no())).getMeal_set_qty()
											+ detailVO.getQty());
						}
					}
				}
			}
			Map<String, Map<String, Object>> jsonMap = new HashMap<>();
			jsonMap.put("mealMap", mealMap);
			jsonMap.put("mealSetMap", mealSetMap);
			jsonMap.put("orderList", listMap);

			Gson gson = new Gson();
			String jsondata = gson.toJson(jsonMap);
			res.setContentType("application/json; charset=utf-8");
			PrintWriter out = res.getWriter();
			out.write(jsondata);
//			System.out.println(jsondata);

			req.setAttribute("orderList", orderList);

		}

	}
}
