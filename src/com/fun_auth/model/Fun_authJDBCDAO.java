package com.fun_auth.model;

import java.sql.*;
import java.util.*;

public class Fun_authJDBCDAO implements Fun_authDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";
	
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1, fun_authVO.getFun_name());
			pstmt.setString(2, fun_authVO.getFun_des());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, fun_authVO.getFun_name());
			pstmt.setString(2, fun_authVO.getFun_des());
			pstmt.setString(3, fun_authVO.getFun_no());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void delete(String fun_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, fun_no);
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fun_authVO = new Fun_authVO();
				fun_authVO.setFun_no(rs.getString("FUN_NO"));
				fun_authVO.setFun_name(rs.getString("FUN_NAME"));
				fun_authVO.setFun_des(rs.getString("FUN_DES"));
				list.add(fun_authVO);
				
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE);
			
			pstmt.setString(1, fun_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				fun_authVO = new Fun_authVO();
				fun_authVO.setFun_no(rs.getString("FUN_NO"));
				fun_authVO.setFun_name(rs.getString("FUN_NAME"));
				fun_authVO.setFun_des(rs.getString("FUN_DES"));
				
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	
public static void main(String[] args) {
		
		Fun_authJDBCDAO dao = new Fun_authJDBCDAO();
		
		// 新增
//		Fun_authVO fun_authVO1 = new Fun_authVO();
//		fun_authVO1.setFun_name("器材管理");
//		fun_authVO1.setFun_des("可以進入器材管理系統");
//		dao.insert(fun_authVO1);
//		System.out.println("*新增成功*");
		
		// 修改
//		Fun_authVO fun_authVO2 = new Fun_authVO();
//		fun_authVO2.setFun_no("FA0029");
//		fun_authVO2.setFun_name("維修管理");
//		fun_authVO2.setFun_des("可以進入維修管理系統");
//		dao.update(fun_authVO2);
//		System.out.println("*修改成功*");
		
		// 刪除
//		dao.delete("FA0029");
//		System.out.println("*刪除成功*");
		
		// 查詢
//		List<Fun_authVO> list = dao.getAll();
//		for (Fun_authVO aFun_auth : list) {
//			System.out.print(aFun_auth.getFun_no() + ",");
//			System.out.print(aFun_auth.getFun_name() + ",");
//			System.out.print(aFun_auth.getFun_des());
//			System.out.println();
//		}
//		System.out.println("*查詢成功*");
		
		// 查詢
		Fun_authVO fun_authVO3 = dao.findByFun_no("FA0005");
		System.out.println(fun_authVO3.getFun_no());
		System.out.println(fun_authVO3.getFun_name());
		System.out.println(fun_authVO3.getFun_des());
		System.out.println("*查詢成功*");
		
	}
	
}
