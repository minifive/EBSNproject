package demo.biz.impl;

import java.util.LinkedList;
import java.util.List;

import demo.biz.EUmapSrv;
import demo.dao.EumapDAO;
import demo.vo.Eumap;
import demo.vo.Gumap;



public class EUmapSrvImpl implements EUmapSrv{
	private EumapDAO eumapDAO;

	
	public EumapDAO getEumapDAO() {
		return eumapDAO;
	}

	public void setEumapDAO(EumapDAO eumapDAO) {
		this.eumapDAO = eumapDAO;
	}

	@Override
	public List<Eumap> finduserevents(String userid) {
		// TODO Auto-generated method stub
		List<Eumap> temp=new LinkedList<Eumap>();
		temp=eumapDAO.findByUserid(userid);
		
		return temp;
	}

	@Override
	public void addeumap(Eumap eumap) {
		eumapDAO.save(eumap);
	}

	@Override
	public List<Eumap> findbyE_U_id(String eventid, String userid) {
		// TODO Auto-generated method stub
		List<Eumap> temp=new LinkedList<Eumap>();
		temp=eumapDAO.findByUseridAndEventid(eventid, userid);
		return temp;
	}

	@Override
	public void deleumap(Eumap eumap) {
		// TODO Auto-generated method stub
		eumapDAO.delete(eumap);
	}

	@Override
	public List<Eumap> findeventusers(String eventid) {
		// TODO Auto-generated method stub
		List<Eumap> temp=new LinkedList<Eumap>();
		temp=eumapDAO.findByEventid(eventid);
		return temp;
	}

	@Override
	public void dodelete(String eventid) {
		// TODO Auto-generated method stub
		List<Eumap> temp=new LinkedList<Eumap>();
		temp=eumapDAO.findByEventid(eventid);
		for(int i=0;i<temp.size();i++)
		{	Eumap eumap=new Eumap();
			eumap.setMapid(temp.get(i).getMapid());
			eumapDAO.delete(eumap);
		}
	}

	@Override
	public List<Eumap> findall() {//通过eumap查找所有的活动
		// TODO Auto-generated method stub
		List<Eumap>temp =new LinkedList<Eumap>();
		List<Eumap>retemp =new LinkedList<Eumap>();
		temp=eumapDAO.findAll();
		for(int i=0;i<temp.size();i++)
		{
			if(temp.get(i).getIsbuilder().equals("yes"))
			{
				retemp.add(temp.get(i));
			}
		}
		return retemp;
	}

	@Override
	public List findbuilder(String eventid) {//查找一个活动的常见者
		// TODO Auto-generated method stub
		List<Eumap>temp =new LinkedList<Eumap>();
		temp=eumapDAO.findbuilder(eventid);
		return temp;
	}
}