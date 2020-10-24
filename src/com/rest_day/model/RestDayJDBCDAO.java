package com.rest_day.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestDayJDBCDAO implements RestDayDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO REST_DAY (REST_DAY_NO ,EMP_NO ,REST_DAY_SUP_STA ,REST_DAY_SUP_END ,TIME_PERI_NO, REST_DAY_FIX, REST_STS) VALUES ('RD'||LPAD(SEQ_REST_DAY_NO.NEXTVAL,4,0), ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT REST_DAY_NO, EMP_NO ,REST_DAY_SUP_STA ,REST_DAY_SUP_END ,TIME_PERI_NO, REST_DAY_FIX, REST_STS FROM REST_DAY ORDER BY REST_DAY_NO";
	private static final String GET_ONE_STMT = "SELECT EMP_NO ,REST_DAY_SUP_STA ,REST_DAY_SUP_END ,TIME_PERI_NO, REST_DAY_FIX, REST_STS FROM REST_DAY WHERE REST_DAY_NO = ?";
	private static final String UPDATE = "UPDATE REST_DAY SET EMP_NO=?, REST_DAY_SUP_STA=?, REST_DAY_SUP_END=?, TIME_PERI_NO=?, REST_DAY_FIX=?, REST_STS=? WHERE REST_DAY_NO = ?";
	private static final String DELETE = "DELETE FROM REST_DAY WHERE REST_DAY_NO = ?";

	@Override
	public void insert(RestDayVO restDayVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, restDayVO.getEmp_no());
			pstmt.setDate(2, restDayVO.getRest_day_sup_sta());
			pstmt.setDate(3, restDayVO.getRest_day_sup_end());
			pstmt.setString(4, restDayVO.getTime_peri_no());
			pstmt.setInt(5, restDayVO.getRest_day_fix());
			pstmt.setInt(6, restDayVO.getRest_sts());

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
	public void update(RestDayVO restDayVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, restDayVO.getEmp_no());
			pstmt.setDate(2, restDayVO.getRest_day_sup_sta());
			pstmt.setDate(3, restDayVO.getRest_day_sup_end());
			pstmt.setString(4, restDayVO.getTime_peri_no());
			pstmt.setInt(5, restDayVO.getRest_day_fix());
			pstmt.setInt(6, restDayVO.getRest_sts());
			pstmt.setString(7, restDayVO.getRest_day_no());

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
	public void delete(String rest_day_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rest_day_no);

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
	public RestDayVO findByPrimaryKey(String rest_day_no) {
		RestDayVO restDayVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rest_day_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				restDayVO = new RestDayVO();
				restDayVO.setRest_day_no(rest_day_no);
				restDayVO.setEmp_no(rs.getString("EMP_NO"));
				restDayVO.setRest_day_sup_sta(rs.getDate("REST_DAY_SUP_STA"));
				restDayVO.setRest_day_sup_end(rs.getDate("REST_DAY_SUP_END"));
				restDayVO.setTime_peri_no(rs.getString("TIME_PERI_NO"));
				restDayVO.setRest_day_fix(rs.getInt("REST_DAY_FIX"));
				restDayVO.setRest_sts(rs.getInt("REST_STS"));
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
		return restDayVO;
	}

	@Override
	public List<RestDayVO> getAll() {
		List<RestDayVO> list = new ArrayList<RestDayVO>();
		RestDayVO restDayVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
//				if (new Integer(rs.getInt("SEAT_ISDEL")) == 0) {
					restDayVO = new RestDayVO();
					restDayVO.setRest_day_no(rs.getString("REST_DAY_NO"));
					restDayVO.setEmp_no(rs.getString("EMP_NO"));
					restDayVO.setRest_day_sup_sta(rs.getDate("REST_DAY_SUP_STA"));
					restDayVO.setRest_day_sup_end(rs.getDate("REST_DAY_SUP_END"));
					restDayVO.setTime_peri_no(rs.getString("TIME_PERI_NO"));
					restDayVO.setRest_day_fix(rs.getInt("REST_DAY_FIX"));
					restDayVO.setRest_sts(rs.getInt("REST_STS"));

					list.add(restDayVO); // Store the row in the list
//				}
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
//		RestDayJDBCDAO dao = new RestDayJDBCDAO();
//		// insert
//		RestDayVO restDayVO = new RestDayVO();
//		restDayVO.setEmp_no("EMP0001");
//		restDayVO.setRest_day_sup_sta(null);
//		restDayVO.setRest_day_sup_end(null);
//		restDayVO.setTime_peri_no(null);
//		restDayVO.setRest_day_fix(new Integer(0));
//		restDayVO.setRest_sts(new Integer(1));
//		
//		dao.insert(restDayVO);
//
//		// update
//		RestDayVO restDayVO1 = new RestDayVO();
//		restDayVO1.setRest_day_no("RD0001");
//		restDayVO1.setEmp_no("EMP0001");
//		restDayVO1.setRest_day_sup_sta(java.sql.Date.valueOf("2019-10-03"));
//		restDayVO1.setRest_day_sup_end(java.sql.Date.valueOf("2019-10-05"));
//		restDayVO1.setTime_peri_no("TP0007");
//		restDayVO1.setRest_day_fix(new Integer(0));
//		restDayVO1.setRest_sts(new Integer(2));
//		dao.update(restDayVO1);
//
//		// get pk delete
//		dao.delete("RD0010");
//		
//		// get one seat obj
//		RestDayVO restDayVO2 = dao.findByPrimaryKey("RD0009");
//		System.out.print(restDayVO2.getRest_day_no() + ",\t");
//		System.out.print(restDayVO2.getEmp_no() + ",\t");
//		System.out.print(restDayVO2.getRest_day_sup_sta() + ",\t");
//		System.out.print(restDayVO2.getRest_day_sup_end() + ",\t");
//		System.out.print(restDayVO2.getTime_peri_no() + ",\t");
//		System.out.print(restDayVO2.getRest_day_fix() + ",\t");
//		System.out.print(restDayVO2.getRest_sts() + ",\t");
//
//		// select all
//		List<RestDayVO> list = dao.getAll();
//		for (RestDayVO restDayVO3 : list) {
//			System.out.print(restDayVO3.getRest_day_no() + ",\t");
//			System.out.print(restDayVO3.getEmp_no() + ",\t");
//			System.out.print(restDayVO3.getRest_day_sup_sta() + ",\t");
//			System.out.print(restDayVO3.getRest_day_sup_end() + ",\t");
//			System.out.print(restDayVO3.getTime_peri_no() + ",\t");
//			System.out.print(restDayVO3.getRest_day_fix() + ",\t");
//			System.out.print(restDayVO3.getRest_sts() + ",\t");
//			System.out.println();
//		}
//	}

}
