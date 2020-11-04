package com.member_review.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Member_ReviewDAO implements Member_ReviewDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO member_review (review_no,meal_order_no,mem_review_con,review_date) VALUES ('MR'||LPAD(SEQ_REVIEW_NO.NEXTVAL,4,0), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT review_no,meal_order_no,mem_review_con,to_char(review_date,'yyyy-mm-dd') review_date, review_sts,review_del FROM member_review order by review_no";
	private static final String GET_ONE_STMT = "SELECT review_no,meal_order_no,mem_review_con,to_char(review_date,'yyyy-mm-dd') review_date, review_sts,review_del FROM member_review where review_no = ?";

	private static final String DELETE_RAs = "DELETE FROM report_appraise where review_no = ?";
	private static final String DELETE_MR = "DELETE FROM member_review where review_no = ?";

	private static final String UPDATE = "UPDATE member_review set meal_order_no=?, mem_review_con=?, review_date=? where review_no = ?";

	@Override
	public void insert(Member_ReviewVO member_reviewVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, member_reviewVO.getMeal_order_no());
			pstmt.setString(2, member_reviewVO.getMem_review_con());
			pstmt.setDate(3, member_reviewVO.getReview_date());

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
	public void update(Member_ReviewVO member_reviewVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, member_reviewVO.getMeal_order_no());
			pstmt.setString(2, member_reviewVO.getMem_review_con());
			pstmt.setDate(3, member_reviewVO.getReview_date());
			pstmt.setString(4, member_reviewVO.getReview_no());

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
	public void delete(String review_no) {
		int updateCount_RAs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

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

		Member_ReviewVO member_reviewVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, review_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				member_reviewVO = new Member_ReviewVO();
				member_reviewVO.setReview_no(rs.getString("review_no"));
				member_reviewVO.setMeal_order_no(rs.getString("meal_order_no"));
				member_reviewVO.setMem_review_con(rs.getString("mem_review_con"));
				member_reviewVO.setReview_date(rs.getDate("review_date"));
				member_reviewVO.setReview_sts(rs.getInt("review_sts"));
				member_reviewVO.setReview_del(rs.getInt("review_del"));
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
		return member_reviewVO;
	}

	@Override
	public List<Member_ReviewVO> getAll() {
		List<Member_ReviewVO> list = new ArrayList<Member_ReviewVO>();
		Member_ReviewVO member_reviewVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
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
}