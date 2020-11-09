package com.bonus.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bonus.model.BonusService;


@WebServlet("/bonus/bonus.showPic")
public class BonusServletShowImg extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/*");
		ServletOutputStream out = res.getOutputStream();
		

		String bonusNo = req.getParameter("bonus_img");
		BonusService bonusSrv = new BonusService();
		byte[] thisPic = bonusSrv.getOneBonus(bonusNo).getBns_img();
		out.write(thisPic);

	
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
