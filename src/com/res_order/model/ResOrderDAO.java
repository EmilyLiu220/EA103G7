package com.res_order.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.res_detail.model.*;

public class ResOrderDAO implements ResOrderDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO RES_ORDER (RES_NO ,MEAL_ORDER_NO ,MEM_NO ,EMP_NO, RES_TIME, RES_DATE, PEOPLE, TIME_PERI_NO, INFO_STS, SEAT_STS) VALUES ('RESO'||LPAD(SEQ_RES_NO.NEXTVAL,4,0), ?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT RES_NO ,MEAL_ORDER_NO ,MEM_NO ,EMP_NO ,RES_TIME ,RES_DATE ,PEOPLE ,TIME_PERI_NO ,INFO_STS, SEAT_STS FROM RES_ORDER ORDER BY RES_NO";
	private static final String GET_ONE_STMT = "SELECT MEAL_ORDER_NO ,MEM_NO ,EMP_NO ,RES_TIME ,RES_DATE ,PEOPLE ,TIME_PERI_NO ,INFO_STS, SEAT_STS FROM RES_ORDER WHERE RES_NO = ?";
	private static final String UPDATE = "UPDATE RES_ORDER SET MEAL_ORDER_NO=? ,MEM_NO=? ,EMP_NO=? ,RES_TIME=CURRENT_TIMESTAMP, RES_DATE=? ,PEOPLE=? ,TIME_PERI_NO=? ,INFO_STS=? ,SEAT_STS=? WHERE RES_NO = ?";
	private static final String GET_ONE_MEM_STMT = "SELECT RES_NO, MEAL_ORDER_NO, MEM_NO, EMP_NO, RES_TIME, RES_DATE, PEOPLE, TIME_PERI_NO, INFO_STS, SEAT_STS FROM RES_ORDER WHERE MEM_NO = ? ORDER BY RES_NO DESC";
	private static final String GET_ONE_MEAL_ORDER_STMT = "SELECT RES_NO, MEAL_ORDER_NO, MEM_NO, EMP_NO, RES_TIME, RES_DATE, PEOPLE, TIME_PERI_NO, INFO_STS, SEAT_STS FROM RES_ORDER WHERE MEAL_ORDER_NO = ? ";
	private static final String GET_RES_DATE_AND_TIME_PERI_STMT = "SELECT RES_NO, MEAL_ORDER_NO, MEM_NO, EMP_NO, RES_TIME, RES_DATE, PEOPLE, TIME_PERI_NO, INFO_STS, SEAT_STS FROM RES_ORDER WHERE RES_DATE = ? AND TIME_PERI_NO = ?";
	private static final String GET_BY_RES_DATE = "SELECT RES_NO ,MEAL_ORDER_NO ,MEM_NO ,EMP_NO ,RES_TIME ,RES_DATE ,PEOPLE ,TIME_PERI_NO ,INFO_STS, SEAT_STS FROM RES_ORDER WHERE RES_DATE = to_date(?,'yyyy-mm-dd') ORDER BY RES_NO";
	
	@Override
	public String insert(ResOrderVO resOrderVO, String seats_no[]) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String next_res_no = null;
		try {
			con = ds.getConnection();
			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);
			// 先新增訂位訂單
			String cols[] = { "res_no" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, resOrderVO.getMeal_order_no());
			pstmt.setString(2, resOrderVO.getMem_no());
			pstmt.setString(3, resOrderVO.getEmp_no());
			pstmt.setDate(4, resOrderVO.getRes_date());
			pstmt.setInt(5, new Integer(resOrderVO.getPeople()));
			pstmt.setString(6, resOrderVO.getTime_peri_no());
			pstmt.setInt(7, new Integer(resOrderVO.getInfo_sts()));
			pstmt.setInt(8, new Integer(resOrderVO.getSeat_sts()));

			pstmt.executeUpdate();

			// 掘取對應的自增主鍵值
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_res_no = rs.getString(1);
//				System.out.println("自增主鍵值= " + next_res_no + "(剛新增成功的訂位編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增訂位訂單明細
			ResDetailService resDetailSvc = new ResDetailService();
			for (String seat_no : seats_no) {
				resDetailSvc.addResDetail(seat_no, next_res_no, con);
			}

			// 交易完成
			con.commit();
			con.setAutoCommit(true);
//			System.out.println("list.size()-B="+seats_no.length);
//			System.out.println("新增訂位訂單編號" + next_res_no + "時,共有" + seats_no.length
//					+ "筆訂位明細被新增");

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-ResOrderDAO");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		return next_res_no;
	}

	@Override
	public void update(ResOrderVO resOrderVO, String[] seats_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// seats_no == null 取消訂單
			if(seats_no == null) {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);
	
				pstmt.setString(1, resOrderVO.getMeal_order_no());
				pstmt.setString(2, resOrderVO.getMem_no());
				pstmt.setString(3, resOrderVO.getEmp_no());
				pstmt.setDate(4, resOrderVO.getRes_date());
				pstmt.setInt(5, new Integer(resOrderVO.getPeople()));
				pstmt.setString(6, resOrderVO.getTime_peri_no());
				pstmt.setInt(7, new Integer(resOrderVO.getInfo_sts()));
				pstmt.setInt(8, new Integer(resOrderVO.getSeat_sts()));
				pstmt.setString(9, resOrderVO.getRes_no());
			
				pstmt.executeUpdate();
				
			// 否則修改是訂單
			} else {
				con = ds.getConnection();
				// 1●設定於 pstm.executeUpdate()之前
				con.setAutoCommit(false);
				
				pstmt = con.prepareStatement(UPDATE);
	
				pstmt.setString(1, resOrderVO.getMeal_order_no());
				pstmt.setString(2, resOrderVO.getMem_no());
				pstmt.setString(3, resOrderVO.getEmp_no());
				pstmt.setDate(4, resOrderVO.getRes_date());
				pstmt.setInt(5, new Integer(resOrderVO.getPeople()));
				pstmt.setString(6, resOrderVO.getTime_peri_no());
				pstmt.setInt(7, new Integer(resOrderVO.getInfo_sts()));
				pstmt.setInt(8, new Integer(resOrderVO.getSeat_sts()));
				pstmt.setString(9, resOrderVO.getRes_no());
				
				pstmt.executeUpdate();
				ResDetailService resDetailSvc = new ResDetailService();
				resDetailSvc.deleteResDetail(resOrderVO.getRes_no(), seats_no, con);
				
				// 交易完成
				con.commit();
				con.setAutoCommit(true);
			}
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
	public ResOrderVO findByPrimaryKey(String res_no) {
		ResOrderVO resOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, res_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				resOrderVO = new ResOrderVO();

				resOrderVO.setRes_no(res_no);
				resOrderVO.setMeal_order_no(rs.getString("MEAL_ORDER_NO"));
				resOrderVO.setMem_no(rs.getString("MEM_NO"));
				resOrderVO.setEmp_no(rs.getString("EMP_NO"));
				resOrderVO.setRes_time(rs.getTimestamp("RES_TIME"));
				resOrderVO.setRes_date(rs.getDate("RES_DATE"));
				resOrderVO.setPeople(rs.getInt("PEOPLE"));
				resOrderVO.setTime_peri_no(rs.getString("TIME_PERI_NO"));
				resOrderVO.setInfo_sts(new Integer(rs.getInt("INFO_STS")));
				resOrderVO.setSeat_sts(new Integer(rs.getInt("SEAT_STS")));
			}

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
		return resOrderVO;
	}

	@Override
	public ResOrderVO findByMEM_NO(String mem_no) {
		ResOrderVO resOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MEM_STMT);

			pstmt.setString(1, mem_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				resOrderVO = new ResOrderVO();

				resOrderVO.setRes_no(rs.getString("RES_NO"));
				resOrderVO.setMeal_order_no(rs.getString("MEAL_ORDER_NO"));
				resOrderVO.setMem_no(mem_no);
				resOrderVO.setEmp_no(rs.getString("EMP_NO"));
				resOrderVO.setRes_time(rs.getTimestamp("RES_TIME"));
				resOrderVO.setRes_date(rs.getDate("RES_DATE"));
				resOrderVO.setPeople(rs.getInt("PEOPLE"));
				resOrderVO.setTime_peri_no(rs.getString("TIME_PERI_NO"));
				resOrderVO.setInfo_sts(new Integer(rs.getInt("INFO_STS")));
				resOrderVO.setSeat_sts(new Integer(rs.getInt("SEAT_STS")));
			}

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
		return resOrderVO;
	}

	@Override
	public List<ResOrderVO> findByMEM_NO_getAll(String mem_no, String sts) {
		List<ResOrderVO> list = new ArrayList<ResOrderVO>();
		ResOrderVO resOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MEM_STMT);

			pstmt.setString(1, mem_no);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				if ("end".equals(sts)) {
					if (new Integer(rs.getInt("SEAT_STS")) == 1 || new Integer(rs.getInt("INFO_STS")) == 3) {
						resOrderVO = new ResOrderVO();

						resOrderVO.setRes_no(rs.getString("RES_NO"));
						resOrderVO.setMeal_order_no(rs.getString("MEAL_ORDER_NO"));
						resOrderVO.setMem_no(mem_no);
						resOrderVO.setEmp_no(rs.getString("EMP_NO"));
						resOrderVO.setRes_time(rs.getTimestamp("RES_TIME"));
						resOrderVO.setRes_date(rs.getDate("RES_DATE"));
						resOrderVO.setPeople(rs.getInt("PEOPLE"));
						resOrderVO.setTime_peri_no(rs.getString("TIME_PERI_NO"));
						resOrderVO.setInfo_sts(new Integer(rs.getInt("INFO_STS")));
						resOrderVO.setSeat_sts(new Integer(rs.getInt("SEAT_STS")));

						list.add(resOrderVO); // Store the row in the list
					}
				} else if ("ing".equals(sts)) {
					if (new Integer(rs.getInt("SEAT_STS")) == 0 && new Integer(rs.getInt("INFO_STS")) < 3) {
						resOrderVO = new ResOrderVO();

						resOrderVO.setRes_no(rs.getString("RES_NO"));
						resOrderVO.setMeal_order_no(rs.getString("MEAL_ORDER_NO"));
						resOrderVO.setMem_no(mem_no);
						resOrderVO.setEmp_no(rs.getString("EMP_NO"));
						resOrderVO.setRes_time(rs.getTimestamp("RES_TIME"));
						resOrderVO.setRes_date(rs.getDate("RES_DATE"));
						resOrderVO.setPeople(rs.getInt("PEOPLE"));
						resOrderVO.setTime_peri_no(rs.getString("TIME_PERI_NO"));
						resOrderVO.setInfo_sts(new Integer(rs.getInt("INFO_STS")));
						resOrderVO.setSeat_sts(new Integer(rs.getInt("SEAT_STS")));

						list.add(resOrderVO); // Store the row in the list
					}
				}
			}
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// bytesArrayToByteObject error
		} catch (NullPointerException npe) {
			throw new RuntimeException("A bytesArrayToByteObject error occured. " + npe.getMessage());
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
	public List<ResOrderVO> findByResDate_And_TimePeri_getAll(String res_date, String time_peri_no) {
		List<ResOrderVO> list = new ArrayList<ResOrderVO>();
		ResOrderVO resOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RES_DATE_AND_TIME_PERI_STMT);

			pstmt.setDate(1, java.sql.Date.valueOf(res_date));
			pstmt.setString(2, time_peri_no);

			rs = pstmt.executeQuery();
			while (rs.next()) {
//				if (new Integer(rs.getInt("SEAT_ISDEL")) == 0) {
				resOrderVO = new ResOrderVO();

				resOrderVO.setRes_no(rs.getString("RES_NO"));
				resOrderVO.setMeal_order_no(rs.getString("MEAL_ORDER_NO"));
				resOrderVO.setMem_no(rs.getString("MEM_NO"));
				resOrderVO.setEmp_no(rs.getString("EMP_NO"));
				resOrderVO.setRes_time(rs.getTimestamp("RES_TIME"));
				resOrderVO.setRes_date(java.sql.Date.valueOf(res_date));
				resOrderVO.setPeople(rs.getInt("PEOPLE"));
				resOrderVO.setTime_peri_no(time_peri_no);
				resOrderVO.setInfo_sts(new Integer(rs.getInt("INFO_STS")));
				resOrderVO.setSeat_sts(new Integer(rs.getInt("SEAT_STS")));

				list.add(resOrderVO); // Store the row in the list
//				}
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// bytesArrayToByteObject error
		} catch (NullPointerException npe) {
			throw new RuntimeException("A bytesArrayToByteObject error occured. " + npe.getMessage());
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
	public List<ResOrderVO> getAll() {
		List<ResOrderVO> list = new ArrayList<ResOrderVO>();
		ResOrderVO resOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
//				if (new Integer(rs.getInt("SEAT_ISDEL")) == 0) {
				resOrderVO = new ResOrderVO();

				resOrderVO.setRes_no(rs.getString("RES_NO"));
				resOrderVO.setMeal_order_no(rs.getString("MEAL_ORDER_NO"));
				resOrderVO.setMem_no(rs.getString("MEM_NO"));
				resOrderVO.setEmp_no(rs.getString("EMP_NO"));
				resOrderVO.setRes_time(rs.getTimestamp("RES_TIME"));
				resOrderVO.setRes_date(rs.getDate("RES_DATE"));
				resOrderVO.setPeople(rs.getInt("PEOPLE"));
				resOrderVO.setTime_peri_no(rs.getString("TIME_PERI_NO"));
				resOrderVO.setInfo_sts(new Integer(rs.getInt("INFO_STS")));
				resOrderVO.setSeat_sts(new Integer(rs.getInt("SEAT_STS")));

				list.add(resOrderVO); // Store the row in the list
//				}
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// bytesArrayToByteObject error
		} catch (NullPointerException npe) {
			throw new RuntimeException("A bytesArrayToByteObject error occured. " + npe.getMessage());
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
	public List<ResOrderVO> findByResDate(String res_date) {
		List<ResOrderVO> list = new ArrayList<ResOrderVO>();
		ResOrderVO resOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_RES_DATE);
			pstmt.setString(1, res_date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				resOrderVO = new ResOrderVO();
				resOrderVO.setRes_no(rs.getString("RES_NO"));
				resOrderVO.setMeal_order_no(rs.getString("MEAL_ORDER_NO"));
				resOrderVO.setMem_no(rs.getString("MEM_NO"));
				resOrderVO.setEmp_no(rs.getString("EMP_NO"));
				resOrderVO.setRes_time(rs.getTimestamp("RES_TIME"));
				resOrderVO.setRes_date(rs.getDate("RES_DATE"));
				resOrderVO.setPeople(rs.getInt("PEOPLE"));
				resOrderVO.setTime_peri_no(rs.getString("TIME_PERI_NO"));
				resOrderVO.setInfo_sts(new Integer(rs.getInt("INFO_STS")));
				resOrderVO.setSeat_sts(new Integer(rs.getInt("SEAT_STS")));
				list.add(resOrderVO); // Store the row in the list
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (NullPointerException npe) {
			throw new RuntimeException("A bytesArrayToByteObject error occured. " + npe.getMessage());
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
	public ResOrderVO findByMealOrderNO(String meal_order_no) {
		ResOrderVO resOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MEAL_ORDER_STMT);

			pstmt.setString(1, meal_order_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				resOrderVO = new ResOrderVO();

				resOrderVO.setRes_no(rs.getString("res_no"));
				resOrderVO.setMeal_order_no(meal_order_no);
				resOrderVO.setMem_no(rs.getString("MEM_NO"));
				resOrderVO.setEmp_no(rs.getString("EMP_NO"));
				resOrderVO.setRes_time(rs.getTimestamp("RES_TIME"));
				resOrderVO.setRes_date(rs.getDate("RES_DATE"));
				resOrderVO.setPeople(rs.getInt("PEOPLE"));
				resOrderVO.setTime_peri_no(rs.getString("TIME_PERI_NO"));
				resOrderVO.setInfo_sts(new Integer(rs.getInt("INFO_STS")));
				resOrderVO.setSeat_sts(new Integer(rs.getInt("SEAT_STS")));
			}

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
		return resOrderVO;
	}
}
