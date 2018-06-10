package cn.anan.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.anan.bookstore.order.dao.OrderDao;
import cn.anan.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	
	private OrderDao dao = new  OrderDao();

	public List<Order> myOrders(String uid) {
		return dao.findByUid(uid);
	}

	public void add(Order order) {
		
		
		
		try {
			JdbcUtils.beginTransaction();
			dao.addOrder(order);
			dao.addOrderItemList(order.getOrderItemList());
			
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			throw new RuntimeException(e);
		}
		
	}

	public Order load(String oid) {
		return dao.load(oid);
	}

	public void confirm(String oid) throws OrderException {
	
		int state = dao.getStateByOid(oid);
		if(state != 3) throw new OrderException("订单确认失败！");
		
		dao.updateState(oid, 4);
	}

	public void pay(String oid) {
		
		int state = dao.getStateByOid(oid);
		if(state == 1) {
			dao.updateState(oid, 2);
		}
		
	}
	
}
