package com.mem.controller;

import com.mem.model.MemService;
import com.mem.model.MemVO;

public class LoginHandler {
	
	protected Integer allowUser(String account, String password) {
		
		MemVO memVO = new MemVO();
		MemService memSvc = new MemService();
		
		memVO = memSvc.login(account);
		
		if (memVO == null) {
			return 0; // 帳密錯誤
		}
				
		if ((memVO.getMem_act()).equals(account) && (memVO.getMem_psw()).equals(password) && memVO.getMem_sts() == 1) {
		    return 2; // 登入成功
		} else if ((memVO.getMem_act()).equals(account) && (memVO.getMem_psw()).equals(password) && memVO.getMem_sts() == 0) {
		    return 1; // 已被停權無法登入
		} else {
			return 0; // 帳密錯誤
		}
		
	}
	
}
