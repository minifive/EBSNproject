package demo.biz.impl;

import java.util.LinkedList;
import java.util.List;

import demo.biz.GUmapSrv;
import demo.dao.GumapDAO;
import demo.dao.UserDAO;
import demo.vo.Group;
import demo.vo.Gumap;
import demo.vo.User;

public class GUmapSrvImpl implements GUmapSrv{
	private GumapDAO gumapDAO;
	private UserDAO userDAO;
	public GumapDAO getGumapDAO() {
		return gumapDAO;
	}

	public void setGumapDAO(GumapDAO gumapDAO) {
		this.gumapDAO = gumapDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void addusertogroup(Gumap gumap) {
		// TODO Auto-generated method stub
		gumapDAO.save(gumap);
	}

	@Override
	public List<User> alluserforgroup(Group group) {
		// TODO Auto-generated method stub
		List<Gumap> temp=new LinkedList<Gumap>();
		List<User> users=new LinkedList<User>();
		temp=gumapDAO.findByGroupid(group.getGroupid());
		System.out.println(temp.size()+" users have been found!");
		for(int i=0;i<temp.size();i++)
		{	User user=new User();
			user=(User)userDAO.findById(temp.get(i).getUserid());
			users.add(user);
		}
		return users;
	}

	@Override
	public void doaddgumap(Gumap gumap) {
		// TODO Auto-generated method stub
		gumapDAO.save(gumap);
	}

	@Override
	public int deletefromgroup(String userid,String groupid) {
		// TODO Auto-generated method stub
		List<Gumap> gumap=new LinkedList<Gumap>();
		gumap=gumapDAO.finduserfromgroup(userid,groupid);
		if(gumap.size()!=0)
		{
			for(int i=0;i<gumap.size();i++)
				gumapDAO.delete(gumap.get(i));
			return gumap.size();
		}
		return 0;
	}

	@Override
	public List<Gumap> allgroupsforuser(String userid) {
		// TODO Auto-generated method stub
		List<Gumap> temp=new LinkedList<Gumap>();
		temp=gumapDAO.findByUserid(userid);
		return temp;
	}

	@Override
	public List<Gumap> userfroumgroup(String userid, String groupid) {
		// TODO Auto-generated method stub
		List<Gumap> gumap=new LinkedList<Gumap>();
		gumap=gumapDAO.finduserfromgroup(userid,groupid);
		return gumap;
	}

	@Override
	public List<Gumap> findusersfromgroup(String groupid) {
		// TODO Auto-generated method stub
		List<Gumap> temp=new LinkedList<Gumap>();
		temp=gumapDAO.findByGroupid(groupid);
		return temp;
	}

	@Override
	public void dodelete(Gumap gumap) {
		// TODO Auto-generated method stub
		gumapDAO.delete(gumap);
	
	}

	@Override
	public List<Gumap> findall() {
		// TODO Auto-generated method stub
		List<Gumap>temp=new LinkedList<Gumap>();
		List<Gumap>retemp=new LinkedList<Gumap>();
		temp=gumapDAO.findAll();
		for(int i=0;i<temp.size();i++)
		{
			if(temp.get(i).getIsbuilder().equals("yes"))
			{
				retemp.add(temp.get(i));
			}
		}
		return retemp;
	}

}