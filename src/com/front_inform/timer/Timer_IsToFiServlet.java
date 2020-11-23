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
	
	TimerTask taskIsToFi = new Timer_IsToFi();
	public void init() {
    	java.util.Timer clock1 = new java.util.Timer();
    	long delay1 = 1 * 1000;
    	long period = 86400000; // 24 小時
    	// 從現在開始的 1 秒後，每 24 小時會執行一次此排程器，將當日的通知設定 insert 進前台通知裡
    	clock1.schedule(taskIsToFi, delay1, period);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("Timer_IsToFiServlet init immediately.(load-on-startup)");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
		taskIsToFi.cancel();
	}
	
}
