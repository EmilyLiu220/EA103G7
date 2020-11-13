package frontfilters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FrontLoginFilter implements Filter {

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
		// 【從 session 判斷此user是否登入過】
		Object account_m = session.getAttribute("account_m");
		Object account_e = session.getAttribute("account_e");
		if (account_m == null && account_e == null) {
			session.setAttribute("location_m", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/front-end/mem/login_mem.jsp");
			return;
		} else {
			chain.doFilter(request, response);
		}
	}
}