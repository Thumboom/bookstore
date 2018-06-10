package cn.anan.bookstore.category.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.anan.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	
	private CategoryService service = new CategoryService();
	
	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("categoryList", service.findAll());
		return "f:/jsps/left.jsp";
	}



}
