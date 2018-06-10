package cn.anan.bookstore.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.anan.bookstore.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	QueryRunner qr = new TxQueryRunner();

	public List<Category> findAll() {
		
		String sql = "select * from category";
		
		try {
			List<Category> categoryList = qr.query(sql, new BeanListHandler<Category>(Category.class));
			return categoryList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void add(Category category) {

		String sql = "insert into category values(?,?)";
		
		try {
			qr.update(sql,category.getCid(), category.getCname());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void del(String cid) {
		String sql = "delete from category where cid=?";
		
		try {
			qr.update(sql,cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Category load(String cid) {
		
		String sql = "select * from category where cid=?";
		
		try {
			return qr.query(sql, new BeanHandler<Category>(Category.class), cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void edit(Category category) {
		String sql = "update category set cname=? where cid=?";
		
		try {
			qr.update(sql, category.getCname(), category.getCid());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
