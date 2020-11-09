package com.bonus.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BonusDAO implements BonusDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO bonus (bns_no,bns_name,bns_price,bns_stks,bns_date,bns_img) VALUES ('BN'||LPAD(SEQ_BNS_NO.NEXTVAL,4,0), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT bns_no,bns_name,bns_price,bns_stks,to_char(bns_date,'yyyy-mm-dd') bns_date,bns_sts,bns_img FROM bonus order by bns_no";
	private static final String GET_ONE_STMT = "SELECT bns_no,bns_name,bns_price,bns_stks,to_char(bns_date,'yyyy-mm-dd') bns_date,bns_sts,bns_img FROM bonus where bns_no = ?";
	private static final String DELETE = "DELETE FROM bonus where bns_no = ?";
	private static final String UPDATE = "UPDATE bonus set bns_name=?, bns_price=?, bns_stks=?, bns_date=?, bns_sts=?, bns_img=? where bns_no = ?";

	@Override
	public void insertFromBack(BonusVO bonusVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bonusVO.getBns_name());
			pstmt.setInt(2, bonusVO.getBns_price());
			pstmt.setInt(3, bonusVO.getBns_stks());
			pstmt.setDate(4, bonusVO.getBns_date());
			pstmt.setBytes(5, bonusVO.getBns_img());

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
	public void update(BonusVO bonusVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, bonusVO.getBns_name());
			pstmt.setInt(2, bonusVO.getBns_price());
			pstmt.setInt(3, bonusVO.getBns_stks());
			pstmt.setDate(4, bonusVO.getBns_date());
			pstmt.setInt(5, bonusVO.getBns_sts());
			pstmt.setBytes(6, bonusVO.getBns_img());
			pstmt.setString(7, bonusVO.getBns_no());

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
	public void delete(String bns_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, bns_no);

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
	public BonusVO findByPrimaryKey(String bns_no) {

		BonusVO bonusVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, bns_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bonusVO = new BonusVO();
				bonusVO.setBns_no(rs.getString("bns_no"));
				bonusVO.setBns_name(rs.getString("bns_name"));
				bonusVO.setBns_price(rs.getInt("bns_price"));
				bonusVO.setBns_stks(rs.getInt("bns_stks"));
				bonusVO.setBns_date(rs.getDate("bns_date"));
				bonusVO.setBns_sts(rs.getInt("bns_sts"));
				bonusVO.setBns_img(rs.getBytes("bns_img"));
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
		return bonusVO;
	}

	@Override
	public List<BonusVO> getAll() {
		List<BonusVO> list = new ArrayList<BonusVO>();
		BonusVO bonusVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bonusVO = new BonusVO();
				bonusVO.setBns_no(rs.getString("bns_no"));
				bonusVO.setBns_name(rs.getString("bns_name"));
				bonusVO.setBns_price(rs.getInt("bns_price"));
				bonusVO.setBns_stks(rs.getInt("bns_stks"));
				bonusVO.setBns_date(rs.getDate("bns_date"));
				bonusVO.setBns_sts(rs.getInt("bns_sts"));
				bonusVO.setBns_img(rs.getBytes("bns_img"));
				list.add(bonusVO); // Store the row in the list
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