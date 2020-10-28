package com.res_detail.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ResDetailDAO implements ResDetailDAO_interface {

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

	private static final String INSERT_STMT = "INSERT INTO RES_DETAIL (RES_DE_NO ,SEAT_NO ,RES_NO) VALUES ('RDN'||LPAD(SEQ_RES_DE_NO.NEXTVAL,4,0), ?, ?)";
	private static final String GET_ALL_STMT = "SELECT RES_DE_NO ,SEAT_NO ,RES_NO FROM RES_DETAIL ORDER BY RES_NO";
	private static final String GET_ONE_STMT = "SELECT SEAT_NO, RES_NO FROM RES_DETAIL WHERE RES_DE_NO = ?";
	private static final String UPDATE = "UPDATE RES_DETAIL SET SEAT_NO=? ,RES_NO=? WHERE RES_DE_NO = ?";
	private static final String GET_RES_NO_ALL_STMT = "SELECT RES_DE_NO, SEAT_NO, RES_NO FROM RES_DETAIL WHERE RES_NO = ?";
	private static final String DELETE = "DELETE FROM RES_DETAIL WHERE RES_NO = ?";

	@Override
	public void insert(ResDetailVO resDetailVO, Connection outer_con) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			if (outer_con == null) {
				con = ds.getConnection();
			} else {
				con = outer_con;
			}
			
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, resDetailVO.getSeat_no());
			pstmt.setString(2, resDetailVO.getRes_no());
			
			pstmt.executeUpdate();
			
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-ResDetailDAO");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
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
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
		}
	}
	
	@Override
	public void delete(String res_no, String[] seats_no, Connection outer_con) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			if(outer_con == null) {
				con = ds.getConnection();
			} else {
				con = outer_con;
			}
			
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, res_no);
			
			pstmt.executeUpdate();
			
			// 修改座位編號時段
			ResDetailService resDetailSvc = new ResDetailService();
			for (String seat_no : seats_no) {
				resDetailSvc.addResDetail(seat_no, res_no, con);
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
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
		}
	}
	
	@Override
	public void update(ResDetailVO resDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, resDetailVO.getSeat_no());
			pstmt.setString(2, resDetailVO.getRes_no());
			pstmt.setString(3, resDetailVO.getRes_de_no());

			pstmt.executeUpdate();

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
	public ResDetailVO findByPrimaryKey(String res_de_no) {
		ResDetailVO resDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, res_de_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				resDetailVO = new ResDetailVO();

				resDetailVO.setRes_de_no(res_de_no);
				resDetailVO.setSeat_no(rs.getString("SEAT_NO"));
				resDetailVO.setRes_no(rs.getString("RES_NO"));
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
		return resDetailVO;
	}

	@Override
	public List<ResDetailVO> getAll() {
		List<ResDetailVO> list = new ArrayList<ResDetailVO>();
		ResDetailVO resDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
//				if (new Integer(rs.getInt("SEAT_ISDEL")) == 0) {
				resDetailVO = new ResDetailVO();

				resDetailVO.setRes_de_no(rs.getString("RES_DE_NO"));
				resDetailVO.setSeat_no(rs.getString("SEAT_NO"));
				resDetailVO.setRes_no(rs.getString("RES_NO"));

				list.add(resDetailVO); // Store the row in the list
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
	public List<ResDetailVO> getResNoAll(String res_no) {
		List<ResDetailVO> list = new ArrayList<ResDetailVO>();
		ResDetailVO resDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RES_NO_ALL_STMT);
			
			pstmt.setString(1, res_no);

			rs = pstmt.executeQuery();
			while (rs.next()) {
//				if (new Integer(rs.getInt("SEAT_ISDEL")) == 0) {
				resDetailVO = new ResDetailVO();

				resDetailVO.setRes_de_no(rs.getString("RES_DE_NO"));
				resDetailVO.setSeat_no(rs.getString("SEAT_NO"));
				resDetailVO.setRes_no(res_no);

				list.add(resDetailVO); // Store the row in the list
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
}
