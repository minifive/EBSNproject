package demo.biz;

import java.util.List;

import demo.vo.Eumap;

public interface EUmapSrv{
	
	List<Eumap> finduserevents(String userid);//通过userid在eumap表中获取一个用户参与到的所有活动
	
	void addeumap(Eumap eumap);//保存一个item到eumap表中
	void deleumap(Eumap eumap);//从eumap中删除
	
	List<Eumap> findbyE_U_id(String eventid,String userid);//addeumapAction、geteventimfaction中验证用户是否已经报过名
	
	List<Eumap> findeventusers(String eventid);//查询一个eventid下的所有user
	
	void dodelete(String eventid);
	
	List<Eumap> findbuilder(String eventid);
	
	List<Eumap> findall();//用于管理员获取所有活动
	
	
}