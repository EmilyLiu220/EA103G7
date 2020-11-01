package com.food.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.meal_order_detail.model.MealOrderDetailVO;
import com.meal_part.model.Meal_partDAO;
import com.meal_part.model.Meal_partVO;
import com.meal_set_consist.model.MealSetConDAO;
import com.meal_set_consist.model.MealSetConVO;

import java.sql.*;

public class FoodDAO implements FoodDAO_interface {
	private static final String INSERT_STMT = 
			"INSERT INTO FOOD (FD_NO,FD_NAME,FD_ISDEL,FD_STK,STK_LL,CAL,PROT,CARB,FAT) VALUES ('FD'||LPAD(SEQ_FD_NO.NEXTVAL,4,0),?,?,?,?,?,?,?,?)";
	private static final String DELETE = 
			"UPDATE FOOD SET FD_ISDEL='0' WHERE FD_NO = ?";
	private static final String GET_ALL_STMT = 
			"SELECT FD_NO,FD_NAME,FD_ISDEL,FD_STK,STK_LL,CAL,PROT,CARB,FAT FROM FOOD WHERE FD_ISDEL='1'";
	private static final String GET_ONE_STMT = 
			"SELECT FD_NAME,FD_ISDEL,FD_STK,STK_LL,CAL,PROT,CARB,FAT FROM FOOD WHERE FD_NO = ?";
	private static final String UPDATE = 
			"UPDATE FOOD SET FD_NAME=?,FD_ISDEL=?,FD_STK=?,STK_LL=?,CAL=?,PROT=?,CARB=?,FAT=? WHERE FD_NO = ?";
	private static final String UPDATE_STK = 
			"UPDATE FOOD SET FD_STK=? WHERE FD_NO = ?";
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insert(FoodVO foodVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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

	}

