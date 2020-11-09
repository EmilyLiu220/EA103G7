package com.seat.controller;

import java.io.IOException;
import java.io.PrintWriter;
//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.res_detail.model.ResDetailService;
import com.res_detail.model.ResDetailVO;
import com.res_order.model.ResOrderService;
import com.res_order.model.ResOrderVO;
import com.seat.model.SeatService;
import com.seat.model.SeatVO;

@WebServlet("/seat/SeatServlet.do")
public class SeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SeatServlet() {
		super();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = res.getWriter();
		String action = req.getParameter("action");
		String floor = req.getParameter("floor_list");
		/******************************** 桌位存檔 ********************************/
		if ("archive_seat".equals(action)) {
			boolean updataStatus = false;
			/*********** 前台取值不為空做以下 ***********/
//			System.out.println(req.getParameter("floor_list"));
//			System.out.println(req.getParameter("jsonDataStr"));
			if (req.getParameter("jsonDataStr") != null) {
//				System.out.println(req.getParameter("jsonDataStr"));
				// NEW JSONObject Array
				SeatService seatSvn = new SeatService();
				List<JSONObject> objArray = new ArrayList<JSONObject>();
				JSONArray jsonArray = new JSONArray(req.getParameter("jsonDataStr"));
				// get JSONObject add objArray
				for (Object jsonObjArray : jsonArray) {
					objArray.add((JSONObject) jsonObjArray);
				}
				List<SeatVO> seatVOList = seatSvn.getAll();
				// select objArray all element compare
				for (int i = 0; i < objArray.size(); i++) {
					/*********** 物件沒有編號代表是新clone物件，執行新增 ***********/
					if (!objArray.get(i).has("seat_no")) {
						/*********** 新桌位，桌位名稱為空值 ***********/
						if ("".equals(objArray.get(i).get("seat_name").toString().trim())) {
//							out.print("桌位名稱為空值");
//							out.close();
							res.sendError(HttpServletResponse.SC_FORBIDDEN, "桌位名稱為空值");
							return;
						}
						/*********** 取出資料庫所有座位物件，比對this座位 ***********/
						for (SeatVO seatVO : seatVOList) {
							/*********** 新增的物件，如果資料庫這個樓層，這個位子已經有物件，不再新增 ***********/
							if ((seatVO.getSeat_x().equals(Double.valueOf(objArray.get(i).get("seat_x").toString()))
									&& seatVO.getSeat_y()
											.equals(Double.valueOf(objArray.get(i).get("seat_y").toString())))
									&& floor.equals(objArray.get(i).get("seat_f").toString())) {
								System.out.println(12312);
								res.sendError(HttpServletResponse.SC_FORBIDDEN, "沒有更新物件");
								return;
							}
							/*********** 新增的物件，桌位名相同，不再新增 ***********/
							if ((seatVO.getSeat_name().equals(objArray.get(i).get("seat_name").toString())
									&& seatVO.getSeat_f().equals(objArray.get(i).get("seat_f")))) {
								res.sendError(HttpServletResponse.SC_FORBIDDEN, "同一樓層有桌位同名");
								return;
							}
						}
						/********************** 開始更新 **********************/
						seatSvn.addSeat(objArray.get(i).get("seat_obj_no").toString(),
								objArray.get(i).get("seat_name").toString().trim(),
								Double.valueOf(objArray.get(i).get("seat_x").toString()),
								Double.valueOf(objArray.get(i).get("seat_y").toString()),
								(int) objArray.get(i).get("seat_l"), (int) objArray.get(i).get("seat_w"),
								(int) objArray.get(i).get("seat_f"));
						/*********** 只要有更新，更新狀態改為true ***********/
						updataStatus = true;
					}
					/*********** 物件有編號代表是舊物件物件，執行更新 ***********/
					if (objArray.get(i).has("seat_no")) {
						SeatVO seatVO = new SeatVO();
						seatVO = seatSvn.getOneSeat(objArray.get(i).get("seat_no").toString());
						/*********** 舊桌位，桌位名稱為空值 ***********/
						if ("".equals(objArray.get(i).get("seat_name").toString().trim())) {
							res.sendError(HttpServletResponse.SC_FORBIDDEN, "桌位名稱為空值");
							return;
						}
						/*********** 如果現在的物件x,y不等於資料庫物件x,y，更新x,y ***********/
						/*********** 或如果現在的物件name不等於資料庫物件name，更新name ***********/
						if (!(seatVO.getSeat_x().equals(Double.valueOf(objArray.get(i).get("seat_x").toString()))
								|| seatVO.getSeat_y().equals(Double.valueOf(objArray.get(i).get("seat_y").toString())))
								|| !(seatVO.getSeat_name().equals(objArray.get(i).get("seat_name").toString()))) {
							/*********** 取出資料庫所有座位物件，比對this座位 ***********/
							List<SeatVO> seatVOLists = seatSvn.getAll();
							for (SeatVO seatVO1 : seatVOLists) {
								/*********** 不同座位編號的的桌位名稱才進行比較 ***********/
								if (!seatVO1.getSeat_no().equals(objArray.get(i).get("seat_no").toString())) {
									if ((seatVO1.getSeat_name().equals(objArray.get(i).get("seat_name").toString())
											&& seatVO1.getSeat_f().equals(seatVO.getSeat_f()))) {
										res.sendError(HttpServletResponse.SC_FORBIDDEN, "同一樓層有桌位同名");
										return;
									}
								}
							}
							/********************** 開始更新 **********************/
							seatSvn.updateSeat(objArray.get(i).get("seat_no").toString(),
									objArray.get(i).get("seat_obj_no").toString(),
									objArray.get(i).get("seat_name").toString().trim(), new Integer(0),
									Double.valueOf(objArray.get(i).get("seat_x").toString()),
									Double.valueOf(objArray.get(i).get("seat_y").toString()),
									(int) objArray.get(i).get("seat_l"), (int) objArray.get(i).get("seat_w"),
									(int) objArray.get(i).get("seat_f"));
							/*********** 只要有更新，更新狀態改為true ***********/
							updataStatus = true;
						}
					}
				}
				/*********** 有執行過一次更新updataStatus=true，否則false ***********/
				if (updataStatus) {
					PrintWriter out = res.getWriter();
					SeatService seatSvc = new SeatService();
					List<SeatVO> seatVOList1 = seatSvc.getAll();
					List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
					for (SeatVO seatVO : seatVOList1) {
						if (floor.equals(seatVO.getSeat_f().toString())) {
							JSONObject jsonObject = new JSONObject(seatVO.toString());
							jsonObjectList.add(jsonObject);
						}
					}
					out.print(jsonObjectList.toString());
					out.close();
					res.setStatus(HttpServletResponse.SC_OK);
					return;
				} else {
					res.sendError(HttpServletResponse.SC_FORBIDDEN, "沒有更新物件");
					return;
				}
			}
			/*********** 沒有接收到前端的值，無法處裡 ***********/
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JSONObject是null");
			return;
		}
		/******************************** 桌位刪除(更改狀態) ********************************/
		if ("delete_seat".equals(action)) {
			SeatService seatSvn = new SeatService();
			if (req.getParameter("jsonDataStr") != null) {
				JSONObject obj = new JSONObject(req.getParameter("jsonDataStr"));
				SeatVO seatVO = new SeatVO();
				seatVO = seatSvn.getOneSeat(obj.get("seat_no").toString());

				seatSvn.updateSeat(obj.get("seat_no").toString(), seatVO.getSeat_obj_no(), seatVO.getSeat_name(),
						new Integer(1), new Double(0), new Double(0), new Integer(0), new Integer(0), seatVO.getSeat_f());
			}
			return;
		}

