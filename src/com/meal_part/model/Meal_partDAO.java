package com.meal_part.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.food.model.FoodService;
import com.meal.model.MealService;

import java.sql.*;

public class Meal_partDAO implements Meal_partDAO_interface {
	
	private static final String INSERT_STMT = "INSERT INTO MEAL_PART (MEAL_NO,FD_NO,FD_GW) VALUES (?,?,?)";
	private static final String DELETE = "DELETE FROM MEAL_PART WHERE MEAL_NO=? AND FD_NO=?";
	private static final String GET_ALL_STMT = "SELECT MEAL_NO,FD_NO,FD_GW FROM MEAL_PART";
	private static final String GET_ONE_STMT = "SELECT FD_GW FROM MEAL_PART WHERE MEAL_NO=? AND FD_NO=?";
	private static final String UPDATE = "UPDATE MEAL_PART SET FD_GW=? WHERE MEAL_NO=? AND FD_NO=?";
	private static final String GET_NUT_ByMealno_STMT =
			"select sum(fd_gw*cal)/100 cal, sum(fd_gw*prot)/100 prot,sum(fd_gw*carb)/100 carb,sum(fd_gw*fat)/100 fat from food f " + 
			"join meal_part m on m.fd_no=f.fd_no " + 
			"where m.meal_no=?";
	private static final String GET_NUT_BY_MEAL_NO_STMT =
			"select sum(fd_gw*cal*meal_qty)/100 cal, sum(fd_gw*prot*meal_qty)/100 prot,sum(fd_gw*carb*meal_qty)/100 carb,sum(fd_gw*fat*meal_qty)/100 fat from food f " +
			"join meal_part mp on mp.fd_no=f.fd_no " +
			"join meal_set_consist m on m.meal_no=mp.meal_no " +
			"where m.meal_set_no=?";
	private static final String GET_MEAL_PART_BY_MEAL_NO_STMT =
			"select * from meal_part " +
			"where meal_no=?";
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
			MealService Msvc=new MealService();
			FoodService FDsvc=new FoodService();
			if(se.toString().contains("ORA-00001")) {
				throw new RuntimeException("餐點"+Msvc.searchByNo(meal_partVO.getMeal_no()).getMeal_name()+"已經有使用食材"+FDsvc.getOneFood(meal_partVO.getFd_no()).getFd_name());
			}
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
	public void update(Meal_partVO meal_partVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
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
	public void insert(List<Meal_partVO> list, Connection con) {
		PreparedStatement pstmt = null;

		for(Meal_partVO meal_partVO:list) {
			try {
				pstmt = con.prepareStatement(INSERT_STMT);
				pstmt.setString(1, meal_partVO.getMeal_no());
				pstmt.setString(2, meal_partVO.getFd_no());
				pstmt.setDouble(3, meal_partVO.getFd_gw());
				pstmt.executeUpdate();
				
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
			con = ds.getConnection();
			map=new HashMap<String,Double>();
			pstmt = con.prepareStatement(GET_NUT_ByMealno_STMT);
			pstmt.setString(1, meal_no);
			rs = pstmt.executeQuery();
			rs.next(); 
			map.put("cal", rs.getDouble(1));
			map.put("prot", rs.getDouble(2));
			map.put("carb", rs.getDouble(3));
			map.put("fat", rs.getDouble(4));
			System.out.println(map.get("fat"));
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

	@Override
	public Map<String, Double> get_nut_by_meal_set_no(String meal_set_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String,Double> map=null;
		try {
			con = ds.getConnection();
			map=new HashMap<String,Double>();
			pstmt = con.prepareStatement(GET_NUT_BY_MEAL_NO_STMT);
			pstmt.setString(1, meal_set_no);
			rs = pstmt.executeQuery();
			rs.next(); 
			map.put("cal", rs.getDouble(1));
			map.put("prot", rs.getDouble(2));
			map.put("carb", rs.getDouble(3));
			map.put("fat", rs.getDouble(4));
			System.out.println(map.get("fat"));
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
	
	@Override
	public List<Meal_partVO> get_meal_part_by_mealno(String meal_no) {
		List<Meal_partVO> list = new ArrayList<Meal_partVO>();
		Meal_partVO meal_partVO = null;		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEAL_PART_BY_MEAL_NO_STMT);
			pstmt.setString(1, meal_no);
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
	
	public void delete(String meal_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("DELETE FROM MEAL_PART WHERE MEAL_NO = ?");
			pstmt.setString(1, meal_no);

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
}