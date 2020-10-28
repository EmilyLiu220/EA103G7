package com.res_detail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResDetailJDBCDAO implements ResDetailDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO RES_DETAIL (RES_DE_NO ,SEAT_NO ,RES_NO) VALUES ('RDN'||LPAD(SEQ_RES_DE_NO.NEXTVAL,4,0), ?, ?)";
	private static final String GET_ALL_STMT = "SELECT RES_DE_NO ,SEAT_NO ,RES_NO FROM RES_DETAIL ORDER BY RES_NO";
	private static final String GET_ONE_STMT = "SELECT SEAT_NO, RES_NO FROM RES_DETAIL WHERE RES_DE_NO = ?";
	private static final String UPDATE = "UPDATE RES_DETAIL SET SEAT_NO=? ,RES_NO=? WHERE RES_DE_NO = ?";
	private static final String GET_RES_NO_ALL_STMT = "SELECT RES_DE_NO, SEAT_NO, RES_NO FROM RES_DETAIL WHERE RES_NO = ?";

	@Override
	public void insert(ResDetailVO resDetailVO, Connection outer_con) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			if (outer_con == null) {
				con = DriverManager.getConnection(url, userid, passwd);
			} else {
				con = outer_con;
			}
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, resDetailVO.getSeat_no());
			pstmt.setString(2, resDetailVO.getRes_no());

			pstmt.executeUpdate();

			System.out.println("Insert success");

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
	public void update(ResDetailVO resDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, resDetailVO.getSeat_no());
			pstmt.setString(2, resDetailVO.getRes_no());
			pstmt.setString(3, resDetailVO.getRes_de_no());

			pstmt.executeUpdate();

			System.out.println("Update success");

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
	public ResDetailVO findByPrimaryKey(String res_de_no) {
		ResDetailVO resDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, res_de_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				resDetailVO = new ResDetailVO();

				resDetailVO.setRes_de_no(res_de_no);
				resDetailVO.setSeat_no(rs.getString("SEAT_NO"));
				resDetailVO.setRes_no(rs.getString("RES_NO"));
			}

			System.out.println("FindByPrimaryKey success");

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
		return resDetailVO;
	}

	@Override
	public List<ResDetailVO> getAll() {
		List<ResDetailVO> list = new ArrayList<ResDetailVO>();
		ResDetailVO resDetailVO = null;

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
				resDetailVO = new ResDetailVO();

				resDetailVO.setRes_de_no(rs.getString("RES_DE_NO"));
				resDetailVO.setSeat_no(rs.getString("SEAT_NO"));
				resDetailVO.setRes_no(rs.getString("RES_NO"));

				list.add(resDetailVO); // Store the row in the list
//				}
			}

			System.out.println("Get All success");

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

	@Override
	public List<ResDetailVO> getResNoAll(String res_no) {
		List<ResDetailVO> list = new ArrayList<ResDetailVO>();
		ResDetailVO resDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_RES_NO_ALL_STMT);

			pstmt.setString(1, res_no);

			rs = pstmt.executeQuery();
			while (rs.next()) {
//				if (new Integer(rs.getInt("SEAT_ISDEL")) == 0) {
				resDetailVO = new ResDetailVO();

				resDetailVO.setRes_de_no(rs.getString("RES_DE_NO"));
				resDetailVO.setSeat_no(rs.getString("SEAT_NO"));
				resDetailVO.setRes_no(res_no);

				list.add(resDetailVO); // Store the row in the list
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

	@Override
	public void delete(String res_no, String seats_no[], Connection outer_con) {
		// TODO Auto-generated method stub
		
	}

//	public static void main(String[] args) {
//
//		ResDetailJDBCDAO dao = new ResDetailJDBCDAO();
//		// insert
//		ResDetailVO resDetailVO = new ResDetailVO();
//		resDetailVO.setSeat_no("SEN0009");
//		resDetailVO.setRes_no("RESO0010");
//		dao.insert(resDetailVO);
//
//		// update
//		ResDetailVO resDetailVO1 = new ResDetailVO();
//		resDetailVO1.setRes_de_no("RDN0011");
//		resDetailVO1.setSeat_no("SEN0010");
//		resDetailVO1.setRes_no("RESO0009");
//		dao.update(resDetailVO1);
//
//		// get one seat obj
//		ResDetailVO resDetailVO2 = dao.findByPrimaryKey("RDN0011");
//		System.out.print(resDetailVO2.getRes_de_no() + ",\t");
//		System.out.print(resDetailVO2.getSeat_no() + ",\t");
//		System.out.print(resDetailVO2.getRes_no() + ",\t");
//
//		// select all
//		List<ResDetailVO> list = dao.getAll();
//		for (ResDetailVO resDetailVO3 : list) {
//			System.out.print(resDetailVO3.getRes_de_no() + ",\t");
//			System.out.print(resDetailVO3.getSeat_no() + ",\t");
//			System.out.print(resDetailVO3.getRes_no() + ",\t");
//			System.out.println();
//		}
//	}
}
