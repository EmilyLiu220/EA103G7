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
//			"INSERT INTO WAIT_SEAT (WAIT_SEAT_NO,MEM_NO,N_MEM_NAME,PHONE_M,DELAY,wait_n)VALUES('WAIT_SEAT'||LPAD(SEQ_WAIT_SEAT_NO.NEXTVAL,4,0),?,?,?,?,SEQ_WAIT_SEAT2_NO.NEXTVAL)";
			"INSERT INTO WAIT_SEAT (WAIT_SEAT_NO,MEM_NO,N_MEM_NAME,PHONE_M,DELAY,wait_n) VALUES ('WAIT_SEAT'||LPAD(SEQ_WAIT_SEAT_NO.NEXTVAL,4,0),?,?,?,?,SEQ_WAIT_SEAT_NO.CURRVAL)";
	private static final String DELETE_WAIT_SEAT = "DELETE FROM WAIT_SEAT WHERE WAIT_SEAT_NO = ?";
	private static final String GET_ALL_STMT = "SELECT WAIT_SEAT_NO,MEM_NO,N_MEM_NAME,PHONE_M,DELAY,WAIT_N FROM WAIT_SEAT ORDER BY WAIT_N";
	private static final String GET_ONE_STMT = "SELECT WAIT_SEAT_NO,MEM_NO,N_MEM_NAME,PHONE_M,DELAY,WAIT_N FROM WAIT_SEAT WHERE WAIT_SEAT_NO = ?";
	private static final String UPDATE = 
			"UPDATE WAIT_SEAT SET MEM_NO=?,N_MEM_NAME=?,PHONE_M=?,DELAY=?,WAIT_N=? WHERE WAIT_SEAT_NO = ?";
	private static final String SUPER_UPDATE = 
			"UPDATE WAIT_SEAT SET MEM_NO=?,N_MEM_NAME=?,PHONE_M=?,delay=?,WAIT_SEAT_NO = ? WHERE WAIT_SEAT_NO = ?";
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
			pstmt.setDouble(4, wait_seatVO.getDelay());
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
			pstmt.setInt(4, wait_seatVO.getDelay());
			pstmt.setInt(5, wait_seatVO.getWait_n());
			pstmt.setString(6, wait_seatVO.getWait_seat_no());

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
	public void update2(Wait_seatVO wait_seatVO,String wait_seat_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SUPER_UPDATE);
			pstmt.setString(1, wait_seatVO.getMem_no());
			pstmt.setString(2, wait_seatVO.getN_mem_name());
			pstmt.setString(3, wait_seatVO.getPhone_m());
			pstmt.setDouble(4, wait_seatVO.getDelay());
			pstmt.setString(5, wait_seat_no);
			pstmt.setString(6, wait_seatVO.getWait_seat_no());

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
				wait_seatVO.setDelay(rs.getInt("DELAY"));
				wait_seatVO.setWait_n(rs.getInt("WAIT_N"));
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
	public Wait_seatVO getFirst() {
		Wait_seatVO wait_seatVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			rs.next();
			wait_seatVO = new Wait_seatVO();
			wait_seatVO.setWait_seat_no(rs.getString("WAIT_SEAT_NO"));
			wait_seatVO.setMem_no(rs.getString("MEM_NO"));
			wait_seatVO.setN_mem_name(rs.getString("N_MEM_NAME"));
			wait_seatVO.setPhone_m(rs.getString("PHONE_M"));
			wait_seatVO.setDelay(rs.getInt("DELAY"));
			wait_seatVO.setWait_n(rs.getInt("WAIT_N"));
			
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
				wait_seatVO.setDelay(rs.getInt("DELAY"));
				wait_seatVO.setWait_n(rs.getInt("WAIT_N"));
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
	
	@Override
	public List<Wait_seatVO> getAllForUser() {
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
				wait_seatVO.setPhone_m(rs.getString("PHONE_M").substring(0,7).replaceAll("[0-9]", "x")+rs.getString("PHONE_M").substring(7));
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
