package cn.anan.bookstore.book.service;

import java.util.List;

import cn.anan.bookstore.book.dao.BookDao;
import cn.anan.bookstore.book.domain.Book;

public class BookService {

	private BookDao dao = new BookDao();

	public List<Book> findAll() {
		
		return dao.findAll();
	}

	public List<Book> findByCategory(String cid) {
		return dao.findByCategory(cid);
	}

	public Book load(String bid) {
		
		return dao.findByBid(bid);
	}

	public void add(Book book) {
		dao.add(book);
	}
	
	public void delete( String bid) {
		dao.delete(bid);
	}

	public void edit(Book book) {
		dao.edit(book);
	
	}
	
}
