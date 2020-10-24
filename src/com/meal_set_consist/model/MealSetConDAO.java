package com.meal_set_consist.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MealSetConDAO implements MealSetConDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT = "INSERT INTO MEAL_SET_CONSIST (MEAL_SET_NO,MEAL_NO,MEAL_QTY) VALUES (?,?,?)";
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
		} finally {
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
		}

	};

	public void delete(String mealSetNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mealSetNo);

			pstmt.execute();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	};

	public List<MealSetConVO> searchBySetNo(String meal_set_no) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealSetConVO mealSetConVO = null;
		List<MealSetConVO> list = new ArrayList<MealSetConVO>();
		try {
			con = ds.getConnection();
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
		MealSetConVO mealSetConVO = null;
		List<MealSetConVO> list = new ArrayList<MealSetConVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);

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

}
