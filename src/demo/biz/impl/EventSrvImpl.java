package demo.biz.impl;

import java.util.LinkedList;
import java.util.List;

import demo.biz.EventSrv;
import demo.dao.EventDAO;
import demo.vo.Event;


public class EventSrvImpl implements EventSrv{

	private EventDAO eventDAO;

	public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	@Override
	public void createvent(Event event) {
		// TODO Auto-generated method stub
		eventDAO.save(event);
	}

	@Override
	public List<Event> getallevents() {
		//获取所有的groupid为空的event
		List<Event> returnList=new LinkedList<Event>();
		List<Event>temp=eventDAO.findAll();
		for(int i=0;i<temp.size();i++)
		{
			if((temp.get(i).getGroupid()==null)&&(temp.get(i).getState().equals("audited")))
				returnList.add(temp.get(i));
		}
		return returnList;
	}

	@Override
	public Event doValidate(Event event) {
		// TODO Auto-generated method stub
		Event e=eventDAO.findById(event.getEventId());
		return e;
	}

	@Override
	public String addextingnum(String eventid) {
		// TODO Auto-generated method stub
		Event event=new Event();
		event.setEventId(eventid);
		event=doValidate(event);
		if(event==null)
			return "false";
		int maxnum;
		if(event.getMaxnum().equals("不限"))
		{
			maxnum=10000;
		}
		else
		{
			maxnum=Integer.parseInt(event.getMaxnum());
		}
		int extnum=Integer.parseInt(event.getExstnum());
		if(extnum>=maxnum)
			return "false";
		extnum++;
		event.setExstnum(""+extnum);
		eventDAO.delete(event);
		eventDAO.save(event);
		return "add success";
	}

	@Override
	public Event delextingnum(String eventid, int size) {
		// TODO Auto-generated method stub
		Event e=eventDAO.findById(eventid);
		int extnum=Integer.parseInt(e.getExstnum());
		if(extnum<size)
			return null;
		extnum--;
		e.setExstnum(""+extnum);
		eventDAO.delete(e);
		eventDAO.save(e);
		return e;
	}

	@Override
	public List<Event> findgroupevent(String groupid) {
		// TODO Auto-generated method stub
		List<Event> events=new LinkedList<Event>();
		events=eventDAO.findByGroupid(groupid);
		return events;
	}

	@Override
	public List<Event> search(String key) {
		List<Event> temp=new LinkedList<Event>();
		temp=eventDAO.mysearch(key);
		
		return temp;
	}

	@Override
	public Event doupdate(Event event) {
		// TODO Auto-generated method stub
		eventDAO.delete(event);
		eventDAO.save(event);
		return event;
	}

	@Override
	public void dodelete(Event event) {
		eventDAO.delete(event);
	}
}