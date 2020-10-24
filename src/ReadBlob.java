import java.io.*;
import java.sql.*;

public class ReadBlob {

	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ea103g7";
	private static final String PASSWORD = "123456";
	private static final String SQL = "update meal set meal_img = ? where mealNo= ? ";

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			FileInputStream fis = new FileInputStream("meal_img//" + 1 + ".png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			rs.last();
			int len = rs.getRow();
			for (int i = 1; i <= len; i++) {
				
				InputStream is = getPictureStream("meal_img//" + i + ".png");
				pstmt.setBinaryStream(1, is, is.available());// 第三個參數可放可不放
				pstmt.setString(2, "meal001");
				pstmt.executeUpdate();
				
			}

		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} catch (IOException ie) {
			System.out.println(ie);
		} finally {
			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
		}

	}

	public static InputStream getPictureStream(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		return fis;
	}
}
