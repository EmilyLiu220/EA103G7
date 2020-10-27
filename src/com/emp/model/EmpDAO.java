package com.emp.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmpDAO implements EmpDAO_interface {

private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_EMP = 
		"INSERT INTO EMPLOYEE (EMP_NO, EMP_PSW, EMP_NAME, EMP_STS) VALUES ('EMP'||LPAD(to_char(SEQ_EMP_NO.nextval), 4, '0'), '0000', ?, 1)";
	private static final String UPDATE_BY_EMP = 
		"UPDATE EMPLOYEE SET EMP_PSW = ?, EMP_NAME = ? WHERE EMP_NO = ?";
	private static final String UPDATE_BY_SV = 
		"UPDATE EMPLOYEE SET EMP_STS = ? WHERE EMP_NO = ?";
	private static final String GET_ONE_EMP = 
		"SELECT EMP_NO, EMP_PSW, EMP_NAME, EMP_STS FROM EMPLOYEE WHERE EMP_NO = ?";
	private static final String GET_ALL_EMP = 
		"SELECT EMP_NO, EMP_PSW, EMP_NAME, EMP_STS FROM EMPLOYEE ORDER BY EMP_STS DESC, EMP_NO ASC";
	
	@Override
	public Object insert(EmpVO empVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] generatedColumns = {"emp_no"};
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_EMP, generatedColumns);
			
			pstmt.setString(1, empVO.getEmp_name());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			String emp_no = rs.getString(1);
			
			empVO.setEmp_no(emp_no);
			
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_BY_EMP);
			
			pstmt.setString(1, empVO.getEmp_psw());
			pstmt.setString(2, empVO.getEmp_name());
			pstmt.setString(3, empVO.getEmp_no());
			
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
	public void updateBySv(EmpVO empVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_BY_SV);
			
			pstmt.setInt(1, empVO.getEmp_sts());
			pstmt.setString(2, empVO.getEmp_no());
			
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
	public EmpVO findByEmp_no(String emp_no) {
		
		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
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
			
			con = ds.getConnection();
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
	
}