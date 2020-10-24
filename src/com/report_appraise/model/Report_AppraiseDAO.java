package com.report_appraise.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Report_AppraiseDAO implements Report_AppraiseDAO_interface {
	
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
		
		private static final String INSERT_STMT =
				"INSERT INTO report_appraise (report_no,review_no,mem_no,emp_no,report_date,report_con) VALUES ('RO'||LPAD(SEQ_REPORT_NO.NEXTVAL,4,0), ?, ?, ?, ?, ?)";
			private static final String GET_ALL_STMT =
				"SELECT report_no,review_no,mem_no,emp_no,to_char(report_date,'yyyy-mm-dd') report_date, report_con,reply_sts FROM report_appraise order by report_no";
			private static final String GET_ONE_STMT =
				"SELECT report_no,review_no,mem_no,emp_no,to_char(report_date,'yyyy-mm-dd') report_date, report_con,reply_sts FROM report_appraise where report_no = ?";
			private static final String DELETE =
				"DELETE FROM report_appraise where report_no = ?";
			private static final String UPDATE =
				"UPDATE report_appraise set review_no=?, mem_no=?, emp_no=? ,report_date=? ,report_con=?, reply_sts=? where report_no = ?";

			@Override
			public void insert(Report_AppraiseVO report_appraiseVO) {

				Connection con = null;
				PreparedStatement pstmt = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(INSERT_STMT);

					pstmt.setString(1, report_appraiseVO.getReview_no());
					pstmt.setString(2, report_appraiseVO.getMem_no());
					pstmt.setString(3, report_appraiseVO.getEmp_no());				
					pstmt.setDate(4, report_appraiseVO.getReport_date());
					pstmt.setString(5, report_appraiseVO.getReport_con());
					pstmt.executeUpdate();

					// Handle any SQL errors
				} catch (SQLException se) {
					throw new RuntimeException("A database error occurred. "
							+ se.getMessage());
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
			public void update(Report_AppraiseVO report_appraiseVO) {

				Connection con = null;
				PreparedStatement pstmt = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(UPDATE);

					pstmt.setString(1, report_appraiseVO.getReview_no());
					pstmt.setString(2, report_appraiseVO.getMem_no());
					pstmt.setString(3, report_appraiseVO.getEmp_no());
					pstmt.setDate(4, report_appraiseVO.getReport_date());
					pstmt.setString(5, report_appraiseVO.getReport_con());
					pstmt.setInt(6, report_appraiseVO.getReply_sts());	
					pstmt.setString(7, report_appraiseVO.getReport_no());
					
					pstmt.executeUpdate();

					// Handle any SQL errors
				} catch (SQLException se) {
					throw new RuntimeException("A database error occurred. "
							+ se.getMessage());
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
			public void delete(String report_appraiseVO) {

				Connection con = null;
				PreparedStatement pstmt = null;

				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(DELETE);
					
					pstmt.setString(1, report_appraiseVO);
				
					pstmt.executeUpdate();
					
					// Handle any SQL errors
				} catch (SQLException se) {
					throw new RuntimeException("A database error occurred. "
							+ se.getMessage());
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
			public Report_AppraiseVO findByPrimaryKey(String report_no) {
				
				Report_AppraiseVO report_appraiseVO = null;
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_ONE_STMT);
					
					pstmt.setString(1, report_no);
					
					rs = pstmt.executeQuery();
					
					while (rs.next()) {
						report_appraiseVO = new Report_AppraiseVO();
						report_appraiseVO.setReport_no(rs.getString("report_no"));
						report_appraiseVO.setReview_no(rs.getString("review_no"));
						report_appraiseVO.setMem_no(rs.getString("mem_no"));
						report_appraiseVO.setEmp_no(rs.getString("emp_no"));
						report_appraiseVO.setReport_date(rs.getDate("report_date"));
						report_appraiseVO.setReport_con(rs.getString("report_con"));
						report_appraiseVO.setReply_sts(rs.getInt("reply_sts"));
					}		
					

					// Handle any driver errors
				} catch (SQLException se) {
					throw new RuntimeException("A database error occurred. "
							+ se.getMessage());
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
				return report_appraiseVO;
			}

			@Override
			public List<Report_AppraiseVO> getAll() {
				List<Report_AppraiseVO> list = new ArrayList<Report_AppraiseVO>();
				Report_AppraiseVO report_appraiseVO = null;

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_ALL_STMT);
					rs = pstmt.executeQuery();

					while (rs.next()) {
						// empVO 也稱為 Domain objects
						report_appraiseVO = new Report_AppraiseVO();
						report_appraiseVO.setReport_no(rs.getString("report_no"));
						report_appraiseVO.setReview_no(rs.getString("review_no"));
						report_appraiseVO.setMem_no(rs.getString("mem_no"));
						report_appraiseVO.setEmp_no(rs.getString("emp_no"));
						report_appraiseVO.setReport_date(rs.getDate("report_date"));
						report_appraiseVO.setReport_con(rs.getString("report_con"));
						report_appraiseVO.setReply_sts(rs.getInt("reply_sts"));
						list.add(report_appraiseVO); // Store the row in the list
					}

					// Handle any driver errors
				} catch (SQLException se) {
					throw new RuntimeException("A database error occurred. "
							+ se.getMessage());
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