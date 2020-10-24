package com.seat_obj.controller;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.seat_obj.model.SeatObjService;
import com.seat_obj.model.SeatObjVO;
@WebServlet("/seat/Seat_ObjServlet.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class SeatObjServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		/**************************** 桌位物件刪除(修改狀態) ****************************/
		if ("delete_use_update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String seat_obj_no = req.getParameter("seat_obj_no");
				SeatObjService seatObjSve = new SeatObjService();
				SeatObjVO seatObjVO = seatObjSve.getOneSeatObj(seat_obj_no);
				seatObjSve.updateSeatObj(seatObjVO.getSeat_obj_no(), seatObjVO.getSeat_obj(), new Integer(1),
						seatObjVO.getSeat_people(), seatObjVO.getSeat_use());
				res.sendRedirect(req.getContextPath() + "/back-end/seat_obj/setSeatObj.jsp");
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/seat_obj/setSeatObj.jsp");
				failureView.forward(req, res);
			}
			return;
		}
		/*************************** 到桌位物件修改頁面 ***************************/
		if ("go_update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				SeatObjService seatObjSvc = new SeatObjService();
				SeatObjVO seatObjVO = seatObjSvc.getOneSeatObj(req.getParameter("seat_obj_no"));
				req.setAttribute("seatObjVO", seatObjVO);
			} catch (Exception e) {
				errorMsgs.add("到桌位物件修改頁面異常:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/seat_obj/updateSeatObj.jsp");
				failureView.forward(req, res);
			}
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/seat_obj/updateSeatObj.jsp");
			failureView.forward(req, res);
			return;
		}

		/****************************** 桌位物件修改 ******************************/
		if ("one_obj_update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			Collection<Part> parts;
			InputStream is = null;
			parts = req.getParts(); // Servlet3.0新增了Part介面，讓我們方便的進行檔案上傳處理

			try {
				SeatObjService seatObjSve = new SeatObjService();
				String seat_obj_no = req.getParameter("seat_obj_no");

				SeatObjVO seatObjVO = seatObjSve.getOneSeatObj(seat_obj_no);
				String[] seat_people = req.getParameterValues("seat_people");
				for (Part part : parts) {
					is = part.getInputStream();
					byte[] pic = new byte[is.available()];
					is.read(pic);
					String filename = getFileNameFromPart(part);
					String mimeType = getServletContext().getMimeType(filename);
					if (filename == null && part.getContentType() != null) {
						if (Integer.parseInt(req.getParameter("seat_people")) == seatObjVO.getSeat_people()) {
							if (Integer.parseInt(req.getParameter("seat_use")) == seatObjVO.getSeat_use())
								errorMsgs.add("您剛剛的操作對於桌位物件" + seat_obj_no + "既無更改人數、物件用處，也無更新圖片");
						}
						seatObjSve.updateSeatObj(seatObjVO.getSeat_obj_no(), seatObjVO.getSeat_obj(),
								seatObjVO.getSeat_obj_sts(),
								new Integer(Integer.parseInt(req.getParameter("seat_people"))),
								new Integer(Integer.parseInt(req.getParameter("seat_use"))));
					} else if (filename != null && part.getContentType() != null) {
						if (mimeType == null ? false : !mimeType.startsWith("image/")) {
							errorMsgs.add("選擇的不是圖片");
						}
						if (seat_people == null) {
							errorMsgs.add("桌位人數未設定");
						} else
							seatObjSve.updateSeatObj(seatObjVO.getSeat_obj_no(), pic, new Integer(0),
									new Integer(Integer.parseInt(req.getParameter("seat_people"))),
									new Integer(Integer.parseInt(req.getParameter("seat_use"))));
					}
					req.setAttribute("seatObjVO", seatObjVO);
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/back-end/seat_obj/updateSeatObj.jsp");
						failureView.forward(req, res);
						is.close();
						return;
					}
					is.close();
				}
			} catch (Exception e) {
				errorMsgs.add("修改位物件失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/seat_obj/updateSeatObj.jsp");
				failureView.forward(req, res);
			} finally {
				is.close();
			}

			res.sendRedirect(req.getContextPath() + "/back-end/seat_obj/setSeatObj.jsp");
			req.removeAttribute("errorMsgs");
			return;
		}
		/**************************** 桌位物件檔案上傳 ****************************/
		if ("upload_seat_obj".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			Collection<Part> parts;
			InputStream is = null;

			parts = req.getParts(); // Servlet3.0新增了Part介面，讓我們方便的進行檔案上傳處理
			try {
				SeatObjService seatObjSve = new SeatObjService();
				String[] seat_people = req.getParameterValues("seat_people");
				String[] seat_use = req.getParameterValues("seat_use");
				int i = 0, j = 0;
				for (Part part : parts) {
					is = part.getInputStream();
					byte[] pic = new byte[is.available()];
					is.read(pic);
					String filename = getFileNameFromPart(part);
					String mimeType = getServletContext().getMimeType(filename);
					if (filename == null && part.getContentType() != null) {
						errorMsgs.add("圖片未選擇");
					} else if (filename != null && part.getContentType() != null) {
						if (mimeType == null ? false : !mimeType.startsWith("image/")) {
							errorMsgs.add("選擇的不是圖片");
						}
						if (seat_people == null) {
							errorMsgs.add("桌位人數未設定");
						}
						if (!errorMsgs.isEmpty()) {
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back-end/seat_obj/addSeatObj.jsp");
							failureView.forward(req, res);
							is.close();
							return;
						}
						seatObjSve.addSeatObj(pic, new Integer(0), Integer.parseInt(seat_people[i++]),
								Integer.parseInt(seat_use[j]));
					}
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("上傳桌位物件失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/seat_obj/updateSeatObj.jsp");
				failureView.forward(req, res);
				is.close();
				return;
			}
			res.sendRedirect(req.getContextPath() + "/back-end/seat_obj/setSeatObj.jsp");
			req.removeAttribute("errorMsgs");
			return;
		}

		/**************************** 圖片顯示 ****************************/
		String seat_obj_no = req.getParameter("seat_obj_no");
		if (!(seat_obj_no.equals("") || seat_obj_no == null || req.getQueryString() == null)) {
			SeatObjShow seatObjShow = new SeatObjShow(req, res, seat_obj_no);
			seatObjShow.run();
			return;
		}
	}

	// 取出上傳的檔案名稱 (因為API未提供method,所以必須自行撰寫)
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
//		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
//		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
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
