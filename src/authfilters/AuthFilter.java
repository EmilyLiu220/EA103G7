package authfilters;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.emp_auth.model.Emp_authVO;

public class AuthFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 【取得 session】
		HttpSession session = req.getSession();
		// 取得使用者請求的路徑
		String path = req.getRequestURI();
		// 取得使用者所有的權限編號
		List<Emp_authVO> emp_authVO = (List<Emp_authVO>) session.getAttribute("emp_authVO2");
		List<String> funs = new ArrayList<>();
		for (int i = 0; i < emp_authVO.size(); i++) {
			funs.add(emp_authVO.get(i).getFun_no());
		}
		// 各項權限所包含的全部路徑
		List<String> fa0001 = new ArrayList<>();
		fa0001.add(req.getContextPath() + "/back-end/emp/emp.do");
		fa0001.add(req.getContextPath() + "/back-end/emp/select_page.jsp");
		fa0001.add(req.getContextPath() + "/back-end/emp/addEmp.jsp");
		fa0001.add(req.getContextPath() + "/back-end/emp/listAllEmp.jsp");
		fa0001.add(req.getContextPath() + "/back-end/emp/listOneEmp.jsp");
		fa0001.add(req.getContextPath() + "/back-end/emp/update_emp_auth.jsp");
		fa0001.add(req.getContextPath() + "/back-end/emp/update_emp_sts.jsp");
		
		List<String> fa0002 = new ArrayList<>();
		fa0002.add(req.getContextPath() + "/back-end/mem/mem.do");
		fa0002.add(req.getContextPath() + "/back-end/mem/select_page_mem.jsp");
		fa0002.add(req.getContextPath() + "/back-end/mem/listAllMem.jsp");
		fa0002.add(req.getContextPath() + "/back-end/mem/listAllMem_sts.jsp");
		fa0002.add(req.getContextPath() + "/back-end/mem/listOneMem.jsp");
		fa0002.add(req.getContextPath() + "/back-end/mem/update_mem_sts.jsp");
		
		
		List<String> fa0003 = new ArrayList<>();
		fa0003.add(req.getContextPath() + "/back-end/message_record/msg.do");
		fa0003.add(req.getContextPath() + "/back-end/message_record/backEndChatRoom.jsp");
		
		List<String> fa0004 = new ArrayList<>();
		fa0004.add(req.getContextPath() + "/back-end/front_inform/fi.do");
		fa0004.add(req.getContextPath() + "/back-end/front_inform/select_fi.jsp");
		fa0004.add(req.getContextPath() + "/back-end/front_inform/listByComplex_fi.jsp");
		fa0004.add(req.getContextPath() + "/back-end/front_inform/empCheckAllInform.jsp");
		
		List<String> fa0005 = new ArrayList<>();
		List<String> fa0006 = new ArrayList<>();
		List<String> fa0007 = new ArrayList<>();
		List<String> fa0008 = new ArrayList<>();
		
		List<String> fa0009 = new ArrayList<>();
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/mealOrder.do");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/1.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/2.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/3.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/4.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/5.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/6.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/7.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/8.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/9.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/10.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/11.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/12.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/13.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/14.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/icon/15.png");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/asignOrder.jsp");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/listAllOrder2.jsp");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/listOneOrder.jsp");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/listQueryOrder2.jsp");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/mealOrderManagement.jsp");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/orderChart.jsp");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/OrderDone.jsp");
		fa0009.add(req.getContextPath() + "/back-end/mealOrder/prepareOrder.jsp");
		
		List<String> fa0010 = new ArrayList<>();
		fa0010.add(req.getContextPath() + "/back-end/res_order/res_order.do");
		fa0010.add(req.getContextPath() + "/back-end/res_order/orderSeat.jsp");
		fa0010.add(req.getContextPath() + "/back-end/res_order/resOrderManage.jsp");
		
		List<String> fa0011 = new ArrayList<>();
		fa0011.add(req.getContextPath() + "/back-end/bonus/forwarded");
		fa0011.add(req.getContextPath() + "/back-end/bonus_order/forwarded");
		fa0011.add(req.getContextPath() + "/back-end/bonus_order_detail/forwarded");
		fa0011.add(req.getContextPath() + "/back-end/bonus/addBonus.jsp");
		fa0011.add(req.getContextPath() + "/back-end/bonus/listOneBonus.jsp");
		fa0011.add(req.getContextPath() + "/back-end/bonus/listAllBonus.jsp");
		fa0011.add(req.getContextPath() + "/back-end/bonus/select_page.jsp");
		fa0011.add(req.getContextPath() + "/back-end/bonus/update_bonus_input.jsp");
		fa0011.add(req.getContextPath() + "/back-end/bonus_order/listAllBonus_Order.jsp");
		fa0011.add(req.getContextPath() + "/back-end/bonus_order/listOneBonus_Order.jsp");
		fa0011.add(req.getContextPath() + "/back-end/bonus_order/update_bonus_order_input.jsp");
		fa0011.add(req.getContextPath() + "/back-end/bonus_order_detail/listOneBonus_Order_Detail.jsp");

		List<String> fa0012 = new ArrayList<>();
		fa0010.add(req.getContextPath() + "/back-end/seat/seat.do");
		fa0010.add(req.getContextPath() + "/back-end/seat/editSeat_include.jsp");
		fa0010.add(req.getContextPath() + "/back-end/seat/editSeat.jsp");
		fa0010.add(req.getContextPath() + "/back-end/seat_obj/seat_obj.do");
		fa0010.add(req.getContextPath() + "/back-end/seat_obj/addSeatObj.jsp");
		fa0010.add(req.getContextPath() + "/back-end/seat_obj/setSeatObj.jsp");
		fa0010.add(req.getContextPath() + "/back-end/seat_obj/updateSeatObj.jsp");
		
		List<String> fa0013 = new ArrayList<>();
		List<String> fa0014 = new ArrayList<>();
		List<String> fa0015 = new ArrayList<>();
		
		List<String> fa0016 = new ArrayList<>();
		fa0016.add(req.getContextPath() + "/back-end/meal/meal.do");
		fa0016.add(req.getContextPath() + "/back-end/meal/insertMeal2.jsp");
		fa0016.add(req.getContextPath() + "/back-end/meal/listAllMeal2.jsp");
		fa0016.add(req.getContextPath() + "/back-end/meal/menuManagement.jsp");
		fa0016.add(req.getContextPath() + "/back-end/meal/updateMeal2.jsp");
		fa0016.add(req.getContextPath() + "/back-end/meal_set/meal_set.do");
		fa0016.add(req.getContextPath() + "/back-end/meal_set/insertMealSet2.jsp");
		fa0016.add(req.getContextPath() + "/back-end/meal_set/listAllMealSet2.jsp");
		fa0016.add(req.getContextPath() + "/back-end/meal_set/updateMealSet2.jsp");
		
		List<String> fa0017 = new ArrayList<>();
		fa0017.add(req.getContextPath() + "/back-end/member_review/forwarded");
		fa0017.add(req.getContextPath() + "/back-end/report_appraise/forwarded");
		fa0017.add(req.getContextPath() + "/back-end/member_review/addMember_Review.jsp");
		fa0017.add(req.getContextPath() + "/back-end/member_review/listAllMember_Review.jsp");
		fa0017.add(req.getContextPath() + "/back-end/member_review/listOneMember_Review.jsp");
		fa0017.add(req.getContextPath() + "/back-end/member_review/select_page.jsp");
		fa0017.add(req.getContextPath() + "/back-end/report_appraise/listAllReport_Appraise.jsp");

		
		List<String> fa0018 = new ArrayList<>();
		fa0018.add(req.getContextPath() + "/back-end/inform_set/is.do");
		fa0018.add(req.getContextPath() + "/back-end/inform_set/add_is.jsp");
		fa0018.add(req.getContextPath() + "/back-end/inform_set/listAll_is.jsp");
		fa0018.add(req.getContextPath() + "/back-end/inform_set/listByComplex_is.jsp");
		fa0018.add(req.getContextPath() + "/back-end/inform_set/listOne_is.jsp");
		fa0018.add(req.getContextPath() + "/back-end/inform_set/select_is.jsp");
		fa0018.add(req.getContextPath() + "/back-end/inform_set/update_is.jsp");
		
		List<String> fa0019 = new ArrayList<>();
		List<String> fa0020 = new ArrayList<>();
		List<String> fa0021 = new ArrayList<>();
		
		// 比對員工權限和請求的路徑 (之後新增全部的權限)
		if (funs.contains("FA0001") && fa0001.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0002") && fa0002.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0003") && fa0003.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0004") && fa0004.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0005") && fa0005.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0006") && fa0006.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0007") && fa0007.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0008") && fa0008.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0009") && fa0009.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0010") && fa0010.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0011") && fa0011.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0012") && fa0012.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0013") && fa0013.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0014") && fa0014.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0015") && fa0015.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0016") && fa0016.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0017") && fa0017.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0018") && fa0018.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0019") && fa0019.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0020") && fa0020.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0021") && fa0021.contains(path)) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/back-end/backindex.jsp");
		}	
		
	}
}