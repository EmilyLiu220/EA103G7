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
		fa0001.add(req.getContextPath() + "/back-end/emp/update_emp_info.jsp");
		fa0001.add(req.getContextPath() + "/back-end/emp/update_emp_sts.jsp");
		
		List<String> fa0002 = new ArrayList<>();
		fa0002.add(req.getContextPath() + "/back-end/mem/mem.do");
		fa0002.add(req.getContextPath() + "/back-end/mem/select_page_mem.jsp");
		fa0002.add(req.getContextPath() + "/back-end/mem/listAllMem.jsp");
		fa0002.add(req.getContextPath() + "/back-end/mem/listAllMem_sts.jsp");
		fa0002.add(req.getContextPath() + "/back-end/mem/listOneMem.jsp");
		fa0002.add(req.getContextPath() + "/back-end/mem/update_mem_sts.jsp");
		
		
		if (funs.contains("FA0001") && fa0001.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0002") && fa0002.contains(path)) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/back-end/backindex.jsp");
		}	
		
	}
}