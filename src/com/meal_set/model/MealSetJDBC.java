package com.meal_set.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.meal.model.MealVO;
import com.meal_set_consist.model.MealSetConDAO;
import com.meal_set_consist.model.MealSetConJDBCDAO;
import com.meal_set_consist.model.MealSetConVO;

public class MealSetJDBC implements MealSetDAO_interface {
	
	String DRIVER = "oracle.jdbc.driver.OracleDriver";
	String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	String USERNAME = "EA103G7";
	String PASSWORD = "123456";
	
	private static final String INSERT = "insert into meal_set (meal_set_no,meal_set_name,meal_set_info,meal_set_img,meal_set_price,meal_set_sts,cat_no) values ('MSET' || LPAD(seq_meal_set_no.nextval, 3, 0),?,?,?,?,?,?)";
	private static final String UPDATE = "update meal_set set meal_set_name=?,meal_set_info=?,meal_set_img=?,meal_set_price=?,meal_set_sts=?,cat_no=? where meal_set_no=?";
	private static final String SEARCHBYNO ="select * from meal_set where meal_set_no like ?";
	private static final String SEARCHBYNOANDNAME ="select * from meal_set where meal_set_name like ? or meal_set_no like ?";
	private static final String SEARCHBYMEALSETSTS = "select * from meal_set where meal_set_sts =?";
	private static final String GETALL = "select * from meal_set order by meal_set_no";

	public void insert(MealSetVO mealSetVO,List<MealSetConVO>conList) {
		
		Connection con =null;
		PreparedStatement pstmt = null;
		String nextNo = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
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
			
			MealSetConJDBCDAO conDAO = new MealSetConJDBCDAO();
			for(MealSetConVO mealSetConVO:conList ) {
				mealSetConVO.setMeal_set_no(nextNo);
				conDAO.insert(mealSetConVO,con);
			}
			
			con.commit();			
		}catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			}
		finally {
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
		
		Connection con =null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, mealSetVO.getMeal_set_name());
			pstmt.setString(2, mealSetVO.getMeal_set_info());
			pstmt.setBytes(3, mealSetVO.getMeal_set_img());
			pstmt.setInt(4, mealSetVO.getMeal_set_price());
			pstmt.setInt(5, mealSetVO.getMeal_set_sts());
			pstmt.setInt(6, mealSetVO.getCat_no());
			pstmt.setString(7,mealSetVO.getMeal_set_no());
			
			pstmt.executeUpdate();
			

			MealSetConJDBCDAO conDAO = new MealSetConJDBCDAO();
			for(MealSetConVO mealSetConVO:conList ) {
				mealSetConVO.setMeal_set_no(mealSetVO.getMeal_set_no());
				conDAO.insert(mealSetConVO,con);
			}
			
			con.commit();			

			
		}catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			}
		finally {
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);	
			pstmt = con.prepareStatement(SEARCHBYNO);
			
			pstmt.setString(1, "%" + keyWord + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
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
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());}
		finally {
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
		return mealSetVO;

	};
	
	public List<MealSetVO> searchByNoAndName(String keyWord) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealSetVO> list = new ArrayList<MealSetVO>();
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);	
			pstmt = con.prepareStatement(SEARCHBYNOANDNAME);
			
			pstmt.setString(1, "%" + keyWord + "%");
			pstmt.setString(2, "%" + keyWord + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
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
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());}
		finally {
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
	
public List<MealSetVO> searchByMealSetSts(Integer mealSetSts){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealSetVO> list = new ArrayList<MealSetVO>();

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
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

			

		} catch (SQLException | ClassNotFoundException se) {
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt=con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
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
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());}
		finally {
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
	
	public static void main(String[] args) throws IOException {
		
		MealSetJDBC dao = new MealSetJDBC();
		
		/*insert*/
		MealSetVO mealSetVO = new MealSetVO();
		mealSetVO.setMeal_set_name("肥宅套餐");
		mealSetVO.setMeal_set_info("肥宅的最愛");
		mealSetVO.setMeal_set_img(getPicByteArray());
		mealSetVO.setMeal_set_price(5800);
		mealSetVO.setMeal_set_sts(1);
		mealSetVO.setCat_no(50);
		
		MealSetConVO mealSetConVO = new MealSetConVO();
		mealSetConVO.setMeal_no("MEAL0002");
		mealSetConVO.setMeal_qty(1);
		
		MealSetConVO mealSetConVO2 = new MealSetConVO();
		mealSetConVO2.setMeal_no("MEAL0008");
		mealSetConVO2.setMeal_qty(2);
		
		List<MealSetConVO> conList = new ArrayList<>();
		conList.add(mealSetConVO);
		conList.add(mealSetConVO2);
		
		dao.insert(mealSetVO,conList);
		
		/*update*/
//		MealSetVO mealSetVO2 = new MealSetVO();
//		mealSetVO2.setMeal_set_name("修改套餐");
//		mealSetVO2.setMeal_set_info("嘿嘿嘿");
//		mealSetVO2.setMeal_set_img(getPicByteArray());
//		mealSetVO2.setMeal_set_price(999);
//		mealSetVO2.setMeal_set_sts(1);
//		mealSetVO2.setCat_no(50);
//		mealSetVO2.setMeal_set_no("MES0001");
//		dao.update(mealSetVO2);
		
		/*searchByNo*/
//		MealSetVO mealSetVO3 = dao.searchByNo("MES0001");
//		System.out.print(mealSetVO3.getMeal_set_no()+",");
//		System.out.print(mealSetVO3.getMeal_set_name()+",");
//		System.out.print(mealSetVO3.getMeal_set_info()+",");
//		System.out.print(mealSetVO3.getMeal_set_price()+",");
//		System.out.print(mealSetVO3.getMeal_set_sts()+",");
//		System.out.print(mealSetVO3.getCat_no());
		
		/*searchByNoAndName*/
		
//		List<MealSetVO> list = dao.searchByNoAndName("火鍋");
//		List<MealSetVO> list = dao.searchByNoAndName("套餐");
//		
//		for(MealSetVO mealSetVO : list) {
//			System.out.print(mealSetVO.getMeal_set_no()+",");
//			System.out.print(mealSetVO.getMeal_set_name()+",");
//			System.out.print(mealSetVO.getMeal_set_info()+",");
//			System.out.print(mealSetVO.getMeal_set_price()+",");
//			System.out.print(mealSetVO.getMeal_set_sts()+",");
//			System.out.println(mealSetVO.getCat_no());
//		}
		
		/*getall*/
		
//		List<MealSetVO> list = dao.getAll();
//		for(MealSetVO mealSetVO2 : list) {
//			System.out.print(mealSetVO2.getMeal_set_no()+",");
//			System.out.print(mealSetVO2.getMeal_set_name()+",");
//			System.out.print(mealSetVO2.getMeal_set_info()+",");
//			System.out.print(mealSetVO2.getMeal_set_price()+",");
//			System.out.print(mealSetVO2.getMeal_set_sts()+",");
//			System.out.println(mealSetVO2.getCat_no());
//		}
		
		
		
	}
	
	public static byte[] getPicByteArray() throws IOException{
		File file = new File("WebContent/back-end/meal/meal_Img/hotpot3.PNG");
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
			baos.flush();
		}
		baos.close();
		fis.close();
		
		return baos.toByteArray();
		
	}
	
	
	
}
