package com.mem.model;

import java.sql.Date;
import java.util.List;

public class MemService {

	private MemDAO_interface dao;
	
	public MemService() {
		dao = new MemDAO();
	}
	
	public MemVO addMem(String mem_name, String mem_act, String mem_psw, String mem_gen,
			Date mem_bir, String mem_tel, String mem_adrs, String mem_mail) {
		
		MemVO memVO = new MemVO();
		
		memVO.setMem_name(mem_name);
		memVO.setMem_act(mem_act);
		memVO.setMem_psw(mem_psw);
		memVO.setMem_gen(mem_gen);
		memVO.setMem_bir(mem_bir);
		memVO.setMem_tel(mem_tel);
		memVO.setMem_adrs(mem_adrs);
		memVO.setMem_mail(mem_mail);
		dao.insert(memVO);
		
		return memVO;
	}
	
	public MemVO updateByMem(String mem_name, String mem_psw, String mem_gen, Date mem_bir,
			String mem_tel, String mem_adrs, String mem_mail, String mem_no) {
		
		MemVO memVO = new MemVO();
		
		memVO.setMem_name(mem_name);
		memVO.setMem_psw(mem_psw);
		memVO.setMem_gen(mem_gen);
		memVO.setMem_bir(mem_bir);
		memVO.setMem_tel(mem_tel);
		memVO.setMem_adrs(mem_adrs);
		memVO.setMem_mail(mem_mail);
		memVO.setMem_no(mem_no);
		dao.updateByMem(memVO);
		
		return memVO;
	}
	
	public MemVO updateByEmp(Integer mem_bns, Integer mem_od_m, Integer mem_od_r,
			Integer mem_review, Integer mem_repo, Integer mem_sts, String mem_no) {
		
		MemVO memVO = new MemVO();
		
		memVO.setMem_bns(mem_bns);
		memVO.setMem_od_m(mem_od_m);
		memVO.setMem_od_r(mem_od_r);
		memVO.setMem_review(mem_review);
		memVO.setMem_repo(mem_repo);
		memVO.setMem_sts(mem_sts);
		memVO.setMem_no(mem_no);
		dao.updateByEmp(memVO);
		
		return memVO;
	}
	
	public MemVO forgetPsw(String mem_psw, String mem_mail) {
		
		MemVO memVO = new MemVO();
		
		memVO.setMem_psw(mem_psw);
		memVO.setMem_mail(mem_mail);
		dao.forgetPsw(memVO);
		
		return memVO;
	}
	
	public MemVO login(String mem_act) {
		return dao.login(mem_act);
	}
	
	public MemVO getOneMem(String mem_no) {
		return dao.findByMem_no(mem_no);
	}
	
	public List<MemVO> getAll() {
		return dao.getAll();
	}
	
}
