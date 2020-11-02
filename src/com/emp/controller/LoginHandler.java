package com.emp.controller;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;

public class LoginHandler {
	
	protected Integer allowUser(String account, String password) {
		
		EmpVO empVO = new EmpVO();
		EmpService empSvc = new EmpService();
		
		empVO = empSvc.getOneEmp(account);
		
		if (empVO == null) {
			return 0; // 帳密錯誤
		}
				
		if ((empVO.getEmp_no()).equals(account) && (empVO.getEmp_psw()).equals(password) && empVO.getEmp_sts() == 1) {
		    return 2; // 登入成功
		} else if ((empVO.getEmp_no()).equals(account) && (empVO.getEmp_psw()).equals(password) && empVO.getEmp_sts() == 0) {
		    return 1; // 已離職無法登入
		} else {
			return 0; // 帳密錯誤
		}
		
	}
	
}
