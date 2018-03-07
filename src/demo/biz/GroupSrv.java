package demo.biz;

import java.util.List;

import demo.vo.Group;

public interface GroupSrv{
	
	Group dovalidate(Group group);//此函数用来检测该群组是否已存在
	
	List<Group> FindByGroupname(Group group);
	
	void dobuildgroup(Group group);//创建群组
	
	void dodelete(Group group);//删除群组
	
	String addextingnum(String groupid);
	String delextingnum(String groupid,int size);
	
	Group doupdate(Group group);
}