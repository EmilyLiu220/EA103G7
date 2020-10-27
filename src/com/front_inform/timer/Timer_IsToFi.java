package com.front_inform.timer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimerTask;
import com.front_inform.model.*;
import com.inform_set.model.*;
import com.mem.model.*;

// 每日掃 Inform_Set table，Query 當日通知設定，insert 至 Front_Inform 表格中(To 所有 Member)
public class Timer_IsToFi extends TimerTask {
	
	private Front_InformService fiSvc = new Front_InformService();
	private Inform_SetService isSvc = new Inform_SetService();
	private MemService memSvc = new MemService();
	
	public Timer_IsToFi() { 
		super(); 
	} 
	
	@Override 
	public void run() { 
		java.util.Date now = new java.util.Date(); // 取得當日日期
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String today = fmt.format(now);
		List<Inform_SetVO> isVOs = isSvc.getAll(); // 取得當日通知 VO
		List<MemVO> memVOs = memSvc.getAll(); // 取得所有會員 VO
		for( Inform_SetVO isVO : isVOs ) { // 其實每天只會有一則
			if(today.equals(isVO.getIs_date().toString())) {
				String is_cont = isVO.getIs_cont();
				for(MemVO memVO : memVOs) {
					String mem_no = memVO.getMem_no(); // 取得會員編號
					fiSvc.addNormalFI(mem_no, is_cont);
				}
			}
		}
	}
	
}

