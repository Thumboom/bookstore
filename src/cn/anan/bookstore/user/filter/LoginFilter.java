package cn.anan.bookstore.user.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import cn.anan.bookstore.user.domain.User;

@WebFilter(
		urlPatterns = { 
				"/jsps/cart/*", 
				"/jsps/order/*"
		}, 
		servletNames = { 
				"OrderServlet", 
				"CartServlet"
		})
public class LoginFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("session_user");
		
//		System.out.println("hey I'm here");
		
		if( user != null) {
			chain.doFilter(request, response);
		} else {
			req.setAttribute("msg", "您还没有登录!");
			req.getRequestDispatcher("/jsps/user/login.jsp")
				.forward(req, response);
		}
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
//		System.out.println("init....");
	}

}
