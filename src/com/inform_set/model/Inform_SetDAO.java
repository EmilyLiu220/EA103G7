package com.inform_set.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Inform_SetDAO implements Inform_SetDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_IS_STMT = "INSERT INTO INFORM_SET (IS_NO,EMP_NO,IS_CONT,IS_DATE) VALUES ('IS'||LPAD(to_char(SEQ_IS_NO.nextval), 4, '0'), ?, ?, ?)"; 
	private static final String UPDATE_IS_STMT = "UPDATE INFORM_SET SET EMP_NO=?, IS_CONT=?, IS_DATE=? WHERE IS_NO=?";
	private static final String DELETE_IS_STMT = "DELETE FROM INFORM_SET WHERE IS_NO=?"; 
	private static final String GET_BY_ISNO = "SELECT IS_NO, IS_CONT, IS_DATE, EMP_NO FROM INFORM_SET WHERE IS_NO=?"; 
	private static final String GET_BY_EMPNO = "SELECT IS_NO, IS_CONT, IS_DATE, EMP_NO FROM INFORM_SET WHERE EMP_NO=? ORDER BY IS_NO DESC"; 
	private static final String GET_BY_VAGUE = "SELECT IS_NO, IS_CONT, IS_DATE, EMP_NO FROM INFORM_SET WHERE IS_CONT LIKE ? ORDER BY IS_NO DESC"; 
	private static final String GET_BY_DATE = "SELECT IS_NO, IS_CONT, IS_DATE, EMP_NO FROM INFORM_SET WHERE IS_DATE BETWEEN to_date(?,'yyyy-mm-dd') AND to_date(?,'yyyy-mm-dd') ORDER BY IS_NO DESC";
	private static String Get_Complex = "SELECT IS_NO, IS_CONT, IS_DATE, EMP_NO FROM INFORM_SET WHERE 1=1";
	private static final String GET_ALL = "SELECT IS_NO, IS_CONT, IS_DATE, EMP_NO FROM INFORM_SET ORDER BY IS_NO DESC"; 
	
	@Override
	public Inform_SetVO insert(Inform_SetVO inform_setVO) {
		Inform_SetVO isVO = new Inform_SetVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_IS_STMT);
			pstmt.setString(1, inform_setVO.getEmp_no());
			pstmt.setString(2, inform_setVO.getIs_cont()); 
			pstmt.setDate(3, inform_setVO.getIs_date());
			int check = pstmt.executeUpdate();
			if(check!=0) {
				stmt = con.createStatement();
				rs = stmt.executeQuery(GET_ALL);
				rs.next();
				isVO.setIs_no(rs.getString("IS_NO"));
				isVO.setIs_cont(rs.getString("IS_CONT"));
				isVO.setIs_date(rs.getDate("IS_DATE"));
				isVO.setEmp_no(rs.getString("EMP_NO"));
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
		return isVO;
	}

	@Override
	public void update(Inform_SetVO inform_setVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_IS_STMT);
			pstmt.setString(1, inform_setVO.getEmp_no());
			pstmt.setString(2, inform_setVO.getIs_cont());
			pstmt.setDate(3, inform_setVO.getIs_date());
			pstmt.setString(4, inform_setVO.getIs_no());
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
	public void delete(String is_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_IS_STMT);
			pstmt.setString(1, is_no);
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
	public Inform_SetVO findByIsNo(String is_no) {
		Inform_SetVO inform_setVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_ISNO);
			pstmt.setString(1, is_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				inform_setVO = new Inform_SetVO();
				inform_setVO.setIs_no(rs.getString("IS_NO"));
				inform_setVO.setIs_cont(rs.getString("IS_CONT"));
				inform_setVO.setIs_date(rs.getDate("IS_DATE"));
				inform_setVO.setEmp_no(rs.getString("EMP_NO"));
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
		return inform_setVO;
	}

	@Override
	public List<Inform_SetVO> findByEmpNo(String emp_no) {
		List<Inform_SetVO> list = new ArrayList<Inform_SetVO>();
		Inform_SetVO inform_setVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_EMPNO);
			pstmt.setString(1, emp_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				inform_setVO = new Inform_SetVO();
				inform_setVO.setIs_no(rs.getString("IS_NO"));
				inform_setVO.setIs_cont(rs.getString("IS_CONT"));
				inform_setVO.setIs_date(rs.getDate("IS_DATE"));
				inform_setVO.setEmp_no(rs.getString("EMP_NO"));
				list.add(inform_setVO);
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
	public List<Inform_SetVO> findByIsCont(String is_cont) {
		List<Inform_SetVO> list = new ArrayList<Inform_SetVO>();
		Inform_SetVO inform_setVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_VAGUE);
			pstmt.setString(1, '%'+is_cont+'%');
			rs = pstmt.executeQuery();
			while (rs.next()) {
				inform_setVO = new Inform_SetVO();
				inform_setVO.setIs_no(rs.getString("IS_NO"));
				inform_setVO.setIs_cont(rs.getString("IS_CONT"));
				inform_setVO.setIs_date(rs.getDate("IS_DATE"));
				inform_setVO.setEmp_no(rs.getString("EMP_NO"));
				list.add(inform_setVO);
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
	public List<Inform_SetVO> findByDate(String startDate, String stopDate) {
		List<Inform_SetVO> list = new ArrayList<Inform_SetVO>();
		Inform_SetVO inform_setVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_DATE);
			pstmt.setString(1, startDate);
			pstmt.setString(2, stopDate);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				inform_setVO = new Inform_SetVO();
				inform_setVO.setIs_no(rs.getString("IS_NO"));
				inform_setVO.setIs_cont(rs.getString("IS_CONT"));
				inform_setVO.setIs_date(rs.getDate("IS_DATE"));
				inform_setVO.setEmp_no(rs.getString("EMP_NO"));
				list.add(inform_setVO);
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
	public List<Inform_SetVO> findByComplex( String is_no, String emp_no, String[] is_cont, String startDate, String stopDate ) {
		List<Inform_SetVO> list = new ArrayList<Inform_SetVO>();
		Inform_SetVO inform_setVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 取得參數，並把從前方傳來的參數條件加入 sql 語法中 ( 若無傳入參數則等同於 list all )
		StringBuilder builder = new StringBuilder();
		List<String> params = new ArrayList<String>();
		builder.append(Get_Complex);
		if(is_no != null && !is_no.isEmpty()) {
			builder.append(" AND IS_NO=?");
			params.add("IS_NO,"+is_no);
		}
		if(emp_no != null && !emp_no.isEmpty()) {
			builder.append(" AND EMP_NO=?");
			params.add("EMP_NO,"+emp_no);
		}
		if(is_cont != null && is_cont.length!=0 ) { // 迴圈放入
			builder.append(" AND (");
			for(int i=0; i<is_cont.length; i++) {
				if(i == (is_cont.length-1)) {
					builder.append(" IS_CONT LIKE ? )");
					params.add("IS_CONT,"+is_cont[i]);
				} else {
					builder.append(" IS_CONT LIKE ? OR");
					params.add("IS_CONT,"+is_cont[i]);
				}
			}
		}
		if(startDate != null && !startDate.isEmpty()) {
			builder.append(" AND IS_DATE BETWEEN to_date(?,'yyyy-mm-dd')");
			params.add("STARTDATE,"+startDate);
		}
		if(stopDate != null && !stopDate.isEmpty()) {
			builder.append(" AND to_date(?,'yyyy-mm-dd') ORDER BY IS_NO DESC");
			params.add("STOPDATE,"+stopDate);
		}
		// 開始取得連線
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(builder.toString());
			// 將 params 切割並 set 進相對應的 ? 中
			for(int i=0; i<params.size(); i++) {
				String str = params.get(i);
				String[] arr = str.split(",");
				if("IS_CONT".equals(arr[0])) {
					pstmt.setString(i+1, '%'+arr[1]+'%');
				} else {
					pstmt.setString(i+1, arr[1]);
				}
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				inform_setVO = new Inform_SetVO();
				inform_setVO.setIs_no(rs.getString("IS_NO"));
				inform_setVO.setIs_cont(rs.getString("IS_CONT"));
				inform_setVO.setIs_date(rs.getDate("IS_DATE"));
				inform_setVO.setEmp_no(rs.getString("EMP_NO"));
				list.add(inform_setVO);
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
	public List<Inform_SetVO> getAll() {
		List<Inform_SetVO> list = new ArrayList<Inform_SetVO>();
		Inform_SetVO inform_setVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				inform_setVO = new Inform_SetVO();
				inform_setVO.setIs_no(rs.getString("IS_NO"));
				inform_setVO.setIs_cont(rs.getString("IS_CONT"));
				inform_setVO.setIs_date(rs.getDate("IS_DATE"));
				inform_setVO.setEmp_no(rs.getString("EMP_NO"));
				list.add(inform_setVO);
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
