package com.news.model;

import java.sql.SQLException;
import java.util.*;

import com.news.model.NewsJDBCDAO;

import java.sql.*;

public class NewsJDBCDAO implements NewsDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "12345";

	private static final String INSERT_STMT = "INSERT INTO NEWS(NEWS_NO ,EMP_NO ,NEWS_CONT ,NEWS_DATE,NEWS_STS)VALUES('NEWS'||LPAD(SEQ_NEWS_NO.nextval,4,0),?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT NEWS_NO ,EMP_NO ,NEWS_CONT ,to_char(NEWS_DATE,'yyyy-mm-dd') NEWS_DATE,NEWS_STS FROM news order by NEWS_NO DESC";
	private static final String GET_ONE_STMT = "SELECT * FROM news where NEWS_NO =?";
	private static final String DELETE = "DELETE FROM NEWS where NEWS_NO = ?";
	private static final String UPDATE = "UPDATE NEWS SET EMP_NO=? ,NEWS_CONT=? ,NEWS_DATE=? ,NEWS_STS=? WHERE NEWS_NO=?";
	private static final String GET_news_STMT = "SELECT * FROM news where NEWS_NO =? order by news_no DESC";
	private static final String GET_NEWS_STS = "select * from news where news_sts =? ORDER BY news_NO DESC";
	
	// 新增
	@Override
	public void insert(NewsVO newsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, newsVO.getEmp_no());
			pstmt.setString(2, newsVO.getNews_cont());
			pstmt.setDate(3, newsVO.getNews_date());
			pstmt.setInt(4, newsVO.getNews_sts());
			pstmt.executeUpdate();
//			System.out.print("123");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	// 修改
	@Override
	public void update(NewsVO newsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, newsVO.getEmp_no());
			pstmt.setString(2, newsVO.getNews_cont());
			pstmt.setDate(3, newsVO.getNews_date());
			pstmt.setInt(4, newsVO.getNews_sts());
			pstmt.setString(5, newsVO.getNews_no());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Conldn't load database driver. " + e.getMessage());
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

	// 刪除
	@Override
	public void delete(String news_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, news_no);
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Conldn't load database driver. " + e.getMessage());
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

	// 查詢
	@Override
	public NewsVO findByPrimaryKey(String news_no) {

		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, news_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNews_no(rs.getString("news_no"));
				newsVO.setEmp_no(rs.getString("emp_no"));
				newsVO.setNews_cont(rs.getString("news_cont"));
				newsVO.setNews_date(rs.getDate("news_date"));
				newsVO.setNews_sts(rs.getInt("news_sts"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Conldn't load database driver. " + e.getMessage());
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

		return newsVO;
	}

	// 查詢
	@Override
	public List<NewsVO> getAll() {
		List<NewsVO> list = new ArrayList<NewsVO>();

		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNews_no(rs.getString("news_no"));
				newsVO.setEmp_no(rs.getString("emp_no"));
				newsVO.setNews_cont(rs.getString("news_cont"));
				newsVO.setNews_date(rs.getDate("news_date"));
				newsVO.setNews_sts(rs.getInt("news_sts"));
				list.add(newsVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public static void main(String[] args) {

		NewsJDBCDAO dao = new NewsJDBCDAO();

//		
//		NewsVO newsVO1 = new NewsVO();
//		
//		newsVO1.setEmp_no("EMP0002");
//		newsVO1.setNews_cont("15");
//		newsVO1.setNews_date(java.sql.Date.valueOf("2020-11-01"));
//
//		dao.insert(newsVO1);

//		
		NewsVO newsVO2 = new NewsVO();
		newsVO2.setEmp_no("EMP0006");
		newsVO2.setNews_cont("16");
		newsVO2.setNews_date(java.sql.Date.valueOf("2020-01-15"));
		newsVO2.setNews_no("NEWS0007");

		dao.update(newsVO2);

//		刪除
//		dao.delete("NEWS0005");

//		NewsVO newsVO3 = dao.findByPrimaryKey("NEWS0005");
//		System.out.print(newsVO3.getNews_no() + ",");
//		System.out.print(newsVO3.getEmp_no() + ",");
//		System.out.print(newsVO3.getNews_cont() + ",");
//		System.out.print(newsVO3.getNews_date() + ",");

//		List<NewsVO> list = dao.getAll();
//		for(NewsVO aNews:list) {
//			System.out.print(aNews.getNews_no() + ",");
//			System.out.print(aNews.getEmp_no() + ",");
//			System.out.print(aNews.getNews_cont() + ",");
//			System.out.print(aNews.getNews_date() + ",");
//			System.out.println();
//		}
	}

	@Override
	public List<NewsVO> getnewsno(String emp_no) {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_news_STMT);

			pstmt.setString(1, emp_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNews_no(rs.getString("news_no"));
				newsVO.setEmp_no(rs.getString("emp_no"));
				newsVO.setNews_cont(rs.getString("news_cont"));
				newsVO.setNews_date(rs.getDate("news_date"));
				newsVO.setNews_sts(rs.getInt("news_sts"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Conldn't load database driver. " + e.getMessage());
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

	@Override
	public List<NewsVO> frontNews_sts(Integer news_sts) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NewsVO> list = new ArrayList<NewsVO>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_NEWS_STS);
			pstmt.setInt(1, news_sts);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				NewsVO newsVO = new NewsVO();
				newsVO.setNews_no(rs.getString("news_no"));
				newsVO.setEmp_no(rs.getString("emp_no"));
				newsVO.setNews_cont(rs.getString("news_cont"));
				newsVO.setNews_date(rs.getDate("news_date"));
				newsVO.setNews_sts(rs.getInt("news_sts"));
				list.add(newsVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
