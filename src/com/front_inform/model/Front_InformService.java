package com.front_inform.model;

import java.util.List;

public class Front_InformService {
	
	private Front_InformDAO_interface dao;
	
	public Front_InformService() {
		dao = new Front_InformDAO();
	}
	
	public void addNormalFI(String mem_no, String info_cont) { 
		// 由 res_order 的 controller，在符合的動作區塊內去 new 這支 Service 並使用此方法
		// info_cont="訂餐成功！您尚未付款，點選前往結帳" 或 "您已成功付款，點選查看訂單明細" 或 "您的餐點已完成，請至本餐廳取餐" 或 "您的訂單已取消"
		dao.insertInfo(mem_no, info_cont);
	}
	
	public void addROFI(String mem_no, String res_no, String info_cont) {
		// 由 meal_order 的 controller，在符合的動作區塊內去 new 這支 Service 並使用此方法
		// info_cont = "訂位成功，點選查看訂位明細" 或 "您的訂位已取消"
		dao.insertFromRO(mem_no, res_no, info_cont);
	}
	
	public void addRCFI(String res_no) {
		// 寫一支額外的排程器，每一個小時掃一次 DB 的訂位訂單表格時間，若時間 +6 為該次掃 DB 的時間，
		// 則 new Front_InformService 並 call 此方法去新增並發出通知
		dao.insertResCheInform(res_no);
	}
	
	public void updateSts(Integer info_sts, String info_no) {
		Front_InformVO front_informVO = new Front_InformVO();
		front_informVO.setInfo_sts(info_sts);
		front_informVO.setInfo_no(info_no);
		dao.updateSts(front_informVO);
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
	
//	// 取得通知狀態的列舉子
//	public InfoSts[] getInfoStsAll() {
//		return InfoSts.values();
//	}
//	
//	// 得到該狀態的中文內容
//	public String getInfoStsMsgAll(Integer info_sts) {
//		return InfoSts.findByInfoSts(info_sts).getInfoStsMsg();
//	}
//
//	// 取得通知讀取狀態的列舉子
//	public ReadSts[] getReadStsAll() {
//		return ReadSts.values();
//	}
//	
//	// 得到該狀態的中文內容
//	public String getReadStsMsgAll(Integer read_sts) {
//		return ReadSts.findByReadSts(read_sts).getReadStsMsg();
//	}
	
}
