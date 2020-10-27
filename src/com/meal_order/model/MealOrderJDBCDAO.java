package com.meal_order.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meal_order.*;
import com.meal_order_detail.*;
import com.meal_order_detail.model.MealOrderDetailJDBCDAO;
import com.meal_order_detail.model.MealOrderDetailVO;

import jdbc.util.CompositeQuery.CompositeQuery;

public class MealOrderJDBCDAO implements MealOrderDAO_interface{
	
	String DRIVER = "oracle.jdbc.driver.OracleDriver";
	String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	String USERNAME = "EA103G7";
	String PASSWORD = "123456";
	
	private static final String INSERT = "INSERT INTO MEAL_ORDER(MEAL_ORDER_NO,MEM_NO,EMP_NO,MEAL_ORDER_STS,AMOUNT,ORDER_TIME,NOTI_STS,PAY_STS,PICKUP_TIME) VALUES ('MEO' || LPAD(SEQ_MEAL_ORDER_NO.NEXTVAL,4,0),?,?,?,?,CURRENT_TIMESTAMP,?,?,?)";
	private static final String UPDATE = "UPDATE MEAL_ORDER SET MEAL_ORDER_STS=?,NOTI_STS=?,PAY_STS=? WHERE MEAL_ORDER_NO=?";
	private static final String UPDATEPICKUPTIME = "UPDATE MEAL_ORDER SET PICKUP_TIME CURRENT_TIMESTAMP WHERE MEAL_ORDER_NO =?";
	private static final String GETONE = "SELECT * FROM MEAL_ORDER WHERE MEAL_ORDER_NO = ?";
	private static final String GETBYMEMNO = "SELECT * FROM MEAL_ORDER WHERE MEM_NO = ?";
	private static final String GETBYORDERSTS = "SELECT * FROM MEAL_ORDER WHERE MEAL_ORDER_STS = ?";
	private static final String GETBYNOTISTS = "SELECT * FROM MEAL_ORDER WHERE NOTI_STS = ?";
	private static final String GETBYPAYSTS = "SELECT * FROM MEAL_ORDER WHERE PAY_STS = ?";
	private static final String GETALL = "SELECT * FROM MEAL_ORDER ORDER BY MEAL_ORDER_NO";
	
	@Override
	public Map<String,Object> insert (MealOrderVO mealOrderVO, List<MealOrderDetailVO> detailList,Connection ...rescon) {
		
		Map<String,Object> map = new HashMap<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		String nextNo = null;
		
		try {
			if (rescon.length != 0) {
				con = rescon[0];
			} else {
				Class.forName(DRIVER);
				con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			}
//			Class.forName(DRIVER);
//			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			con.setAutoCommit(false);
			String[] col = { "MEAL_ORDER_NO" };
			pstmt = con.prepareStatement(INSERT,col);
			

			pstmt.setString(1,mealOrderVO.getMem_no());
			pstmt.setString(2,mealOrderVO.getEmp_no());
			pstmt.setInt(3,mealOrderVO.getMeal_order_sts());
			pstmt.setInt(4,mealOrderVO.getAmount());
//			pstmt.setTimestamp(5, mealOrderVO.getOrder_time());
			pstmt.setInt(5,mealOrderVO.getNoti_sts());
			pstmt.setInt(6,mealOrderVO.getPay_sts());
			pstmt.setTimestamp(7,mealOrderVO.getPickup_time());
			pstmt.execute();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				nextNo = rs.getString(1);
				mealOrderVO.setMeal_order_no(nextNo);
				System.out.println("MEAL_ORDER_NO = " + nextNo);
			}
			
			MealOrderDetailJDBCDAO detailDAO = new MealOrderDetailJDBCDAO();
			for (MealOrderDetailVO detailVO : detailList) {
				detailVO.setMeal_order_no(nextNo);
				detailDAO.insert(detailVO, con);
			}
			map.put("mealOrderVO", mealOrderVO);
			map.put("con",con);
			
			con.commit();
			return map;

		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

		
		
	};
	
	public void update (MealOrderVO mealOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1,mealOrderVO.getMeal_order_sts());
			pstmt.setInt(2,mealOrderVO.getNoti_sts());
			pstmt.setInt(3,mealOrderVO.getPay_sts());
			pstmt.setString(4,mealOrderVO.getMeal_order_no());
			pstmt.executeUpdate();

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

		
		
	};
	
	
