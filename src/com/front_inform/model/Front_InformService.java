package com.front_inform.model;

import java.util.ArrayList;
import java.util.List;
import com.front_inform.webSocket.Front_InformWS;
import com.res_detail.model.*;
import com.res_order.model.*;

public class Front_InformService {
	
	private Front_InformDAO_interface dao;
	
	public Front_InformService() {
		dao = new Front_InformDAO();
	}
	
	public Front_InformVO getByInfoNo(String info_no) {
		return dao.findByFiNo(info_no);
	}
	
	public void addNormalFI(String mem_no, String info_cont) { 
		// 由 res_order 的 controller，在符合的動作區塊內去 new 這支 Service 並使用此方法
		// info_cont="訂餐成功！您尚未付款，點選前往結帳" 或 "您已成功付款，點選查看訂單明細" 或 "您的餐點已完成，請至本餐廳取餐" 或 "您的訂單已取消"
		List<Front_InformVO> fiVOs = new ArrayList<Front_InformVO>();
		Front_InformVO fiVO = dao.insertInfo(mem_no, info_cont);
		if(fiVO!=null) {
			Front_InformWS fiWS = new Front_InformWS();
			fiVOs.add(fiVO);
			fiWS.onMessage(fiVOs);
		}
	}
	
	public void addROFI(String mem_no, String res_no, String info_cont) {
		// 由 meal_order 的 controller，在符合的動作區塊內去 new 這支 Service 並使用此方法
		// info_cont = "訂位成功，點選查看訂位明細" 或 "您的訂位已取消"
		List<Front_InformVO> fiVOs = new ArrayList<Front_InformVO>();
		Front_InformVO fiVO = dao.insertFromRO(mem_no, res_no, info_cont);
		if(fiVO!=null) {
			Front_InformWS fiWS = new Front_InformWS();
			fiVOs.add(fiVO);
			fiWS.onMessage(fiVOs);
		}
	}
	
	public void addRCFI(String res_no) {
		// 寫一支額外的排程器，每天 0900 對 RES_ORDER 抓取當日訂位訂單
		// new Front_InformService 並 call 此方法去新增並發出通知
		List<Front_InformVO> fiVOs = new ArrayList<Front_InformVO>();
		Front_InformVO fiVO = dao.insertResCheInform(res_no);
		if(fiVO!=null) { // WebSocket 需要的前檯通知推播內容
			Front_InformWS fiWS = new Front_InformWS();
			fiVOs.add(fiVO);
			fiWS.onMessage(fiVOs);
		}
		// 取得該 ResOrderVO 及其所有原值
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
		seats_noList.toArray(seats_no);
		// 發送當日訂位確認通知後必須修改 Info_Sts 為 1 (已發送未確認)
		resOrderSvc.updateResOrder(res_no, resOrderVO.getMeal_order_no(), resOrderVO.getMem_no(),
				resOrderVO.getEmp_no(), resOrderVO.getRes_date(), resOrderVO.getPeople(), resOrderVO.getTime_peri_no(),
				new Integer(1), resOrderVO.getSeat_sts(), seats_no);
		// 起一個 thread，若 1 小時後沒有回覆則取消訂單
		
	}
	
	public void addISToAll(String is_no) {
		List<Front_InformVO> fiVOs = dao.insertManyIs(is_no);
		if(!fiVOs.isEmpty()) {
			Front_InformWS fiWS = new Front_InformWS();
			fiWS.onMessage(fiVOs);
		}
	}
	
	public boolean updateSts(Integer info_sts, String info_no) {
		Front_InformVO fiVO = new Front_InformVO();
		fiVO.setInfo_sts(info_sts);
		fiVO.setInfo_no(info_no);
		int check = dao.updateSts(fiVO);
		if(check>0) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<Front_InformVO> getMyInform(String mem_no){
		return dao.findByMemNo(mem_no);
	}
	
	public boolean updateReadSts(String mem_no) {
		int check = dao.updateReadSts(mem_no);
		if(check>0) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<Front_InformVO> getAllInform() {
		return dao.findAll();
	}
	
}
