package com.seat_obj.model;

import java.io.*;
import java.sql.*;
import java.util.*;

public class SeatObjJDBCDAO implements SeatObjDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G7";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO SEAT_OBJ (SEAT_OBJ_NO, SEAT_OBJ, SEAT_OBJ_STS, SEAT_PEOPLE, SEAT_USE) VALUES ('SO'||LPAD(SEQ_SEAT_OBJ_NO.NEXTVAL,4,0), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT SEAT_OBJ_NO, SEAT_OBJ, SEAT_OBJ_STS, SEAT_PEOPLE, SEAT_USE FROM SEAT_OBJ ORDER BY SEAT_OBJ_NO";
	private static final String GET_ONE_STMT = "SELECT SEAT_OBJ, SEAT_OBJ_STS, SEAT_PEOPLE, SEAT_USE FROM SEAT_OBJ WHERE SEAT_OBJ_NO = ?";
	private static final String UPDATE = "UPDATE SEAT_OBJ SET SEAT_OBJ=?, SEAT_OBJ_STS=?, SEAT_PEOPLE=?, SEAT_USE=? WHERE SEAT_OBJ_NO = ?";

	@Override
	public void insert(SeatObjVO seatObjVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setBytes(1, seatObjVO.getSeat_obj());
			pstmt.setInt(2, new Integer(seatObjVO.getSeat_obj_sts()));
			pstmt.setInt(3, new Integer(seatObjVO.getSeat_people()));
			pstmt.setInt(4, new Integer(seatObjVO.getSeat_use()));

			pstmt.executeUpdate();

//			System.out.println("Insert success");

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public void update(SeatObjVO seatObjVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			if (seatObjVO.getSeat_obj() != null) {
				pstmt.setBytes(1, seatObjVO.getSeat_obj());
			} else {
				pstmt.setBytes(1, null);
			}
			pstmt.setInt(2, new Integer(seatObjVO.getSeat_obj_sts()));
			pstmt.setInt(3, new Integer(seatObjVO.getSeat_people()));
			pstmt.setInt(4, new Integer(seatObjVO.getSeat_use()));
			pstmt.setString(5, seatObjVO.getSeat_obj_no());

			pstmt.executeUpdate();

//			System.out.println("Update success");

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public SeatObjVO findByPrimaryKey(String seat_obj_no) {

		SeatObjVO seatObjVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, seat_obj_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				seatObjVO = new SeatObjVO();
				seatObjVO.setSeat_obj_no(seat_obj_no);
				if (rs.getBytes("SEAT_OBJ") != null) {
					seatObjVO.setSeat_obj(rs.getBytes("SEAT_OBJ"));
				}
				seatObjVO.setSeat_obj_sts(new Integer(rs.getInt("SEAT_OBJ_STS")));
				seatObjVO.setSeat_people(new Integer(rs.getInt("SEAT_PEOPLE")));
				seatObjVO.setSeat_use(new Integer(rs.getInt("SEAT_USE")));
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return seatObjVO;
	}

	@Override
	public List<SeatObjVO> getAll() {
		List<SeatObjVO> list = new ArrayList<SeatObjVO>();
		SeatObjVO seatObjVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt("SEAT_OBJ_STS") == 0) {
					seatObjVO = new SeatObjVO();
					seatObjVO.setSeat_obj_no(rs.getString("SEAT_OBJ_NO"));
					if (rs.getBytes("SEAT_OBJ") != null) {
						seatObjVO.setSeat_obj(rs.getBytes("SEAT_OBJ"));
					}
					seatObjVO.setSeat_obj_sts(new Integer(rs.getInt("SEAT_OBJ_STS")));
					seatObjVO.setSeat_people(new Integer(rs.getInt("SEAT_PEOPLE")));
					seatObjVO.setSeat_use(new Integer(rs.getInt("SEAT_USE")));
					
					list.add(seatObjVO); // Store the row in the list
				}
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// bytesArrayToByteObject error
		} catch (NullPointerException npe) {
			throw new RuntimeException("A bytesArrayToByteObject error occured. " + npe.getMessage());
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

//	public static void main(String[] args) {
//
//		SeatObjJDBCDAO dao = new SeatObjJDBCDAO();
//
//		// insert
//		try {
//			SeatObjVO seatObjVO = new SeatObjVO();
//			seatObjVO.setSeat_obj(getPictureByteArray("WebContent/test_images/apple.png"));
//			seatObjVO.setSeat_obj_sts(0);
//			seatObjVO.setSeat_people(4);
//			seatObjVO.setSeat_use(0);
//			dao.insert(seatObjVO);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		// update
//		try {
//			SeatObjVO seatObjVO1 = new SeatObjVO();
//			seatObjVO1.setSeat_obj_no("SO0007");
//			seatObjVO1.setSeat_obj(getPictureByteArray("WebContent/test_images/apple.png"));
//			seatObjVO1.setSeat_obj_sts(1);
//			seatObjVO1.setSeat_people(2);
//			seatObjVO1.setSeat_use(0);
//			dao.update(seatObjVO1);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		// get one seat obj
//		SeatObjVO seatObjVO2 = dao.findByPrimaryKey("SO0001");
//		System.out.print(seatObjVO2.getSeat_obj_no() + ",");
//		System.out.print(seatObjVO2.getSeat_obj() + ",\t");
//		System.out.print(seatObjVO2.getSeat_obj_sts() + ",");
//		System.out.print(seatObjVO2.getSeat_people() + ",");
//		System.out.print(seatObjVO2.getSeat_use() + ",");
//	
//		// select all
//		List<SeatObjVO> list = dao.getAll();for(
//		SeatObjVO seatObjVO3:list)
//		{
//			System.out.print(seatObjVO3.getSeat_obj_no() + ",");
//			System.out.print(seatObjVO3.getSeat_obj() + ",\t");
//			System.out.print(seatObjVO3.getSeat_obj_sts() + ",");
//			System.out.print(seatObjVO3.getSeat_people() + ",");
//			System.out.print(seatObjVO3.getSeat_use() + ",");
//	
//			System.out.println();
//		}
//	}

	// 使用byte[]方式傳輸圖片
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // 此資料就會把write的位元資料存到一個內建的byte[]
		byte[] buffer = new byte[8 * 1024];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
			baos.flush();
		}
		baos.close();
		fis.close();

		return baos.toByteArray(); // toByteArray()可以讓我們取得這個資料流內建的byte[]
	}

//	// byte[]轉包裝類別Byte[]
//	public static Byte[] bytesArrayToByteObject(byte[] bytesArray) {
//
//		Byte[] bytesObject = new Byte[bytesArray.length];
//
//		int i = 0;
//		for (byte bytesArray1 : bytesArray)
//			bytesObject[i++] = bytesArray1;
//
//		return bytesObject;
//	}
//
//	// 包裝類別Byte[]轉byte[]
//	public static byte[] bytesObjectToBytesArray(Byte[] bytesObject) {
//
//		byte[] bytesArray = new byte[bytesObject.length];
//
//		int i = 0;
//		for (Byte bytesObject1 : bytesObject)
//			bytesArray[i++] = bytesObject1.byteValue();
//
//		return bytesArray;
//	}
}
