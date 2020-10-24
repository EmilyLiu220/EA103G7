package com.meal_set_consist.model;

import java.sql.*;
import java.util.*;

import org.apache.tomcat.dbcp.dbcp2.DriverConnectionFactory;

public class MealSetConJDBCDAO implements MealSetConDAO_interface{

	String DRIVER = "oracle.jdbc.driver.OracleDriver";
	String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	String USERNAME = "EA103G7";
	String PASSWORD = "123456";

	private static final String INSERT = "INSERT INTO MEAL_SET_CONSIST (MEAL_SET_NO,MEAL_NO,MEAL_QTY) VALUES (?,?,?)";
//	private static final String UPDATE = "UPDATE MEAL_SET_CONSIST SET MEAL_NO=?,MEAL_QTY=? WHERE MEAL_SET_NO=?";
	private static final String DELETE = "DELETE FROM MEAL_SET_CONSIST WHERE MEAL_SET_NO=?";
	private static final String GETONE = "SELECT * FROM MEAL_SET_CONSIST WHERE MEAL_SET_NO=?";
	private static final String GETALL = "SELECT * FROM MEAL_SET_CONSIST ORDER BY MEAL_SET_NO";

	public void insert(MealSetConVO mealSetConVO,Connection con) {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, mealSetConVO.getMeal_set_no());
			pstmt.setString(2, mealSetConVO.getMeal_no());
			pstmt.setInt(3, mealSetConVO.getMeal_qty());

			pstmt.execute();

		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}  finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
		}

	}
	
	

//	public void update(MealSetConVO mealSetConVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		PreparedStatement pstmt2 = null;
//
//		try {
//			Class.forName(DRIVER);
//			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			pstmt = con.prepareStatement(UPDATE);
//			pstmt2 = con.prepareStatement(DELETE);
//			pstmt2.setString(1,mealSetConVO.getMeal_set_no());
//			pstmt2.executeUpdate();
//			
//			pstmt.setString(1, mealSetConVO.getMeal_no());
//			pstmt.setInt(2, mealSetConVO.getMeal_qty());
//			pstmt.setString(3, mealSetConVO.getMeal_set_no());
//			
//			pstmt.executeUpdate();
//
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}

	public void delete(String mealSetConNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mealSetConNo);

			pstmt.execute();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public List<MealSetConVO> searchBySetNo(String meal_set_no) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealSetConVO mealSetConVO = null;
		List<MealSetConVO> list = new ArrayList<MealSetConVO>();
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(GETONE);
			pstmt.setString(1, meal_set_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				mealSetConVO = new MealSetConVO();
				mealSetConVO.setMeal_set_no(rs.getString("meal_set_no"));
				mealSetConVO.setMeal_no(rs.getString("meal_no"));
				mealSetConVO.setMeal_qty(rs.getInt("meal_qty"));
				list.add(mealSetConVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
		return list;
	}

	public List<MealSetConVO> getAll() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealSetConVO> list = new ArrayList<MealSetConVO>();
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MealSetConVO mealSetConVO = new MealSetConVO();
				mealSetConVO.setMeal_set_no(rs.getString("meal_set_no"));
				mealSetConVO.setMeal_no(rs.getString("meal_no"));
				mealSetConVO.setMeal_qty(rs.getInt("meal_qty"));
				list.add(mealSetConVO);

			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
		return list;
	}

	public static void main(String[] args) {

		MealSetConJDBCDAO dao = new MealSetConJDBCDAO();

		/* insert */
//		MealSetConVO mealSetConVO = new MealSetConVO();
//		mealSetConVO.setMeal_set_no("MES0003");
//		mealSetConVO.setMeal_no("MEAL0002");
//		mealSetConVO.setMeal_qty(1);
//		dao.insert(mealSetConVO);

		/* update */
//		MealSetConVO mealSetConVO2 = new MealSetConVO();
//		mealSetConVO2.setMeal_set_no("MES0003");
//		mealSetConVO2.setMeal_no("MEAL0006");
//		mealSetConVO2.setMeal_qty(2);
//		dao.update(mealSetConVO2);

		/* delete */
//		dao.delete("MES0003");

		/* searchBySetNo */
//		List<MealSetConVO> list = dao.searchBySetNo("MES0001");
//		for (MealSetConVO mealSetConVO : list) {
//			System.out.print(mealSetConVO.getMeal_set_no() + "的組成:");
//			System.out.print(mealSetConVO.getMeal_no() + ",");
//			System.out.println(mealSetConVO.getMeal_qty() + "個");
//		}

		/* getall */
		List<MealSetConVO> list2 = dao.getAll();
		for (MealSetConVO mealSetConVO : list2) {
			System.out.print(mealSetConVO.getMeal_set_no() + "的組成:");
			System.out.print(mealSetConVO.getMeal_no() + ",");
			System.out.println(mealSetConVO.getMeal_qty() + "個");
		}

	}

}
