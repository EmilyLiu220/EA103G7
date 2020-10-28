package com.front_inform.model;

import java.util.ArrayList;
import java.util.List;
import com.front_inform.webSocket.Front_InformWS;

public class Front_InformService {
	
	private Front_InformDAO_interface dao;
	
	public Front_InformService() {
		dao = new Front_InformDAO();
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
		// 寫一支額外的排程器，每一個小時掃一次 DB 的訂位訂單表格時間，若時間 +6 為該次掃 DB 的時間，
		// 則 new Front_InformService 並 call 此方法去新增並發出通知
		List<Front_InformVO> fiVOs = new ArrayList<Front_InformVO>();
		Front_InformVO fiVO = dao.insertResCheInform(res_no);
		if(fiVO!=null) {
			Front_InformWS fiWS = new Front_InformWS();
			fiVOs.add(fiVO);
			fiWS.onMessage(fiVOs);
		}
	}
	
	public void addISToAll(String is_no) {
		// 寫一支額外的排程器，每一個小時掃一次 DB 的訂位訂單表格時間，若時間 +6 為該次掃 DB 的時間，
		// 則 new Front_InformService 並 call 此方法去新增並發出通知
		List<Front_InformVO> fiVOs = dao.insertManyIs(is_no);
		if(!fiVOs.isEmpty()) {
			Front_InformWS fiWS = new Front_InformWS();
			fiWS.onMessage(fiVOs);
		}
	}
	
	// 動作後如果狀態是取消 → 要 insert 新的通知('您的訂位已取消')到 Front_Inform 表格內
	public void updateSts(Integer info_sts, String info_no) {
		List<Front_InformVO> fiVOs = new ArrayList<Front_InformVO>();
		Front_InformVO fiVO = new Front_InformVO();
		fiVO.setInfo_sts(info_sts);
		fiVO.setInfo_no(info_no);
		dao.updateSts(fiVO);
	}
	
	public List<Front_InformVO> getMyInform(String mem_no){
		return dao.findByMemNo(mem_no);
	}
	
	public void updateReadSts(String mem_no) {
		dao.updateReadSts(mem_no);
	}
	
	public List<Front_InformVO> getAllInform() {
		return dao.findAll();
	}
	
}
