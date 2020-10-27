package com.front_inform.timer;

import java.io.IOException;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Timer_IsToFiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public Timer_IsToFiServlet() {
        super();
    }
	
	TimerTask task = new Timer_IsToFi();
	public void init() {
    	java.util.Timer clock1 = new java.util.Timer();
//    	java.sql.Date sqlDate = java.sql.Date.valueOf("2020-10-26");
//    	java.util.Date firstRun = new java.util.Date(sqlDate.getTime());
//    	// 從 "2020-10-26" 開始，每 24 小時執行一次
//    	clock1.scheduleAtFixedRate(task, firstRun, period);
    	long delay1 = 1 * 1000;
    	long period = 86400000; // 24 小時
    	// 從現在開始 1 秒鐘之後，每隔 24 小時執行一次 
    	clock1.schedule(task, delay1, period);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
		task.cancel();
	}
	
}