	@Override
	public void delete(String fd_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, fd_no);
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

	}

	@Override
	public FoodVO findByPrimaryKey(String fd_no) {

		FoodVO foodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, fd_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
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
	}
	
	@Override
	public String getFdnameByFdno(String fd_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String fd_name;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, fd_no);
			rs = pstmt.executeQuery();
			rs.next();
			fd_name= rs.getString("fd_name");
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
		return fd_name;
	}
	
	public Map<String,Double> GetFdnoAndQtyByListMealOrderDetail(List<MealOrderDetailVO> list){
		//用來取出所有的meal，再從所有的meal取出fd_no，以及這筆訂單相對應食材所需要的數量
		Map<String,Integer> mealMap=new HashMap<String,Integer>(); //單點的部分
		for(MealOrderDetailVO modVO:list) {
			if(modVO.getMeal_no()!=null) {
				//一筆訂單細節，要不就是餐點，要不就是套餐，用有沒有餐點編號當判斷
				if(mealMap.containsKey(modVO.getMeal_no())) { 
					//mealMap內有相同的meal_no
					mealMap.put(modVO.getMeal_no(), mealMap.get(modVO.getMeal_no())+modVO.getQty());
					//把相同的meal_no的值取出，加上meal_no的數量放回去，因為key值相同會直接覆蓋原有的value
					System.out.print("套餐"+modVO.getMeal_no()+" "+modVO.getMeal_name());
					System.out.println(" 數量"+mealMap.get(modVO.getMeal_no())+modVO.getQty());
				}else{
					//mealMap內有沒有相同的meal_no
					mealMap.put(modVO.getMeal_no(),modVO.getQty());
					System.out.print("套餐"+modVO.getMeal_no()+" "+modVO.getMeal_name());
					System.out.println(" 數量"+modVO.getQty());
					//把meal_no當key,qty當value放入map內
				}
			}//到這裡就處理好所有的單點餐點，不用else處理套餐，另寫一個if來處理，同時處理有點複雜晚點再處理
		}		
		Map<String,Integer> mealSetMap=new HashMap<String,Integer>();
		for(MealOrderDetailVO modVO:list) {
			if(modVO.getMeal_set_no()!=null) {
				mealSetMap.put(modVO.getMeal_set_no(),modVO.getQty());
				System.out.print("套餐的合併"+modVO.getMeal_set_no()+" "+modVO.getMeal_name());
				System.out.println(" 訂單數量"+modVO.getQty());
			}
		}//map內有所有的套餐編號和數量，接著把所有的套餐取出他組成的餐點
		MealSetConDAO MSCDao=new MealSetConDAO();
		for(Map.Entry<String,Integer> mealSetnoMap:mealSetMap.entrySet()) {
			//map的foreach
			for(MealSetConVO mscVO:MSCDao.searchBySetNo(mealSetnoMap.getKey())) {
			//取出套餐中每個餐點
				if(mealMap.containsKey(mscVO.getMeal_no())) { 
					//邏輯同上
					mealMap.put(mscVO.getMeal_no(), mealMap.get(mscVO.getMeal_no())+mscVO.getMeal_qty()*mealSetnoMap.getValue());
					//有相同的餐點則取出餐點原有的數量+(該餐點在套餐中的數量*套餐的數量)
				}else {
					mealMap.put(mscVO.getMeal_no(), mscVO.getMeal_qty()*mealSetnoMap.getValue());
					//沒有相同的餐點則該餐點在套餐中的數量*套餐的數量
				}
			}
		}//取出所有套餐的餐點，將meal_no當key和數量當value放入mealMap
		//----
		for(Map.Entry<String,Integer> map:mealMap.entrySet()) {
			System.out.println(map.getKey()+" "+map.getValue());
		}
		//----
		Meal_partDAO MPDao=new Meal_partDAO();
		Map<String,Double> foodMap=new HashMap<String,Double>();
		for(Map.Entry<String,Integer> mealnoMap:mealMap.entrySet()) {
			for(Meal_partVO meal_partVO:MPDao.get_meal_part_by_mealno(mealnoMap.getKey())) {
				if(foodMap.containsKey(meal_partVO.getFd_no())) {
					foodMap.put(meal_partVO.getFd_no(), foodMap.get(meal_partVO.getFd_no())+meal_partVO.getFd_gw()*mealnoMap.getValue());
				}else {
					foodMap.put(meal_partVO.getFd_no(), meal_partVO.getFd_gw()*mealnoMap.getValue());
				}
			}
		}//取出所有的食材，將fd_no當key和數量當value放入foodMap		
		//---
		System.out.println("-----------------");
		for(Map.Entry<String,Double> map:foodMap.entrySet()) {
			System.out.println(map.getKey()+" "+map.getValue());
		}
		//---
		return foodMap;
	}	
	
	public void update(List<MealOrderDetailVO> list,Connection con){
		FoodDAO foodDao = new FoodDAO();
		Map<String,Double> foodnoMap=foodDao.GetFdnoAndQtyByListMealOrderDetail(list);
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(UPDATE_STK);
			for(Map.Entry<String,Double> map:foodnoMap.entrySet()) {
				pstmt.setInt(1, foodDao.findByPrimaryKey(map.getKey()).getFd_stk()-map.getValue().intValue());
				//原本應該要把庫存與庫存底線改成Double比較合理，但需要改的地方實在太多，直接轉型
				pstmt.setString(2, map.getKey());
				pstmt.executeUpdate();
			}
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
	}
	
	public boolean check_food(List<MealOrderDetailVO> list) { 
		//檢查並回傳夠不夠，足夠就update並回傳ture，不足就回傳false
		//參數是map<食材的編號,所需的重量>
		FoodDAO FdDao=new FoodDAO();
		Map<String,Double> foodMap=FdDao.GetFdnoAndQtyByListMealOrderDetail(list);
		boolean enough=true;
		//當值為false,break
		System.out.println("-----------------");
		for(Map.Entry<String,Double> fdnoMap:foodMap.entrySet()) {
			System.out.print("庫存量:"+FdDao.findByPrimaryKey(fdnoMap.getKey()).getFd_stk());
			System.out.println(" 數量:"+fdnoMap.getValue());
			if(FdDao.findByPrimaryKey(fdnoMap.getKey()).getFd_stk()-fdnoMap.getValue()<0) {
				enough=false;
				break;
			}
		}//用食材編號查詢該食材的重量，相減若有不夠，break,return false
		
		if(enough) {			
			return true;
		}
		return false;
	}
}