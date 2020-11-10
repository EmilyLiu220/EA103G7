package com.meal.model;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.meal_part.model.Meal_partDAO;
import com.meal_part.model.Meal_partJDBCDAO;
import com.meal_part.model.Meal_partVO;


public class MealJDBCDAO implements MealDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";
	
	private static final String INSERT = "insert into meal (meal_no,meal_name,meal_info,meal_img,meal_price,meal_sts,cat_no) values ('MEAL' || LPAD(seq_meal_no.nextval, 4, 0),?,?,?,?,?,?)";
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
				System.out.println(mealNo);
			}
			List<Meal_partVO> partList2 = new ArrayList<Meal_partVO>();
			Meal_partJDBCDAO partDAO = new Meal_partJDBCDAO();
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

	public void update(MealVO mealVO,List<Meal_partVO> partList) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (SQLException | ClassNotFoundException se) {
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);	
			pstmt = con.prepareStatement(SEARCHBYNOANDNAME);
			
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
		return mealVO;

	};
	
	public List<MealVO> searchByNoAndName(String keyWord) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealVO> list =new ArrayList();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);	
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
				mealVO.setCat_no(rs.getInt("meal_cat"));
				list.add(mealVO);

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
	
public List<MealVO> searchByMealSts(Integer mealSts){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealVO> list = new ArrayList<MealVO>();
		MealVO mealVO = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (SQLException | ClassNotFoundException se) {
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt=con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealVO = new MealVO();
				
				mealVO.setMeal_no(rs.getString("meal_no"));
				mealVO.setMeal_name(rs.getString("meal_name"));
				mealVO.setMeal_info(rs.getString("meal_info"));
				mealVO.setMeal_img(rs.getBytes("meal_img"));
				mealVO.setMeal_price(rs.getInt("meal_price"));
				mealVO.setMeal_sts(rs.getInt("meal_sts"));
				mealVO.setCat_no(rs.getInt("meal_cat"));
				list.add(mealVO);

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
	
	public List<MealVO> getAll2() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealVO> list = new ArrayList<MealVO>();
		MealVO mealVO = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (SQLException | ClassNotFoundException se) {
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
	
	public static void main(String[] args) throws IOException{
		
		/*-------------------insert����-------------------*/
		MealJDBCDAO dao = new MealJDBCDAO();
//		
//		MealVO mealVO = new MealVO();
//		
//		mealVO.setMeal_name("香蕉火鍋");
//		mealVO.setMeal_info("跟中央的食物一樣難吃");
//		mealVO.setMeal_img(getPicByteArray());
//		mealVO.setMeal_price(888);
//		mealVO.setMeal_sts(0);
//		mealVO.setCat_no(10);
//		
//		Meal_partVO partVO = new Meal_partVO();
//		partVO.setFd_no("FD0001");
//		// 馮改1020
//		partVO.setFd_gw(new Double(500));
//		
//		Meal_partVO partVO2 = new Meal_partVO();
//		partVO2.setFd_no("FD0002");
//		// 馮改1020
//		partVO2.setFd_gw(new Double(900));
//		
//		List<Meal_partVO> partList = new ArrayList<>();
//		partList.add(partVO);
//		partList.add(partVO2);
//		
//		dao.insert(mealVO,partList);
		
		/*-------------------update����-------------------*/
		
//		MealVO mealVO2 = new MealVO();
//		
//		mealVO2.setMeal_no("MEAL003");
//		mealVO2.setMeal_name("�»��n��");
//		mealVO2.setMeal_info("�����Y");
//		mealVO2.setMeal_img(getPicByteArray());
//		mealVO2.setMeal_price(100);
//		mealVO2.setMeal_sts(0);
//		
//		dao.update(mealVO2);
		
		/*-------------------getone����-------------------*/
		
//		MealVO mealVO3 = dao.search("MEAL003");
//		System.out.println(mealVO3.getMealNo());
//		System.out.println(mealVO3.getMealName());
//		System.out.println(mealVO3.getMealInfo());
//		readPic(mealVO3.getMealImg());
//		File dir = new File("C:\\Users\\Big data\\Desktop\\projectTest");
//		File file = new File(dir,"picture.jpg");
//		FileOutputStream fos = new FileOutputStream(file,true);
//		fos.write(mealVO3.getMealImg());
//		fos.flush();
//		fos.close();
//		
//		System.out.println(mealVO3.getMealPrice());
//		System.out.println(mealVO3.getMealSts());
		
		/*searchbymealsts*/
		
		List<MealVO> list = dao.searchByMealSts(0);
		for(MealVO mealVO5 : list) {
			System.out.println(mealVO5.getMeal_no());
		}
		
		/*-------------------getall����-------------------*/
		
//		List<MealVO> list = dao.getAll();
//		for (MealVO mealVO4 : list) {
//			System.out.print(mealVO4.getMealNo() + ",");
//			System.out.print(mealVO4.getMealName() + ",");
//			System.out.print(mealVO4.getMealInfo() + ",");
////			File dir = new File("C:\\Users\\Big data\\Desktop\\projectTest");
////			File file = new File(dir,"picture.jpg");
////			FileOutputStream fos = new FileOutputStream(file,false);
////			fos.write(mealVO4.getMealImg());
////			fos.flush();
////			fos.close();
//			System.out.print(mealVO4.getMealPrice()+ ",");
//			System.out.print(mealVO4.getMealSts());
//		}
//		
		
	}
	
	public static byte[] getPicByteArray() throws IOException{
		File file = new File("Webcontent/meal_Img/hotpot3.PNG");
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
	public static void readPic(byte[] bytes) throws IOException {
		FileOutputStream fos = new FileOutputStream("picture");
		fos.write(bytes);
		fos.flush();
		fos.close();
	}

}

	
