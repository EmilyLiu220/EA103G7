package com.bonus_order_detail.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.bonus.model.BonusVO;

public class Bonus_Order_DetailDAO implements Bonus_Order_DetailDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO bonus_order_detail (bo_no,bns_no,quantity) VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT bo_no,bns_no,quantity FROM bonus_order_detail order by bo_no";
	private static final String GET_ONE_STMT = "SELECT bo_no,bns_no,quantity FROM bonus_order_detail where bo_no = ?";
	private static final String DELETE = "DELETE FROM bonus_order_detail where bo_no = ?";
	private static final String UPDATE = "UPDATE bonus_order_detail set bo_no=?, bns_no=?, quantity=? where bo_no = ?";

	@Override
	public void insert(Bonus_Order_DetailVO bonus_order_detailDAO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bonus_order_detailDAO.getBo_no());
			pstmt.setString(2, bonus_order_detailDAO.getBns_no());
			pstmt.setInt(3, bonus_order_detailDAO.getQuantity());

			pstmt.executeUpdate();

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
	public void update(Bonus_Order_DetailVO bonus_order_detailDAO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, bonus_order_detailDAO.getBns_no());
			pstmt.setInt(2, bonus_order_detailDAO.getQuantity());
			pstmt.setString(3, bonus_order_detailDAO.getBo_no());

			pstmt.executeUpdate();

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

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, bo_no);

			pstmt.executeUpdate();

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

		Bonus_Order_DetailVO bonus_order_detailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, bo_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				bonus_order_detailVO = new Bonus_Order_DetailVO();
				bonus_order_detailVO.setBo_no(rs.getString("bo_no"));
				bonus_order_detailVO.setBns_no(rs.getString("bns_no"));
				bonus_order_detailVO.setQuantity(rs.getInt("quantity"));
			}

			// Handle any driver errors
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
		List<Bonus_Order_DetailVO> list = new ArrayList<Bonus_Order_DetailVO>();
		Bonus_Order_DetailVO bonus_order_detailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				bonus_order_detailVO = new Bonus_Order_DetailVO();
				bonus_order_detailVO.setBo_no(rs.getString("bo_no"));
				bonus_order_detailVO.setBns_no(rs.getString("bns_no"));
				bonus_order_detailVO.setQuantity(rs.getInt("quantity"));
				list.add(bonus_order_detailVO); // Store the row in the list
			}

			// Handle any driver errors
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
			pstmt.setInt(3,new Integer(1));

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
}