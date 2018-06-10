package cn.anan.bookstore.cart.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.anan.bookstore.book.domain.Book;
import cn.anan.bookstore.book.service.BookService;
import cn.anan.bookstore.cart.domain.Cart;
import cn.anan.bookstore.cart.domain.CartItem;
import cn.anan.bookstore.cart.service.CartService;
import cn.itcast.servlet.BaseServlet;

@WebServlet(name="CartServlet", urlPatterns="/CartServlet")
public class CartServlet extends BaseServlet {
	
	private CartService service = new CartService();
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		Book book  = new BookService().load(bid);
		
		int count = Integer.parseInt(request.getParameter("count"));
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		cart.add(cartItem);
		
		return "f:/jsps/cart/list.jsp";

	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		cart.delete(bid);
		request.setAttribute("cart", cart);
		return "f:/jsps/cart/list.jsp";
		
	}
	
	
	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}

}
