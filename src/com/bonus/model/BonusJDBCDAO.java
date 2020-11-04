package com.bonus.model;

import java.util.*;
import java.sql.*;

public class BonusJDBCDAO implements BonusDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EASHIOU";
	String passwd = "123456";

		private static final String INSERT_STMT =
			"INSERT INTO bonus (bns_no,bns_name,bns_price,bns_stks,bns_date,bns_sts,bns_img) VALUES ('BN'||LPAD(SEQ_BNS_NO.NEXTVAL,4,0), ?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT =
			"SELECT bns_no,bns_name,bns_price,bns_stks,to_char(bns_date,'yyyy-mm-dd') bns_date,bns_sts,bns_img FROM bonus order by bns_no";
		private static final String GET_ONE_STMT =
			"SELECT bns_no,bns_name,bns_price,bns_stks,to_char(bns_date,'yyyy-mm-dd') bns_date,bns_sts,bns_img FROM bonus where bns_no = ?";
		private static final String DELETE =
			"DELETE FROM bonus where bns_no = ?";
		private static final String UPDATE =
			"UPDATE bonus set bns_name=?, bns_price=?, bns_stks=?, bns_date=?, bns_sts=?, bns_img=? where bns_no = ?";

		@Override
		public void insertFromBack(BonusVO bonusVO) {
			// TODO Auto-generated method stub
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);
				
				pstmt.setString(1, bonusVO.getBns_name());
				pstmt.setInt(2, bonusVO.getBns_price());
				pstmt.setInt(3, bonusVO.getBns_stks());
				pstmt.setDate(4, bonusVO.getBns_date());
				pstmt.setInt(5, bonusVO.getBns_sts());
				pstmt.setBytes(6, bonusVO.getBns_img());

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
		public void update(BonusVO bonusVO) {
			// TODO Auto-generated method stub
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, bonusVO.getBns_no());
				pstmt.setString(2, bonusVO.getBns_name());
				pstmt.setInt(3, bonusVO.getBns_price());
				pstmt.setInt(4, bonusVO.getBns_stks());
				pstmt.setDate(5, bonusVO.getBns_date());
				pstmt.setInt(6, bonusVO.getBns_sts());
				pstmt.setBytes(7, bonusVO.getBns_img());
				
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
		public void delete(String bns_no) {
			// TODO Auto-generated method stub
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setString(1, bns_no);

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
		public BonusVO findByPrimaryKey(String bns_no) {
			// TODO Auto-generated method stub
			
			BonusVO bonusVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, bns_no);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// member_reviewVO 也稱為 Domain objects
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
			return bonusVO;
		}

		@Override
		public List<BonusVO> getAll() {
			// TODO Auto-generated method stub
			
				List<BonusVO> list = new ArrayList<BonusVO>();
				BonusVO bonusVO = null;

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

			BonusJDBCDAO dao = new BonusJDBCDAO();

//			// 新增	
			BonusVO bonusVO1 = new BonusVO();
			bonusVO1.setBns_name("iPhone");
			bonusVO1.setBns_price(40000);
			bonusVO1.setBns_stks(23);
			bonusVO1.setBns_date(java.sql.Date.valueOf("2020-12-31"));
			bonusVO1.setBns_sts(1);
//			bonusVO1.setBns_img(?);		
		    dao.insertFromBack(bonusVO1);
			
//			// 修改	    
//		    BonusVO bonusVO2 = new BonusVO();
//		    bonusVO2.setBns_no("BN0021");
//		    bonusVO2.setBns_name("掃地機器人");
//		    bonusVO2.setBns_price(10000);
//		    bonusVO2.setBns_stks(50);
//		    bonusVO2.setBns_date(java.sql.Date.valueOf("2020-12-30"));
//		    bonusVO2.setBns_sts(1);
////		    bonusVO2.setBns_img(?);
//			dao.update(bonusVO2);

			// 刪除
//			dao.delete("BN0022");

			// 查詢		
//			BonusVO bonusVO3 = dao.findByPrimaryKey("BN0008");
//			System.out.print(bonusVO3.getBns_no() + ",");
//			System.out.print(bonusVO3.getBns_name() + ",");
//			System.out.print(bonusVO3.getBns_price() + ",");
//			System.out.print(bonusVO3.getBns_stks() + ",");
//			System.out.print(bonusVO3.getBns_date() + ",");
//			System.out.print(bonusVO3.getBns_sts() + ",");
//			System.out.println("---------------------");

		    // 查詢		
//			List<BonusVO> list = dao.getAll();
//			for (BonusVO aB : list) {
//			System.out.print(aB.getBns_no() + ",");
//			System.out.print(aB.getBns_name() + ",");
//			System.out.print(aB.getBns_price() + ",");
//			System.out.print(aB.getBns_stks() + ",");
//			System.out.print(aB.getBns_date() + ",");
//			System.out.print(aB.getBns_sts() + ",");
//			System.out.println();
//			}
		}
	}