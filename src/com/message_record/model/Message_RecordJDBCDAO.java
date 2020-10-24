package com.message_record.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Message_RecordJDBCDAO implements Message_RecordDAO_interface{
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "EA103G7";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_MSG = "INSERT INTO MESSAGE_RECORD (MSG_NO,EMP_NO,MEM_NO,MSG_CONT,MSG_STS) VALUES ('MSG'||LPAD(to_char(SEQ_MSG_NO.nextval), 4, '0'), ?, ?, ?, ?)"; 
	private static final String GET_BY_MEMNO = "SELECT EMP_NO, MSG_CONT, MSG_TIME, MSG_STS FROM MESSAGE_RECORD WHERE MEM_NO=? ORDER BY MSG_NO";
	private static final String GET_BY_EMPNO = "SELECT MEM_NO, MSG_CONT, MSG_TIME, MSG_STS FROM MESSAGE_RECORD WHERE EMP_NO=? ORDER BY MSG_NO";
	private static final String UPDATE_MEM_READ_STS = "UPDATE MESSAGE_RECORD SET READ_STS=1 WHERE MSG_STS='0' AND MEM_NO=?";
	private static final String UPDATE_EMP_READ_STS = "UPDATE MESSAGE_RECORD SET READ_STS=1 WHERE MSG_STS='1' AND EMP_NO=?";
	
	@Override
	public void insert(Message_RecordVO message_recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_MSG);
			pstmt.setString(1, message_recordVO.getEmp_no());
			pstmt.setString(2, message_recordVO.getMem_no());
			pstmt.setString(3, message_recordVO.getMsg_cont());
			pstmt.setInt(4, message_recordVO.getMsg_sts());
			pstmt.executeUpdate();
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_BY_EMPNO);
			pstmt.setString(1, emp_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				message_recordVO = new Message_RecordVO();
				message_recordVO.setMem_no(rs.getString("MEM_NO"));
				message_recordVO.setMsg_cont(rs.getString("MSG_CONT"));
				message_recordVO.setMsg_time(rs.getDate("MSG_TIME"));
				message_recordVO.setMsg_sts(rs.getInt("MSG_STS"));
				list.add(message_recordVO);
			}
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_MEM_READ_STS);
			pstmt.setString(1, mem_no);
			pstmt.executeUpdate();
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_EMP_READ_STS);
			pstmt.setString(1, emp_no);
			pstmt.executeUpdate();
		} catch(ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. "+ ce.getMessage());
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

	public static void main(String[] args) {
		
		Message_RecordJDBCDAO dao = new Message_RecordJDBCDAO();
		
		// 新增
		Message_RecordVO message_recordVO = new Message_RecordVO();
		message_recordVO.setEmp_no("EMP0005");
		message_recordVO.setMem_no("MEM0012");
		message_recordVO.setMsg_cont("請問今天有營業嗎?");
		message_recordVO.setMsg_sts(0);
		dao.insert(message_recordVO);
		
		// 查詢
		List<Message_RecordVO> list = dao.findByMemNo("MEM0032");
		for(Message_RecordVO amsgVO : list) {
			System.out.print(amsgVO.getEmp_no() + ", ");
			System.out.print(amsgVO.getMsg_cont() + ", ");
			System.out.print(amsgVO.getMsg_time() + ", ");
			System.out.print(amsgVO.getMsg_sts() + ", ");
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------------------------");

		// 修改會員訊息讀取狀態
		dao.updateMemReadSts("MEM0032");
		
		// 修改員工訊息讀取狀態
		dao.updateEmpReadSts("EMP0009");
		
	}

}
