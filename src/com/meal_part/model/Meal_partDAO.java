package com.meal_part.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;

public class Meal_partDAO implements Meal_partDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO MEAL_PART (MEAL_NO,FD_NO,FD_GW) VALUES (?,?,?)";
	private static final String DELETE = "DELETE FROM MEAL_PART WHERE MEAL_NO=? AND FD_NO=?";
	private static final String GET_ALL_STMT = "SELECT MEAL_NO,FD_NO,FD_GW FROM MEAL_PART";
	private static final String GET_MEAL_NAME_STMT = "SELECT MEAL_NAME WHERE MEAL_NO=?";
	//private static final String GET_FOOD_NAME_STMT = "SELECT FD_NAME WHERE FD_NO=?";
	
	private static final String GET_ONE_STMT = "SELECT FD_GW FROM MEAL_PART WHERE MEAL_NO=? AND FD_NO=?";
	private static final String UPDATE = "UPDATE MEAL_PART SET FD_GW=? WHERE MEAL_NO=? AND FD_NO=?";
	private static final String GET_NUT_ByMealno_STMT =
			"select n.nut_name,sum(mp.fd_gw*nv.nut_per100/100) as nuts from meal_part mp " +
			"join nut_value nv on nv.fd_no=mp.fd_no " +
			"join nut n on n.nut_no=nv.nut_no " +
			"where mp.meal_no=? " +
			"group by n.nut_name";
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G7");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void insert(Meal_partVO meal_partVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, meal_partVO.getMeal_no());
			pstmt.setString(2, meal_partVO.getFd_no());
			pstmt.setDouble(3, meal_partVO.getFd_gw());
			pstmt.executeUpdate();			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void insert(List<Meal_partVO> list, Connection con) {
		PreparedStatement pstmt = null;

		for(Meal_partVO meal_partVO:list) {
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(INSERT_STMT);
				pstmt.setString(1, meal_partVO.getMeal_no());
				pstmt.setString(2, meal_partVO.getFd_no());
				pstmt.setDouble(3, meal_partVO.getFd_gw());
				pstmt.executeUpdate();
				con.commit();
			} catch (SQLException se) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "+ excep.getMessage());
				}
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
			}
		}
	}
	
	@Override
	public void update(Meal_partVO meal_partVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();pstmt = con.prepareStatement(UPDATE);
			pstmt.setDouble(1, meal_partVO.getFd_gw());
			pstmt.setString(2, meal_partVO.getMeal_no());
			pstmt.setString(3, meal_partVO.getFd_no());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(String meal_no, String fd_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, meal_no);
			pstmt.setString(2, fd_no);

			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public Meal_partVO findByPrimaryKey(String meal_no, String fd_no) {
		Meal_partVO meal_partVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, meal_no);
			pstmt.setString(2, fd_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				meal_partVO = new Meal_partVO();
				meal_partVO.setMeal_no(meal_no);
				meal_partVO.setFd_no(fd_no);
				meal_partVO.setFd_gw(rs.getDouble("fd_gw"));
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return meal_partVO;
	}

	@Override
	public List<Meal_partVO> getAll() {
		List<Meal_partVO> list = new ArrayList<Meal_partVO>();
		Meal_partVO meal_partVO = null;		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				meal_partVO = new Meal_partVO();
				meal_partVO.setMeal_no(rs.getString("meal_no"));
				meal_partVO.setFd_no(rs.getString("fd_no"));
				meal_partVO.setFd_gw(rs.getDouble("fd_gw"));
				list.add(meal_partVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	
	@Override
	public Map<String,Double> get_NUT_ByMealno(String meal_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String,Double> map=null;
		try {
			map=new HashMap<String,Double>();
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NUT_ByMealno_STMT);
			
			pstmt.setString(1, meal_no);		
			rs = pstmt.executeQuery();		
			while(rs.next()) {
				map.put(rs.getString(1), rs.getDouble(2));
			}
			
			// Handle any driver errors
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
		return map;
	}
}