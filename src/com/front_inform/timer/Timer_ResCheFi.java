package com.front_inform.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import com.front_inform.model.*;
import com.res_order.model.*;

// 每日 0900 掃 Res_Order table，Query 當日 res_ordery 資料，insert 至 Front_Inform 表格中
public class Timer_ResCheFi extends TimerTask {
	
	private Front_InformService fiSvc = new Front_InformService();
	private ResOrderService roSvc = new ResOrderService();
	
	public Timer_ResCheFi() {
		super();
	}
	
	@Override
	public void run() {
		java.util.Date now = new java.util.Date(); // 取得當日日期
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String today = fmt.format(now);
		List<ResOrderVO> resoVOs = roSvc.getByResDate(today);
		for( ResOrderVO resoVO: resoVOs ) { // 一一發送通知
			fiSvc.addRCFI(resoVO.getRes_no());
		}
	}
	
	public Calendar getEarliestDate(Calendar currentDate, int hourOfDay, 
			int minuteOfHour, int secondOfMinite) {
		//計算當前時間的 DAY_OF_WEEK, HOUR_OF_DAY, MINUTE,SECOND 等各個欄位值
		int currentDayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK);
		int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
		int currentMinute = currentDate.get(Calendar.MINUTE);
		int currentSecond = currentDate.get(Calendar.SECOND);
		//如果輸入條件中的 dayOfWeek 小於當前日期的 dayOfWeek,則 DAY_OF_WEEK 需要推遲一天
		boolean dayLater = false;
		if (hourOfDay < currentHour) {
			dayLater = true;
		} else if (hourOfDay == currentHour) {
			// 當輸入條件與當前日期的 hourOfDay 相等時，如果輸入條件中的
			// minuteOfHour 小於當前日期的 currentMinute，則 DAY_OF_WEEK 需要推遲一天
			if (minuteOfHour < currentMinute) {
				dayLater = true;
			} else if (minuteOfHour == currentMinute) {
				// 當輸入條件與當前日期的hourOfDay, minuteOfHour相等時，
				// 如果輸入條件中的secondOfMinite小於當前日期的
				// currentSecond，則DAY_OF_WEEK需要推遲一天
				if (secondOfMinite < currentSecond) {
					dayLater = true;
				}
			}
		}
		if (dayLater) {
			//設定當前日期中的 DAY_OF_WEEK 為當前日推遲一天
			currentDate.set(Calendar.DAY_OF_WEEK, currentDayOfWeek+1);
		}
		// 設定當前日期中的 HOUR_OF_DAY, MINUTE, SECOND 為輸入條件中的值
		currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
		currentDate.set(Calendar.MINUTE, minuteOfHour);
		currentDate.set(Calendar.SECOND, secondOfMinite);
		return currentDate;
	}
	
}
