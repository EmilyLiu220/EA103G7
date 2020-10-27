package com.mem.model;

import java.sql.*;
import java.util.*;

public class MemJDBCDAO implements MemDAO_interface {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";
	
	private static final String INSERT_MEM = 
		"INSERT INTO MEMBER_TABLE (MEM_NO, MEM_NAME, MEM_ACT, MEM_PSW, MEM_GEN, MEM_BIR, MEM_TEL, MEM_ADRS, MEM_MAIL, MEM_BNS,\r\n" + 
		"    MEM_OD_M, MEM_OD_R, MEM_REVIEW, MEM_REPO, MEM_STS) \r\n" + 
		"    VALUES ('MEM'||LPAD(to_char(SEQ_MEM_NO.nextval), ?, ?, ?, ?, ?, ?, ?, ?, 0, 1, 1, 1, 1, 1)";
	private static final String UPDATE_BY_MEM = 
		"UPDATE MEMBER_TABLE SET MEM_NAME = ?, MEM_PSW = ?, MEM_GEN = ?, MEM_BIR = ?, MEM_TEL = ?, MEM_ADRS = ?, MEM_MAIL = ? WHERE MEM_NO = ?";
	private static final String UPDATE_BY_EMP = 
		"UPDATE MEMBER_TABLE SET MEM_BNS = ?, MEM_OD_M = ?, MEM_OD_R = ?, MEM_REVIEW = ?, MEM_REPO = ?, MEM_STS = ? WHERE MEM_NO = ?";
	private static final String LOGIN_IN = 
			"SELECT MEM_NO, MEM_ACT, MEM_PSW, MEM_STS FROM MEMBER_TABLE WHERE MEM_ACT = ?";
	private static final String FORGET_PSW = 
			"SELECT MEM_NO FROM MEMBER_TABLE WHERE MEM_MAIL = ?";
	private static final String GET_ONE_MEM = 
		"SELECT MEM_NO, MEM_NAME, MEM_ACT, MEM_PSW, MEM_GEN, to_char(MEM_BIR,'yyyy-mm-dd') MEM_BIR, MEM_TEL, MEM_ADRS, MEM_MAIL, MEM_BNS,\r\n" + 
		"    MEM_OD_M, MEM_OD_R, MEM_REVIEW, MEM_REPO, MEM_STS FROM MEMBER_TABLE WHERE MEM_NO = ?";
	private static final String GET_ALL_MEM = 
		"SELECT MEM_NO, MEM_NAME, MEM_ACT, MEM_PSW, MEM_GEN, to_char(MEM_BIR,'yyyy-mm-dd') MEM_BIR, MEM_TEL, MEM_ADRS, MEM_MAIL, MEM_BNS,\r\n" + 
		"    MEM_OD_M, MEM_OD_R, MEM_REVIEW, MEM_REPO, MEM_STS FROM MEMBER_TABLE ORDER BY MEM_NO";
	
	@Override
	public Object insert(MemVO memVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_MEM);
			
