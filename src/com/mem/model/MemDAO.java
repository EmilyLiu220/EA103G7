package com.mem.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemDAO implements MemDAO_interface {
	
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_MEM = 
			"INSERT INTO MEMBER_TABLE (MEM_NO, MEM_NAME, MEM_ACT, MEM_PSW, MEM_GEN, MEM_BIR, MEM_TEL, MEM_ADRS, MEM_MAIL, MEM_BNS,\r\n" + 
			"    MEM_OD_M, MEM_OD_R, MEM_REVIEW, MEM_REPO, MEM_STS) \r\n" + 
			"    VALUES ('MEM'||LPAD(to_char(SEQ_MEM_NO.nextval), 4, '0'), ?, ?, ?, ?, ?, ?, ?, ?, 0, 1, 1, 1, 1, 1)";
	private static final String UPDATE_BY_MEM = 
			"UPDATE MEMBER_TABLE SET MEM_NAME = ?, MEM_PSW = ?, MEM_GEN = ?, MEM_BIR = ?, MEM_TEL = ?, MEM_ADRS = ?, MEM_MAIL = ? WHERE MEM_NO = ?";
	private static final String UPDATE_BY_EMP = 
			"UPDATE MEMBER_TABLE SET MEM_BNS = ?, MEM_OD_M = ?, MEM_OD_R = ?, MEM_REVIEW = ?, MEM_REPO = ?, MEM_STS = ? WHERE MEM_NO = ?";
	private static final String FORGET_PSW = 
			"UPDATE MEMBER_TABLE SET MEM_PSW = ? WHERE MEM_MAIL = ?";
	private static final String LOGIN_IN = 
			"SELECT MEM_NO, MEM_ACT, MEM_PSW, MEM_STS FROM MEMBER_TABLE WHERE MEM_ACT = ?";
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
		String[] generatedColumns = {"mem_no"};
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_MEM, generatedColumns);
			
			pstmt.setString(1, memVO.getMem_name());
			pstmt.setString(2, memVO.getMem_act());
			pstmt.setString(3, memVO.getMem_psw());
			pstmt.setString(4, memVO.getMem_gen());
			pstmt.setDate(5, memVO.getMem_bir());
			pstmt.setString(6, memVO.getMem_tel());
			pstmt.setString(7, memVO.getMem_adrs());
			pstmt.setString(8, memVO.getMem_mail());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			String mem_no = rs.getString(1);
			
			memVO.setMem_no(mem_no);
			
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
			
			con = ds.getConnection();
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_BY_EMP);
			
			pstmt.setInt(1, memVO.getMem_bns());
			pstmt.setInt(2, memVO.getMem_od_m());
			pstmt.setInt(3, memVO.getMem_od_r());
			pstmt.setInt(4, memVO.getMem_review());
			pstmt.setInt(5, memVO.getMem_repo());
			pstmt.setInt(6, memVO.getMem_sts());
			pstmt.setString(7, memVO.getMem_no());
			
			pstmt.executeUpdate();
			
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FORGET_PSW);
			
			pstmt.setString(1, memVO.getMem_psw());
			pstmt.setString(2, memVO.getMem_mail());
			
			pstmt.executeUpdate();
			
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
			con = ds.getConnection();
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
			
			con = ds.getConnection();
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
			
			con = ds.getConnection();
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

}
