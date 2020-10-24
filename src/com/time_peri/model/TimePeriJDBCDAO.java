package com.time_peri.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimePeriJDBCDAO implements TimePeriDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO TIME_PERI (TIME_PERI_NO ,TIME_START ,TIME_DUR ,TIME_END, ISDEL_STS, TIME_STS) VALUES ('TP'||LPAD(SEQ_TIME_PERI_NO.NEXTVAL,4,0), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT TIME_PERI_NO, TIME_START, TIME_DUR, TIME_END,ISDEL_STS ,TIME_STS FROM TIME_PERI ORDER BY TIME_PERI_NO";
	private static final String GET_ONE_STMT = "SELECT TIME_START, TIME_DUR, TIME_END,ISDEL_STS ,TIME_STS FROM TIME_PERI WHERE TIME_PERI_NO = ?";
	private static final String UPDATE = "UPDATE TIME_PERI SET TIME_START=?, TIME_DUR=?, TIME_END=?, TIME_STS=?, ISDEL_STS=?  WHERE TIME_PERI_NO = ?";

	@Override
	public void insert(TimePeriVO timePeriVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, timePeriVO.getTime_start());
			pstmt.setInt(2, new Integer(timePeriVO.getTime_dur()));
			pstmt.setString(3, timePeriVO.getTime_start());
			pstmt.setInt(4, new Integer(timePeriVO.getIsdel_sts()));
			pstmt.setInt(5, new Integer(timePeriVO.getTime_sts()));

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
	public void update(TimePeriVO timePeriVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, timePeriVO.getTime_start());
			pstmt.setInt(2, new Integer(timePeriVO.getTime_dur()));
			pstmt.setString(3, timePeriVO.getTime_start());
			pstmt.setInt(4, new Integer(timePeriVO.getIsdel_sts()));
			pstmt.setInt(5, new Integer(timePeriVO.getTime_sts()));
			pstmt.setString(6, timePeriVO.getTime_peri_no());

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
	public TimePeriVO findByPrimaryKey(String time_peri_no) {
		TimePeriVO timePeriVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

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
//		TimePeriJDBCDAO dao = new TimePeriJDBCDAO();
//		// insert
//		TimePeriVO timePeriVO = new TimePeriVO();
//		timePeriVO.setTime_start("01:00AM");
//		timePeriVO.setTime_dur(new Integer(2));
//		timePeriVO.setTime_end("23:00PM");
//		timePeriVO.setIsdel_sts(new Integer(0));
//		timePeriVO.setTime_sts(new Integer(1));
//		dao.insert(timePeriVO);
//
//		// update
//		TimePeriVO timePeriVO1 = new TimePeriVO();
//		timePeriVO1.setTime_peri_no("TP0008");
//		timePeriVO1.setTime_start("01:00AM");
//		timePeriVO1.setTime_dur(new Integer(2));
//		timePeriVO1.setTime_end("21:00PM");
//		timePeriVO1.setIsdel_sts(new Integer(0));
//		timePeriVO1.setTime_sts(new Integer(0));
//		dao.update(timePeriVO1);
//
//		// get one seat obj
//		TimePeriVO timePeriVO2 = dao.findByPrimaryKey("TP0008");
//		System.out.print(timePeriVO2.getTime_peri_no() + ",\t");
//		System.out.print(timePeriVO2.getTime_start() + ",\t");
//		System.out.print(timePeriVO2.getTime_dur() + ",\t");
//		System.out.print(timePeriVO2.getTime_end() + ",\t");
//		System.out.print(timePeriVO2.getIsdel_sts() + ",\t");
//		System.out.print(timePeriVO2.getTime_sts() + ",\t");
//
//		// select all
//		List<TimePeriVO> list = dao.getAll();
//		for (TimePeriVO timePeriVO3 : list) {
//			System.out.print(timePeriVO3.getTime_peri_no() + ",\t");
//			System.out.print(timePeriVO3.getTime_start() + ",\t");
//			System.out.print(timePeriVO3.getTime_dur() + ",\t");
//			System.out.print(timePeriVO3.getTime_end() + ",\t");
//			System.out.print(timePeriVO3.getIsdel_sts() + ",\t");
//			System.out.print(timePeriVO3.getTime_sts() + ",\t");
//			System.out.println();
//		}
//	}
}
