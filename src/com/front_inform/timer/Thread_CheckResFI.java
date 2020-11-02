package com.front_inform.timer;

import java.util.ArrayList;
import java.util.List;

import com.front_inform.model.*;
import com.front_inform.webSocket.Front_InformWS;
import com.res_order.model.*;

public class Thread_CheckResFI implements Runnable {
	
    private Front_InformVO fiVO;

    public Thread_CheckResFI(Front_InformVO fiVO) {
    	super();
    	this.fiVO = fiVO;
    }
	
	@Override
	public void run() {
		try {
			// 起來後先讓它睡個 8s...QQ
            Thread.sleep(8000);
            // 8s 過去了，這則通知依舊沒有被回覆
            
            Front_InformService fiSvc = new Front_InformService();
            
            if(fiSvc.getByInfoNo(fiVO.getInfo_no()).getInfo_sts()==2) { // 需要回覆的通知未確認
            	
            	String res_no = fiSvc.getByInfoNo(fiVO.getInfo_no()).getRes_no();
				ResOrderService resOrderSvc = new ResOrderService();
				ResOrderVO resOrderVO = resOrderSvc.getOneResOrder(res_no);
				
				// 修改 Front_Inform 的 INFO_STS，若修改成功則會再去修改 Res_Order 的 INFO_STS
				boolean check = fiSvc.updateSts(3, fiVO.getInfo_no());
				
				if(check) {
					// 若這則通知沒有被回覆，且在 DB 已被改為 INFO_STS==3
					List<Front_InformVO> fiVOs = new ArrayList<Front_InformVO>();
					fiVOs.add(fiSvc.getByInfoNo(fiVO.getInfo_no()));
					Front_InformWS fiWS = new Front_InformWS();
					fiWS.onMessage(fiVOs); // 回傳這筆被更動的通知
					
					// 並加入告知訂位取消的通知
					fiSvc.addROFI(fiVO.getMem_no(), res_no, "您的訂位已取消");
					
					// 發送當日訂位確認通知後必須修改 Info_Sts 為 3 (會員已取消)
					resOrderSvc.updateResOrder(res_no, resOrderVO.getMeal_order_no(), resOrderVO.getMem_no(),
							resOrderVO.getEmp_no(), resOrderVO.getRes_date(), resOrderVO.getPeople(), resOrderVO.getTime_peri_no(),
							new Integer(3), resOrderVO.getSeat_sts(), null);
				}

            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}
