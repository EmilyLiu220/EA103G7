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
		
		// 各權限確定路徑後加上
		List<String> fa0003 = new ArrayList<>();
		List<String> fa0004 = new ArrayList<>();
		List<String> fa0005 = new ArrayList<>();
		List<String> fa0006 = new ArrayList<>();
		List<String> fa0007 = new ArrayList<>();
		List<String> fa0008 = new ArrayList<>();
		List<String> fa0009 = new ArrayList<>();
		List<String> fa0010 = new ArrayList<>();
		List<String> fa0011 = new ArrayList<>();
		List<String> fa0012 = new ArrayList<>();
		List<String> fa0013 = new ArrayList<>();
		List<String> fa0014 = new ArrayList<>();
		List<String> fa0015 = new ArrayList<>();
		List<String> fa0016 = new ArrayList<>();
		List<String> fa0017 = new ArrayList<>();
		List<String> fa0018 = new ArrayList<>();
		List<String> fa0019 = new ArrayList<>();
		List<String> fa0020 = new ArrayList<>();
		List<String> fa0021 = new ArrayList<>();
		List<String> fa0022 = new ArrayList<>();
		List<String> fa0023 = new ArrayList<>();
		List<String> fa0024 = new ArrayList<>();
		List<String> fa0025 = new ArrayList<>();
		
		
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
		} else if (funs.contains("FA0022") && fa0022.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0023") && fa0023.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0024") && fa0024.contains(path)) {
			chain.doFilter(request, response);
		} else if (funs.contains("FA0025") && fa0025.contains(path)) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/back-end/backindex.jsp");
		}	
		
	}
}