package com.wait_seat.model;

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

public class Wait_seatDAO implements Wait_seatDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO WAIT_SEAT (WAIT_SEAT_NO,MEM_NO,N_MEM_NAME,PHONE_M)VALUES('WAIT_SEAT'||LPAD(SEQ_WAIT_SEAT_NO.NEXTVAL,4,0),?,?,?)";
	private static final String DELETE_WAIT_SEAT = "DELETE FROM WAIT_SEAT WHERE WAIT_SEAT_NO = ?";
	private static final String GET_ALL_STMT = "SELECT WAIT_SEAT_NO,MEM_NO,N_MEM_NAME,PHONE_M FROM WAIT_SEAT ORDER BY WAIT_SEAT_NO,MEM_NO";
	private static final String GET_ONE_STMT = "SELECT WAIT_SEAT_NO,MEM_NO,N_MEM_NAME,PHONE_M FROM WAIT_SEAT WHERE WAIT_SEAT_NO = ?";
	private static final String UPDATE = 
			"UPDATE WAIT_SEAT SET MEM_NO=?,N_MEM_NAME=?,PHONE_M=? WHERE WAIT_SEAT_NO = ?";
	@Override
	public void insert(Wait_seatVO wait_seatVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, wait_seatVO.getMem_no());
			pstmt.setString(2, wait_seatVO.getN_mem_name());
			pstmt.setString(3, wait_seatVO.getPhone_m());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(Wait_seatVO wait_seatVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, wait_seatVO.getMem_no());
			pstmt.setString(2, wait_seatVO.getN_mem_name());
			pstmt.setString(3, wait_seatVO.getPhone_m());
			pstmt.setString(4, wait_seatVO.getWait_seat_no());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String wait_seat_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_WAIT_SEAT);
			pstmt.setString(1, wait_seat_no);
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public Wait_seatVO findByPrimaryKey(String wait_seat_no) {

		Wait_seatVO wait_seatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, wait_seat_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				wait_seatVO = new Wait_seatVO();
				wait_seatVO.setWait_seat_no(rs.getString("WAIT_SEAT_NO"));
				wait_seatVO.setMem_no(rs.getString("MEM_NO"));
				wait_seatVO.setN_mem_name(rs.getString("N_MEM_NAME"));
				wait_seatVO.setPhone_m(rs.getString("PHONE_M"));
			}
			// Handle any driver errors
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
		return wait_seatVO;
	}

	@Override
	public List<Wait_seatVO> getAll() {
		List<Wait_seatVO> list = new ArrayList<Wait_seatVO>();
		Wait_seatVO wait_seatVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				wait_seatVO = new Wait_seatVO();
				wait_seatVO.setWait_seat_no(rs.getString("WAIT_SEAT_NO"));
				wait_seatVO.setMem_no(rs.getString("MEM_NO"));
				wait_seatVO.setN_mem_name(rs.getString("N_MEM_NAME"));
				wait_seatVO.setPhone_m(rs.getString("PHONE_M"));
				list.add(wait_seatVO);
			}

			// Handle any driver errors
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
}
