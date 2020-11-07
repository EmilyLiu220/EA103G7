package com.bonus_order.model;

import java.util.*;
import java.sql.*;

import com.bonus.model.BonusVO;
import com.bonus_order_detail.model.Bonus_Order_DetailJDBCDAO;
import com.bonus_order_detail.model.Bonus_Order_DetailVO;

public class Bonus_OrderJDBCDAO implements Bonus_OrderDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EASHIOU";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO bonus_order (bo_no,mem_no,bo_date,promo_code) VALUES ('BO'||LPAD(SEQ_BO_NO.NEXTVAL,4,0), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT bo_no,mem_no,to_char(bo_date,'yyyy-mm-dd') bo_date, promo_code FROM bonus_order order by bo_no";
	private static final String GET_ONE_STMT = "SELECT bo_no,mem_no,to_char(bo_date,'yyyy-mm-dd') bo_date, promo_code FROM bonus_order where bo_no = ?";
	private static final String DELETE_BONUS_ORDER_DETAIL = "DELETE FROM bonus_order_detail where bo_no = ?";
	private static final String DELETE_BONUS_ORDER = "DELETE FROM bonus_order where bo_no = ?";
	private static final String UPDATE = "UPDATE bonus_order set mem_no=?, bo_date=?, promo_code=? where bo_no = ?";

	@Override
	public void insert(Bonus_OrderVO bonus_orderVO) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bonus_orderVO.getMem_no());
			pstmt.setDate(2, bonus_orderVO.getBo_date());
			pstmt.setString(3, bonus_orderVO.getPromo_code());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occurred. " + se.getMessage());
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
	public void update(Bonus_OrderVO bonus_orderVO) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, bonus_orderVO.getMem_no());
			pstmt.setDate(2, bonus_orderVO.getBo_date());
			pstmt.setString(3, bonus_orderVO.getPromo_code());
			pstmt.setString(4, bonus_orderVO.getBo_no());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occurred. " + se.getMessage());
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
	public void delete(String bo_no) {
		// TODO Auto-generated method stub

		int updateCount_Bonus_Order_Detail = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先刪除明細
			pstmt = con.prepareStatement(DELETE_BONUS_ORDER_DETAIL);
			pstmt.setString(1, bo_no);
			updateCount_Bonus_Order_Detail = pstmt.executeUpdate();
			// 再刪除訂單
			pstmt = con.prepareStatement(DELETE_BONUS_ORDER);
			pstmt.setString(1, bo_no);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除明細編號" + bo_no + "時,共有訂單" + updateCount_Bonus_Order_Detail + "張同時被刪除");

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
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
	}

	@Override
	public Bonus_OrderVO findByPrimaryKey(String bo_no) {
		// TODO Auto-generated method stub

		Bonus_OrderVO bonus_orderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, bo_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// member_reviewVO 也稱為 Domain objects
				bonus_orderVO = new Bonus_OrderVO();
				bonus_orderVO.setBo_no(rs.getString("bo_no"));
				bonus_orderVO.setMem_no(rs.getString("mem_no"));
				bonus_orderVO.setBo_date(rs.getDate("bo_date"));
				bonus_orderVO.setPromo_code(rs.getString("promo_code"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occurred. " + se.getMessage());
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
		return bonus_orderVO;
	}

	@Override
	public List<Bonus_OrderVO> getAll() {
		// TODO Auto-generated method stub

		List<Bonus_OrderVO> list = new ArrayList<Bonus_OrderVO>();
		Bonus_OrderVO bonus_orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// member_reviewVO 也稱為 Domain objects
				bonus_orderVO = new Bonus_OrderVO();
				bonus_orderVO.setBo_no(rs.getString("bo_no"));
				bonus_orderVO.setMem_no(rs.getString("mem_no"));
				bonus_orderVO.setBo_date(rs.getDate("bo_date"));
				bonus_orderVO.setPromo_code(rs.getString("promo_code"));
				list.add(bonus_orderVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occurred. " + se.getMessage());
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
	public void insertWithBonus_Order_Detail(Bonus_OrderVO bonus_orderVO, List<BonusVO> list) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先新增訂單
			String cols[] = { "BO_NO" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setString(1, bonus_orderVO.getMem_no());
			pstmt.setDate(2, bonus_orderVO.getBo_date());
			pstmt.setString(3, bonus_orderVO.getPromo_code());
			pstmt.executeUpdate();
			// 掘取對應的自增主鍵值
			String next_bo_no = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_bo_no = rs.getString(1);
				System.out.println("自增主鍵值= " + next_bo_no + "(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增訂單明細
			Bonus_Order_DetailJDBCDAO dao = new Bonus_Order_DetailJDBCDAO();
			System.out.println("list.size()-A=" + list.size());
			for (BonusVO aBod : list) {
				aBod.setBns_no(new String(next_bo_no));
				dao.insert2(next_bo_no, con, list);
			}

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B=" + list.size());
			System.out.println("新增訂單編號" + next_bo_no + "時,共有訂單明細" + list.size() + "張同時被新增");

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-bonus_order");
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
	}

	public static void main(String[] args) {

		Bonus_OrderJDBCDAO dao = new Bonus_OrderJDBCDAO();
		
		Bonus_OrderVO bonus_orderVO = new Bonus_OrderVO();
		bonus_orderVO.setMem_no("MEM0047");
		bonus_orderVO.setBo_date(java.sql.Date.valueOf("2020-11-13"));
		bonus_orderVO.setPromo_code("d4658wadr8w");

		List<BonusVO> testList = new ArrayList<BonusVO>(); 
		BonusVO bodXX = new BonusVO(); 
		bodXX.setBns_no("BN0001");
		
		Bonus_Order_DetailVO bodYY = new Bonus_Order_DetailVO(); 
		bodYY.setBns_no("BN0002");

		testList.add(bodXX);

		dao.insertWithBonus_Order_Detail(bonus_orderVO, testList);

////		 新增
//		Bonus_OrderVO bonus_orderVO1 = new Bonus_OrderVO();
//		bonus_orderVO1.setMem_no("MEM0008");
//		bonus_orderVO1.setBo_date(java.sql.Date.valueOf("2020-12-31"));
//		bonus_orderVO1.setPromo_code("z45qw1gt5a");
//		dao.insert(bonus_orderVO1);
//
////		 修改
//		Bonus_OrderVO bonus_orderVO2 = new Bonus_OrderVO();
//		bonus_orderVO2.setBo_no("BO0008");
//		bonus_orderVO2.setMem_no("MEM0008");
//		bonus_orderVO2.setBo_date(java.sql.Date.valueOf("2020-12-25"));
//		bonus_orderVO2.setPromo_code("Q48ersoy9v");
//		dao.update(bonus_orderVO2);
//
////		 刪除
//		dao.delete("BO0005");
//
//		 查詢
//		Bonus_OrderVO bonus_orderVO3 = dao.findByPrimaryKey("BO0008");
//		System.out.print(bonus_orderVO3.getBo_no() + ",");
//		System.out.print(bonus_orderVO3.getMem_no() + ",");
//		System.out.print(bonus_orderVO3.getBo_date() + ",");
//		System.out.print(bonus_orderVO3.getPromo_code() + ",");
//		System.out.println("---------------------");

//		查詢			
//		List<Bonus_OrderVO> list = dao.getAll();
//		for (Bonus_OrderVO aBo : list) {
//			System.out.print(aBo.getBo_no() + ",");
//			System.out.print(aBo.getMem_no() + ",");
//			System.out.print(aBo.getBo_date() + ",");
//			System.out.print(aBo.getPromo_code() + ",");
//			System.out.println();
//		}
	}

	@Override
	public Bonus_OrderVO insert(Bonus_OrderVO bonus_orderVO, List<BonusVO> list) {
		
		return bonus_orderVO;// TODO Auto-generated method stub
		
	}
}