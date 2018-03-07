package demo.biz.impl;

import java.util.List;

import demo.biz.UserSrv;
import demo.dao.UserDAO;
import demo.vo.User;

public class UserSrvImpl implements UserSrv{
	
	private UserDAO userDAO = null;
	@Override
	public List<User> getAllitem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User dologin(User user) {
		// TODO Auto-generated method stub
		User u=userDAO.findById(user.getUserid());
		if (u != null && u.getPassword().equals(user.getPassword())){
			return u;
		}
		return null;
	}
	
	@Override
	public void doregist(User user) {//此函数用来注册
		userDAO.save(user);
		System.out.println(user.getPassword()+"注册成功");
	}
	public User doValidate(User user) {//此函数用来验证用户iD是否存在
		
		User u= (User)userDAO.findById(user.getUserid());	
		return u;
	}
	@Override
	public User doupdate(User user) {
		// TODO 更新用户信息
		System.out.println("update user imformation");
		userDAO.delete(user);
		userDAO.save(user);
		return user;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	
	
	
}