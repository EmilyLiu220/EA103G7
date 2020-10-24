package com.meal.controller;

import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meal.model.MealService;
@WebServlet("/meal/meal.showPic")
public class MealServletShowImg extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("image/*");
		ServletOutputStream out = res.getOutputStream();

//		try {

			String mealNo = req.getParameter("meal_img");
			MealService mealSrv = new MealService();
			byte[] thisPic = mealSrv.searchByNo(mealNo).getMeal_img();
			out.write(thisPic);

//		} catch (Exception e) {
//			InputStream in = getServletContext().getResourceAsStream("/NoData/null.jpg");
//			byte[] b = new byte[in.available()];
//			in.read(b);
//			out.write(b);
//			in.close();
//		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
