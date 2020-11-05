package com.ad.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdJDBCDAO implements AdDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "12345";

	private static final String INSERT_STMT = "INSERT INTO AD(AD_NO ,EMP_NO ,AD_TITLE ,AD_CONT,AD_ADD_DATE,AD_RE_DATE,AD_IMG,AD_STS)VALUES('AD'||LPAD(SEQ_AD_NO.nextval,4,0),?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM AD ODRER BY AD_NO DESC";
	private static final String GET_ONE_STMT = "SELECT * FROM AD WHERE AD_NO =?";
	private static final String DELETE = "DELETE FROM AD WHERE AD_NO = ?";
	private static final String UPDATE = "UPDATE AD SET EMP_NO=? , AD_TITLE=? ,AD_CONT=? ,AD_ADD_DATE=? ,AD_RE_DATE=? ,AD_IMG=? ,AD_STS=? WHERE AD_NO=?";
	private static final String GET_AD_STMT = "SELECT * FROM AD WHERE emp_NO =? order by ad_NO DESE";
//	private static final String UPAD = "UPDATE AD SET EMP_NO=? , AD_ADD_DATE=? ,AD_RE_DATE=? ,AD_STS=? WHERE AD_NO=?";
	private static final String SEARCH_ADSTS = "select * from ad where ad_sts =?ORDER BY AD_NO DESC";

	@Override
	public List<AdVO> getadno(String emp_no) {

		List<AdVO> list = new ArrayList<AdVO>();

		AdVO adVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_AD_STMT);
			pstmt.setString(1, emp_no);
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
				adVO.setAd_sts(rs.getInt("ad_sts"));
				list.add(adVO);
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

	// 新增
	@Override
	public void insert(AdVO adVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, adVO.getEmp_no());
			pstmt.setString(2, adVO.getAd_title());
			pstmt.setString(3, adVO.getAd_cont());
			pstmt.setDate(4, adVO.getAd_add_date());
			pstmt.setDate(5, adVO.getAd_re_date());
			pstmt.setBytes(6, adVO.getAd_img());
			pstmt.setInt(7, adVO.getAd_sts());
			pstmt.executeUpdate();

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
	public void update(AdVO adVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(8, adVO.getAd_no());
			pstmt.setString(1, adVO.getEmp_no());
			pstmt.setString(2, adVO.getAd_title());
			pstmt.setString(3, adVO.getAd_cont());
			pstmt.setDate(4, adVO.getAd_add_date());
			pstmt.setDate(5, adVO.getAd_re_date());
			pstmt.setBytes(6, adVO.getAd_img());
			pstmt.setInt(7, adVO.getAd_sts());
			pstmt.executeUpdate();

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

	// 刪除
	@Override
	public void delete(String ad_no) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ad_no);
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
	public AdVO findByPrimaryKey(String ad_no) {

		AdVO adVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

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
				adVO.setAd_sts(rs.getInt("ad_sts"));
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
		return adVO;
	}

	// 查詢
	@Override
	public List<AdVO> getAll() {

		List<AdVO> list = new ArrayList<AdVO>();

		AdVO adVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
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
				adVO.setAd_sts(rs.getInt("ad_sts"));
				list.add(adVO);
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

		AdJDBCDAO dao = new AdJDBCDAO();

		// 新增
//		try {
//			byte[] pic = getPictureByteArray("Img/888.jpg");
//			AdVO AdVO1 = new AdVO();
//			
//			AdVO1.setEmp_no("EMP0003");
//			AdVO1.setAd_title("c8 c9 c8 ");
//			AdVO1.setAd_cont("c8 c8 8c ");
//			AdVO1.setAd_add_date(java.sql.Date.valueOf("2020-01-01"));
//			AdVO1.setAd_re_date(java.sql.Date.valueOf("2020-01-01"));
//			AdVO1.setAd_img(pic);
//			dao.insert(AdVO1);
//		} catch (IOException ie) {
//			throw new RuntimeException(" error . " + ie.getMessage());
//		}

		// 修改
//		try {
//			byte[] pic = getPictureByteArray("Img/222.jpg");
//			AdVO AdVO2 = new AdVO();
//			AdVO2.setAd_no("AD0009");
//			AdVO2.setEmp_no("EMP0010");
//			AdVO2.setAd_title("ccccc");
//			AdVO2.setAd_cont("cccc");
//			AdVO2.setAd_add_date(java.sql.Date.valueOf("2019-01-01"));
//			AdVO2.setAd_re_date(java.sql.Date.valueOf("2019-12-01"));
//			AdVO2.setAd_img(pic);
//			dao.update(AdVO2);
//		} catch (IOException ie) {
//			throw new RuntimeException(" error . " + ie.getMessage());
//		}

		// 刪除
//		dao.delete("AD0001");

		// 查詢
//
//		AdVO AdVO4 = dao.findByPrimaryKey("AD0002");
//		System.out.print(AdVO4.getAd_no() + ",");
//		System.out.print(AdVO4.getEmp_no() + ",");
//		System.out.print(AdVO4.getAd_title() + ",");
//		System.out.print(AdVO4.getAd_cont() + ",");
//		System.out.print(AdVO4.getAd_add_date() + ",");
//		System.out.print(AdVO4.getAd_re_date() + ",");
//		System.out.print(AdVO4.getAd_img() + ",");

		// 查詢
		List<AdVO> list = dao.getAll();
		for (AdVO aAd : list) {
			System.out.print(aAd.getAd_no() + ",");
			System.out.print(aAd.getEmp_no() + ",");
			System.out.print(aAd.getAd_title() + ",");
			System.out.print(aAd.getAd_cont() + ",");
			System.out.print(aAd.getAd_add_date() + ",");
			System.out.print(aAd.getAd_re_date() + ",");
			System.out.print(aAd.getAd_img() + ",");
			System.out.print(aAd.getAd_sts() + ",");
			System.out.println();
		}
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
			baos.flush();
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

//	@Override
//	public void upad(AdVO adVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(UPAD);
//
//			pstmt.setString(1, adVO.getEmp_no());
//			pstmt.setDate(2, adVO.getAd_add_date());
//			pstmt.setDate(3, adVO.getAd_re_date());
//			pstmt.setInt(4, adVO.getAd_sts());
//			pstmt.setString(5, adVO.getAd_no());
//			pstmt.executeUpdate();
//
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//		public List<AdVO> find_adsts() {
//
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			List<AdVO> list = new ArrayList<AdVO>();
//
//			try {
//				Class.forName(driver);
//				con = DriverManager.getConnection(url, userid, passwd);
//				pstmt = con.prepareStatement(SEARCH_ADSTS);
//
//				pstmt.setInt(1, ad_sts);
//				rs = pstmt.executeQuery();
//
//				while (rs.next()) {
//					adVO = new AdVO();
//					adVO.setAd_no(rs.getString("ad_no"));
//					adVO.setEmp_no(rs.getString("emp_no"));
//					adVO.setAd_title(rs.getString("ad_title"));
//					adVO.setAd_cont(rs.getString("ad_cont"));
//					adVO.setAd_add_date(rs.getDate("ad_add_date"));
//					adVO.setAd_re_date(rs.getDate("ad_re_date"));
//					adVO.setAd_img(rs.getBytes("ad_img"));
//					adVO.setAd_sts(rs.getInt("ad_sts"));
//				}
//			} catch (ClassNotFoundException e) {
//				throw new RuntimeException("Conldn't load database driver. " + e.getMessage());
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return list;
//		}
//		}

	@Override
	public List<AdVO> find_adsts(Integer ad_sts) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdVO> list = new ArrayList<AdVO>();

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SEARCH_ADSTS);

			pstmt.setInt(1, ad_sts);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AdVO adVO = new AdVO();
				adVO.setAd_no(rs.getString("ad_no"));
				adVO.setEmp_no(rs.getString("emp_no"));
				adVO.setAd_title(rs.getString("ad_title"));
				adVO.setAd_cont(rs.getString("ad_cont"));
				adVO.setAd_add_date(rs.getDate("ad_add_date"));
				adVO.setAd_re_date(rs.getDate("ad_re_date"));
				adVO.setAd_img(rs.getBytes("ad_img"));
				adVO.setAd_sts(rs.getInt("ad_sts"));
				list.add(adVO);
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
}
