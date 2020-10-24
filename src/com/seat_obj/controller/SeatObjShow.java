package com.seat_obj.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seat_obj.model.SeatObjService;
import com.seat_obj.model.SeatObjVO;

public class SeatObjShow extends HttpServlet implements Runnable {

	private static final long serialVersionUID = 1L;
	
	HttpServletRequest req;
	HttpServletResponse res;
	String seat_obj_no;
	ServletOutputStream sos;
	int i =0;

	public SeatObjShow(HttpServletRequest req, HttpServletResponse res, String seat_obj_no) {
		this.req = req;
		this.res = res;
		this.seat_obj_no = seat_obj_no;
	}

	@Override
	public void run() {
		// 獲取圖片來源，將queryString丟入查詢
		SeatObjService seatObjSvc = new SeatObjService();
		SeatObjVO seatObjVO = seatObjSvc.getOneSeatObj(seat_obj_no);

		try {
			// 建立圖片的輸出管
			sos = res.getOutputStream();
			// 條件判斷
			if (seatObjVO.getSeat_obj() == null) {
				sos.println("圖片無法顯示 ！<br>");
			} else {
				if (seatObjVO.getSeat_obj().length == 0) {
					byte[] null_pic = getPictureByteArray(req.getServletContext().getRealPath("/images/null.jpg"));
					ByteArrayInputStream bais = new ByteArrayInputStream(null_pic);
					while (bais.read(null_pic) != -1) {
						sos.write(null_pic);
					}
					// 輸入完畢，清空緩衝
					sos.flush();
				} else {
					// 將bytes位元用ByteArrayInputStream輸入
					ByteArrayInputStream bais = new ByteArrayInputStream(seatObjVO.getSeat_obj());
					// 設定緩衝
					byte[] buffer = new byte[4 * 1024];
					// 輸出，當無字時，read()返回-1
					while (bais.read(buffer) != -1) {
						sos.write(buffer);
					}
					// 輸入完畢，清空緩衝
					sos.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 輸入完畢，清空緩衝
			if (sos != null) {
				try {
					sos.flush();
					sos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 使用byte[]方式
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // 此資料就會把write的位元資料存到一個內建的byte[]
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
			baos.flush();
		}
		baos.close();
		fis.close();

		return baos.toByteArray(); // toByteArray()可以讓我們取得這個資料流內建的byte[]
	}
}
