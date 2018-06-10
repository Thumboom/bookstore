package cn.anan.bookstore.user.web.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.anan.bookstore.cart.domain.Cart;
import cn.anan.bookstore.user.domain.User;
import cn.anan.bookstore.user.service.UserException;
import cn.anan.bookstore.user.service.UserService;
import cn.anan.demo.Mail;
import cn.anan.demo.MailUtils;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private UserService service = new UserService();
	
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		
		Map<String, String > errors = new HashMap<String, String>();
		
		String username = form.getUsername();
		if( username == null || username.trim().isEmpty()) {
			errors.put("username", "用户名不能为空");
		} else if( username.length() < 3 || username.length() > 10) {
			errors.put("username", "用户名长度必须为3~10!");
		} 
		
		String password = form.getPassword();
		
		if( password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能为空");
		} else if( password.length() < 3 || password.length() > 10) {
			errors.put("password", "密码长度必须为3~10!");
		} 
		
		
		String email = form.getEmail();
		
		if( email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空");
		} else if(! email.matches("\\w+@\\w+.\\w+")) {
			errors.put("email", "Email格式错误!");
		} 
		
		
		
		if(errors.size() > 0) {
			
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			
			return "f:/jsps/user/regist.jsp";
		}
		
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader()
				.getResourceAsStream("email_template.properties"));
		
		String host = props.getProperty("host");
		String uname = props.getProperty("uname");
		String pwd = props.getProperty("pwd");
		String from = props.getProperty("from");
		String to = form.getEmail();
		String subject = props.getProperty("subject");
		String content = props.getProperty("content");
		content = MessageFormat.format(content, form.getCode());
		
		Session session = MailUtils.createSession(host, uname, pwd);
		Mail mail = new Mail(from, to, subject, content);
		
		try {
			service.regist(form);
		} catch (UserException e1) {
			
			request.setAttribute("msg", e1.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
		
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("msg", "恭喜，注册成功！请马上登陆");
		
		return "f:/jsps/msg.jsp";
	}

	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String code = request.getParameter("code");
		
		try {
			service.active(code);
			request.setAttribute("msg", "激活成功，请马上登陆！");
			
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
		}
		System.out.println("激活-------");
		
		return"f:/jsps/msg.jsp";
	}
	
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		Map<String,String> errors = new HashMap<String, String>();
		
		
		String username = form.getUsername();
		if( username == null || username.trim().isEmpty()) {
			errors.put("username", "用户名不能为空");
		} else if( username.length() < 3 || username.length() > 10) {
			errors.put("username", "用户名长度必须为3~10!");
		} 
		
		String password = form.getPassword();
		
		if( password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能为空");
		} else if( password.length() < 3 || password.length() > 10) {
			errors.put("password", "密码长度必须为3~10!");
		} 
		
		
		if(errors.size() > 0) {
			
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
		
		User user=null;
		
		try {
			user = service.login(form);
			request.getSession().setAttribute("session_user", user);
			
			request.getSession().setAttribute("cart", new Cart());
			
			return "f:/index.jsp";
			
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			
			return "f:/jsps/user/login.jsp";
		}
		
		
		
	}
	
	public String quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/index.jsp";
	}
	
}
