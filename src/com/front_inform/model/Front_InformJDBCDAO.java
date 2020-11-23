package com.front_inform.model;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.inform_set.model.*;
import com.mem.model.*;

public class Front_InformJDBCDAO implements Front_InformDAO_interface {
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "EA103G7";
	private static final String PASSWORD = "123456";
	
	// 依據通知編號查詢通知
	private static final String GET_BY_FINO = "SELECT INFO_NO, MEM_NO, RES_NO, INFO_CONT, INFO_DATE, INFO_STS, READ_STS FROM FRONT_INFORM WHERE INFO_NO=?";
	// 新增訂餐相關或停權通知 (一般不須回應的通知)
	private static final String INFO_STMT = "INSERT INTO FRONT_INFORM (INFO_NO,MEM_NO,INFO_CONT,INFO_STS) VALUES ('FI'||LPAD(to_char(SEQ_INFO_NO.nextval), 4, '0'), ?, ?, 0)"; 
	// 新增訂位相關通知
	private static final String RO_STMT = "INSERT INTO FRONT_INFORM (INFO_NO,MEM_NO,RES_NO,INFO_CONT,INFO_STS) VALUES ('FI'||LPAD(to_char(SEQ_INFO_NO.nextval), 4, '0'), ?, ?, ?, 0)";
	// 新增訂位確認通知
	private static final String GET_TIME = "SELECT RO.MEM_NO, TP.TIME_START FROM RES_ORDER RO JOIN TIME_PERI TP ON RO.TIME_PERI_NO = TP.TIME_PERI_NO WHERE RO.RES_NO = ?";
	// '您預約今日 '+ ? +' 用餐，是否確認今日用餐？'
	private static final String RCHECK_STMT = "INSERT INTO FRONT_INFORM (INFO_NO,MEM_NO,RES_NO,INFO_CONT,INFO_STS) VALUES ('FI'||LPAD(to_char(SEQ_INFO_NO.nextval), 4, '0'), ?, ?, ?, 2)"; // 訂位確認
	// 大量新增 From Inform_Set to Front_Inform
	private static final String INSERT_IS_STMT = "INSERT INTO FRONT_INFORM (INFO_NO,MEM_NO,INFO_CONT,INFO_STS) VALUES ('FI'||LPAD(to_char(SEQ_INFO_NO.nextval), 4, '0'), ?, ?, 0)";
	// 更新通知狀態
	private static final String UPDATE_INFO_STS_STMT = "UPDATE FRONT_INFORM SET INFO_STS=? WHERE INFO_NO=?";
	// 該名會員登入後，取得其個人的所有通知(降冪)
	private static final String GET_STMT_BY_MEM = "SELECT INFO_NO, RES_NO, INFO_CONT, INFO_DATE, INFO_STS, READ_STS FROM FRONT_INFORM WHERE MEM_NO=? ORDER BY INFO_NO DESC";
	// 打開通知小鈴鐺後，更新未讀的通知為已讀
	private static final String UPDATE_READ_STS = "UPDATE FRONT_INFORM SET READ_STS='1' WHERE MEM_NO=?";
	// 取得所有會員的各種通知
	private static final String GET_ALL_STMT = "SELECT INFO_NO, MEM_NO, RES_NO, INFO_CONT, INFO_DATE, INFO_STS, READ_STS FROM FRONT_INFORM ORDER BY INFO_NO DESC";	
	// 取得所有通知筆數
	private static final String GET_ALL_COUNT = "SELECT COUNT(1) AS COUNT FROM FRONT_INFORM";
	// 取得所有會員的最新通知
	private static final String GET_NEW_STMT = "SELECT INFO_NO, MEM_NO, RES_NO, INFO_CONT, INFO_DATE, INFO_STS, READ_STS FROM FRONT_INFORM ORDER BY INFO_NO";	
	
	// 員工取得特殊通知
	private static String Get_Complex = "SELECT INFO_NO, MEM_NO, RES_NO, INFO_CONT, INFO_DATE, INFO_STS, READ_STS FROM FRONT_INFORM WHERE 1=1";
	
