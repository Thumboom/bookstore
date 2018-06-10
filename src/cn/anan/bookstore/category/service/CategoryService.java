package cn.anan.bookstore.category.service;

import java.util.List;

import cn.anan.bookstore.book.dao.BookDao;
import cn.anan.bookstore.category.dao.CategoryDao;
import cn.anan.bookstore.category.domain.Category;

public class CategoryService {

	private CategoryDao dao = new CategoryDao();
	private BookDao bookDao = new BookDao();
	

	public List<Category> findAll() {
		
		return dao.findAll();
	}

	public void add(Category category) {
		dao.add(category);
	}

	public void del(String cid) throws CategoryException {
		int count = bookDao.getCountByCid(cid);
		if(count > 0) throw new CategoryException("该分类下还有图书，您不能删除！");
		
		dao.del(cid);
	}

	public Category load(String cid) {
		
		return dao.load(cid);
	}

	public void edit(Category category) {
	
		dao.edit(category);
	}
	
	
}
