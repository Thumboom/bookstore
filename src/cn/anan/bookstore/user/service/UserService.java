package cn.anan.bookstore.user.service;

import cn.anan.bookstore.user.dao.UserDao;
import cn.anan.bookstore.user.domain.User;

public class UserService {
	private UserDao dao = new UserDao();
	
	public void regist(User form) throws UserException {
		
		User user = dao.findByUsername(form.getUsername());
		if(user != null) throw new UserException("用户名已被注册");
		
		user = dao.findByEmail(form.getEmail());
		
		if( user != null) throw new UserException("email已经被注册");
		
		dao.add(form);
			
	}
	
	
	public void active(String code) throws UserException {
		
		User user = dao.findByCode(code);
		
		if(user == null ) throw new UserException("激活码无效");
		
		if(user.isState()) throw new UserException("您已经激活过了，请勿重复激活！");
		
		dao.updateState(user.getUid(), true);
	}
	
	public User login(User form) throws UserException {
		
		String username = form.getUsername();
		
		User user = dao.findByUsername(username);
		
		if( user == null) {
			throw new UserException("用户不存在");
		}
		
		String password = form.getPassword();
		
		if(!user.getPassword().equals(password)) {
			throw new UserException("密码错误！");
		}
		
		if(!user.isState()) {
			throw new UserException("您的账号未激活！");
		}
		
		return user;
	}
	
}
