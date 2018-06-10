package cn.anan.bookstore.category.web.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.anan.bookstore.category.domain.Category;
import cn.anan.bookstore.category.service.CategoryException;
import cn.anan.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class CategoryAdminServlet
 */
@WebServlet("/admin/CategoryAdminServlet")
public class CategoryAdminServlet extends BaseServlet {
	
	private CategoryService categoryService = new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("categoryList", categoryService.findAll());
		return "f:/adminjsps/admin/category/list.jsp";
		
	} 
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());
		categoryService.add(category);
		
		return findAll(request, response);
	}
	
	public String del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			categoryService.del(request.getParameter("cid"));
			return findAll(request, response);
		} catch (CategoryException e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/jsps/msg.jsp";
		}
		
	}
	
	public String preEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cid = request.getParameter("cid");
		
		Category category = categoryService.load(cid);
		request.setAttribute("category", category);
		
		return "f:/adminjsps/admin/category/mod.jsp";
	}
	
	
	public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		
		categoryService.edit(category);
		
		return findAll(request, response);
	}
	
	
	
}
