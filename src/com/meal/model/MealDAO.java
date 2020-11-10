package com.meal.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.sql.DataSource;

import com.food.model.*;
import com.meal_part.model.*;

public class MealDAO implements MealDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT = "insert into meal (meal_no,meal_name,meal_info,meal_img,meal_price,meal_sts,cat_no) values ('MEAL' || LPAD(seq_meal_no.nextval,4,0),?,?,?,?,?,?)";
	private static final String UPDATE = "update meal set meal_name=?,meal_info=?,meal_img=?,meal_price=?,meal_sts=?,cat_no=? where meal_no=?";
	private static final String SEARCHBYNO ="select * from meal where meal_no like ?";
	private static final String SEARCHBYNOANDNAME ="select * from meal where meal_name like ? or meal_no like ?";
	private static final String SEARCHBYMEALSTS ="select * from meal where meal_sts = ?";
	private static final String GETALL = "select meal_no,meal_name,meal_info,meal_img,meal_price,meal_sts,cat_no from meal order by meal_no";
	
	public void insert(MealVO mealVO,List<Meal_partVO> partList) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String mealNo = null;

		try {
			
			con = ds.getConnection();
			con.setAutoCommit(false);
			String [] col = {"MEAL_NO"};
			pstmt = con.prepareStatement(INSERT,col);

			pstmt.setString(1, mealVO.getMeal_name());
			pstmt.setString(2, mealVO.getMeal_info());
			pstmt.setBytes(3, mealVO.getMeal_img());
			pstmt.setInt(4, mealVO.getMeal_price());
			pstmt.setInt(5, mealVO.getMeal_sts());
			pstmt.setInt(6, mealVO.getCat_no());

			pstmt.execute();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				mealNo = rs.getString(1);
//				System.out.println(mealNo);
			}
			// 馮改1020
			List<Meal_partVO> partList2 = new ArrayList<Meal_partVO>();
			Meal_partDAO partDAO = new Meal_partDAO();
			for(Meal_partVO partVO:partList) {
				partVO.setMeal_no(mealNo);
				partList2.add(partVO);
			}
			partDAO.insert(partList2,con);
			con.commit();
			
			

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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	};

	public void update(MealVO mealVO,List<Meal_partVO> partList) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, mealVO.getMeal_name());
			pstmt.setString(2, mealVO.getMeal_info());
			pstmt.setBytes(3, mealVO.getMeal_img());
			pstmt.setInt(4, mealVO.getMeal_price());
			pstmt.setInt(5, mealVO.getMeal_sts());
			pstmt.setInt(6, mealVO.getCat_no());
			pstmt.setString(7, mealVO.getMeal_no());

			pstmt.executeUpdate();
			
			
			List<Meal_partVO> partList2 = new ArrayList<Meal_partVO>();
			Meal_partDAO partDAO = new Meal_partDAO();
			partDAO.delete(mealVO.getMeal_no());
			for(Meal_partVO partVO:partList) {
				partVO.setMeal_no(mealVO.getMeal_no());
				partList2.add(partVO);
			}
			partDAO.insert(partList2,con);
			con.commit();

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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	};
	
	public MealVO searchByNo(String keyWord) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealVO mealVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCHBYNO);
			pstmt.setString(1, "%" + keyWord + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealVO = new MealVO();
				
				mealVO.setMeal_no(rs.getString("meal_no"));
				mealVO.setMeal_name(rs.getString("meal_name"));
				mealVO.setMeal_info(rs.getString("meal_info"));
				mealVO.setMeal_img(rs.getBytes("meal_img"));
				mealVO.setMeal_price(rs.getInt("meal_price"));
				mealVO.setMeal_sts(rs.getInt("meal_sts"));
				mealVO.setCat_no(rs.getInt("cat_no"));

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
		return mealVO;

	};
	
	public List<MealVO> searchByNoAndName(String keyWord) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealVO> list = new ArrayList();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCHBYNOANDNAME);
			pstmt.setString(1, "%" + keyWord + "%");
			pstmt.setString(2, "%" + keyWord + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MealVO mealVO = new MealVO();
				mealVO.setMeal_no(rs.getString("meal_no"));
				mealVO.setMeal_name(rs.getString("meal_name"));
				mealVO.setMeal_info(rs.getString("meal_info"));
				mealVO.setMeal_img(rs.getBytes("meal_img"));
				mealVO.setMeal_price(rs.getInt("meal_price"));
				mealVO.setMeal_sts(rs.getInt("meal_sts"));
				mealVO.setCat_no(rs.getInt("cat_no"));
				list.add(mealVO);

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
	
	public List<MealVO> searchByMealSts(Integer mealSts){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealVO> list = new ArrayList<MealVO>();
		MealVO mealVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCHBYMEALSTS);
			pstmt.setInt(1,mealSts);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealVO = new MealVO();
				
				mealVO.setMeal_no(rs.getString("meal_no"));
				mealVO.setMeal_name(rs.getString("meal_name"));
				mealVO.setMeal_info(rs.getString("meal_info"));
				mealVO.setMeal_img(rs.getBytes("meal_img"));
				mealVO.setMeal_price(rs.getInt("meal_price"));
				mealVO.setMeal_sts(rs.getInt("meal_sts"));
				mealVO.setCat_no(rs.getInt("cat_no"));
				list.add(mealVO);

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


	public List<MealVO> getAll() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealVO> list = new ArrayList<MealVO>();
		MealVO mealVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealVO = new MealVO();
				
				mealVO.setMeal_no(rs.getString("meal_no"));
				mealVO.setMeal_name(rs.getString("meal_name"));
				mealVO.setMeal_info(rs.getString("meal_info"));
				mealVO.setMeal_img(rs.getBytes("meal_img"));
				mealVO.setMeal_price(rs.getInt("meal_price"));
				mealVO.setMeal_sts(rs.getInt("meal_sts"));
				mealVO.setCat_no(rs.getInt("cat_no"));
				list.add(mealVO);

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
	
	public List<MealVO> getAll2() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealVO> list = new ArrayList<MealVO>();
		MealVO mealVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealVO = new MealVO();
				
				mealVO.setMeal_no(rs.getString("meal_no"));
				mealVO.setMeal_name(rs.getString("meal_name"));
				mealVO.setMeal_info(rs.getString("meal_info"));
				mealVO.setMeal_price(rs.getInt("meal_price"));
				mealVO.setMeal_sts(rs.getInt("meal_sts"));
				mealVO.setCat_no(rs.getInt("cat_no"));
				list.add(mealVO);

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
