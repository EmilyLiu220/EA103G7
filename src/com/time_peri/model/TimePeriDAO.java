package com.time_peri.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TimePeriDAO implements TimePeriDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO TIME_PERI (TIME_PERI_NO ,TIME_START ,TIME_DUR ,TIME_END, ISDEL_STS, TIME_STS) VALUES ('TP'||LPAD(SEQ_TIME_PERI_NO.NEXTVAL,4,0), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT TIME_PERI_NO, TIME_START, TIME_DUR, TIME_END,ISDEL_STS ,TIME_STS FROM TIME_PERI ORDER BY TIME_PERI_NO";
	private static final String GET_ONE_STMT = "SELECT TIME_START, TIME_DUR, TIME_END,ISDEL_STS ,TIME_STS FROM TIME_PERI WHERE TIME_PERI_NO = ?";
	private static final String UPDATE = "UPDATE TIME_PERI SET TIME_START=?, TIME_DUR=?, TIME_END=?, TIME_STS=?, ISDEL_STS=?  WHERE TIME_PERI_NO = ?";

	@Override
	public void insert(TimePeriVO timePeriVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, timePeriVO.getTime_start());
			pstmt.setInt(2, new Integer(timePeriVO.getTime_dur()));
			pstmt.setString(3, timePeriVO.getTime_start());
			pstmt.setInt(4, new Integer(timePeriVO.getIsdel_sts()));
			pstmt.setInt(5, new Integer(timePeriVO.getTime_sts()));

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
	public void update(TimePeriVO timePeriVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, timePeriVO.getTime_start());
			pstmt.setInt(2, new Integer(timePeriVO.getTime_dur()));
			pstmt.setString(3, timePeriVO.getTime_start());
			pstmt.setInt(4, new Integer(timePeriVO.getIsdel_sts()));
			pstmt.setInt(5, new Integer(timePeriVO.getTime_sts()));
			pstmt.setString(6, timePeriVO.getTime_peri_no());

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
	public TimePeriVO findByPrimaryKey(String time_peri_no) {
		TimePeriVO timePeriVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, time_peri_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				timePeriVO = new TimePeriVO();

				timePeriVO.setTime_peri_no(time_peri_no);
				timePeriVO.setTime_start(rs.getString("TIME_START"));
				timePeriVO.setTime_dur(new Integer(rs.getInt("TIME_DUR")));
				timePeriVO.setTime_end(rs.getString("TIME_END"));
				timePeriVO.setIsdel_sts(new Integer(rs.getInt("ISDEL_STS")));
				timePeriVO.setTime_sts(new Integer(rs.getInt("TIME_STS")));
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
		return timePeriVO;
	}

	@Override
	public List<TimePeriVO> getAll() {
		List<TimePeriVO> list = new ArrayList<TimePeriVO>();
		TimePeriVO timePeriVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt("ISDEL_STS") == 0) {
					timePeriVO = new TimePeriVO();
					timePeriVO.setTime_peri_no(rs.getString("TIME_PERI_NO"));
					timePeriVO.setTime_start(rs.getString("TIME_START"));
					timePeriVO.setTime_dur(new Integer(rs.getInt("TIME_DUR")));
					timePeriVO.setTime_end(rs.getString("TIME_END"));
					timePeriVO.setIsdel_sts(new Integer(rs.getInt("ISDEL_STS")));
					timePeriVO.setTime_sts(new Integer(rs.getInt("TIME_STS")));

					list.add(timePeriVO); // Store the row in the list
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
