package demo.biz;

import java.util.List;

import demo.vo.Group;
import demo.vo.Gumap;
import demo.vo.User;

public interface GUmapSrv{
	
	List<Gumap> allgroupsforuser(String userid);
	
	void addusertogroup(Gumap gumap);//添加群组成员
	
	List<User> alluserforgroup(Group group);//getgroupimf中
	
	void doaddgumap(Gumap gumap);//此方法用来向一个group里添加一个成员
	
	int deletefromgroup(String userid,String groupid);//此方法用来从group中删除一个成员
	
	List<Gumap> userfroumgroup(String userid,String groupid);
	
	List<Gumap> findusersfromgroup(String groupid);//功能同allusrforgroup 返回不同
	
	void dodelete(Gumap gumap);//管理员删除群组成员
	
	List<Gumap> findall();//管理员获取所有群组
}