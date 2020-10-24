package com.res_order.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.res_detail.model.ResDetailService;
import com.res_detail.model.ResDetailVO;
import com.res_order.model.ResOrderService;
import com.res_order.model.ResOrderVO;
import com.seat.model.SeatService;
import com.seat.model.SeatVO;
import com.seat_obj.model.SeatObjService;
import com.seat_obj.model.SeatObjVO;

@WebServlet("/res_order/ResOrderServlet.do")
public class ResOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ResOrderServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String goMeal = req.getParameter("goMeal");
		HttpSession hs = req.getSession();
		if ("order_seat".equals(action) && goMeal == null && "insert".equals(hs.getAttribute("insert"))) {
			hs.removeAttribute("insert");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String res_date = req.getParameter("res_date");
			String time_peri_no = req.getParameter("time_peri_no");
			String floor = req.getParameter("floor_list");
			String seats_no[] = req.getParameterValues("seat_checked");
			String people = req.getParameter("people");
			String emp_no = req.getParameter("emp_no");
			String mem_no = req.getParameter("mem_no");

			if ("--請選擇日期--".equals(res_date)) {
				errorMsgs.add("請選擇訂位日期");
			}
			if ("-1".equals(time_peri_no)) {
				errorMsgs.add("請選擇訂位時段");
			}
			if (seats_no == null) {
				errorMsgs.add("請選擇座位");
			}
			if ("".equals(floor)) {
				errorMsgs.add("請選擇樓層");
			}
			if ("".equals(people)) {
				errorMsgs.add("請輸入訂位人數");
			}
			

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res_order/orderSeat.jsp");
				failureView.forward(req, res);
				return;
			}
			ResOrderService resOrderSvc = new ResOrderService();
			String next_res_no = resOrderSvc.addResOrder(null, mem_no, emp_no, java.sql.Date.valueOf(res_date),
					new Integer(people), time_peri_no, new Integer(0), new Integer(0), seats_no);

