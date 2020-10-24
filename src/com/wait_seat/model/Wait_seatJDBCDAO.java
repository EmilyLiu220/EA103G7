package com.wait_seat.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Wait_seatJDBCDAO implements Wait_seatDAO_interface{
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";
	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, wait_seatVO.getMem_no());
			pstmt.setString(2, wait_seatVO.getN_mem_name());
			pstmt.setString(3, wait_seatVO.getPhone_m());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, wait_seatVO.getMem_no());
			pstmt.setString(2, wait_seatVO.getN_mem_name());
			pstmt.setString(3, wait_seatVO.getPhone_m());
			pstmt.setString(4, wait_seatVO.getWait_seat_no());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String wait_seat_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_WAIT_SEAT);
			pstmt.setString(1, wait_seat_no);
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public Wait_seatVO findByPrimaryKey(String wait_seat_no) {

		Wait_seatVO wait_seatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	
	public static void main(String[] args) {
		//新增
		Wait_seatJDBCDAO dao = new Wait_seatJDBCDAO();
		
		Wait_seatVO wait_seatVO1 = new Wait_seatVO();
		wait_seatVO1.setMem_no("MEM0001");
		wait_seatVO1.setN_mem_name("111");
		wait_seatVO1.setPhone_m("0912345678");
		
		dao.insert(wait_seatVO1);
		
		// 修改
		Wait_seatVO wait_seatVO2 = new Wait_seatVO();
		wait_seatVO2.setWait_seat_no("WAIT_SEAT0001");;
		wait_seatVO2.setMem_no("MEM0001");
		wait_seatVO2.setN_mem_name("張三");
		wait_seatVO2.setPhone_m("0988777666");
		dao.update(wait_seatVO2);
		
		// 刪除
		dao.delete("WAIT_SEAT0011");
		
		// 查詢
		Wait_seatVO wait_seatVO3 = dao.findByPrimaryKey("WAIT_SEAT0006");
		System.out.print("Wait_seat_no=" + wait_seatVO3.getWait_seat_no() + ",");
		System.out.print("Mem_no=" + wait_seatVO3.getMem_no() + ",");
		System.out.print("N_mem_name=" + wait_seatVO3.getN_mem_name() + ",");
		System.out.print("Phone_m=" + wait_seatVO3.getPhone_m());
		System.out.println();
		System.out.println("---------------------");
		
		// 查詢
		List<Wait_seatVO> list = dao.getAll();
		for (Wait_seatVO aWait_seatVO : list) {
			System.out.print("Wait_seat_no=" + aWait_seatVO.getWait_seat_no() + ",");
			System.out.print("Mem_no=" + aWait_seatVO.getMem_no() + ",");
			System.out.print("N_mem_name=" + aWait_seatVO.getN_mem_name() + ",");
			System.out.print("Phone_m=" + aWait_seatVO.getPhone_m());
			System.out.println();
		}
	}
}
