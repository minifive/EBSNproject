package demo.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.EUmapSrv;
import demo.biz.EventSrv;
import demo.biz.UserSrv;
import demo.vo.Eumap;
import demo.vo.Event;
import demo.vo.Myevent;
import demo.vo.Myuser;
import demo.vo.User;

@SuppressWarnings("serial")
public class EventusersAction extends ActionSupport {

	/**
	 * @return event-member.html 查询活动成员即活动信息
	 */
	private EUmapSrv eumapSrv;
	private EventSrv eventSrv;
	private UserSrv userSrv;
	private String eventid;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		eventid=request.getParameter("ID");
		//eventid="1";
		//String userid="09012432";
		System.out.println("EventusersAction  eventid:"+eventid);
		String userid=(String) request.getSession().getAttribute("userid");
		JSONObject jo=new JSONObject();
		Myevent myevent=new Myevent();
		if(userid==null)
		{
			jo.put("returncode", "1");
			response.getWriter().print(jo.toString());
			response.flushBuffer();
			return SUCCESS;
		}
		Event event=new Event();
		event.setEventId(eventid);
		event=eventSrv.doValidate(event);
		if(event==null)
		{
			System.out.println("查无此活动。 EventusersAction");
			jo.put("returncode", "0");
		}
		else{
			System.out.println(event.getEventName());
			myevent.setEventId(eventid);
			myevent.setEventName(event.getEventName());
			myevent.setExstnum(event.getExstnum());
			myevent.setMaxnum(event.getMaxnum());
			myevent.setStarttime(event.getStarttime().toString());
			myevent.setEndtime(event.getEndtime().toString());
			jo.put("event", myevent);
//---------------------查成员信息---------------------------	
			List<Myuser> users=new LinkedList<Myuser>();
			List<Eumap>temp=new LinkedList<Eumap>();
			temp=eumapSrv.findeventusers(eventid);
			for(int i=0;i<temp.size();i++)
			{
				Myuser myuser=new Myuser();
				User user=new User();
				user.setUserid(temp.get(i).getUserid());
				user=userSrv.doValidate(user);
				myuser.setUserid(user.getUserid());
				myuser.setUsername(user.getUsername());
				myuser.setHeadport(user.getHeadport());
				myuser.setPhone(user.getPhone());
				if(temp.get(i).getIsbuilder().equals("yes"))
				{
					jo.put("builder", myuser);
					if(userid.equals(temp.get(i).getUserid()))//当前用户是否为创建者
					{
						jo.put("authority", "1");
					}
					else{
						jo.put("authority", "0");
					}
				}
				else//users 中不包含创建者
					users.add(myuser);
			}
			jo.put("memberList", users);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		response.flushBuffer();
	
		return SUCCESS;
	}
	public EUmapSrv getEumapSrv() {
		return eumapSrv;
	}
	public void setEumapSrv(EUmapSrv eumapSrv) {
		this.eumapSrv = eumapSrv;
	}
	public EventSrv getEventSrv() {
		return eventSrv;
	}
	public void setEventSrv(EventSrv eventSrv) {
		this.eventSrv = eventSrv;
	}
	public UserSrv getUserSrv() {
		return userSrv;
	}
	public void setUserSrv(UserSrv userSrv) {
		this.userSrv = userSrv;
	}
}