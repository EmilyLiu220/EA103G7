package com.food.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;

public class FoodDAO implements FoodDAO_interface {
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
			"INSERT INTO FOOD (FD_NO,FD_NAME,FD_ISDEL,FD_STK,STK_LL,CAL,PROT,CARB,FAT) VALUES ('FD'||LPAD(SEQ_FD_NO.NEXTVAL,4,0),?,?,?,?,?,?,?,?)";
	private static final String DELETE = 
			"UPDATE FOOD SET FD_ISDEL='0' WHERE FD_NO = ?";
	private static final String GET_ALL_STMT = 
			"SELECT FD_NO,FD_NAME,FD_ISDEL,FD_STK,STK_LL,CAL,PROT,CARB,FAT FROM FOOD WHERE FD_ISDEL='1'";
	private static final String GET_ONE_STMT = 
			"SELECT FD_NAME,FD_ISDEL,FD_STK,STK_LL,CAL,PROT,CARB,FAT FROM FOOD WHERE FD_NO = ? AND FD_ISDEL='1'";
	private static final String UPDATE = 
			"UPDATE FOOD SET FD_NAME=?,FD_ISDEL=?,FD_STK=?,STK_LL=?,CAL=?,PROT=?,CARB=?,FAT=? WHERE FD_NO = ?";
	private static final String Statistics =
			"select f.fd_no,fd_name,to_char(order_time,'yyyy')as s_year,to_char(order_time,'mm') as s_month, sum(qty)*fd_gw as qty from meal_order_detail meod " +
			"join meal_order mo on mo.meal_order_no=meod.meal_order_no " +
			"join meal_part mp on mp.meal_no=meod.meal_no " +
			"join food f on f.fd_no=mp.fd_no " +
			"where meod.meal_no is not null " +
			"group by meod.meal_no,to_char(order_time,'yyyy'),to_char(order_time,'mm'),f.fd_no ,fd_name,fd_gw " +
			"union all " +
			"select f.fd_no,fd_name,to_char(order_time,'yyyy')as s_year,to_char(order_time,'mm')as s_month, sum(qty)*msc.meal_qty*fd_gw as qty from meal_order_detail meod " +
			"join meal_order mo on mo.meal_order_no=meod.meal_order_no " +
			"join meal_set_consist msc on msc.meal_set_no=meod.meal_set_no " +
			"join meal_part mp on mp.meal_no=msc.meal_no " +
			"join food f on f.fd_no=mp.fd_no " +
			"where meod.meal_set_no is not null " +
			"group by msc.meal_no,to_char(order_time,'yyyy'),to_char(order_time,'mm'),meal_qty,f.fd_no ,fd_name,fd_gw " +
			"order by fd_no,s_year,s_month";
	 
