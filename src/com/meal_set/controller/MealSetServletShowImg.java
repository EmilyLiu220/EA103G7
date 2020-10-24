package com.meal_set.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meal.model.MealService;
import com.meal_set.model.MealSetService;

@WebServlet("/meal_set/mealSet.showPic")
public class MealSetServletShowImg extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/*");
		ServletOutputStream out = res.getOutputStream();
		

		String mealSetNo = req.getParameter("meal_set_img");
		MealSetService mealSetSrv = new MealSetService();
		byte[] thisPic = mealSetSrv.searchByNo(mealSetNo).getMeal_set_img();
		out.write(thisPic);

	
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
