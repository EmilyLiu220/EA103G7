package com.message_record.model;

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

public class Message_RecordJNDIDAO implements Message_RecordDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_MSG = "INSERT INTO MESSAGE_RECORD (MSG_NO,EMP_NO,MEM_NO,MSG_CONT,MSG_STS) VALUES ('MSG'||LPAD(to_char(SEQ_MSG_NO.nextval), 4, '0'), ?, ?, ?, ?)"; 
	private static final String GET_BY_MEMNO = "SELECT EMP_NO, MSG_CONT, MSG_TIME, MSG_STS FROM MESSAGE_RECORD WHERE MEM_NO=? ORDER BY MSG_NO";
	private static final String GET_BY_EMPNO = "SELECT MEM_NO, MSG_CONT, MSG_TIME, MSG_STS FROM MESSAGE_RECORD WHERE EMP_NO=? ORDER BY MSG_NO";
	private static final String UPDATE_MEM_READ_STS = "UPDATE MESSAGE_RECORD SET READ_STS='1' WHERE MSG_STS='0' AND MEM_NO=?";
	private static final String UPDATE_EMP_READ_STS = "UPDATE MESSAGE_RECORD SET READ_STS='1' WHERE MSG_STS='1' AND EMP_NO=?";
	
	@Override
	public void insert(Message_RecordVO message_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_MSG);
			pstmt.setString(1, message_recordVO.getEmp_no());
			pstmt.setString(2, message_recordVO.getMem_no());
			pstmt.setString(3, message_recordVO.getMsg_cont());
			pstmt.setInt(4, message_recordVO.getMsg_sts());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
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
	public List<Message_RecordVO> findByMemNo(String mem_no) {
		List<Message_RecordVO> list = new ArrayList<Message_RecordVO>();
		Message_RecordVO message_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MEMNO);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				message_recordVO = new Message_RecordVO();
				message_recordVO.setEmp_no(rs.getString("EMP_NO"));
				message_recordVO.setMsg_cont(rs.getString("MSG_CONT"));
				message_recordVO.setMsg_time(rs.getDate("MSG_TIME"));
				message_recordVO.setMsg_sts(rs.getInt("MSG_STS"));
				list.add(message_recordVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
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
	public List<Message_RecordVO> findByEmpNo(String emp_no) {
		List<Message_RecordVO> list = new ArrayList<Message_RecordVO>();
		Message_RecordVO message_recordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_EMPNO);
			pstmt.setString(1, emp_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				message_recordVO = new Message_RecordVO();
				message_recordVO.setEmp_no(rs.getString("EMP_NO"));
				message_recordVO.setMsg_cont(rs.getString("MSG_CONT"));
				message_recordVO.setMsg_time(rs.getDate("MSG_TIME"));
				message_recordVO.setMsg_sts(rs.getInt("MSG_STS"));
				list.add(message_recordVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
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
	public void updateMemReadSts(String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_MEM_READ_STS);
			pstmt.setString(1, mem_no);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
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
	public void updateEmpReadSts(String emp_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_EMP_READ_STS);
			pstmt.setString(1, emp_no);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
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
}
