package com.time_peri.model;

/*
 * JavaBean的三要素
 * 1.要是public的類別，且有一個預設建構子
 * 2.存取屬性需靠 getXXX() & setXXX()方法
 * 3.需要implements java.io.Serializable，成為可序列化之類別
 */
public class TimePeriVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String time_peri_no; // PK
	private String time_start;
	private Integer time_dur;
	private String time_end;
	private Integer isdel_sts;
	private Integer time_sts;

	public String getTime_peri_no() {
		return time_peri_no;
	}

	public void setTime_peri_no(String time_peri_no) {
		this.time_peri_no = time_peri_no;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public Integer getTime_dur() {
		return time_dur;
	}

	public void setTime_dur(Integer time_dur) {
		this.time_dur = time_dur;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public Integer getIsdel_sts() {
		return isdel_sts;
	}

	public void setIsdel_sts(Integer isdel_sts) {
		this.isdel_sts = isdel_sts;
	}

	public Integer getTime_sts() {
		return time_sts;
	}

	public void setTime_sts(Integer time_sts) {
		this.time_sts = time_sts;
	}

	@Override
	public String toString() {
		return "{time_peri_no:" + time_peri_no + ", time_start:" + time_start.replaceFirst(":", "-") + ", time_dur:" + time_dur + ", time_end:"
				+ time_end.replaceFirst(":", "-") + ", isdel_sts:" + isdel_sts + ", time_sts:" + time_sts + "}";
	}
}
