package com.member_review.model;

import java.util.*;
import java.sql.*;

public class Member_ReviewJDBCDAO implements Member_ReviewDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EASHIOU";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO member_review (review_no,meal_order_no,mem_review_con,review_date,review_sts,review_del) VALUES ('MR'||LPAD(SEQ_REVIEW_NO.NEXTVAL,4,0), ?, ?, ?, ?, ? )";
	private static final String GET_ALL_STMT = "SELECT review_no,meal_order_no,mem_review_con,to_char(review_date,'yyyy-mm-dd') review_date, review_sts,review_del FROM member_review order by review_no";
	private static final String GET_ONE_STMT = "SELECT review_no,meal_order_no,mem_review_con,to_char(review_date,'yyyy-mm-dd') review_date, review_sts,review_del FROM member_review where review_no = ?";

	private static final String DELETE_RAs = "DELETE FROM report_appraise where review_no = ?";
	private static final String DELETE_MR = "DELETE FROM member_review where review_no = ?";

	private static final String UPDATE = "UPDATE member_review set meal_order_no=?, mem_review_con=?, review_date=?, review_sts=?, review_del=? where review_no = ?";

	@Override
	public void insert(Member_ReviewVO member_reviewVO) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, member_reviewVO.getMeal_order_no());
			pstmt.setString(2, member_reviewVO.getMem_review_con());
			pstmt.setDate(3, member_reviewVO.getReview_date());
			pstmt.setInt(4, member_reviewVO.getReview_sts());
			pstmt.setInt(5, member_reviewVO.getReview_del());

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
	public void update(Member_ReviewVO member_reviewVO) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, member_reviewVO.getMeal_order_no());
			pstmt.setString(2, member_reviewVO.getMem_review_con());
			pstmt.setDate(3, member_reviewVO.getReview_date());
			pstmt.setInt(4, member_reviewVO.getReview_sts());
			pstmt.setInt(5, member_reviewVO.getReview_del());
			pstmt.setString(6, member_reviewVO.getReview_no());

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
	public void delete(String review_no) {
		// TODO Auto-generated method stub
		int updateCount_RAs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先刪除評價檢舉
			pstmt = con.prepareStatement(DELETE_RAs);
			pstmt.setString(1, review_no);
			updateCount_RAs = pstmt.executeUpdate();
			// 再刪除評價編號
			pstmt = con.prepareStatement(DELETE_MR);
			pstmt.setString(1, review_no);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除評價編號" + review_no + "時，共有評價檢舉" + updateCount_RAs + "張同時被刪除");

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
	public Member_ReviewVO findByPrimaryKey(String review_no) {
		// TODO Auto-generated method stub

		Member_ReviewVO member_reviewVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, review_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// member_reviewVO 也稱為 Domain objects
				member_reviewVO = new Member_ReviewVO();
				member_reviewVO.setReview_no(rs.getString("review_no"));
				member_reviewVO.setMeal_order_no(rs.getString("meal_order_no"));
				member_reviewVO.setMem_review_con(rs.getString("mem_review_con"));
				member_reviewVO.setReview_date(rs.getDate("review_date"));
				member_reviewVO.setReview_sts(rs.getInt("review_sts"));
				member_reviewVO.setReview_del(rs.getInt("review_del"));
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
		return member_reviewVO;
	}

	@Override
	public List<Member_ReviewVO> getAll() {
		// TODO Auto-generated method stub

		List<Member_ReviewVO> list = new ArrayList<Member_ReviewVO>();
		Member_ReviewVO member_reviewVO = null;

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
				member_reviewVO = new Member_ReviewVO();
				member_reviewVO.setReview_no(rs.getString("review_no"));
				member_reviewVO.setMeal_order_no(rs.getString("meal_order_no"));
				member_reviewVO.setMem_review_con(rs.getString("mem_review_con"));
				member_reviewVO.setReview_date(rs.getDate("review_date"));
				member_reviewVO.setReview_sts(rs.getInt("review_sts"));
				member_reviewVO.setReview_del(rs.getInt("review_del"));
				list.add(member_reviewVO); // Store the row in the list
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

	public static void main(String[] args) {

		Member_ReviewJDBCDAO dao = new Member_ReviewJDBCDAO();

//		// 新增		
//		Member_ReviewVO member_reviewVO1 = new Member_ReviewVO();
//		member_reviewVO1.setMeal_order_no("MEO0002");
//		member_reviewVO1.setMem_review_con("好吃");
//		member_reviewVO1.setReview_date(java.sql.Date.valueOf("2020-12-25"));
//		member_reviewVO1.setReview_sts(new Integer(0));
//		member_reviewVO1.setReview_del(new Integer(0));
//		dao.insert(member_reviewVO1);

//		// 修改	    
//		Member_ReviewVO member_reviewVO2 = new Member_ReviewVO ();
//		member_reviewVO2.setReview_no("MR0045");
//		member_reviewVO2.setMeal_order_no("MEO0050");
//		member_reviewVO2.setMem_review_con("難吃");
//		member_reviewVO2.setReview_date(java.sql.Date.valueOf("2020-12-31"));
//		member_reviewVO2.setReview_sts(1);
//		member_reviewVO2.setReview_del(1);
//		dao.update(member_reviewVO2);

		// 刪除
		dao.delete("MR0001");

//		// 查詢		
//		Member_ReviewVO member_reviewVO3 = dao.findByPrimaryKey("MR0001");
//		System.out.print(member_reviewVO3.getReview_no() + ",");
//		System.out.print(member_reviewVO3.getMeal_order_no() + ",");
//		System.out.print(member_reviewVO3.getMem_review_con() + ",");
//		System.out.print(member_reviewVO3.getReview_date() + ",");
//		System.out.print(member_reviewVO3.getReview_sts() + ",");
//		System.out.print(member_reviewVO3.getReview_del() + ",");
//		System.out.println("---------------------");

//		// 查詢	
//		List<Member_ReviewVO> list = dao.getAll();
//		for (Member_ReviewVO aMr : list) {
//		System.out.print(aMr.getReview_no() + ",");
//		System.out.print(aMr.getMeal_order_no() + ",");
//		System.out.print(aMr.getMem_review_con() + ",");
//		System.out.print(aMr.getReview_date() + ",");
//		System.out.print(aMr.getReview_sts() + ",");
//		System.out.print(aMr.getReview_del() + ",");
//		System.out.println();
//		}
	}
}