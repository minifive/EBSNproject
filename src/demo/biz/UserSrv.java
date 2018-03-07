package demo.biz;

import java.util.List;

import demo.vo.User;


public interface UserSrv {
	
	List<User> getAllitem();
	
	User dologin(User user);
	
	void doregist(User user);
	
	User doValidate(User user);//此方法用来检测数据库中是否存在该id
		
	User doupdate(User user);

} 
