package com.emp.controller;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;

public class LoginHandler {
	
	protected boolean allowUser(String account, String password) {
		
		EmpVO empVO = new EmpVO();
		EmpService empSvc = new EmpService();
		
		empVO = empSvc.getOneEmp(account);
		
		if (empVO == null) {
			return false;
		}
				
		if ((empVO.getEmp_no()).equals(account) && (empVO.getEmp_psw()).equals(password) && empVO.getEmp_sts() == 1) {
		    return true;
		} else {
		    return false;
		}
		
	}
	
}
