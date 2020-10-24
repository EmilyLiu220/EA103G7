package com.emp_auth.model;

import java.sql.*;
import java.util.*;

public class Emp_authJDBCDAO implements Emp_authDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";
	
	private static final String INSERT = 
		"INSERT INTO EMP_AUTH (FUN_NO, EMP_NO) VALUES (?, ?)";
	private static final String DELETE = 
		"DELETE FROM EMP_AUTH WHERE EMP_NO = ?";
	private static final String GET_ONE = 
		"SELECT FUN_NO, EMP_NO FROM EMP_AUTH WHERE EMP_NO = ?";
	private static final String GET_ALL = 
		"SELECT FUN_NO, EMP_NO FROM EMP_AUTH ORDER BY EMP_NO, FUN_NO";
	
	@Override
	public void insert(Emp_authVO emp_authVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1, emp_authVO.getFun_no());
			pstmt.setString(2, emp_authVO.getEmp_no());
			
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
	public void delete(String emp_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, emp_no);
			
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
	public List<Emp_authVO> findByEmp_no(String emp_no) {
		
		List<Emp_authVO> list = new ArrayList<Emp_authVO>();
		Emp_authVO emp_authVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE);
			
			pstmt.setString(1, emp_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				emp_authVO = new Emp_authVO();
				emp_authVO.setFun_no(rs.getString("FUN_NO"));
				list.add(emp_authVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<Emp_authVO> getAll() {
		
		List<Emp_authVO> list = new ArrayList<Emp_authVO>();
		Emp_authVO emp_authVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				emp_authVO = new Emp_authVO();
				emp_authVO.setEmp_no(rs.getString("EMP_NO"));
				emp_authVO.setFun_no(rs.getString("FUN_NO"));
				list.add(emp_authVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	
	public static void main(String[] args) {
		
		Emp_authJDBCDAO dao = new Emp_authJDBCDAO();
		
		// �s�W
//		Emp_authVO emp_authVO1 = new Emp_authVO();
//		emp_authVO1.setFun_no("FA0026");
//		emp_authVO1.setEmp_no("EMP0001");
//		dao.insert(emp_authVO1);
//		System.out.println("*�s�W���\*");
		
		// �R���v��
//		dao.delete("EMP0002");
//		System.out.println("*�R�����\*");
		
		// �d�߳�@���u�v��
//		List<Emp_authVO> list1 = dao.findByEmp_no("EMP0001");
//		for (Emp_authVO aEmp_auth1 : list1) {
//			System.out.println(aEmp_auth1.getFun_no());
//		}
//		System.out.println("*�d�ߦ��\*");
		
		// �d�ߩҦ����u�v��
//		List<Emp_authVO> list2 = dao.getAll();
//		for (Emp_authVO aEmp_auth2 : list2) {
//			System.out.print(aEmp_auth2.getEmp_no() + ",");
//			System.out.print(aEmp_auth2.getFun_no());
//			System.out.println();
//		}
//		System.out.println("*�d�ߦ��\*");
		
	}
	
}