	@Override
	public void insert(FoodVO foodVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, foodVO.getFd_name());
			pstmt.setInt(2, foodVO.getFd_isdel());
			pstmt.setInt(3, foodVO.getFd_stk());
			pstmt.setInt(4, foodVO.getStk_ll());
			pstmt.setDouble(5, foodVO.getCal());
			pstmt.setDouble(6, foodVO.getProt());
			pstmt.setDouble(7, foodVO.getCarb());
			pstmt.setDouble(8, foodVO.getFat());
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void update(FoodVO foodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, foodVO.getFd_name());
			pstmt.setInt(2, foodVO.getFd_isdel());
			pstmt.setInt(3, foodVO.getFd_stk());
			pstmt.setInt(4, foodVO.getStk_ll());
			pstmt.setDouble(5, foodVO.getCal());
			pstmt.setDouble(6, foodVO.getProt());
			pstmt.setDouble(7, foodVO.getCarb());
			pstmt.setDouble(8, foodVO.getFat());
			pstmt.setString(9, foodVO.getFd_no());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String fd_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, fd_no);
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public FoodVO findByPrimaryKey(String fd_no) {

		FoodVO foodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, fd_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Vo 也稱為 Domain objects
				foodVO = new FoodVO();
				foodVO.setFd_no(fd_no);
				foodVO.setFd_name(rs.getString("FD_NAME"));
				foodVO.setFd_isdel(rs.getInt("FD_ISDEL"));
				foodVO.setFd_stk(rs.getInt("FD_STK"));
				foodVO.setStk_ll(rs.getInt("STK_LL"));
				foodVO.setCal(rs.getDouble("CAL"));
				foodVO.setProt(rs.getDouble("PROT"));
				foodVO.setCarb(rs.getDouble("CARB"));
				foodVO.setFat(rs.getDouble("FAT"));				
			}
			// Handle any driver errors
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
		return foodVO;
	}

	@Override
	public List<FoodVO> getAll() {
		List<FoodVO> list = new ArrayList<FoodVO>();
		FoodVO foodVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// VO 也稱為 Domain objects
				foodVO = new FoodVO();
				foodVO.setFd_no(rs.getString("FD_NO"));
				foodVO.setFd_name(rs.getString("FD_NAME"));
				foodVO.setFd_isdel(rs.getInt("FD_ISDEL"));
				foodVO.setFd_stk(rs.getInt("FD_STK"));
				foodVO.setStk_ll(rs.getInt("STK_LL"));
				foodVO.setCal(rs.getDouble("CAL"));
				foodVO.setProt(rs.getDouble("PROT"));
				foodVO.setCarb(rs.getDouble("CARB"));
				foodVO.setFat(rs.getDouble("FAT"));	
				list.add(foodVO);
			}

			// Handle any driver errors
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
	public String getFdnameByFdno(String fd_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, fd_no);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getString("fd_name");
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
	}
	
	public List<List<String>> eachMonthFoodStatistics(){
		FoodDAO dao=new FoodDAO();
		List<List<String>> list =dao.Statistics();
		List<List<String>> statList=new ArrayList<>();
		List<String> statData=null; //統整資料，一筆資料就有1~12個月(為了前端畫統計圖的元件需要)
		List<FoodVO> fList=dao.getAll();
		for(int i=0;i<fList.size();i++) {
			statData=new ArrayList<>();
			statData.add(fList.get(i).getFd_no());//編號
			statData.add(fList.get(i).getFd_name());//食材名稱
			statData.add("2020");//年，寫死，只有一年
			for(int j=0;j<12;j++) {
				statData.add("0");//使用量初始化，1~12個月
			}
			statList.add(statData);				
		}
		//--------------------------------------------
//		for(List<String> l: statList) {
//			for(int i=0;i<l.size();i++) {
//				System.out.printf("%10s",l.get(i));
//			}
//			System.out.println();
//		}
		//--------------------------------------------
		for(int i=0;i<list.size();i++) {
			for(int j=0;j<statList.size();j++) {
//				System.out.println("list="+list.get(i).get(0));
//				System.out.println("statList"+statList.get(j).get(0) );
				if(list.get(i).get(0).equals( statList.get(j).get(0) )) {
					int month=Integer.valueOf(list.get(i).get(3));
//					System.out.println(month);
//					System.out.println(list.get(i).get(3));
					if(statList.get(j).get(month+2).equals("0")) {
						statList.get(j).set(month+2,list.get(i).get(4));
						break;
					}else {
						statList.get(j).set(month+2,list.get(i).get(4)+Double.valueOf(statList.get(j).get(month+3)));
						break;
					}
				}
			}
		}
//		System.out.println(statList);
//		for(List<String> data1:list) {
//			System.out.println("編號"+data1.get(0)+" 食材"+data1.get(1)+"年分"+data1.get(2)+"月份"+data1.get(3)+"使用量"+data1.get(4));
//		}
		return statList;
	}
	
	@Override
	public List<List<String>> Statistics() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> data;
		List<List<String>> list=new ArrayList<>();
		List<List<String>> statList=null;
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(Statistics);
			rs = pstmt.executeQuery();
			boolean flag=false;
			//select f.fd_no,fd_name,to_char(order_time,'yyyy')as s_year,to_char(order_time,'mm') as s_month, sum(qty)*fd_gw as qty
			FoodDAO dao=new FoodDAO();
			List<FoodVO> fList=dao.getAll();
			for(int i=0;i<fList.size();i++) {
				for(int j=1;j<13;j++) {//12月
					data=new ArrayList<>();
					data.add(fList.get(i).getFd_no());//編號
					data.add(fList.get(i).getFd_name());//食材名稱
					data.add("2020");//年，寫死，只有一年
					data.add("0");//初始化
					data.add("0");//使用量初始化
					if(j<10)data.set(3,"0"+j);
					else data.set(3,""+j);
					list.add(data);
				}
			}
//			System.out.println("------------------------------------------------");
//			for(List l:list) {
//				System.out.print("編號="+l.get(0));
//				System.out.print("  名稱="+l.get(1));
//				System.out.print("  年份="+l.get(2));
//				System.out.print("  月份="+l.get(3));
//				System.out.println("  使用量="+l.get(4));
//			}
//			System.out.println("------------------------------------------------");
			while(rs.next()) {

				flag=false;
				int index=0;
				data=new ArrayList<>();
				data.add(rs.getString("fd_no"));//編號
				data.add(rs.getString("fd_name"));//食材名稱
				data.add(rs.getString("s_year"));//年
				data.add(rs.getString("s_month"));//月
				data.add(rs.getString("qty"));
				for(int i=0;i<list.size();i++) {//檢查有沒有這項
					if(//如果已經有值
						list.get(i).get(0).equals(data.get(0)) &&
						list.get(i).get(2).equals(data.get(2)) && 
						list.get(i).get(3).equals(data.get(3)) ) {
						flag=true; //已經有此項了
						index=i;
						break;
					}
				}
				if(flag) { //已經有此項了
					Double temp=rs.getDouble(5)+Double.valueOf(list.get(index).get(4));
					list.get(index).set(4,temp.toString());
//					System.out.println(list.get(index).set(4,temp.toString()));
				}else {
//					System.out.println("-------"+data+"-------");
					list.add(data);
				}
//				System.out.println(list.get(index).get(3)+"   "+list.get(index).get(4));
			}
//			System.out.println("------------------------------------------------");
//			for(List<String> l:list) {
//				System.out.print("編號="+l.get(0));
//				System.out.print("  名稱="+l.get(1));
//				System.out.print("  年份="+l.get(2));
//				System.out.print("  月份="+l.get(3));
//				System.out.println("  使用量="+l.get(4));
//			}
//			System.out.println("------------------------------------------------");
			
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
}