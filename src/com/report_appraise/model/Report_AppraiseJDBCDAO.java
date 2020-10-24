package com.report_appraise.model;

import java.util.*;
import java.sql.*;

public class Report_AppraiseJDBCDAO implements Report_AppraiseDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EASHIOU";
	String passwd = "123456";

		private static final String INSERT_STMT =
			"INSERT INTO report_appraise (report_no,review_no,mem_no,emp_no,report_date,report_con,reply_sts) VALUES ('RO'||LPAD(SEQ_REPORT_NO.NEXTVAL,4,0), ?, ?, ?, ?, ?, ?)";
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
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
            pstmt.setString(1, report_appraiseVO.getReview_no());
            pstmt.setString(2, report_appraiseVO.getMem_no());
            pstmt.setString(3, report_appraiseVO.getEmp_no());
            pstmt.setDate(4, report_appraiseVO.getReport_date());
            pstmt.setString(5, report_appraiseVO.getReport_con());
            pstmt.setInt(6, report_appraiseVO.getReply_sts());


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
	public void update(Report_AppraiseVO report_appraiseVO) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, report_appraiseVO.getReview_no());
            pstmt.setString(2, report_appraiseVO.getMem_no());
            pstmt.setString(3, report_appraiseVO.getEmp_no());
            pstmt.setDate(4, report_appraiseVO.getReport_date());
            pstmt.setString(5, report_appraiseVO.getReport_con());
            pstmt.setInt(6, report_appraiseVO.getReply_sts());
    		pstmt.setString(7, report_appraiseVO.getReport_no());
			
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
	public void delete(String report_no) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, report_no);

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
	public Report_AppraiseVO findByPrimaryKey(String report_no) {
		// TODO Auto-generated method stub
		
		Report_AppraiseVO report_appraiseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		return report_appraiseVO;
	}

	@Override
	public List<Report_AppraiseVO> getAll() {
		// TODO Auto-generated method stub
		
			List<Report_AppraiseVO> list = new ArrayList<Report_AppraiseVO>();
			Report_AppraiseVO report_appraiseVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
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
					list.add(report_appraiseVO); // Store the row in the list
				}

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
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
	
	public static void main(String[] args) {

		Report_AppraiseJDBCDAO dao = new Report_AppraiseJDBCDAO();

//		// 新增
		Report_AppraiseVO report_appraiseVO1 = new Report_AppraiseVO();
		report_appraiseVO1.setReview_no("MR0001");
		report_appraiseVO1.setMem_no("MEM0003");
		report_appraiseVO1.setEmp_no("EMP0004");
		report_appraiseVO1.setReport_date(java.sql.Date.valueOf("2020-12-30"));
		report_appraiseVO1.setReport_con("垃圾連結");
		report_appraiseVO1.setReply_sts(new Integer(0));
	    dao.insert(report_appraiseVO1);

		// 修改
//		Report_AppraiseVO report_appraiseVO2 = new Report_AppraiseVO();
//		report_appraiseVO2.setReport_no("RO0007");
//		report_appraiseVO2.setReview_no("MR0003");
//		report_appraiseVO2.setMem_no("MEM0002");
//		report_appraiseVO2.setEmp_no("EMP0001");
//		report_appraiseVO2.setReport_date(java.sql.Date.valueOf("2020-12-31"));
//		report_appraiseVO2.setReport_con("直銷廣告");
//		report_appraiseVO2.setReply_sts(new Integer(0));
//	    dao.update(report_appraiseVO2);

//		// 刪除
//		dao.delete("RO0009");

//		// 查詢
		
//		Report_AppraiseVO report_appraiseVO3 = dao.findByPrimaryKey("RO0004");
//		System.out.print(report_appraiseVO3.getReport_no() + ",");
//		System.out.print(report_appraiseVO3.getReview_no() + ",");
//		System.out.print(report_appraiseVO3.getMem_no() + ",");
//		System.out.print(report_appraiseVO3.getEmp_no() + ",");
//		System.out.print(report_appraiseVO3.getReport_date() + ",");
//		System.out.print(report_appraiseVO3.getReport_con() + ",");
//		System.out.print(report_appraiseVO3.getReply_sts() + ",");
//		System.out.println("---------------------");

//		// 查詢		
//		List<Report_AppraiseVO> list = dao.getAll();
//		for (Report_AppraiseVO aRa : list) {
//		System.out.print(aRa.getReport_no() + ",");
//		System.out.print(aRa.getReview_no() + ",");
//		System.out.print(aRa.getMem_no() + ",");
//		System.out.print(aRa.getEmp_no() + ",");
//		System.out.print(aRa.getReport_date() + ",");
//		System.out.print(aRa.getReport_con() + ",");
//		System.out.print(aRa.getReply_sts() + ",");
//		System.out.println();
//		}
	}
}