package com.front_inform.timer;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Timer_ResCheFiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Timer_ResCheFiServlet() {
        super();
    }
    
    TimerTask taskResChe = new Timer_ResCheFi();
	public void init() {
    	// 每天 0900 會執行一次此排程器，將當日用餐確認通知 insert 進前台通知裡
    	// 獲取當前時間
    	Calendar currentDate = Calendar.getInstance();
    	long currentDateLong = currentDate.getTime().getTime();
    	// 計算滿足條件的最近一次執行時間
    	Calendar earliestDate = ((Timer_ResCheFi) taskResChe).getEarliestDate(currentDate, 9, 0, 0);
    	long earliestDateLong = earliestDate.getTime().getTime();
    	// 計算從當前時間到最近一次執行時間的時間間隔
    	long delay = earliestDateLong - currentDateLong;
    	// 計算執行週期為一天
    	long period = 86400000; // 24 小時，24 * 60 * 60 * 1000
    	ScheduledExecutorService clock2 = Executors.newScheduledThreadPool(10);
    	// 從現在開始 delay 毫秒之後，每隔一星期執行一次 taskResChe
    	clock2.scheduleAtFixedRate(taskResChe, delay, period, TimeUnit.MILLISECONDS);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	System.out.println("Timer_ResCheFiServlet init immediately.(load-on-startup)");
//    	response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void destroy() {
		taskResChe.cancel();
	}

}
