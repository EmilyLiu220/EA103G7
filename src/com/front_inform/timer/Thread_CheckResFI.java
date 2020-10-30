package com.front_inform.timer;

import java.util.ArrayList;
import java.util.List;

import com.front_inform.model.*;
import com.res_detail.model.*;
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
			// 起來後先讓它睡個一個小時...QQ
            Thread.sleep(3600000);
            // 一個小時過去了...
            // 發現居然這則通知依舊沒有被回覆！！！
            if(fiVO.getInfo_sts()==2) { // 需要回覆的通知未確認
            	Front_InformService fiSvc = new Front_InformService();
            	// 要來搞事情了~~~直接把這則通知的狀態改成已取消，然後去取消訂位 =..=
            	
            	// 取得該 ResOrderVO 及其所有原值...我好像一直在反覆做這個動作QQ
				String res_no = fiSvc.getByInfoNo(fiVO.getInfo_no()).getRes_no();
				ResOrderService resOrderSvc = new ResOrderService();
				ResOrderVO resOrderVO = resOrderSvc.getOneResOrder(res_no);
				// 透過 ResDetailService 取得 String[] seats_no 才能塞入 updateResOrder() 方法
				ResDetailService resDetailSvc = new ResDetailService();
				List<ResDetailVO> resDetailVOs = resDetailSvc.getAllResNO(res_no);
				List<String> seats_noList = new ArrayList<String>();
				for(ResDetailVO resDetailVO : resDetailVOs) {
					seats_noList.add(resDetailVO.getSeat_no());
				}
				String[] seats_no= new String[seats_noList.size()];
				seats_noList.toArray(seats_no); // 又是一個 toArray...QQ
				
				// 修改 Front_Inform 的 INFO_STS，若修改成功則會再去修改 Res_Order 的 INFO_STS
				boolean checked = fiSvc.updateSts(3, fiVO.getInfo_no());
				if(checked) {
					fiSvc.addROFI(fiVO.getMem_no(), res_no, "您的訂位已取消");
					// 發送當日訂位確認通知後必須修改 Info_Sts 為 3 (會員已取消)
					resOrderSvc.updateResOrder(res_no, resOrderVO.getMeal_order_no(), resOrderVO.getMem_no(),
							resOrderVO.getEmp_no(), resOrderVO.getRes_date(), resOrderVO.getPeople(), resOrderVO.getTime_peri_no(),
							new Integer(3), resOrderVO.getSeat_sts(), seats_no);
				}
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}