		if ("get_seatVO".equals(action)) {

			String res_no = req.getParameter("res_no");

			ResDetailService resDetailSvc = new ResDetailService();
			SeatService seatSvc = new SeatService();

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

			List<SeatVO> seatVOList = new ArrayList<SeatVO>();

			List<ResDetailVO> resDetailVOList = resDetailSvc.getAllResNO(res_no);
			for (ResDetailVO resDetailVO : resDetailVOList) {
				seatVOList.add(seatSvc.getOneSeat(resDetailVO.getSeat_no()));
			}
			
			String jsonStr = gson.toJson(seatVOList);
			PrintWriter out = res.getWriter();
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");

			out.write(jsonStr);
			out.flush();
			out.close();
			return;

		}

		/*********** 座位編輯換頁 ***********/
		if ("floor_load".equals(action)) {
			if (req.getParameter("floor") == null) {
				return;
			}
			PrintWriter out = res.getWriter();
			String floor1 = req.getParameter("floor");
			SeatService seatSvc = new SeatService();
			List<SeatVO> seatVOList = seatSvc.getAll();
			List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
			for (SeatVO seatVO : seatVOList) {
				if (floor1.equals(seatVO.getSeat_f().toString())) {
					JSONObject jsonObject = new JSONObject(seatVO.toString());
					jsonObjectList.add(jsonObject);
				}
			}
			out.print(jsonObjectList.toString());
			out.close();
			return;
		}
	}
}