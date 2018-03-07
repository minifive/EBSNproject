package demo.action;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.EventSrv;
import demo.biz.GUmapSrv;
import demo.biz.GroupSrv;
import demo.biz.UserSrv;
import demo.vo.Event;
import demo.vo.Group;
import demo.vo.Myevent;
import demo.vo.MygroupImf;
import demo.vo.Myuser;
import demo.vo.User;

@SuppressWarnings("serial")
public class GetGroupimfAction extends ActionSupport {

	/**
	 * @return group.html 获取前段所需要的所有信息
	 */
	private String groupid;//in
	private GroupSrv groupSrv;
	private GUmapSrv gumapSrv;
	private EventSrv eventSrv;
	private UserSrv userSrv;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		groupid=request.getParameter("ID");
		String userid=(String) request.getSession().getAttribute("userid");
		JSONObject jo=new JSONObject();
		if(userid==null)
		{
			jo.put("returncode", "1");
			response.getWriter().print(jo.toString());
			response.flushBuffer();
			return SUCCESS;
		}
		
//---------get group basic information
		Group group=new Group();
		group.setGroupid(groupid);
		group=groupSrv.dovalidate(group);
		if(group.getBuilderid().equals(userid))//判断当前用户的权限
		{
			jo.put("authority", "1");//活动创建者
		}
		else
		{
			jo.put("authority", "0");
		}
		MygroupImf groupimf=new MygroupImf();
		groupimf.setGroupname(group.getGroupname());
		groupimf.setExtnum(group.getExistmemnum());
		groupimf.setMaxnum(group.getMemberceiling());;
//---------get all users for a group			
		List<User> users=new LinkedList<User>();
		users=gumapSrv.alluserforgroup(group);
		List<Myuser> myusers=new LinkedList<Myuser>();
		for(int i=0;i<users.size();i++)
		{
			Myuser myuser=new Myuser();
			myuser.setUserid(users.get(i).getUserid());
			myuser.setUsername(users.get(i).getUsername());
			myuser.setPhone(users.get(i).getPhone());
			myuser.setHeadport(users.get(i).getHeadport());
			if(users.get(i).getUserid().equals(group.getBuilderid()))
			{
				jo.put("builder", myuser);//创建者单独显示
			}
			else
				myusers.add(myuser);
		}
//------------get all events for a group
		List<Event> events=new LinkedList<Event>();
		events=eventSrv.findgroupevent(groupid);
		List<Myevent> myevents=new LinkedList<Myevent>();
		for(int i=0;i<events.size();i++)
		{
			Event e=events.get(i);
			Myevent myevent=new Myevent();
			myevent.setAddress(e.getAddress());
			myevent.setCoordinate(e.getCoordinate());
			myevent.setEndtime(e.getEndtime().toString());
			myevent.setEventId(e.getEventId());
			myevent.setEventName(e.getEventName());
			myevent.setExstnum(e.getExstnum());
			myevent.setIntroduce(e.getIntroduce());
			myevent.setMaxnum(e.getMaxnum());
			myevent.setPoster(e.getPoster());
			myevent.setStarttime(e.getStarttime().toString());
			myevents.add(myevent);
		}
//--------return--------
		jo.put("group", groupimf);
		jo.put("userList", myusers);
		jo.put("eventList", myevents);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
	public GUmapSrv getGumapSrv() {
		return gumapSrv;
	}
	public void setGumapSrv(GUmapSrv gumapSrv) {
		this.gumapSrv = gumapSrv;
	}
	public EventSrv getEventSrv() {
		return eventSrv;
	}
	public void setEventSrv(EventSrv eventSrv) {
		this.eventSrv = eventSrv;
	}
	public GroupSrv getGroupSrv() {
		return groupSrv;
	}
	public void setGroupSrv(GroupSrv groupSrv) {
		this.groupSrv = groupSrv;
	}
	public UserSrv getUserSrv() {
		return userSrv;
	}
	public void setUserSrv(UserSrv userSrv) {
		this.userSrv = userSrv;
	}
}