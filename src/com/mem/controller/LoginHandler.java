package com.mem.controller;

import com.mem.model.MemService;
import com.mem.model.MemVO;

public class LoginHandler {
	
	protected boolean allowUser(String account, String password) {
		
		MemVO memVO = new MemVO();
		MemService memSvc = new MemService();
		
		memVO = memSvc.login(account);
		
		if (memVO == null) {
			return false;
		}
				
		if ((memVO.getMem_act()).equals(account) && (memVO.getMem_psw()).equals(password) && memVO.getMem_sts() == 1) {
		    return true;
		} else {
		    return false;
		}
		
	}
	
}