			pstmt.setString(1, memVO.getMem_name());
			pstmt.setString(2, memVO.getMem_act());
			pstmt.setString(3, memVO.getMem_psw());
			pstmt.setString(4, memVO.getMem_gen());
			pstmt.setDate(5, memVO.getMem_bir());
			pstmt.setString(6, memVO.getMem_tel());
			pstmt.setString(7, memVO.getMem_adrs());
			pstmt.setString(8, memVO.getMem_mail());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return memVO;
	}
	
	@Override
	public void updateByMem(MemVO memVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_BY_MEM);
			
			pstmt.setString(1, memVO.getMem_name());
			pstmt.setString(2, memVO.getMem_psw());
			pstmt.setString(3, memVO.getMem_gen());
			pstmt.setDate(4, memVO.getMem_bir());
			pstmt.setString(5, memVO.getMem_tel());
			pstmt.setString(6, memVO.getMem_adrs());
			pstmt.setString(7, memVO.getMem_mail());
			pstmt.setString(8, memVO.getMem_no());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void updateByEmp(MemVO memVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_BY_EMP);
			
			pstmt.setInt(1, memVO.getMem_bns());
			pstmt.setInt(2, memVO.getMem_od_m());
			pstmt.setInt(3, memVO.getMem_od_r());
			pstmt.setInt(4, memVO.getMem_review());
			pstmt.setInt(5, memVO.getMem_repo());
			pstmt.setInt(6, memVO.getMem_sts());
			pstmt.setString(7, memVO.getMem_no());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void forgetPsw(MemVO memVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FORGET_PSW);
			
			pstmt.setString(1, memVO.getMem_psw());
			pstmt.setString(2, memVO.getMem_mail());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public MemVO login(String mem_act) {
		
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(LOGIN_IN);
			
			pstmt.setString(1, mem_act);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("MEM_NO"));
				memVO.setMem_act(rs.getString("MEM_ACT"));
				memVO.setMem_psw(rs.getString("MEM_PSW"));
				memVO.setMem_sts(rs.getInt("MEM_STS"));
				
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		
		return memVO;
	}
	
	@Override
	public MemVO findByMem_no(String mem_no) {
		
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_MEM);
			
			pstmt.setString(1, mem_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {	
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("MEM_NO"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setMem_act(rs.getString("MEM_ACT"));
				memVO.setMem_psw(rs.getString("MEM_PSW"));
				memVO.setMem_gen(rs.getString("MEM_GEN"));
				memVO.setMem_bir(rs.getDate("MEM_BIR"));
				memVO.setMem_tel(rs.getString("MEM_TEL"));
				memVO.setMem_adrs(rs.getString("MEM_ADRS"));
				memVO.setMem_mail(rs.getString("MEM_MAIL"));
				memVO.setMem_bns(rs.getInt("MEM_BNS"));
				memVO.setMem_od_m(rs.getInt("MEM_OD_M"));
				memVO.setMem_od_r(rs.getInt("MEM_OD_R"));
				memVO.setMem_review(rs.getInt("MEM_REVIEW"));
				memVO.setMem_repo(rs.getInt("MEM_REPO"));
				memVO.setMem_sts(rs.getInt("MEM_STS"));
				
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		
		return memVO;
	}
	
	@Override
	public List<MemVO> getAll() {
		
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_MEM);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {	
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("MEM_NO"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setMem_act(rs.getString("MEM_ACT"));
				memVO.setMem_psw(rs.getString("MEM_PSW"));
				memVO.setMem_gen(rs.getString("MEM_GEN"));
				memVO.setMem_bir(rs.getDate("MEM_BIR"));
				memVO.setMem_tel(rs.getString("MEM_TEL"));
				memVO.setMem_adrs(rs.getString("MEM_ADRS"));
				memVO.setMem_mail(rs.getString("MEM_MAIL"));
				memVO.setMem_bns(rs.getInt("MEM_BNS"));
				memVO.setMem_od_m(rs.getInt("MEM_OD_M"));
				memVO.setMem_od_r(rs.getInt("MEM_OD_R"));
				memVO.setMem_review(rs.getInt("MEM_REVIEW"));
				memVO.setMem_repo(rs.getInt("MEM_REPO"));
				memVO.setMem_sts(rs.getInt("MEM_STS"));
				list.add(memVO);
				
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
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
		
		MemJDBCDAO dao = new MemJDBCDAO();
		
		// �s�W
//		MemVO memVO1 = new MemVO();
//		memVO1.setMem_name("���̪L");
//		memVO1.setMem_act("D00004");
//		memVO1.setMem_psw("D12345");
//		memVO1.setMem_gen("�k");
//		memVO1.setMem_bir(java.sql.Date.valueOf("1980-09-15"));
//		memVO1.setMem_tel("0988159753");
//		memVO1.setMem_adrs("��饫����");
//		memVO1.setMem_mail("ddd101@gmail.com");
//		dao.insert(memVO1);
//		System.out.println("*�s�W���\*");
		
		// �ק���
//		MemVO memVO2 = new MemVO();
//		memVO2.setMem_no("MEM0055");
//		memVO2.setMem_name("���ש_");
//		memVO2.setMem_psw("E12345");
//		memVO2.setMem_gen("�k");
//		memVO2.setMem_bir(java.sql.Date.valueOf("1976-09-22"));
//		memVO2.setMem_tel("0934852456");
//		memVO2.setMem_adrs("�s�_���T�l��");
//		memVO2.setMem_mail("eee202@gmail.com");
//		dao.updateByMem(memVO2);
//		System.out.println("*��ƭק令�\*");
		
		// �ק��v��
//		MemVO memVO3 = new MemVO();
//		memVO3.setMem_no("MEM0055");
//		memVO3.setMem_bns(10000);
//		memVO3.setMem_od_m(0);
//		memVO3.setMem_od_r(0);
//		memVO3.setMem_review(0);
//		memVO3.setMem_repo(0);
//		memVO3.setMem_sts(0);
//		dao.updateByEmp(memVO3);
//		System.out.println("*�v���ק令�\*");
		
		// �d��
//		MemVO memVO4 = dao.findByMem_no("MEM0001");
//		System.out.println(memVO4.getMem_no());
//		System.out.println(memVO4.getMem_name());
//		System.out.println(memVO4.getMem_act());
//		System.out.println(memVO4.getMem_psw());
//		System.out.println(memVO4.getMem_gen());
//		System.out.println(memVO4.getMem_bir());
//		System.out.println(memVO4.getMem_tel());
//		System.out.println(memVO4.getMem_adrs());
//		System.out.println(memVO4.getMem_mail());
//		System.out.println(memVO4.getMem_od_m());
//		System.out.println(memVO4.getMem_od_r());
//		System.out.println(memVO4.getMem_review());
//		System.out.println(memVO4.getMem_repo());
//		System.out.println(memVO4.getMem_sts());
//		System.out.println("*�d�ߦ��\*");
		
		// �d�ߥ���
//		List<MemVO> list = dao.getAll();
//		for (MemVO aMem : list) {
//			System.out.println(aMem.getMem_no());
//			System.out.println(aMem.getMem_name());
//			System.out.println(aMem.getMem_act());
//			System.out.println(aMem.getMem_psw());
//			System.out.println(aMem.getMem_gen());
//			System.out.println(aMem.getMem_bir());
//			System.out.println(aMem.getMem_tel());
//			System.out.println(aMem.getMem_adrs());
//			System.out.println(aMem.getMem_mail());
//			System.out.println(aMem.getMem_od_m());
//			System.out.println(aMem.getMem_od_r());
//			System.out.println(aMem.getMem_review());
//			System.out.println(aMem.getMem_repo());
//			System.out.println(aMem.getMem_sts());
//		}
//		System.out.println("*�d�ߦ��\*");
		
	}
	
}
