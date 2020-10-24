package com.front_inform.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Front_InformDAO implements Front_InformDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	// 新增訂餐相關或停權通知 (一般不須回應的通知)
	private static final String INFO_STMT = "INSERT INTO FRONT_INFORM (INFO_NO,MEM_NO,INFO_CONT,INFO_STS) VALUES ('FI'||LPAD(to_char(SEQ_INFO_NO.nextval), 4, '0'), ?, ?, 0)"; 
	
	// 新增訂位相關通知
	private static final String RO_STMT = "INSERT INTO FRONT_INFORM (INFO_NO,MEM_NO,RES_NO,INFO_CONT,INFO_STS) VALUES ('FI'||LPAD(to_char(SEQ_INFO_NO.nextval), 4, '0'), ?, ?, ?, 0)";
	
	// 新增訂位確認通知
	private static final String GET_TIME = "SELECT RO.MEM_NO, TP.TIME_START FROM RES_ORDER RO JOIN TIME_PERI TP ON RO.TIME_PERI_NO = TP.TIME_PERI_NO WHERE RO.RES_NO = ?";
	// '您預約今日 '+ ? +' 用餐，是否確認今日用餐？'
	private static final String RCHECK_STMT = "INSERT INTO FRONT_INFORM (INFO_NO,MEM_NO,RES_NO,INFO_CONT,INFO_STS) VALUES ('FI'||LPAD(to_char(SEQ_INFO_NO.nextval), 4, '0'), ?, ?, ?, 2)"; // 訂位確認
	
	// 更新通知狀態
	private static final String UPDATE_INFO_STS_STMT = "UPDATE FRONT_INFORM SET INFO_STS=? WHERE INFO_NO=?";
	
	// 該名會員登入後，取得其個人的所有通知(降冪)
	private static final String GET_STMT_BY_MEM = "SELECT INFO_NO, RES_NO, INFO_CONT, INFO_DATE, INFO_STS, READ_STS FROM FRONT_INFORM WHERE MEM_NO=? ORDER BY INFO_NO DESC";

	// 打開通知小鈴鐺後，更新未讀的通知為已讀
	private static final String UPDATE_READ_STS = "UPDATE FRONT_INFORM SET READ_STS='1' WHERE MEM_NO=?";
	
	// 取得所有會員的各種通知
	private static final String GET_ALL_STMT = "SELECT INFO_NO, MEM_NO, RES_NO, INFO_CONT, INFO_DATE, INFO_STS, READ_STS FROM FRONT_INFORM ORDER BY INFO_NO DESC";
	
	@Override
	public void insertInfo(String mem_no, String info_cont) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INFO_STMT);
			pstmt.setString(1, mem_no);
			// '提醒您，您將於 1 分鐘後被停權'、'提醒您，因您多次訂餐付款但皆未至本餐廳取餐，您的訂餐功能將於 3 天後恢復'、
			// '提醒您，因您多次訂位且多次點按確認當天用餐按鈕，但皆未至本餐廳用餐，您的訂位功能將於 3 天後恢復'、'提醒您，因您有多則評價被檢舉成功，您的評價功能將於 14 天後恢復'、
			// '提醒您，因您檢舉多則評價，但評價內容多數未達不當言論之標準，您的檢舉功能將於 7 天後恢復'
			// or '訂餐成功！您尚未付款，點選前往結帳'、'您已成功付款，點選查看訂單明細'、'您的訂單已取消'、'您的餐點已完成，請至本餐廳取餐'
			pstmt.setString(2, info_cont); 
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void insertFromRO(String mem_no, String res_no, String info_cont) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(RO_STMT);
			pstmt.setString(1, mem_no);
			pstmt.setString(2, res_no);
			// '訂位成功，點選查看訂位明細'、'您的訂位已取消'
			pstmt.setString(3, info_cont);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void insertResCheInform(String res_no) {
		Connection con = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;		
		PreparedStatement pstmt2 = null;
		try {
			con = ds.getConnection();
			pstmt1 = con.prepareStatement(GET_TIME);
			pstmt1.setString(1, res_no);
			rs = pstmt1.executeQuery();
			while(rs.next()) {
				// 新增通知
				pstmt2 = con.prepareStatement(RCHECK_STMT);
				pstmt2.setString(1, rs.getString("MEM_NO"));
				pstmt2.setString(2, res_no);
				pstmt2.setString(3, "您預約今日 "+ rs.getString("TIME_START") + " 用餐，是否確認今日用餐？");
				pstmt2.executeUpdate();
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt1 != null) {
				try {
					pstmt1.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void updateSts(Front_InformVO front_informVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_INFO_STS_STMT);
			pstmt.setInt(1, front_informVO.getInfo_sts());
			pstmt.setString(2, front_informVO.getInfo_no());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<Front_InformVO> findByMemNo(String mem_no) {
		List<Front_InformVO> list = new ArrayList<Front_InformVO>();
		Front_InformVO front_informVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STMT_BY_MEM);
			pstmt.setString(1, mem_no);			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				front_informVO = new Front_InformVO();
				front_informVO.setInfo_no(rs.getString("INFO_NO"));
				front_informVO.setRes_no(rs.getString("RES_NO"));
				front_informVO.setInfo_cont(rs.getString("INFO_CONT"));
				front_informVO.setInfo_date(rs.getDate("INFO_DATE"));
				front_informVO.setInfo_sts(rs.getInt("INFO_STS"));
				front_informVO.setRead_sts(rs.getInt("READ_STS"));
				list.add(front_informVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void updateReadSts(String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_READ_STS);
			pstmt.setString(1, mem_no);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<Front_InformVO> findAll() {
		List<Front_InformVO> list = new ArrayList<Front_InformVO>();
		Front_InformVO front_informVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				front_informVO = new Front_InformVO();
				front_informVO.setInfo_no(rs.getString("INFO_NO"));
				front_informVO.setMem_no(rs.getString("MEM_NO"));
				front_informVO.setRes_no(rs.getString("RES_NO"));
				front_informVO.setInfo_cont(rs.getString("INFO_CONT"));
				front_informVO.setInfo_date(rs.getDate("INFO_DATE"));
				front_informVO.setInfo_sts(rs.getInt("INFO_STS"));
				front_informVO.setRead_sts(rs.getInt("READ_STS"));
				list.add(front_informVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}