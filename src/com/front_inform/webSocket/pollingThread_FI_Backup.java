package com.front_inform.webSocket;

import java.util.ArrayList;
import java.util.List;

import com.front_inform.model.*;

public class pollingThread_FI_Backup implements Runnable {
	
    private Integer count;
    private Integer new_count;
    private boolean stopMe = true;  
    
    public void stopMe() {  
        stopMe = false;  
    } 
	
	@Override
	public void run() {
		Front_InformDAO fiDao = new Front_InformDAO();
		List<Front_InformVO> fiVOs = new ArrayList<Front_InformVO>();
		// 查詢原始資料筆數
		count = fiDao.countData();
		try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Front_InformWS fiWS = new Front_InformWS();
        while(stopMe){
        	// 查詢最新資料筆數
        	new_count = fiDao.countData();
        	// 若有新資料則進入 WS 流程
            if(new_count!=count){
            	for(Front_InformVO fiVO : fiDao.findNew(count)) {
            		fiVOs.add(fiVO);
            	}
            	fiWS.onMessage(fiVOs);
                count = new_count;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
}
