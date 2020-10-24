package com.seat_obj.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SeatObjDAO implements SeatObjDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO SEAT_OBJ (SEAT_OBJ_NO,SEAT_OBJ, SEAT_OBJ_STS, SEAT_PEOPLE, SEAT_USE) VALUES ('SO'||LPAD(SEQ_SEAT_OBJ_NO.NEXTVAL,4,0), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT SEAT_OBJ_NO, SEAT_OBJ, SEAT_OBJ_STS, SEAT_PEOPLE, SEAT_USE FROM SEAT_OBJ ORDER BY SEAT_OBJ_NO";
	private static final String GET_ONE_STMT = "SELECT SEAT_OBJ, SEAT_OBJ_STS, SEAT_PEOPLE, SEAT_USE FROM SEAT_OBJ WHERE SEAT_OBJ_NO = ?";
	private static final String UPDATE = "UPDATE SEAT_OBJ SET SEAT_OBJ=?, SEAT_OBJ_STS=?, SEAT_PEOPLE=?, SEAT_USE=? WHERE SEAT_OBJ_NO = ?";

	@Override
	public void insert(SeatObjVO seatObjVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setBytes(1, seatObjVO.getSeat_obj());
			pstmt.setInt(2, new Integer(seatObjVO.getSeat_obj_sts()));
			pstmt.setInt(3, new Integer(seatObjVO.getSeat_people()));
			pstmt.setInt(4, new Integer(seatObjVO.getSeat_use()));
			
			pstmt.executeUpdate();

		} // Handle any SQL errors
		catch (SQLException se) {
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
	public void update(SeatObjVO seatObjVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			if (seatObjVO.getSeat_obj() != null) {
				pstmt.setBytes(1, seatObjVO.getSeat_obj());
			} else {
				pstmt.setBytes(1, null);
			}
			pstmt.setInt(2, new Integer(seatObjVO.getSeat_obj_sts()));
			pstmt.setInt(3, new Integer(seatObjVO.getSeat_people()));
			pstmt.setInt(4, new Integer(seatObjVO.getSeat_use()));
			pstmt.setString(5, seatObjVO.getSeat_obj_no());

			pstmt.executeUpdate();
		} // Handle any SQL errors
		catch (SQLException se) {
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
	public SeatObjVO findByPrimaryKey(String seat_obj_no) {

		SeatObjVO seatObjVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, seat_obj_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				seatObjVO = new SeatObjVO();
				seatObjVO.setSeat_obj_no(seat_obj_no);
				if (rs.getBytes("SEAT_OBJ") != null) {
					seatObjVO.setSeat_obj(rs.getBytes("SEAT_OBJ"));
				}
				seatObjVO.setSeat_obj_sts(new Integer(rs.getInt("SEAT_OBJ_STS")));
				seatObjVO.setSeat_people(new Integer(rs.getInt("SEAT_PEOPLE")));
				seatObjVO.setSeat_use(new Integer(rs.getInt("SEAT_USE")));
			}
		} // Handle any SQL errors
		catch (SQLException se) {
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
		return seatObjVO;
	}

	@Override
	public List<SeatObjVO> getAll() {
		List<SeatObjVO> list = new ArrayList<SeatObjVO>();
		SeatObjVO seatObjVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt("SEAT_OBJ_STS") == 0) {

					seatObjVO = new SeatObjVO();
					seatObjVO.setSeat_obj_no(rs.getString("SEAT_OBJ_NO"));
					// BLOB_EMPTY()
					if (rs.getBytes("SEAT_OBJ") != null) {
						seatObjVO.setSeat_obj(rs.getBytes("SEAT_OBJ"));
					}
					seatObjVO.setSeat_obj_sts(new Integer(rs.getInt("SEAT_OBJ_STS")));
					seatObjVO.setSeat_people(new Integer(rs.getInt("SEAT_PEOPLE")));
					seatObjVO.setSeat_use(new Integer(rs.getInt("SEAT_USE")));
					
					list.add(seatObjVO); // Store the row in the list
				}
			}

		} // Handle any SQL errors
		catch (SQLException se) {
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
