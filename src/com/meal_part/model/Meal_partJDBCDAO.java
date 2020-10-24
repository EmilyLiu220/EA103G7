package com.meal_part.model;

import java.util.*;

import com.food.model.FoodVO;

import java.sql.*;

public class Meal_partJDBCDAO implements Meal_partDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO MEAL_PART (MEAL_NO,FD_NO,FD_GW) VALUES (?,?,?)";
	private static final String DELETE = "DELETE FROM MEAL_PART WHERE MEAL_NO=? AND FD_NO=?";
	private static final String GET_ALL_STMT = "SELECT MEAL_NO,FD_NO,FD_GW FROM MEAL_PART";
	private static final String GET_ONE_STMT = "SELECT FD_GW FROM MEAL_PART WHERE MEAL_NO=? AND FD_NO=?";
	private static final String UPDATE = "UPDATE MEAL_PART SET FD_GW=? WHERE MEAL_NO=? AND FD_NO=?";
	private static final String GET_NUT_ByMealno_STMT =
			"select n.nut_name,sum(mp.fd_gw*nv.nut_per100/100) as nuts from meal_part mp " +
			"join nut_value nv on nv.fd_no=mp.fd_no " +
			"join nut n on n.nut_no=nv.nut_no " +
			"where mp.meal_no=? " +
			"group by n.nut_name";
	
	@Override
	public void insert(Meal_partVO meal_partVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, meal_partVO.getMeal_no());
			pstmt.setString(2, meal_partVO.getFd_no());
			pstmt.setDouble(3, meal_partVO.getFd_gw());
			pstmt.executeUpdate();			
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
	public void update(Meal_partVO meal_partVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setDouble(1, meal_partVO.getFd_gw());
			pstmt.setString(2, meal_partVO.getMeal_no());
			pstmt.setString(3, meal_partVO.getFd_no());
			pstmt.executeUpdate();

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
	public void delete(String meal_no, String fd_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, meal_no);
			pstmt.setString(2, fd_no);

			pstmt.executeUpdate();
			
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
	public Meal_partVO findByPrimaryKey(String meal_no, String fd_no) {
		Meal_partVO meal_partVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, meal_no);
			pstmt.setString(2, fd_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				meal_partVO = new Meal_partVO();
				meal_partVO.setMeal_no(meal_no);
				meal_partVO.setFd_no(fd_no);
				meal_partVO.setFd_gw(rs.getDouble("fd_gw"));
			}
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
		return meal_partVO;
	}

	@Override
	public List<Meal_partVO> getAll() {
		List<Meal_partVO> list = new ArrayList<Meal_partVO>();
		Meal_partVO meal_partVO = null;		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				meal_partVO = new Meal_partVO();
				meal_partVO.setMeal_no(rs.getString("meal_no"));
				meal_partVO.setFd_no(rs.getString("fd_no"));
				meal_partVO.setFd_gw(rs.getDouble("fd_gw"));
				list.add(meal_partVO);
			}

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
		return list;
	}

	@Override
	public Map<String,Double> get_NUT_ByMealno(String meal_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String,Double> map=null;
		try {
			map=new HashMap<String,Double>();
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_NUT_ByMealno_STMT);
			
			pstmt.setString(1, meal_no);		
			rs = pstmt.executeQuery();		
			while(rs.next()) {
				map.put(rs.getString(1), rs.getDouble(2));
			}
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return map;
	}	

	public static void main(String[] args) {

		Meal_partJDBCDAO dao = new Meal_partJDBCDAO();

		//新增
		Meal_partVO meal_partVO1 = new Meal_partVO();
		meal_partVO1.setMeal_no("111");
		meal_partVO1.setFd_no("333");
		meal_partVO1.setFd_gw(new Double(333));
//		dao.insert(meal_partVO1);

		//修改
		Meal_partVO meal_partVO2 = new Meal_partVO();
		meal_partVO2.setMeal_no("111");
		meal_partVO2.setFd_no("111");
		meal_partVO2.setFd_gw(new Double(144));
//		dao.update(meal_partVO2);

		//刪除
//		dao.delete("333", "333");

		//查詢
//		Meal_partVO meal_partVO3 = dao.findByPrimaryKey("222", "222");
//		System.out.print("Meal_no=" + meal_partVO3.getMeal_no()+ ",");
//		System.out.print("Fd_no=" + meal_partVO3.getFd_no() + ",");
//		System.out.print("Fd_gw=" + meal_partVO3.getFd_gw());
//		System.out.println();
//		System.out.println("---------------------");

		//查詢
		List<Meal_partVO> list = dao.getAll();
		for (Meal_partVO aMeal_partVO : list) {
			System.out.print("Meal_no=" + aMeal_partVO.getMeal_no()+ ",");
			System.out.print("Fd_no=" + aMeal_partVO.getFd_no() + ",");
			System.out.print("Fd_gw=" + aMeal_partVO.getFd_gw());
			System.out.println();
		}
		
		//查詢
		Map<String,Double> map=dao.get_NUT_ByMealno("MEAL0001");
		Set set=map.keySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {		
			String key=(String)it.next();
			Double value=map.get(key);
			System.out.println(key+":"+value+"g");
		}
	}

	@Override
	public void insert(List<Meal_partVO> list, Connection con) {
		// TODO Auto-generated method stub
		//因為是要用連線，所以沒寫JDBC版本
	}
}