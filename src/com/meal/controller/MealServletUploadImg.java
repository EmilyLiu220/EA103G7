package com.meal.controller;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.meal.model.MealService;

@WebServlet("/meal/meal.UploadImg")
@MultipartConfig
public class MealServletUploadImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		Part part = req.getPart("meal_img");
		String mealNo = req.getParameter("meal_no");
        try{
        	InputStream in = part.getInputStream();  
        	byte[] buffer = new byte [in.available()];
        	MealService mealSrv = new MealService();
        	mealSrv.searchByNo(mealNo).setMeal_img(buffer);
        	
//        	in.close();
        }catch(Exception e) {
        	
        }
		
	}

}