	@Override
	public Front_InformVO findByFiNo(String info_no) {
		Front_InformVO fiVO = new Front_InformVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_BY_FINO);
			pstmt.setString(1, info_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				fiVO.setInfo_no(rs.getString("INFO_NO"));
				fiVO.setMem_no(rs.getString("MEM_NO"));
				fiVO.setRes_no(rs.getString("RES_NO"));
				fiVO.setInfo_cont(rs.getString("INFO_CONT"));
				fiVO.setInfo_date(rs.getDate("INFO_DATE"));
				fiVO.setInfo_sts(rs.getInt("INFO_STS"));
				fiVO.setRead_sts(rs.getInt("READ_STS"));
			}
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
		return fiVO;
	}
	
	@Override
	public Front_InformVO insertInfo(String mem_no, String info_cont) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		Front_InformVO fiVO = new Front_InformVO();
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INFO_STMT);
			pstmt.setString(1, mem_no);
			// '提醒您，您將於 1 分鐘後被停權'、'提醒您，因您多次訂餐付款但皆未至本餐廳取餐，您的訂餐功能將於 3 天後恢復'、
			// '提醒您，因您多次訂位且多次點按確認當天用餐按鈕，但皆未至本餐廳用餐，您的訂位功能將於 3 天後恢復'、'提醒您，因您有多則評價被檢舉成功，您的評價功能將於 14 天後恢復'、
			// '提醒您，因您檢舉多則評價，但評價內容多數未達不當言論之標準，您的檢舉功能將於 7 天後恢復'
			// or '訂餐成功！您尚未付款，點選前往結帳'、'您已成功付款，點選查看訂單明細'、'您的餐點已完成，請至本餐廳取餐'、'您的訂單已取消'
			pstmt.setString(2, info_cont); 
			int insertNum = pstmt.executeUpdate();
			if(insertNum>0) {
				stmt = con.createStatement();
				rs = stmt.executeQuery(GET_ALL_STMT);
				rs.next();
				fiVO.setInfo_no(rs.getString("INFO_NO"));
				fiVO.setMem_no(rs.getString("MEM_NO"));
				fiVO.setRes_no(rs.getString("RES_NO"));
				fiVO.setInfo_cont(rs.getString("INFO_CONT"));
				fiVO.setInfo_date(rs.getDate("INFO_DATE"));
				fiVO.setInfo_sts(rs.getInt("INFO_STS"));
				fiVO.setRead_sts(rs.getInt("READ_STS"));
			}
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
			if (stmt != null) {
				try {
					stmt.close();
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
		return fiVO;
	}

	@Override
	public Front_InformVO insertFromRO(String mem_no, String res_no, String info_cont) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		Front_InformVO fiVO = new Front_InformVO();
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(RO_STMT);
			pstmt.setString(1, mem_no);
			pstmt.setString(2, res_no);
			// '訂位成功，點選查看訂位明細'、'您的訂位已取消'
			pstmt.setString(3, info_cont);
			int insertNum = pstmt.executeUpdate();
			if(insertNum>0) {
				stmt = con.createStatement();
				rs = stmt.executeQuery(GET_ALL_STMT);
				rs.next();
				fiVO.setInfo_no(rs.getString("INFO_NO"));
				fiVO.setMem_no(rs.getString("MEM_NO"));
				fiVO.setRes_no(rs.getString("RES_NO"));
				fiVO.setInfo_cont(rs.getString("INFO_CONT"));
				fiVO.setInfo_date(rs.getDate("INFO_DATE"));
				fiVO.setInfo_sts(rs.getInt("INFO_STS"));
				fiVO.setRead_sts(rs.getInt("READ_STS"));
			}
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
			if (stmt != null) {
				try {
					stmt.close();
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
		return fiVO;
	}

	@Override
	public Front_InformVO insertResCheInform(String res_no) { 
		// 寫一支額外的排程器檔，每天 9 點掃一次 DB 的訂位訂單表格時間
		// 則 new Front_InformService、call addRCFI() 方法去新增並發出通知
		Connection con = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;		
		PreparedStatement pstmt2 = null;
		Integer insertNum = 0;
		Statement stmt = null;
		ResultSet rs2 = null;
		Front_InformVO fiVO = new Front_InformVO();
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt1 = con.prepareStatement(GET_TIME);
			pstmt1.setString(1, res_no);
			rs1 = pstmt1.executeQuery();
			while(rs1.next()) {
				// 新增通知
				pstmt2 = con.prepareStatement(RCHECK_STMT);
				pstmt2.setString(1, rs1.getString("MEM_NO"));
				pstmt2.setString(2, res_no);
				pstmt2.setString(3, "您預約今日 "+ rs1.getString("TIME_START") + " 用餐，是否確認今日用餐？");
				insertNum += pstmt2.executeUpdate();
			}
			if(insertNum>0) {
				stmt = con.createStatement();
				rs2 = stmt.executeQuery(GET_ALL_STMT);
				rs2.next();
				fiVO.setInfo_no(rs2.getString("INFO_NO"));
				fiVO.setMem_no(rs2.getString("MEM_NO"));
				fiVO.setRes_no(rs2.getString("RES_NO"));
				fiVO.setInfo_cont(rs2.getString("INFO_CONT"));
				fiVO.setInfo_date(rs2.getDate("INFO_DATE"));
				fiVO.setInfo_sts(rs2.getInt("INFO_STS"));
				fiVO.setRead_sts(rs2.getInt("READ_STS"));
			}
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
		} finally {
			if (rs2 != null) {
				try {
					rs1.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (rs1 != null) {
				try {
					rs1.close();
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
		return fiVO;
	}
	
	@Override
	public List<Front_InformVO> insertManyIs(String is_no) {
		List<Front_InformVO> list = new ArrayList<Front_InformVO>();
		Front_InformVO fiVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_IS_STMT);
			// 取得該 is_no 的 is_cont
			Inform_SetService isSvc = new Inform_SetService();
			Inform_SetVO isVO = isSvc.getOneIs(is_no);
			// 取得所有 _mem_no
			MemService memSvc = new MemService();
			List<MemVO> memVOs = memSvc.getAll();
			for( MemVO memVO : memVOs ) {
				pstmt.setString(1, memVO.getMem_no());
				pstmt.setString(2, isVO.getIs_cont());
				pstmt.addBatch();
			}
			// 回傳 total 成功新增的筆數
			int[] successArr = pstmt.executeBatch();
			int success = successArr.length;
			if (success < 1) {
				throw new SQLException("sql:{} On failure.", pstmt.toString());
			}
	        if (success == memVOs.size()) {
	        	System.out.println("All insert succeeded!");
	        }
	        if (success > 0 && success < memVOs.size()){
	        	System.err.println("The number of successful { " + success + " }");
	        }
	        stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	        rs = stmt.executeQuery(GET_ALL_STMT);
	        for(int i=1 ; i<=success ; i++) {
	        	rs.absolute(i);
	        	fiVO = new Front_InformVO();
	        	fiVO.setInfo_no(rs.getString("INFO_NO"));
	        	fiVO.setMem_no(rs.getString("MEM_NO"));
	        	fiVO.setRes_no(rs.getString("RES_NO"));
	        	fiVO.setInfo_cont(rs.getString("INFO_CONT"));
	        	fiVO.setInfo_date(rs.getDate("INFO_DATE"));
	        	fiVO.setInfo_sts(rs.getInt("INFO_STS"));
	        	fiVO.setRead_sts(rs.getInt("READ_STS"));
	        	list.add(fiVO);
	        }
		} catch (BatchUpdateException  be) {
			throw new RuntimeException("A database error occured. "+ be.getUpdateCounts());
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
			if (stmt != null) {
				try {
					stmt.close();
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
	public Integer updateSts(Front_InformVO front_informVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Integer updateNum = 0;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_INFO_STS_STMT);
			pstmt.setInt(1, front_informVO.getInfo_sts());
			pstmt.setString(2, front_informVO.getInfo_no());
			updateNum += pstmt.executeUpdate();
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
		return updateNum;
	}

	@Override
	public List<Front_InformVO> findByMemNo(String mem_no) {
		List<Front_InformVO> list = new ArrayList<Front_InformVO>();
		Front_InformVO front_informVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
	public Integer updateReadSts(String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Integer updateNum = 0;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_READ_STS);
			pstmt.setString(1, mem_no);
			updateNum += pstmt.executeUpdate();
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
		return updateNum;
	}
	
	@Override
	public List<Front_InformVO> findAll() {
		List<Front_InformVO> list = new ArrayList<Front_InformVO>();
		Front_InformVO front_informVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
	public List<Front_InformVO> findByComplex(String mem_no, Integer info_sts, String startDate, String stopDate) {
		List<Front_InformVO> list = new ArrayList<Front_InformVO>();
		Front_InformVO front_informVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		List<String> params = new ArrayList<String>();
		builder.append(Get_Complex);
		if(mem_no != null && !mem_no.isEmpty()) {
			builder.append(" AND MEM_NO=?");
			params.add("MEM_NO," + mem_no);
		}
		if(info_sts != null && info_sts!=4 ) { // 4 代表未填此欄
			builder.append(" AND INFO_STS=?");
			params.add("INFO_STS," + info_sts);
		}
		if(startDate != null && !startDate.isEmpty()) {
			builder.append(" AND INFO_DATE BETWEEN to_date(?,'yyyy-mm-dd')");
			params.add("STARTDATE," + startDate);
		}
		if(stopDate != null && !stopDate.isEmpty()) {
			builder.append(" AND to_date(?,'yyyy-mm-dd')");
			params.add("STOPDATE," + stopDate);
		}
		builder.append(" ORDER BY INFO_NO DESC");
		// 開始取得連線
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(builder.toString());
			// 將 params 切割並 set 進相對應的 ? 中
			for(int i=0; i<params.size(); i++) {
				String str = params.get(i);
				String[] arr = str.split(",");
				pstmt.setString(i+1, arr[1]);
			}
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
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
	
	
	// 以下方法 just for backup
	@Override
	public Integer countData() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer count = 0 ;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_COUNT);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("COUNT");
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
		return count;
	}

	// 以下方法 just for backup
	@Override
	public List<Front_InformVO> findNew(Integer count) {
		List<Front_InformVO> list = new ArrayList<Front_InformVO>();
		Front_InformVO fiVO = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(GET_NEW_STMT);
			rs.absolute(count);
			while (rs.next()) {
				fiVO = new Front_InformVO();
				fiVO.setInfo_no(rs.getString("INFO_NO"));
				fiVO.setMem_no(rs.getString("MEM_NO"));
				fiVO.setRes_no(rs.getString("RES_NO"));
				fiVO.setInfo_cont(rs.getString("INFO_CONT"));
				fiVO.setInfo_date(rs.getDate("INFO_DATE"));
				fiVO.setInfo_sts(rs.getInt("INFO_STS"));
				fiVO.setRead_sts(rs.getInt("READ_STS"));
				list.add(fiVO);
			}
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
			if (stmt != null) {
				try {
					stmt.close();
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
	
	
	
	public static void main(String[] args) {
		
		Front_InformJDBCDAO dao = new Front_InformJDBCDAO();
		
//		// 依據 info_no 查詢通知
//		Front_InformVO fiVO = dao.findByFiNo("FI0004");
//		System.out.print(fiVO.getInfo_no() + ", ");
//		System.out.print(fiVO.getMem_no() + ", ");
//		System.out.print(fiVO.getRes_no() + ", ");
//		System.out.print(fiVO.getInfo_cont() + ", ");
//		System.out.print(fiVO.getInfo_date() + ", ");
//		System.out.print(fiVO.getInfo_sts() + ", ");
//		System.out.print(fiVO.getRead_sts());
//		System.out.println();
//		System.out.println("-----------------------------------------------------------------------------------");
		
//		// 新增一般不須回應的通知
//		dao.insertInfo("MEM0035", "訂餐成功！您尚未付款，點選前往結帳");
//		dao.insertInfo("MEM0025", "訂餐成功！您尚未付款，點選前往結帳");
//		dao.insertInfo("MEM0030", "訂餐成功！您尚未付款，點選前往結帳");
//		
//		// 新增訂位成功通知
//		dao.insertFromRO("MEM0050", "RESO0010", "訂位成功，點選查看訂位明細");
//		
//		// 新增須回覆的通知 insertResCheInform(String res_no)
//		dao.insertResCheInform("RESO0008");
//		
//		// 大量新增 Inform_Set into Front_Inform
//		System.out.println(dao.insertManyIs("IS0001"));
//		
//		// 修改通知狀態
//		Front_InformVO front_informVO2 = new Front_InformVO();
//		front_informVO2.setInfo_no("FI0054");
//		front_informVO2.setInfo_sts(3);
//		dao.updateSts(front_informVO2);
//		
//		// 查詢 by mem_no
//		List<Front_InformVO> list1 = dao.findByMemNo("MEM0032");
//		for(Front_InformVO afiVO : list1) {
//			System.out.print(afiVO.getInfo_cont() + ", ");
//			System.out.print(afiVO.getInfo_date());
//			System.out.println();
//		}
//		System.out.println("-----------------------------------------------------------------------------------");
//		
//		// 更新讀取狀態
//		dao.updateReadSts("FI0054");
//		
//		// 取得所有通知
//		List<Front_InformVO> list2 = dao.findAll();
//		for(Front_InformVO afiVO : list2) {
//			System.out.print(afiVO.getInfo_no() + ", ");
//			System.out.print(afiVO.getMem_no() + ", ");
//			System.out.print(afiVO.getRes_no() + ", ");
//			System.out.print(afiVO.getInfo_cont() + ", ");
//			System.out.print(afiVO.getInfo_date() + ", ");
//			System.out.print(afiVO.getInfo_sts() + ", ");
//			System.out.print(afiVO.getRead_sts());
//			System.out.println();
//		}
//		System.out.println("-----------------------------------------------------------------------------------");
//	
		// 取得特殊通知
		List<Front_InformVO> list3 = dao.findByComplex("MEM0032", 4, "2019-01-01", "2020-10-10");
		for(Front_InformVO afiVO : list3) {
			System.out.print(afiVO.getInfo_no() + ", ");
			System.out.print(afiVO.getMem_no() + ", ");
			System.out.print(afiVO.getRes_no() + ", ");
			System.out.print(afiVO.getInfo_cont() + ", ");
			System.out.print(afiVO.getInfo_date() + ", ");
			System.out.print(afiVO.getInfo_sts() + ", ");
			System.out.print(afiVO.getRead_sts());
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
//		// 查詢目前資料筆數
//		System.out.println(dao.countData());
//		System.out.println("-----------------------------------------------------------------------------------");
//		
//		// 取得所有新通知
//		List<Front_InformVO> list3 = dao.findNew(217);
//		for(Front_InformVO afiVO : list3) {
//			System.out.print(afiVO.getInfo_no() + ", ");
//			System.out.print(afiVO.getMem_no() + ", ");
//			System.out.print(afiVO.getRes_no() + ", ");
//			System.out.print(afiVO.getInfo_cont() + ", ");
//			System.out.print(afiVO.getInfo_date() + ", ");
//			System.out.print(afiVO.getInfo_sts() + ", ");
//			System.out.print(afiVO.getRead_sts());
//			System.out.println();
//		}
//		System.out.println("-----------------------------------------------------------------------------------");
	
	}

}
