package com.res_order.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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

import com.front_inform.model.Front_InformService;
import com.res_detail.model.ResDetailService;
import com.res_detail.model.ResDetailVO;
import com.res_order.model.ResOrderService;
import com.res_order.model.ResOrderVO;
import com.seat.model.SeatService;
import com.seat.model.SeatVO;
import com.seat_obj.model.SeatObjService;
import com.seat_obj.model.SeatObjVO;
import com.time_peri.model.TimePeriService;
import com.time_peri.model.TimePeriVO;

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
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		String goMeal = req.getParameter("goMeal");
		HttpSession hs = req.getSession();

		/********************** 單純訂位 **********************/
		if ("order_seat".equals(action) && goMeal == null && "insert".equals(hs.getAttribute("insert"))) {
			hs.removeAttribute("insert");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String res_date = req.getParameter("res_date");
			String time_peri_no = req.getParameter("time_peri_no");
			String floor = req.getParameter("floor_list");
			String[] seats_no = req.getParameterValues("seat_checked");
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

			Front_InformService front_InformSvc = new Front_InformService();
			// 發送通知
			front_InformSvc.addROFI(mem_no, next_res_no, "訂位成功，點選查看訂位訂單");

			/**********
			 * 判斷訂位日期是否為今日，若 true 則直接發送當日訂位確認通知 ( 這樣寫我那邊可以少判斷些東西...霸脫組長大大了 QQ )
			 **********/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date today = new java.util.Date();
			String todayStr = sdf.format(today);
			if (todayStr.equals(res_date)) {
				front_InformSvc.addRCFI(next_res_no); // 在執行此動作時順便去修改 RES_ORDER 裡的 INFO_STS 了 → 修改為 1
			}
			req.setAttribute("res_no", next_res_no);
			
			res.sendRedirect(req.getContextPath() + "/front-end/res_order/getMemberResSeat.jsp");
			return;
		}
		// 修改訂位訂單
		if ("modify_seat".equals(action)) {
			hs.removeAttribute("insert");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String res_no = req.getParameter("res_no");
			String res_date = req.getParameter("res_date");
			String time_peri_no = req.getParameter("time_peri_no");
			String floor = req.getParameter("floor_list");
			String[] seats_no = req.getParameterValues("seat_checked");
			String people = req.getParameter("people");
			String emp_no = req.getParameter("emp_no");
			String mem_no = req.getParameter("mem_no");
			String requestURL = req.getParameter("requestURL");
			String whichPage = req.getParameter("whichPage");
			
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
			ResOrderVO resOrderVO = resOrderSvc.getOneResOrder(res_no);

			resOrderSvc.updateResOrder(res_no, resOrderVO.getMeal_order_no(), resOrderVO.getMem_no(), emp_no,
					java.sql.Date.valueOf(res_date), new Integer(people), time_peri_no, resOrderVO.getInfo_sts(),
					resOrderVO.getSeat_sts(), seats_no);

			Front_InformService front_InformSvc = new Front_InformService();
			// 發送通知
			front_InformSvc.addROFI(mem_no, res_no, "訂位訂單修改成功，點選查看訂位訂單");

			/********** 判斷訂單修改後，若修改後的日期=今日，則直接再發送一次當日訂位確認通知 **********/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date today = new java.util.Date();
			String todayStr = sdf.format(today);
			if (todayStr.equals(res_date)) {
				front_InformSvc.addRCFI(res_no);
				// 在執行此動作時，已經順便修改 RES_ORDER 裡的 INFO_STS 了 → 修改為 1
			}

			req.setAttribute("res_no", res_no);
			RequestDispatcher failureView = req.getRequestDispatcher(requestURL + "?whichPage=" + whichPage);
			failureView.forward(req, res);
			return;
		}

		/********************** 訂位又要訂餐 **********************/
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

			Front_InformService front_InformSvc = new Front_InformService();
			// 發送通知
			front_InformSvc.addROFI(mem_no, next_res_no, "訂位成功，點選查看訂位訂單");

			req.setAttribute("res_no", next_res_no);
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/shopping/mealMenu2.jsp");
			failureView.forward(req, res);
			return;
		}
		/********************** 去訂餐 **********************/
		if ("go_res_meal".equals(action)) {
			String res_no = req.getParameter("res_no");
			req.setAttribute("res_no", res_no);
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/shopping/mealMenu2.jsp");
			failureView.forward(req, res);
			return;
		}

		/********************** 取得今日此時段，那些位置被訂了 **********************/
		if ("get_Res_Order_Today".equals(action)) {
			PrintWriter out = res.getWriter();

			String res_date = req.getParameter("res_date");
			String time_peri_no = req.getParameter("time_peri_no");
			ResOrderService resOrderSvc = new ResOrderService();
			ResDetailService resDetailSvc = new ResDetailService();
			SeatService seatSvc = new SeatService();
			if (!"-1".equals(time_peri_no) && res_date != null) {
				List<ResOrderVO> resOrderList = resOrderSvc.getResDate_And_TimePeri_getAll(res_date, time_peri_no);
				List<String> seatNoList = new ArrayList<String>();
				// 取得訂位訂單
				for (ResOrderVO resOrderVO : resOrderList) {
					// 判斷是否被取消
					if (resOrderVO.getInfo_sts() != 3) {
						List<ResDetailVO> resDetailList = resDetailSvc.getAllResNO(resOrderVO.getRes_no());
						// 如果桌位編號有在訂單內，回傳並將該桌號disabled
						for (ResDetailVO resDetailVO : resDetailList) {
							if(req.getParameter("floor") != null) {
								Integer floor = Integer.parseInt(req.getParameter("floor"));
								if (seatSvc.getOneSeat(resDetailVO.getSeat_no()).getSeat_f().equals(floor)) {
									seatNoList.add(resDetailVO.getSeat_no());
								}
							} else {
								seatNoList.add(resDetailVO.getSeat_no());
							}
						}
					}
				}
				JSONArray seatNoJSONList = new JSONArray(seatNoList);
				res.setCharacterEncoding("UTF-8");
				out.print(seatNoJSONList.toString());
				out.flush();
				out.close();
			} else {
				res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "請選擇正確時段");
				return;
			}
			return;
		}

		/********************** 取得所有桌位，可容納之人數 **********************/
		if ("get_All_Seat_People".equals(action)) {

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
				PrintWriter out = res.getWriter();
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				out.print(sb.toString());
				out.flush();
				out.close();
				return;
			}
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JSONObject是null");
			return;
		}

		/*********** 客人選位換頁 ***********/
		if ("floor_load".equals(action)) {
			if (req.getParameter("floor") == null) {
				return;
			}

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
			PrintWriter out = res.getWriter();
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			out.print(jsonObjectList.toString());
			out.flush();
			out.close();
			return;
		}

		/********************** 取消訂位 **********************/
		if ("cancel_Seat_Res_Order".equals(action)) {
			String res_no = req.getParameter("res_no");
			String requestURL = req.getParameter("requestURL");
			String whichPage = req.getParameter("whichPage");

			ResOrderService resOrderSve = new ResOrderService();

			ResOrderVO resOrderVO = resOrderSve.getOneResOrder(res_no);

			resOrderSve.updateResOrder(res_no, resOrderVO.getMeal_order_no(), resOrderVO.getMem_no(),
					resOrderVO.getEmp_no(), resOrderVO.getRes_date(), resOrderVO.getPeople(),
					resOrderVO.getTime_peri_no(), new Integer(3), resOrderVO.getSeat_sts(), null);

			Front_InformService front_InformSvc = new Front_InformService();
			front_InformSvc.addROFI(resOrderVO.getMem_no(), res_no, "您的訂位已取消");

			RequestDispatcher failureView = req.getRequestDispatcher(requestURL + "?whichPage=" + whichPage);
			failureView.forward(req, res);
			return;
		}

		/********************** 修改訂位取得訂單資訊 **********************/
		if ("get_Modify_Seat_Order_Info".equals(action)) {
			String res_no = req.getParameter("res_no");

			ResOrderService resOrderSvc = new ResOrderService();
			ResDetailService resDetailSvc = new ResDetailService();
			SeatService seatSvc = new SeatService();
			SeatObjService seatObjSvc = new SeatObjService();
			TimePeriService timePeriSvc = new TimePeriService();

			ResOrderVO resOrderVO = resOrderSvc.getOneResOrder(res_no);
			TimePeriVO timePeriVO = timePeriSvc.getOneTimePeri(resOrderVO.getTime_peri_no());
			List<ResDetailVO> resDetailVOList = resDetailSvc.getAllResNO(res_no);

			StringBuilder sb = new StringBuilder();

			sb.append("{\"res_date\":" + "\"" + resOrderVO.getRes_date() + "\"" + ", \"time_peri_no\":" + "\""
					+ timePeriVO.getTime_peri_no() + "\"" + ", \"seat_no\":[");

			PrintWriter out = res.getWriter();
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			int i = 0;
			for (ResDetailVO resDetailVO : resDetailVOList) {
				if (i < resDetailVOList.size() - 1) {
					sb.append("\"" + resDetailVO.getSeat_no() + "\"" + ",");
					i++;
				} else
					sb.append("\"" + resDetailVO.getSeat_no() + "\"");
			}
			sb.append("], \"people\":[");
			int j = 0;
			for (ResDetailVO resDetailVO : resDetailVOList) {
				int people = seatObjSvc.getOneSeatObj(seatSvc.getOneSeat(resDetailVO.getSeat_no()).getSeat_obj_no())
						.getSeat_people();
				int seat_f = seatSvc.getOneSeat(resDetailVO.getSeat_no()).getSeat_f();
				if (j < resDetailVOList.size() - 1) {
					sb.append("\"" + people + "\"" + ",");
					j++;
				} else
					sb.append("\"" + people + "\"");
			}
			sb.append("], \"seat_f\":[");
			int k = 0;
			for (ResDetailVO resDetailVO : resDetailVOList) {
				int seat_f = seatSvc.getOneSeat(resDetailVO.getSeat_no()).getSeat_f();
				if (k < resDetailVOList.size() - 1) {
					sb.append("\"" + seat_f + "\"" + ",");
					k++;
				} else
					sb.append("\"" + seat_f + "\"");
			}
			sb.append("]}");
			out.print(sb.toString());
			out.flush();
			out.close();

			return;
		}
		
		// 去修改頁面
		if ("modify_Seat_Order".equals(action)) {
			String requestURL = req.getParameter("requestURL");
			String whichPage = req.getParameter("whichPage");
			req.setAttribute("requestURL", requestURL);
			req.setAttribute("whichPage", whichPage);
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/res_order/modifySeat.jsp");
			failureView.forward(req, res);
			return;
		}

		// 修改頁面返回上一頁
		if("return_former_page".equals(action)) {
			String requestURL = req.getParameter("requestURL");
			String whichPage = req.getParameter("whichPage");
			RequestDispatcher failureView = req.getRequestDispatcher(requestURL + "?whichPage=" + whichPage);
			failureView.forward(req, res);
			return;
		}
		
		// not do anything, go to the this page
		res.sendRedirect(req.getContextPath() + "/front-end/front_home.jsp");
	}
}
