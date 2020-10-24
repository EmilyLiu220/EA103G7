package com.fun_auth.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.emp.model.EmpVO;

public class Fun_authDAO implements Fun_authDAO_interface {

	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT = 
		"INSERT INTO FUN_AUTH (FUN_NO, FUN_NAME, FUN_DES) VALUES (SEQ_FUN_NO.nextval, ?, ?)";
	private static final String UPDATE = 
		"UPDATE FUN_AUTH SET FUN_NAME = ?, FUN_DES = ? WHERE FUN_NO = ?";
	private static final String DELETE = 
		"DELETE FROM FUN_AUTH WHERE FUN_NO = ?";
	private static final String GET_ALL = 
		"SELECT FUN_NO, FUN_NAME, FUN_DES FROM FUN_AUTH ORDER BY FUN_NO";
	private static final String GET_ONE = 
		"SELECT FUN_NO, FUN_NAME, FUN_DES FROM FUN_AUTH WHERE FUN_NO = ?";
	
	@Override
	public void insert(Fun_authVO fun_authVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1, fun_authVO.getFun_name());
			pstmt.setString(2, fun_authVO.getFun_des());
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(Fun_authVO fun_authVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, fun_authVO.getFun_name());
			pstmt.setString(2, fun_authVO.getFun_des());
			pstmt.setString(3, fun_authVO.getFun_no());
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(String funNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, funNo);
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<Fun_authVO> getAll() {
		
		List<Fun_authVO> list = new ArrayList<Fun_authVO>();
		Fun_authVO fun_authVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fun_authVO = new Fun_authVO();
				fun_authVO.setFun_no(rs.getString("FUN_NO"));
				fun_authVO.setFun_name(rs.getString("FUN_NAME"));
				fun_authVO.setFun_des(rs.getString("FUN_DES"));
				list.add(fun_authVO);
				
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public Fun_authVO findByFun_no(String fun_no) {
		
		Fun_authVO fun_authVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);
			
			pstmt.setString(1, fun_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fun_authVO = new Fun_authVO();
				fun_authVO.setFun_no(rs.getString("FUN_NO"));
				fun_authVO.setFun_name(rs.getString("FUN_NAME"));
				fun_authVO.setFun_des(rs.getString("FUN_DES"));
				
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		
		return fun_authVO;
	}
	
}