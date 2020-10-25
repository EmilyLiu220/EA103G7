package com.time_peri.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.res_order.model.ResOrderService;
import com.res_order.model.ResOrderVO;
import com.time_peri.model.TimePeriService;
import com.time_peri.model.TimePeriVO;

@WebServlet("/time_peri/TimePeriServlet.do")
public class TimePeriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TimePeriServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		/**********************************
		 **** 依照日期判斷可選擇用餐時段****
		 **********************************/
		if ("get_TimePeri".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			if (req.getParameter("res_date") != null) {

				PrintWriter out = res.getWriter();

				String res_date = req.getParameter("res_date");
				Calendar now = Calendar.getInstance();
				Integer nowHours = now.get(Calendar.HOUR_OF_DAY);
				java.util.Date toDay = new java.util.Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				TimePeriService timePeriSvc = new TimePeriService();
				List<TimePeriVO> list = timePeriSvc.getAll();
				List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
				for (TimePeriVO timePeriVO : list) {
					int time_start = timePeriVO.getTime_start().indexOf("PM") > 0
							? Integer.parseInt((timePeriVO.getTime_start().substring(0, 2))) + 12
							: Integer.parseInt((timePeriVO.getTime_start().substring(0, 2)));
					if (format.format(toDay).equals(res_date)) {
						if (time_start > nowHours + 6) {
							JSONObject jsonObject = new JSONObject(timePeriVO.toString());
							jsonObjectList.add(jsonObject);
						}
					} else {
						JSONObject jsonObject = new JSONObject(timePeriVO.toString());
						jsonObjectList.add(jsonObject);
					}
				}
				out.print(jsonObjectList.toString());
				out.close();
			} else {
				errorMsgs.add("預定日期不能為空");
			}
			return;
		}
	}
}
