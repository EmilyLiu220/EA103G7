package com.ad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ad.model.AdVO;

public class AdDAO implements AdDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_AD = "INSERT INTO AD(AD_NO ,EMP_NO ,AD_TITLE ,AD_CONT,AD_ADD_DATE,AD_RE_DATE,AD_IMG)VALUES('AD'||LPAD(SEQ_AD_NO.nextval,4,0),?,?,?,?,?,?)";
	private static final String GET_ALL_AD = "SELECT * FROM AD ORDER BY AD_NO";
	private static final String GET_ONE_AD = "SELECT * FROM AD WHERE AD_NO =?";
	private static final String DELETE = "DELETE FROM AD WHERE AD_NO = ?";
	private static final String UPDATE = "UPDATE AD SET EMP_NO=? , AD_TITLE=? ,AD_CONT=? ,AD_ADD_DATE=? ,AD_RE_DATE=? ,AD_IMG=? WHERE AD_NO=?";

	@Override
	public void insert(AdVO adVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_AD);

			pstmt.setString(1, adVO.getEmp_no());
			pstmt.setString(2, adVO.getAd_title());
			pstmt.setString(3, adVO.getAd_cont());
			pstmt.setDate(4, adVO.getAd_add_date());
			pstmt.setDate(5, adVO.getAd_re_date());
			pstmt.setBytes(6, adVO.getAd_img());

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
	public void update(AdVO adVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(7, adVO.getAd_no());
			pstmt.setString(1, adVO.getEmp_no());
			pstmt.setString(2, adVO.getAd_title());
			pstmt.setString(3, adVO.getAd_cont());
			pstmt.setDate(4, adVO.getAd_add_date());
			pstmt.setDate(5, adVO.getAd_re_date());
			pstmt.setBytes(6, adVO.getAd_img());
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
	public void delete(String ad_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ad_no);
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
	public AdVO findByPrimaryKey(String ad_no) {

		AdVO adVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_AD);
			pstmt.setString(1, ad_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adVO = new AdVO();
				adVO.setAd_no(rs.getString("ad_no"));
				adVO.setEmp_no(rs.getString("emp_no"));
				adVO.setAd_title(rs.getString("ad_title"));
				adVO.setAd_cont(rs.getString("ad_cont"));
				adVO.setAd_add_date(rs.getDate("ad_add_date"));
				adVO.setAd_re_date(rs.getDate("ad_re_date"));
				adVO.setAd_img(rs.getBytes("ad_img"));
			}
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
		return adVO;
	}

	@Override
	public List<AdVO> getAll() {

		List<AdVO> list = new ArrayList<AdVO>();
		AdVO adVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_AD);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adVO = new AdVO();
				adVO.setAd_no(rs.getString("ad_no"));
				adVO.setEmp_no(rs.getString("emp_no"));
				adVO.setAd_title(rs.getString("ad_title"));
				adVO.setAd_cont(rs.getString("ad_cont"));
				adVO.setAd_add_date(rs.getDate("ad_add_date"));
				adVO.setAd_re_date(rs.getDate("ad_re_date"));
				adVO.setAd_img(rs.getBytes("ad_img"));
				list.add(adVO);
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
}
