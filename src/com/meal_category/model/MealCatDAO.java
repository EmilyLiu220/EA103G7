package com.meal_category.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.meal.model.MealVO;

public class MealCatDAO implements MealCatDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT = "INSERT INTO MEAL_CATEGORY (CAT_NO,CAT_NAME) VALUES (CAT_SEQ.NEXTVAL,?)";
	private static final String UPDATE = "UPDATE MEAL_CATEGORY SET CAT_NAME=? where CAT_NO=?";
	private static final String GETALL = "SELECT * FROM MEAL_CATEGORY ORDER BY CAT_NO";
	private static final String SEARCH = "SELECT * FROM MEAL_CATEGORY WHERE CATNAME LIKE = ? ";
	public void insert(MealCatVO mealCatVO) {
		

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, mealCatVO.getCat_name());
			

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
		
	public void update(MealCatVO mealCatVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mealCatVO.getCat_name());
			pstmt.setInt(2, mealCatVO.getCat_no());
			

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
	
	public MealCatVO search(String keyWord) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealCatVO mealCatVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCH);
			pstmt.setString(1, "%" + keyWord + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealCatVO = new MealCatVO();
				
				mealCatVO.setCat_no(rs.getInt("cat_no"));
				mealCatVO.setCat_name(rs.getString("cat_name"));

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
		return mealCatVO;

	};
	
		
		
		
	public List<MealCatVO> getAll(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealCatVO> list = new ArrayList<MealCatVO>();
		MealCatVO mealCatVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealCatVO = new MealCatVO();
				
				mealCatVO.setCat_no(rs.getInt("cat_no"));
				mealCatVO.setCat_name(rs.getString("cat_name"));
				list.add(mealCatVO);

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