public void updatePickupTime (String mealOrderNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(UPDATEPICKUPTIME);

			pstmt.setString(1, mealOrderNo);
			pstmt.executeUpdate();

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
		
		
		
	};
	
	public List<MealOrderVO> searchToday(Date today) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealOrderVO mealOrderVO = null;
		List<MealOrderVO> list = new ArrayList<MealOrderVO>();

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Date now = new Date();
			today.setHours(0);
			today.setMinutes(0);
			now.setHours(23);
			now.setMinutes(59);
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String begin = ft.format(today);
			String end = ft.format(now);
			System.out.println(begin);
			System.out.println(end);
			String sql = "SELECT * FROM MEAL_ORDER WHERE PICKUP_TIME >= TO_DATE('"+ begin +"','YYYY-MM-DD hh24:mi') AND PICKUP_TIME <= TO_DATE('"+ end +"','YYYY-MM-DD hh24:mi') ORDER BY PICKUP_TIME";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealOrderVO = new MealOrderVO();
				mealOrderVO.setMeal_order_no(rs.getString("meal_order_no"));
				mealOrderVO.setMem_no(rs.getString("mem_no"));
				mealOrderVO.setEmp_no(rs.getString("emp_no"));
				mealOrderVO.setMeal_order_sts(rs.getInt("meal_order_sts"));
				mealOrderVO.setAmount(rs.getInt("amount"));
				mealOrderVO.setOrder_time(rs.getTimestamp("order_time"));
				mealOrderVO.setNoti_sts(rs.getInt("noti_sts"));
				mealOrderVO.setPay_sts(rs.getInt("pay_sts"));
				mealOrderVO.setPickup_time(rs.getTimestamp("pickup_time"));
				list.add(mealOrderVO);
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
	
