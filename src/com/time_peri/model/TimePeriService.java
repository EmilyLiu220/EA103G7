package com.time_peri.model;

import java.util.List;

public class TimePeriService {
	private TimePeriDAO_interface dao;

	public TimePeriService() {
//		dao = new TimePeriJDBCDAO();
		dao = new TimePeriDAO();
	}

	public TimePeriVO addTimePeri(String time_start, Integer time_dur, String time_end, Integer isdel_sts,
			Integer time_sts) {

		TimePeriVO timePeriVO = new TimePeriVO();

		timePeriVO.setTime_start(time_start);;
		timePeriVO.setTime_dur(time_dur);
		timePeriVO.setTime_end(time_end);
		timePeriVO.setIsdel_sts(isdel_sts);
		timePeriVO.setTime_sts(time_sts);

		dao.insert(timePeriVO);

		return timePeriVO;
	}

	public TimePeriVO updateTimePeri(String time_peri_no, String time_start, Integer time_dur, String time_end, Integer isdel_sts,
			Integer time_sts) {

		TimePeriVO timePeriVO = new TimePeriVO();
		
		timePeriVO.setTime_peri_no(time_peri_no);
		timePeriVO.setTime_start(time_start);;
		timePeriVO.setTime_dur(time_dur);
		timePeriVO.setTime_end(time_end);
		timePeriVO.setIsdel_sts(isdel_sts);
		timePeriVO.setTime_sts(time_sts);

		dao.update(timePeriVO);

		return timePeriVO;
	}
	
	
	public TimePeriVO getOneTimePeri(String time_peri_no) {
		return dao.findByPrimaryKey(time_peri_no);
	}

	public List<TimePeriVO> getAll() {
		return dao.getAll();
	}
}
