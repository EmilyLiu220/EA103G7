package com.news.model;

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

public class NewsDAO implements NewsDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO NEWS(NEWS_NO ,EMP_NO ,NEWS_CONT ,NEWS_DATE,NEWS_STS)VALUES('NEWS'||LPAD(SEQ_NEWS_NO.nextval,4,0),?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT NEWS_NO ,EMP_NO ,NEWS_CONT ,to_char(NEWS_DATE,'yyyy-mm-dd') NEWS_DATE,NEWS_STS FROM news order by NEWS_NO DESC";
	private static final String GET_ONE_STMT = "SELECT NEWS_NO ,EMP_NO ,NEWS_CONT ,to_char(NEWS_DATE,'yyyy-mm-dd') NEWS_DATE,NEWS_STS FROM news where NEWS_NO =?";
	private static final String DELETE = "DELETE FROM NEWS where NEWS_NO = ?";
	private static final String UPDATE = "UPDATE NEWS SET EMP_NO=? ,NEWS_CONT=? ,NEWS_DATE=?,NEWS_STS=? WHERE NEWS_NO=?";
	private static final String GET_NEWS_STMT = "SELECT * FROM news where emp_NO =?order by NEWS_NO DESC";
	private static final String GET_NEWS_STS = "select * from news where news_sts =?ORDER BY news_NO DESC";
	
	@Override
	public void insert(NewsVO newsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, newsVO.getEmp_no());
			pstmt.setString(2, newsVO.getNews_cont());
			pstmt.setDate(3, newsVO.getNews_date());
			pstmt.setInt(4, newsVO.getNews_sts());
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
	public void update(NewsVO newsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			
			pstmt.setString(1, newsVO.getEmp_no());
			pstmt.setString(2, newsVO.getNews_cont());
			pstmt.setDate(3, newsVO.getNews_date());
			pstmt.setInt(4, newsVO.getNews_sts());
			pstmt.setString(5, newsVO.getNews_no());
			pstmt.executeUpdate();
			
			System.out.println("更新成功");
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
	public void delete(String news_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, news_no);
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
	public NewsVO findByPrimaryKey(String news_no) {
		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
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
		return newsVO;
	}

	@Override
	public List<NewsVO> getAll() {
		List<NewsVO> list = new ArrayList<NewsVO>();

		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
	public List<NewsVO> getnewsno(String emp_no) {
		List<NewsVO> list = new ArrayList<NewsVO>();

		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NEWS_STMT);
			pstmt.setString(1, emp_no);
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

			con = ds.getConnection();
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