public List<MealOrderVO> searchByMemNo(String memNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealOrderVO mealOrderVO = null;
		List<MealOrderVO> list = new ArrayList<MealOrderVO>();

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(GETBYMEMNO);
			pstmt.setString(1, memNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealOrderVO = new MealOrderVO();
				mealOrderVO.setMeal_order_no(rs.getString("meal_order_no"));
				mealOrderVO.setMem_no(rs.getString("mem_no"));
				mealOrderVO.setEmp_no(rs.getString("emp_no"));
				mealOrderVO.setMeal_order_sts(rs.getInt("meal_order_sts"));
				mealOrderVO.setAmount(rs.getInt("amount"));
				mealOrderVO.setOrder_time(rs.getTimestamp("order_time"));
				mealOrderVO.setNoti_sts(rs.getInt("noti_sts"));
				mealOrderVO.setPay_sts(rs.getInt("pay_sts"));
				mealOrderVO.setPickup_time(rs.getTimestamp("pickup_time"));

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
	
	
	public MealOrderVO searchByOrderNo(String mealOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealOrderVO mealOrderVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(GETONE);
			pstmt.setString(1,mealOrderNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			mealOrderVO = new MealOrderVO();
			mealOrderVO.setMeal_order_no(mealOrderNo);
			mealOrderVO.setMem_no(rs.getString("mem_no"));
			mealOrderVO.setEmp_no(rs.getString("emp_no"));
			mealOrderVO.setMeal_order_sts(rs.getInt("meal_order_sts"));
			mealOrderVO.setAmount(rs.getInt("amount"));
			mealOrderVO.setOrder_time(rs.getTimestamp("order_time"));
			mealOrderVO.setNoti_sts(rs.getInt("noti_sts"));
			mealOrderVO.setPay_sts(rs.getInt("pay_sts"));
			mealOrderVO.setPickup_time(rs.getTimestamp("pickup_time"));
			
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
		return mealOrderVO;
	};
	
	
	public List<MealOrderVO>searchByOrderSts(Integer orderSts){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealOrderVO mealOrderVO = null;
		List<MealOrderVO> list = new ArrayList<MealOrderVO> ();
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(GETBYORDERSTS);
			pstmt.setInt(1,orderSts);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			mealOrderVO = new MealOrderVO();
			mealOrderVO.setMeal_order_no(rs.getString("meal_order_no"));
			mealOrderVO.setMem_no(rs.getString("mem_no"));
			mealOrderVO.setEmp_no(rs.getString("emp_no"));
			mealOrderVO.setMeal_order_sts(rs.getInt("meal_order_sts"));
			mealOrderVO.setAmount(rs.getInt("amount"));
			mealOrderVO.setOrder_time(rs.getTimestamp("order_time"));
			mealOrderVO.setNoti_sts(rs.getInt("noti_sts"));
			mealOrderVO.setPay_sts(rs.getInt("pay_sts"));
			mealOrderVO.setPickup_time(rs.getTimestamp("pickup_time"));
			list.add(mealOrderVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public List<MealOrderVO>searchByNotiSts(Integer notiSts){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealOrderVO mealOrderVO = null;
		List<MealOrderVO> list = new ArrayList<MealOrderVO> ();
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(GETBYNOTISTS);
			pstmt.setInt(1,notiSts);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			mealOrderVO = new MealOrderVO();
			mealOrderVO.setMeal_order_no(rs.getString("meal_order_no"));
			mealOrderVO.setMem_no(rs.getString("mem_no"));
			mealOrderVO.setEmp_no(rs.getString("emp_no"));
			mealOrderVO.setMeal_order_sts(rs.getInt("meal_order_sts"));
			mealOrderVO.setAmount(rs.getInt("amount"));
			mealOrderVO.setOrder_time(rs.getTimestamp("order_time"));
			mealOrderVO.setNoti_sts(rs.getInt("noti_sts"));
			mealOrderVO.setPay_sts(rs.getInt("pay_sts"));
			mealOrderVO.setPickup_time(rs.getTimestamp("pickup_time"));
			list.add(mealOrderVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public List<MealOrderVO>searchByPaySts(Integer paySts){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealOrderVO mealOrderVO = null;
		List<MealOrderVO> list = new ArrayList<MealOrderVO> ();
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(GETBYPAYSTS);
			pstmt.setInt(1,paySts);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			mealOrderVO = new MealOrderVO();
			mealOrderVO.setMeal_order_no(rs.getString("meal_order_no"));
			mealOrderVO.setMem_no(rs.getString("mem_no"));
			mealOrderVO.setEmp_no(rs.getString("emp_no"));
			mealOrderVO.setMeal_order_sts(rs.getInt("meal_order_sts"));
			mealOrderVO.setAmount(rs.getInt("amount"));
			mealOrderVO.setOrder_time(rs.getTimestamp("order_time"));
			mealOrderVO.setNoti_sts(rs.getInt("noti_sts"));
			mealOrderVO.setPay_sts(rs.getInt("pay_sts"));
			mealOrderVO.setPickup_time(rs.getTimestamp("pickup_time"));
			list.add(mealOrderVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public List<MealOrderVO>getAll(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealOrderVO mealOrderVO = null;
		List<MealOrderVO> list = new ArrayList<MealOrderVO> ();
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			mealOrderVO = new MealOrderVO();
			mealOrderVO.setMeal_order_no(rs.getString("meal_order_no"));
			mealOrderVO.setMem_no(rs.getString("mem_no"));
			mealOrderVO.setEmp_no(rs.getString("emp_no"));
			mealOrderVO.setMeal_order_sts(rs.getInt("meal_order_sts"));
			mealOrderVO.setAmount(rs.getInt("amount"));
			mealOrderVO.setOrder_time(rs.getTimestamp("order_time"));
			mealOrderVO.setNoti_sts(rs.getInt("noti_sts"));
			mealOrderVO.setPay_sts(rs.getInt("pay_sts"));
			mealOrderVO.setPickup_time(rs.getTimestamp("pickup_time"));
			list.add(mealOrderVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	
	public List<MealOrderVO> getAll(Map<String,String[]> queryMap) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealOrderVO mealOrderVO = null;
		List<MealOrderVO> list = new ArrayList<MealOrderVO>();

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String queryAll ="SELECT * FROM MEAL_ORDER " + CompositeQuery.getWhereCondition(queryMap) + "ORDER BY MEAL_ORDER_NO";
//			System.out.println(queryAll);
			pstmt = con.prepareStatement(queryAll);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mealOrderVO = new MealOrderVO();
				mealOrderVO.setMeal_order_no(rs.getString("meal_order_no"));
				mealOrderVO.setMem_no(rs.getString("mem_no"));
				mealOrderVO.setEmp_no(rs.getString("emp_no"));
				mealOrderVO.setMeal_order_sts(rs.getInt("meal_order_sts"));
				mealOrderVO.setAmount(rs.getInt("amount"));
				mealOrderVO.setOrder_time(rs.getTimestamp("order_time"));
				mealOrderVO.setNoti_sts(rs.getInt("noti_sts"));
				mealOrderVO.setPay_sts(rs.getInt("pay_sts"));
				mealOrderVO.setPickup_time(rs.getTimestamp("pickup_time"));
				list.add(mealOrderVO);
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
	
	public static void main(String[] args) {
		
		MealOrderJDBCDAO dao = new MealOrderJDBCDAO();
		
		/*insert*/
		MealOrderVO mealOrderVO = new MealOrderVO();
		mealOrderVO.setMem_no("MEM0006");
		mealOrderVO.setEmp_no("EMP0002");
		mealOrderVO.setMeal_order_sts(1);
		mealOrderVO.setAmount(8888);
//		java.util.Date now = new java.util.Date();
//		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		mealOrderVO.setOrder_time(ft.format(now));
		mealOrderVO.setNoti_sts(0);
		mealOrderVO.setPay_sts(0);
		mealOrderVO.setPickup_time(new Timestamp(System.currentTimeMillis()));
		
		MealOrderDetailVO mealOrderDetailVO = new MealOrderDetailVO();
		mealOrderDetailVO.setMeal_no("MEAL0002");
		mealOrderDetailVO.setQty(5);
		mealOrderDetailVO.setDetail_amount(1688);
		
		MealOrderDetailVO mealOrderDetailVO2 = new MealOrderDetailVO();
		mealOrderDetailVO2.setMeal_no("MEAL0007");
		mealOrderDetailVO2.setQty(3);
		mealOrderDetailVO2.setDetail_amount(1100);
		
		List<MealOrderDetailVO> detailList = new ArrayList<>();
		detailList.add(mealOrderDetailVO);
		detailList.add(mealOrderDetailVO2);
		
		dao.insert(mealOrderVO,detailList);
		
		/*update*/
//		MealOrderVO mealOrderVO3 = new MealOrderVO();
//		mealOrderVO3.setMeal_order_no("MEO0051");
//		mealOrderVO3.setMeal_order_sts(1);
//		mealOrderVO3.setNoti_sts(1);
//		mealOrderVO3.setPay_sts(1);
//		dao.update(mealOrderVO3);
		
		/*searchByOrderNo*/
//		MealOrderVO mealOrderVO2 = dao.searchByOrderNo("MEO0051");
//		
//		System.out.println(mealOrderVO2.getMeal_order_no());
//		System.out.print(mealOrderVO2.getMem_no()+",");
//		System.out.print(mealOrderVO2.getEmp_no()+",");
//		System.out.print(mealOrderVO2.getMeal_order_sts()+",");
//		System.out.print(mealOrderVO2.getAmount()+",");
//		System.out.print(mealOrderVO2.getOrder_time()+",");
//		System.out.print(mealOrderVO2.getNoti_sts()+",");
//		System.out.print(mealOrderVO2.getPay_sts()+",");
//		System.out.print(mealOrderVO2.getPickup_time()+",");
		
		/*searchByOrderSts*/
//		List<MealOrderVO> list = dao.searchByOrderSts(1);
//		for(MealOrderVO mealOrderVO :list) {
//			System.out.println(mealOrderVO.getMeal_order_no());
//			System.out.print(mealOrderVO.getMem_no()+",");
//			System.out.print(mealOrderVO.getEmp_no()+",");
//			System.out.print(mealOrderVO.getMeal_order_sts()+",");
//			System.out.print(mealOrderVO.getAmount()+",");
//			System.out.print(mealOrderVO.getOrder_time()+",");
//			System.out.print(mealOrderVO.getNoti_sts()+",");
//			System.out.print(mealOrderVO.getPay_sts()+",");
//			System.out.println(mealOrderVO.getPickup_time()+",");
//		}
		
		/*searchByNotiSts*/
//		List<MealOrderVO> list = dao.searchByNotiSts(1);
//		for(MealOrderVO mealOrderVO :list) {
//			System.out.println(mealOrderVO.getMeal_order_no());
//			System.out.print(mealOrderVO.getMem_no()+",");
//			System.out.print(mealOrderVO.getEmp_no()+",");
//			System.out.print(mealOrderVO.getMeal_order_sts()+",");
//			System.out.print(mealOrderVO.getAmount()+",");
//			System.out.print(mealOrderVO.getOrder_time()+",");
//			System.out.print(mealOrderVO.getNoti_sts()+",");
//			System.out.print(mealOrderVO.getPay_sts()+",");
//			System.out.println(mealOrderVO.getPickup_time()+",");
//		}
		
		/*searchByPaySts*/
//		List<MealOrderVO> list = dao.searchByPaySts(0);
//		for(MealOrderVO mealOrderVO :list) {
//			System.out.println(mealOrderVO.getMeal_order_no());
//			System.out.print(mealOrderVO.getMem_no()+",");
//			System.out.print(mealOrderVO.getEmp_no()+",");
//			System.out.print(mealOrderVO.getMeal_order_sts()+",");
//			System.out.print(mealOrderVO.getAmount()+",");
//			System.out.print(mealOrderVO.getOrder_time()+",");
//			System.out.print(mealOrderVO.getNoti_sts()+",");
//			System.out.print(mealOrderVO.getPay_sts()+",");
//			System.out.println(mealOrderVO.getPickup_time()+",");
//		}
		
		/*getall*/
//		List<MealOrderVO> list = dao.getAll();
//		for(MealOrderVO mealOrderVO :list) {
//			System.out.println(mealOrderVO.getMeal_order_no());
//			System.out.print(mealOrderVO.getMem_no()+",");
//			System.out.print(mealOrderVO.getEmp_no()+",");
//			System.out.print(mealOrderVO.getMeal_order_sts()+",");
//			System.out.print(mealOrderVO.getAmount()+",");
//			System.out.print(mealOrderVO.getOrder_time()+",");
//			System.out.print(mealOrderVO.getNoti_sts()+",");
//			System.out.print(mealOrderVO.getPay_sts()+",");
//			System.out.println(mealOrderVO.getPickup_time()+",");
//		}
		
	}

}
