package com.seat.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatJDBCDAO implements SeatDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO SEAT (SEAT_NO ,SEAT_OBJ_NO ,SEAT_NAME ,SEAT_ISDEL, SEAT_X, SEAT_Y, SEAT_L, SEAT_W, SEAT_F) VALUES ('SEN'||LPAD(SEQ_SEAT_NO.NEXTVAL,4,0), ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT SEAT_NO ,SEAT_OBJ_NO ,SEAT_NAME ,SEAT_ISDEL ,SEAT_X ,SEAT_Y ,SEAT_L ,SEAT_W ,SEAT_F FROM SEAT ORDER BY SEAT_NO";
	private static final String GET_ONE_STMT = "SELECT SEAT_OBJ_NO ,SEAT_NAME ,SEAT_ISDEL ,SEAT_X ,SEAT_Y ,SEAT_L ,SEAT_W ,SEAT_F FROM SEAT WHERE SEAT_NO = ?";
	private static final String UPDATE = "UPDATE SEAT SET SEAT_OBJ_NO=? ,SEAT_NAME=? ,SEAT_ISDEL=? ,SEAT_X=? ,SEAT_Y=? ,SEAT_L=? ,SEAT_W=? ,SEAT_F=? WHERE SEAT_NO = ?";

	@Override
	public void insert(SeatVO seatVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, seatVO.getSeat_obj_no());
			pstmt.setString(2, seatVO.getSeat_name());
			pstmt.setInt(3, new Integer(seatVO.getSeat_isdel()));
			pstmt.setDouble(4, new Double(seatVO.getSeat_x()));
			pstmt.setDouble(5, new Double(seatVO.getSeat_y()));
			pstmt.setInt(6, new Integer(seatVO.getSeat_l()));
			pstmt.setInt(7, new Integer(seatVO.getSeat_w()));
			pstmt.setInt(8, new Integer(seatVO.getSeat_f()));

			pstmt.executeUpdate();

//			System.out.println("Insert success");

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
	public void update(SeatVO seatVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, seatVO.getSeat_obj_no());
			pstmt.setString(2, seatVO.getSeat_name());
			pstmt.setInt(3, new Integer(seatVO.getSeat_isdel()));
			pstmt.setDouble(4, new Double(seatVO.getSeat_x()));
			pstmt.setDouble(5, new Double(seatVO.getSeat_y()));
			pstmt.setInt(6, new Integer(seatVO.getSeat_l()));
			pstmt.setInt(7, new Integer(seatVO.getSeat_w()));
			pstmt.setInt(8, new Integer(seatVO.getSeat_f()));
			pstmt.setString(9, seatVO.getSeat_no());

			pstmt.executeUpdate();

//			System.out.println("Update success");

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
	public SeatVO findByPrimaryKey(String seat_no) {
		SeatVO seatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, seat_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				seatVO = new SeatVO();

				seatVO.setSeat_no(seat_no);
				seatVO.setSeat_obj_no(rs.getString("SEAT_OBJ_NO"));
				seatVO.setSeat_name(rs.getString("SEAT_NAME"));
				seatVO.setSeat_isdel(new Integer(rs.getInt("SEAT_ISDEL")));
				seatVO.setSeat_x(new Double(rs.getInt("SEAT_X")));
				seatVO.setSeat_y(new Double(rs.getInt("SEAT_Y")));
				seatVO.setSeat_l(new Integer(rs.getInt("SEAT_L")));
				seatVO.setSeat_w(new Integer(rs.getInt("SEAT_W")));
				seatVO.setSeat_f(new Integer(rs.getInt("SEAT_F")));
			}

//			System.out.println("FindByPrimaryKey success");

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return seatVO;
	}

	@Override
	public List<SeatVO> getAll() {
		List<SeatVO> list = new ArrayList<SeatVO>();
		SeatVO seatVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (new Integer(rs.getInt("SEAT_ISDEL")) == 0) {
					seatVO = new SeatVO();
					seatVO.setSeat_no(rs.getString("SEAT_NO"));
					seatVO.setSeat_obj_no(rs.getString("SEAT_OBJ_NO"));
					seatVO.setSeat_name(rs.getString("SEAT_NAME"));
					seatVO.setSeat_isdel(new Integer(rs.getInt("SEAT_ISDEL")));
					seatVO.setSeat_x(new Double(rs.getInt("SEAT_X")));
					seatVO.setSeat_y(new Double(rs.getInt("SEAT_Y")));
					seatVO.setSeat_l(new Integer(rs.getInt("SEAT_L")));
					seatVO.setSeat_w(new Integer(rs.getInt("SEAT_W")));
					seatVO.setSeat_f(new Integer(rs.getInt("SEAT_F")));

					list.add(seatVO); // Store the row in the list
				}
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// bytesArrayToByteObject error
		} catch (NullPointerException npe) {
			throw new RuntimeException("A bytesArrayToByteObject error occured. " + npe.getMessage());
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

//	public static void main(String[] args) {
//
//		SeatJDBCDAO dao = new SeatJDBCDAO();
//		// insert
//		SeatVO seatVO = new SeatVO();
//		seatVO.setSeat_obj_no("SO0001");
//		seatVO.setSeat_name("壹桌");
//		seatVO.setSeat_isdel(new Integer(0));
//		seatVO.setSeat_x(new Integer(123));
//		seatVO.setSeat_y(new Integer(131));
//		seatVO.setSeat_l(new Integer(20));
//		seatVO.setSeat_w(new Integer(30));
//		seatVO.setSeat_f(new Integer(1));
//		dao.insert(seatVO);
//
//		// update
//		SeatVO seatVO1 = new SeatVO();
//		seatVO1.setSeat_no("SEN0031");
//		seatVO1.setSeat_obj_no("SO0001");
//		seatVO1.setSeat_name("貳桌");
//		seatVO1.setSeat_isdel(new Integer(0));
//		seatVO1.setSeat_x(new Integer(123));
//		seatVO1.setSeat_y(new Integer(131));
//		seatVO1.setSeat_l(new Integer(20));
//		seatVO1.setSeat_w(new Integer(30));
//		seatVO1.setSeat_f(new Integer(1));
//		dao.update(seatVO1);
//
//		// get one seat obj
//		SeatVO seatVO2 = dao.findByPrimaryKey("SEN0008");
//		System.out.print(seatVO2.getSeat_no() + ",\t");
//		System.out.print(seatVO2.getSeat_obj_no() + ",\t");
//		System.out.print(seatVO2.getSeat_name() + ",\t");
//		System.out.print(seatVO2.getSeat_isdel() + ",\t");
//		System.out.print(seatVO2.getSeat_x() + ",\t");
//		System.out.print(seatVO2.getSeat_y() + ",\t");
//		System.out.print(seatVO2.getSeat_l() + ",\t");
//		System.out.print(seatVO2.getSeat_w() + ",\t");
//		System.out.print(seatVO2.getSeat_f() + ",\t");
//
//		// select all
//		List<SeatVO> list = dao.getAll();
//		for (SeatVO seatVO3 : list) {
//			System.out.print(seatVO3.getSeat_no() + ",\t");
//			System.out.print(seatVO3.getSeat_obj_no() + ",\t");
//			System.out.print(seatVO3.getSeat_name() + ",\t");
//			System.out.print(seatVO3.getSeat_isdel() + ",\t");
//			System.out.print(seatVO3.getSeat_x() + ",\t");
//			System.out.print(seatVO3.getSeat_y() + ",\t");
//			System.out.print(seatVO3.getSeat_l() + ",\t");
//			System.out.print(seatVO3.getSeat_w() + ",\t");
//			System.out.print(seatVO3.getSeat_f() + ",\t");
//			System.out.println();
//		}
//	}

}
