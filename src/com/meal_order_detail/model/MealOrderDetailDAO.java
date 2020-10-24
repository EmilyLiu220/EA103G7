package com.meal_order_detail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MealOrderDetailDAO implements MealOrderDetailDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	private static final String INSERT = "INSERT INTO MEAL_ORDER_DETAIL(MEAL_ORDER_DETAIL_NO,MEAL_ORDER_NO,MEAL_NO,MEAL_SET_NO,QTY,DETAIL_AMOUNT,MEAL_NAME) VALUES ('MOD' || LPAD(SEQ_MEAL_ORDER_DETAIL_NO.NEXTVAL,4,0),?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE MEAL_ORDER_DETAIL SET ASGN_STS=? WHERE MEAL_ORDER_DETAIL_NO=?";
	private static final String SEARCHBYORDERNO = "SELECT * FROM MEAL_ORDER_DETAIL WHERE MEAL_ORDER_NO=?";
	private static final String SEARCHBYASGNSTS = "SELECT * FROM MEAL_ORDER_DETAIL WHERE ASGN_STS=?";

	public void insert(MealOrderDetailVO mealOrderDetailVO,Connection con) {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, mealOrderDetailVO.getMeal_order_no());
			pstmt.setString(2, mealOrderDetailVO.getMeal_no());
			pstmt.setString(3, mealOrderDetailVO.getMeal_set_no());
			pstmt.setInt(4, mealOrderDetailVO.getQty());
			pstmt.setInt(5, mealOrderDetailVO.getDetail_amount());
			pstmt.setString(6, mealOrderDetailVO.getMeal_name());

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

	public void update(MealOrderDetailVO mealOrderDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, mealOrderDetailVO.getAsgn_sts());
			pstmt.setString(2, mealOrderDetailVO.getMeal_order_detail_no());

			pstmt.executeUpdate();

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

	public List<MealOrderDetailVO> searchByOrderNo(String mealOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealOrderDetailVO mealOrderDetailVO = null;
		List<MealOrderDetailVO> list = new ArrayList<MealOrderDetailVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCHBYORDERNO);
			pstmt.setString(1, mealOrderNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealOrderDetailVO = new MealOrderDetailVO();
				mealOrderDetailVO.setMeal_order_detail_no(rs.getString("meal_order_detail_no"));
				mealOrderDetailVO.setMeal_order_no(rs.getString("meal_order_no"));
				mealOrderDetailVO.setMeal_no(rs.getString("meal_no"));
				mealOrderDetailVO.setMeal_set_no(rs.getString("meal_set_no"));
				mealOrderDetailVO.setQty(rs.getInt("qty"));
				mealOrderDetailVO.setDetail_amount(rs.getInt("detail_amount"));
				mealOrderDetailVO.setAsgn_sts(rs.getInt("asgn_sts"));
				mealOrderDetailVO.setMeal_name(rs.getString("meal_name"));
				list.add(mealOrderDetailVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	};

	public List<MealOrderDetailVO> searchByAsgnSts(Integer asgnSts) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealOrderDetailVO mealOrderDetailVO = null;
		List<MealOrderDetailVO> list = new ArrayList<MealOrderDetailVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCHBYASGNSTS);
			pstmt.setInt(1, asgnSts);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealOrderDetailVO = new MealOrderDetailVO();
				mealOrderDetailVO.setMeal_order_detail_no(rs.getString("meal_order_detail_no"));
				mealOrderDetailVO.setMeal_order_no(rs.getString("meal_order_no"));
				mealOrderDetailVO.setMeal_no(rs.getString("meal_no"));
				mealOrderDetailVO.setMeal_set_no(rs.getString("meal_set_no"));
				mealOrderDetailVO.setQty(rs.getInt("qty"));
				mealOrderDetailVO.setDetail_amount(rs.getInt("detail_amount"));
				mealOrderDetailVO.setAsgn_sts(rs.getInt("asgn_sts"));
				list.add(mealOrderDetailVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	};

}
