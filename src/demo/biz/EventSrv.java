package demo.biz;

import java.util.List;

import demo.vo.Event;

public interface EventSrv {
	
	void createvent(Event event);//次方法用来save创建的活动
	
	List<Event> getallevents();//获取主页显示的所有的活动信息
	
	Event doValidate(Event event);
	
	String addextingnum(String eventid);//报名时 更新evnet.extingnum属性
	Event delextingnum(String eventid,int size);
	
	List<Event> findgroupevent(String groupid);//通过groupid来查找活动
	
	List<Event> search(String key);
	
	Event doupdate(Event event);
	
	void dodelete(Event event);
} 
