package com.meal_category.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealCatJDBCDAO implements MealCatDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";

	private static final String INSERT = "INSERT INTO MEAL_CATEGORY (CAT_NO,CAT_NAME) VALUES (CAT_SEQ.NEXTVAL,?)";
	private static final String UPDATE = "UPDATE MEAL_CATEGORY SET CAT_NAME=? where CAT_NO=?";
	private static final String GETALL = "SELECT * FROM MEAL_CATEGORY ORDER BY CAT_NO";

	public void insert(MealCatVO mealCatVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, mealCatVO.getCat_name());

			pstmt.execute();

		} catch (ClassNotFoundException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mealCatVO.getCat_name());
			pstmt.setInt(2, mealCatVO.getCat_no());

			pstmt.execute();

		} catch (ClassNotFoundException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public List<MealCatVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealCatVO> list = new ArrayList<MealCatVO>();
		MealCatVO mealCatVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALL);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealCatVO = new MealCatVO();

				mealCatVO.setCat_no(rs.getInt("cat_no"));
				mealCatVO.setCat_name(rs.getString("cat_name"));
				list.add(mealCatVO);

			}
		} catch (ClassNotFoundException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	
	public static void main(String[] args) {
		MealCatJDBCDAO dao = new MealCatJDBCDAO();
		
		/*insert test*/
//		MealCatVO mealCatVO = new MealCatVO();
//		
//		mealCatVO.setCat_name("xxxx");
//		dao.insert(mealCatVO);
//		
		/*update test*/
		
//		MealCatVO mealCatVO2 = new MealCatVO();
//		
//		mealCatVO2.setCat_name("甜點");
//		mealCatVO2.setCat_no(30);
//		dao.update(mealCatVO2);
		
		/*getall test*/
		
		List<MealCatVO> list = dao.getAll();
		
		for(MealCatVO catVO:list) {
			System.out.print(catVO.getCat_no() + ",");
			System.out.println(catVO.getCat_name());
		}
		
		
		
		
		
		
	}

}
