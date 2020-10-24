package com.seat.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SeatDAO implements SeatDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO SEAT (SEAT_NO ,SEAT_OBJ_NO ,SEAT_NAME ,SEAT_ISDEL, SEAT_X, SEAT_Y, SEAT_L, SEAT_W, SEAT_F) VALUES ('SEN'||LPAD(SEQ_SEAT_NO.NEXTVAL,4,0), ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT SEAT_NO ,SEAT_OBJ_NO ,SEAT_NAME ,SEAT_ISDEL ,SEAT_X ,SEAT_Y ,SEAT_L ,SEAT_W ,SEAT_F FROM SEAT ORDER BY SEAT_NO";
	private static final String GET_ONE_STMT = "SELECT SEAT_OBJ_NO ,SEAT_NAME ,SEAT_ISDEL ,SEAT_X ,SEAT_Y ,SEAT_L ,SEAT_W ,SEAT_F FROM SEAT WHERE SEAT_NO = ?";
	private static final String UPDATE = "UPDATE SEAT SET SEAT_OBJ_NO=? ,SEAT_NAME=? ,SEAT_ISDEL=? ,SEAT_X=? ,SEAT_Y=? ,SEAT_L=? ,SEAT_W=? ,SEAT_F=? WHERE SEAT_NO = ?";

	@Override
	public void insert(SeatVO seatVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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
}
