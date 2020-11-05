package com.bonus_order_detail.model;

import java.util.*;

import com.bonus.model.BonusVO;

import java.sql.*;

public class Bonus_Order_DetailJDBCDAO implements Bonus_Order_DetailDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EASHIOU";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO bonus_order_detail (bo_no,bns_no,quantity) VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT bo_no,bns_no,quantity FROM bonus_order_detail order by bo_no";
	private static final String GET_ONE_STMT = "SELECT bo_no,bns_no,quantity FROM bonus_order_detail where bo_no = ?";
	private static final String DELETE = "DELETE FROM bonus_order_detail where bo_no = ?";
	private static final String UPDATE = "UPDATE bonus_order_detail set bns_no=?, quantity=? where bo_no = ?";

	@Override
	public void insert(Bonus_Order_DetailVO bonus_order_detailVO) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bonus_order_detailVO.getBo_no());
			pstmt.setString(2, bonus_order_detailVO.getBns_no());
			pstmt.setInt(3, bonus_order_detailVO.getQuantity());

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
	public void update(Bonus_Order_DetailVO bonus_order_detailVO) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, bonus_order_detailVO.getBns_no());
			pstmt.setInt(2, bonus_order_detailVO.getQuantity());
			pstmt.setString(3, bonus_order_detailVO.getBo_no());

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

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, bo_no);

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
	public Bonus_Order_DetailVO findByPrimaryKey(String bo_no) {
		// TODO Auto-generated method stub

		Bonus_Order_DetailVO bonus_order_detailVO = null;
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
				bonus_order_detailVO = new Bonus_Order_DetailVO();
				bonus_order_detailVO.setBo_no(rs.getString("bo_no"));
				bonus_order_detailVO.setBns_no(rs.getString("bns_no"));
				bonus_order_detailVO.setQuantity(rs.getInt("quantity"));
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
		return bonus_order_detailVO;
	}

	@Override
	public List<Bonus_Order_DetailVO> getAll() {
		// TODO Auto-generated method stub

		List<Bonus_Order_DetailVO> list = new ArrayList<Bonus_Order_DetailVO>();
		Bonus_Order_DetailVO bonus_order_detailVO = null;

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
				bonus_order_detailVO = new Bonus_Order_DetailVO();
				bonus_order_detailVO.setBo_no(rs.getString("bo_no"));
				bonus_order_detailVO.setBns_no(rs.getString("bns_no"));
				bonus_order_detailVO.setQuantity(rs.getInt("quantity"));
				list.add(bonus_order_detailVO); // Store the row in the list
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
	public void insert2(String bo_no, Connection con, List<BonusVO> list) {

		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bo_no);
			pstmt.setString(2, list.get(0).getBns_no());
			pstmt.setInt(3,1);

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-bonus_order_detail");
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
		}

	}

	public static void main(String[] args) {

		Bonus_Order_DetailJDBCDAO dao = new Bonus_Order_DetailJDBCDAO();

//// 		新增
//		Bonus_Order_DetailVO bonus_order_detailVO1 = new Bonus_Order_DetailVO();
//		bonus_order_detailVO1.setBo_no("BO0006");
//		bonus_order_detailVO1.setBns_no("BN0025");
//		bonus_order_detailVO1.setQuantity(1);
//		dao.insert(bonus_order_detailVO1);
//
////		 修改
//		Bonus_Order_DetailVO bonus_order_detailVO2 = new Bonus_Order_DetailVO();
//		bonus_order_detailVO2.setBo_no("BO0006");
//		bonus_order_detailVO2.setBns_no("BN0023");
//		bonus_order_detailVO2.setQuantity(1);
//		dao.update(bonus_order_detailVO2);
//
////		 刪除
//		dao.delete("BO0005");
//
////		 查詢
//		Bonus_Order_DetailVO bonus_order_detailVO3 = dao.findByPrimaryKey("BO0004");
//		System.out.print(bonus_order_detailVO3.getBo_no() + ",");
//		System.out.print(bonus_order_detailVO3.getBns_no() + ",");
//		System.out.print(bonus_order_detailVO3.getQuantity() + ",");
//		System.out.println("---------------------");
//
//// 		查詢
		List<Bonus_Order_DetailVO> list = dao.getAll();
		for (Bonus_Order_DetailVO aBod : list) {
			System.out.print(aBod.getBo_no() + ",");
			System.out.print(aBod.getBns_no() + ",");
			System.out.print(aBod.getQuantity() + ",");
			System.out.println();
		}
	}
}