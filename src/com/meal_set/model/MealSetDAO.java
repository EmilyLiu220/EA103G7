package com.meal_set.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.naming.*;
import javax.sql.DataSource;

import com.meal_set_consist.model.MealSetConDAO;
import com.meal_set_consist.model.MealSetConJDBCDAO;
import com.meal_set_consist.model.MealSetConVO;

public class MealSetDAO implements MealSetDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
	private static final String INSERT = "insert into meal_set (meal_set_no,meal_set_name,meal_set_info,meal_set_img,meal_set_price,meal_set_sts,cat_no) values ('MSET' || LPAD(seq_meal_set_no.nextval, 3, 0),?,?,?,?,?,?)";
	private static final String UPDATE = "update meal_set set meal_set_name=?,meal_set_info=?,meal_set_img=?,meal_set_price=?,meal_set_sts=?,cat_no=? where meal_set_no=?";
	private static final String SEARCHBYNO = "select * from meal_set where meal_set_no like ?";
	private static final String SEARCHBYNOANDNAME = "select * from meal_set where meal_set_name like ? or meal_set_no like ?";
	private static final String SEARCHBYMEALSETSTS = "select * from meal_set where meal_set_sts =?";
	private static final String GETALL = "select * from meal_set order by meal_set_no";

	public void insert(MealSetVO mealSetVO,List<MealSetConVO>conList) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String nextNo = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			String[] col = {"MEAL_SET_NO"};
			pstmt = con.prepareStatement(INSERT,col);

			pstmt.setString(1, mealSetVO.getMeal_set_name());
			pstmt.setString(2, mealSetVO.getMeal_set_info());
			pstmt.setBytes(3, mealSetVO.getMeal_set_img());
			pstmt.setInt(4, mealSetVO.getMeal_set_price());
			pstmt.setInt(5, mealSetVO.getMeal_set_sts());
			pstmt.setInt(6, mealSetVO.getCat_no());

			pstmt.execute();
			

			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				nextNo = rs.getString(1);
				System.out.println(nextNo);
			}
			
			MealSetConDAO conDAO = new MealSetConDAO();
			for(MealSetConVO mealSetConVO:conList ) {
				mealSetConVO.setMeal_set_no(nextNo);
				conDAO.insert(mealSetConVO,con);
			}
			
			con.commit();
			System.out.println("commit");

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
	
	public void update(MealSetVO mealSetVO,List<MealSetConVO>conList) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mealSetVO.getMeal_set_name());
			pstmt.setString(2, mealSetVO.getMeal_set_info());
			pstmt.setBytes(3, mealSetVO.getMeal_set_img());
			pstmt.setInt(4, mealSetVO.getMeal_set_price());
			pstmt.setInt(5, mealSetVO.getMeal_set_sts());
			pstmt.setInt(6, mealSetVO.getCat_no());
			pstmt.setString(7, mealSetVO.getMeal_set_no());

			pstmt.executeUpdate();
			
			
			MealSetConDAO conDAO = new MealSetConDAO();
			for(MealSetConVO mealSetConVO:conList ) {
				mealSetConVO.setMeal_set_no(mealSetVO.getMeal_set_no());
				conDAO.insert(mealSetConVO,con);
			}
			
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
	
	public MealSetVO searchByNo(String keyWord) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealSetVO mealSetVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCHBYNO);
			pstmt.setString(1, "%" + keyWord + "%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mealSetVO = new MealSetVO();
				mealSetVO.setMeal_set_no(rs.getString("meal_set_no"));
				mealSetVO.setMeal_set_name(rs.getString("meal_set_name"));
				mealSetVO.setMeal_set_info(rs.getString("meal_set_info"));
				mealSetVO.setMeal_set_img(rs.getBytes("meal_set_img"));
				mealSetVO.setMeal_set_price(rs.getInt("meal_set_price"));
				mealSetVO.setMeal_set_sts(rs.getInt("meal_set_sts"));
				mealSetVO.setCat_no(rs.getInt("cat_no"));
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
		return mealSetVO;

	};
	
	public List<MealSetVO> searchByNoAndName(String keyWord) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealSetVO> list = new ArrayList<MealSetVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCHBYNO);
			pstmt.setString(1, "%" + keyWord + "%");
			pstmt.setString(2, "%" + keyWord + "%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MealSetVO mealSetVO = new MealSetVO();
				mealSetVO.setMeal_set_no(rs.getString("meal_set_no"));
				mealSetVO.setMeal_set_name(rs.getString("meal_set_name"));
				mealSetVO.setMeal_set_info(rs.getString("meal_set_info"));
				mealSetVO.setMeal_set_img(rs.getBytes("meal_set_img"));
				mealSetVO.setMeal_set_price(rs.getInt("meal_set_price"));
				mealSetVO.setMeal_set_sts(rs.getInt("meal_set_sts"));
				mealSetVO.setCat_no(rs.getInt("cat_no"));
				list.add(mealSetVO);
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

	};
	
	public List<MealSetVO> searchByMealSetSts(Integer mealSetSts){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealSetVO> list = new ArrayList<MealSetVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCHBYMEALSETSTS);
			pstmt.setInt(1, mealSetSts);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MealSetVO mealSetVO = new MealSetVO();
				mealSetVO.setMeal_set_no(rs.getString("meal_set_no"));
				mealSetVO.setMeal_set_name(rs.getString("meal_set_name"));
				mealSetVO.setMeal_set_info(rs.getString("meal_set_info"));
				mealSetVO.setMeal_set_img(rs.getBytes("meal_set_img"));
				mealSetVO.setMeal_set_price(rs.getInt("meal_set_price"));
				mealSetVO.setMeal_set_sts(rs.getInt("meal_set_sts"));
				mealSetVO.setCat_no(rs.getInt("cat_no"));
				list.add(mealSetVO);
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
		
	};
	
	public List<MealSetVO> getAll() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealSetVO> list = new ArrayList<MealSetVO>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MealSetVO mealSetVO = new MealSetVO();
				mealSetVO.setMeal_set_no(rs.getString("meal_set_no"));
				mealSetVO.setMeal_set_name(rs.getString("meal_set_name"));
				mealSetVO.setMeal_set_info(rs.getString("meal_set_info"));
				mealSetVO.setMeal_set_img(rs.getBytes("meal_set_img"));
				mealSetVO.setMeal_set_price(rs.getInt("meal_set_price"));
				mealSetVO.setMeal_set_sts(rs.getInt("meal_set_sts"));
				mealSetVO.setCat_no(rs.getInt("cat_no"));
				list.add(mealSetVO);
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

	};

}
