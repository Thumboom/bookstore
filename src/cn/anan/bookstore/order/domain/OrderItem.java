package cn.anan.bookstore.order.domain;

import java.math.BigDecimal;

import cn.anan.bookstore.book.domain.Book;

public class OrderItem {

	private String iid;
	private int count;
	private Order order;
	private Book book;
	private double subTotal;
	
	
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		
		
		BigDecimal d1 = new BigDecimal("" + book.getPrice());
		BigDecimal d2 = new BigDecimal("" + count);
		
		return d1.multiply(d2).doubleValue();
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
}
