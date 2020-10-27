package com.emp.model;

import java.sql.*;
import java.util.*;

public class EmpJDBCDAO implements EmpDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";
	
	private static final String INSERT_EMP = 
		"INSERT INTO EMPLOYEE (EMP_NO, EMP_PSW, EMP_NAME, EMP_STS) VALUES ('EMP'||LPAD(to_char(SEQ_EMP_NO.nextval), 4, '0'), '0000', ?, 1)";
	private static final String UPDATE_BY_EMP = 
		"UPDATE EMPLOYEE SET EMP_PSW = ?, EMP_NAME = ? WHERE EMP_NO = ?";
	private static final String UPDATE_BY_SV = 
		"UPDATE EMPLOYEE SET EMP_STS = ? WHERE EMP_NO = ?";
	private static final String GET_ONE_EMP = 
		"SELECT EMP_NO, EMP_PSW, EMP_NAME, EMP_STS FROM EMPLOYEE WHERE EMP_NO = ?";
	private static final String GET_ALL_EMP = 
		"SELECT EMP_NO, EMP_PSW, EMP_NAME, EMP_STS FROM EMPLOYEE ORDER BY EMP_NO";
	
	@Override
	public Object insert(EmpVO empVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] generatedColumns = {"emp_no"};
		
		try {
			
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				
				pstmt = con.prepareStatement(INSERT_EMP, generatedColumns);
				
				pstmt.setString(1, empVO.getEmp_name());
				
				pstmt.executeUpdate();
				
				ResultSet rs = pstmt.getGeneratedKeys();
				rs.next();
				String emp_no = rs.getString(1);
				
				empVO.setEmp_no(emp_no);
				
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
			return empVO;
	}
	
	@Override
	public void updateByEmp(EmpVO empVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_BY_EMP);
			
			pstmt.setString(1, empVO.getEmp_psw());
			pstmt.setString(2, empVO.getEmp_name());
			pstmt.setString(3, empVO.getEmp_no());
			
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
	public void updateBySv(EmpVO empVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_BY_SV);
			
			pstmt.setInt(1, empVO.getEmp_sts());
			pstmt.setString(2, empVO.getEmp_no());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public EmpVO findByEmp_no(String emp_no) {
		
		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_EMP);
			
			pstmt.setString(1, emp_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				empVO = new EmpVO();
				empVO.setEmp_no(rs.getString("EMP_NO"));
				empVO.setEmp_psw(rs.getString("EMP_PSW"));
				empVO.setEmp_name(rs.getString("EMP_NAME"));
				empVO.setEmp_sts(rs.getInt("EMP_STS"));
				
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
		
		return empVO;
	}
	
	@Override
	public List<EmpVO> getAll() {

		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_EMP);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				empVO = new EmpVO();
				empVO.setEmp_no(rs.getString("EMP_NO"));
				empVO.setEmp_psw(rs.getString("EMP_PSW"));
				empVO.setEmp_name(rs.getString("EMP_NAME"));
				empVO.setEmp_sts(rs.getInt("EMP_STS"));
				list.add(empVO);
				
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
		
		EmpJDBCDAO dao = new EmpJDBCDAO();
		
		// �s�W
//		EmpVO empVO1 = new EmpVO();
//		empVO1.setEmp_name("���l��");
//		dao.insert(empVO1);
//		System.out.println("*�s�W���\*");
		
		// �ק���
//		EmpVO empVO2 = new EmpVO();
//		empVO2.setEmp_no("EMP0014");
//		empVO2.setEmp_psw("1234");
//		empVO2.setEmp_name("�觱ޱ");
//		dao.updateByEmp(empVO2);
//		System.out.println("*�ק��Ʀ��\*");
		
		// �ק窱�A
//		EmpVO empVO3 = new EmpVO();
//		empVO3.setEmp_no("EMP0014");
//		empVO3.setEmp_sts(0);
//		dao.updateBySv(empVO3);
//		System.out.println("*�ק窱�A���\*");
		
		// �d��
//		EmpVO empVO4 = dao.findByEmp_no("EMP0001");
//		System.out.println(empVO4.getEmp_no());
//		System.out.println(empVO4.getEmp_psw());
//		System.out.println(empVO4.getEmp_name());
//		System.out.println(empVO4.getEmp_sts());
//		System.out.println("*�d�ߦ��\*");
		
		// �d��
//		List<EmpVO> list = dao.getAll();
//		for (EmpVO aEmp : list) {
//			System.out.println(aEmp.getEmp_no());
//			System.out.println(aEmp.getEmp_psw());
//			System.out.println(aEmp.getEmp_name());
//			System.out.println(aEmp.getEmp_sts());
//		}
//		System.out.println("*�d�ߦ��\*");
		
	}
	
}
