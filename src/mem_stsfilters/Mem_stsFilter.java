package mem_stsfilters;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.emp_auth.model.Emp_authVO;
import com.mem.model.MemVO;

public class Mem_stsFilter implements Filter {

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
		
		MemVO memVO = (MemVO) session.getAttribute("memVO2");
		
		String reser = req.getContextPath() + "";
		String meal = req.getContextPath() + "";
		String review = req.getContextPath() + "";
		String repo = req.getContextPath() + "";
		
		Integer mem_od_r = memVO.getMem_od_r();
		Integer mem_od_m = memVO.getMem_od_m();
		Integer mem_review = memVO.getMem_review();
		Integer mem_repo = memVO.getMem_repo();
		
		if (mem_od_r.equals(1) && reser.equals(path)) {
			chain.doFilter(request, response);
		} else if (mem_od_m.equals(1) && meal.equals(path)) {
			chain.doFilter(request, response);
		} else if (mem_review.equals(1) && review.equals(path)) {
			chain.doFilter(request, response);
		} else if (mem_repo.equals(1) && repo.equals(path)) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/front-end/front_home.jsp");
		}
		
	}
}