//			Front_InformService front_InformSvc = new Front_InformService();
//
//			// 發送通知
//			front_InformSvc.addROFI(mem_no, next_res_no, "訂位成功，點選查看訂位明細");
//			// 修改回復狀態，應該加在 Front_InformService > addROFI > 對應的DAO
//			ResOrderVO resOrderVO = resOrderSvc.getOneResOrder(next_res_no);
//			resOrderSvc.updateResOrder(next_res_no, resOrderVO.getMeal_order_no(), resOrderVO.getMem_no(), resOrderVO.getEmp_no(), resOrderVO.getRes_time(), java.sql.Date.valueOf(res_date), resOrderVO.getPeople(), resOrderVO.getTime_peri_no(), new Integer(1), resOrderVO.getSeat_sts());
			req.setAttribute("res_no", next_res_no);
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res_order/getMemberResSeat.jsp");

			failureView.forward(req, res);
			return;
		}
		if ("carry_on_res_meal".equals(goMeal) && "order_seat".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String res_date = req.getParameter("res_date");
			String time_peri_no = req.getParameter("time_peri_no");
			String floor = req.getParameter("floor_list");
			String seats_no[] = req.getParameterValues("seat_checked");
			String people = req.getParameter("people");
			String emp_no = req.getParameter("emp_no");
			String mem_no = req.getParameter("mem_no");

			if ("--請選擇日期--".equals(res_date)) {
				errorMsgs.add("請選擇訂位日期");
			}
			if ("-1".equals(time_peri_no)) {
				errorMsgs.add("請選擇訂位時段");
			}
			if (seats_no == null) {
				errorMsgs.add("請選擇座位");
			}
			if ("".equals(floor)) {
				errorMsgs.add("請選擇樓層");
			}
			if ("".equals(people)) {
				errorMsgs.add("請輸入訂位人數");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res_order/orderSeat.jsp");
				failureView.forward(req, res);
				return;
			}
			ResOrderService resOrderSvc = new ResOrderService();
			String next_res_no = resOrderSvc.addResOrder(null, mem_no, emp_no, java.sql.Date.valueOf(res_date),
					new Integer(people), time_peri_no, new Integer(0), new Integer(0), seats_no);
//			Front_InformService front_InformSvc = new Front_InformService();
//
//			// 發送通知
//			front_InformSvc.addROFI(mem_no, next_res_no, "訂位成功，點選查看訂位明細");
//			// 修改回復狀態，應該加在 Front_InformService > addROFI > 對應的DAO
//			ResOrderVO resOrderVO = resOrderSvc.getOneResOrder(next_res_no);
//			resOrderSvc.updateResOrder(next_res_no, resOrderVO.getMeal_order_no(), resOrderVO.getMem_no(), resOrderVO.getEmp_no(), resOrderVO.getRes_time(), java.sql.Date.valueOf(res_date), resOrderVO.getPeople(), resOrderVO.getTime_peri_no(), new Integer(1), resOrderVO.getSeat_sts());

			req.setAttribute("res_no", next_res_no);
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/shopping/mealMenu2.jsp");
			failureView.forward(req, res);
			return;
		}
		/********************** 去訂餐 **********************/
		if ("go_res_meal".equals(action)) {
			System.out.println("go_res_meal");
			return;
		}

		/********************** 取得今日此時段，那些位置被訂了 **********************/
		if ("getResOrderToDay".equals(action)) {

			PrintWriter out = res.getWriter();

			String res_date = req.getParameter("res_date");
			String time_peri_no = req.getParameter("time_peri_no");

			ResOrderService resOrderSvc = new ResOrderService();
			ResDetailService resDetailSvc = new ResDetailService();

			List<ResOrderVO> resOrderList = resOrderSvc.getResDate_And_TimePeri_getAll(res_date, time_peri_no);
			List<String> seatNoList = new ArrayList<String>();
			// 取得訂位訂單
			for (ResOrderVO resOrderVO : resOrderList) {
				// 判斷是否被取消
				if (resOrderVO.getInfo_sts() != 3) {
					List<ResDetailVO> resDetailList = resDetailSvc.getAllResNO(resOrderVO.getRes_no());
					// 如果桌位編號有在訂單內，回傳並將該桌號disabled
					for (ResDetailVO resDetailVO : resDetailList) {
						seatNoList.add(resDetailVO.getSeat_no());
					}
				}
			}
			JSONArray seatNoJSONList = new JSONArray(seatNoList);
			out.print(seatNoJSONList.toString());
			out.close();
			return;
		}

		if ("getAllSeatPeople".equals(action)) {

			PrintWriter out = res.getWriter();

			if (req.getParameter("people") != null) {

				JSONArray jsonArray = new JSONArray(req.getParameter("people"));
				StringBuilder sb = new StringBuilder();

				SeatObjService seatObjSvc = new SeatObjService();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					SeatObjVO seatObjVO = seatObjSvc.getOneSeatObj(object.get("seat_obj_no").toString());
					if (i == 0) {
						sb.append("[{\"" + object.get("seat_no").toString() + "\"" + ":" + seatObjVO.getSeat_people()
								+ "},");
					} else if (i < jsonArray.length() - 1) {
						sb.append("{\"" + object.get("seat_no").toString() + "\"" + ":" + seatObjVO.getSeat_people()
								+ "},");
					} else {
						sb.append("{\"" + object.get("seat_no").toString() + "\"" + ":" + seatObjVO.getSeat_people()
								+ "}]");
					}
				}
				out.print(sb.toString());
				out.close();
				return;
			}
			out.close();
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JSONObject是null");
			return;
		}

		/*********** 客人選位換頁 ***********/
		if ("floor_load".equals(action)) {
			if (req.getParameter("floor") == null) {
				return;
			}
			PrintWriter out = res.getWriter();

			String floor = req.getParameter("floor");
			SeatService seatSvc = new SeatService();
			List<SeatVO> seatVOList = seatSvc.getAll();
			List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
			for (SeatVO seatVO : seatVOList) {
				if (floor.equals(seatVO.getSeat_f().toString())) {
					JSONObject jsonObject = new JSONObject(seatVO.toString());
					jsonObjectList.add(jsonObject);
				}
			}
			out.print(jsonObjectList.toString());
			out.close();
			return;
		}
		
		/********************** 取消訂位 **********************/
		if ("cancelSeatResOrder".equals(action)) {
			System.out.println("cancelSeatResOrder");
			return;
		}
		
		/********************** 修改訂位 **********************/
		if ("modifySeatPosition".equals(action)) {
			System.out.println("modifySeatPosition");
			return;
		}
		
		
		
		// not do anything, go to the this page
		res.sendRedirect(req.getContextPath() + "/front-end/res_order/getMemberResSeat.jsp");
	}
}
