package com.front_inform.timer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimerTask;
import com.front_inform.model.*;
import com.inform_set.model.*;

// 每日掃 Inform_Set table，Query 當日通知設定，insert 至 Front_Inform 表格中(To 所有 Member)
public class Timer_IsToFi extends TimerTask {
	
	private Front_InformService fiSvc = new Front_InformService();
	private Inform_SetService isSvc = new Inform_SetService();
	
	public Timer_IsToFi() { 
		super(); 
	} 
	
	@Override 
	public void run() { 
		java.util.Date now = new java.util.Date(); // 取得當日日期
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String today = fmt.format(now);
		List<Inform_SetVO> isVOs = isSvc.getIsByDate(today, today); // 取得當日通知 VO
		for( Inform_SetVO isVO : isVOs ) { // 其實每天只會有一則
			fiSvc.addISToAll(isVO.getIs_no());
		}
		isVOs.clear();
	}
	
}

