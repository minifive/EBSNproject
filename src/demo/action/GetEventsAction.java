package demo.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.EventSrv;
import demo.biz.SysmsgSrv;
import demo.biz.UserSrv;
import demo.vo.Event;
import demo.vo.Myevent;
import demo.vo.Sysmsg;
import demo.vo.User;

public class GetEventsAction extends ActionSupport {

	/**
	 * @return   main.html 用于主页显示所有活动
	 */
	private List<Event> allevents;
	private EventSrv eventSrv;
	private SysmsgSrv sysmsgSrv;
	private UserSrv userSrv;
	private String page;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request= ServletActionContext.getRequest();
		page=request.getParameter("page");
		int pageint=Integer.parseInt(page)+1;//当前页码
//-----------------get evetnts--------------------
		JSONObject returnjo=new JSONObject();
		setAllevents(eventSrv.getallevents());
		List<Myevent> eventList=new LinkedList<Myevent>();//eventlist为所有活动中的前十条
		int len;
		len=pageint*5;
		int milen=len-5;//上一页
		if(milen<0)
			milen=0;
		if(allevents.size()<=milen)
		{
			returnjo.put("returncode","0");
		}
		else
		{
			len=(allevents.size()<len?allevents.size():len);
			System.out.println("该页的活动长度："+len);
			for(int i=milen;i<len;i++)
			{
				Myevent myevent=new Myevent();
				myevent.setAddress(allevents.get(i).getAddress());
				myevent.setCoordinate(allevents.get(i).getCoordinate());
				myevent.setEndtime(allevents.get(i).getEndtime().toString());//将Time形式的数据转化成string类型
				myevent.setEventId(allevents.get(i).getEventId());
				myevent.setEventName(allevents.get(i).getEventName());
				myevent.setExstnum(allevents.get(i).getExstnum());
				myevent.setIntroduce(allevents.get(i).getIntroduce());
				myevent.setMaxnum(allevents.get(i).getMaxnum());
				myevent.setPoster(allevents.get(i).getPoster());
				myevent.setStarttime(allevents.get(i).getStarttime().toString());//
				eventList.add(myevent);
				/*数据格式装换*/
			}
		}
//-----------------get system message and userimf-------------------	
		String userid=(String)request.getSession().getAttribute("userid");
		System.out.println("userid:"+userid);
		JSONObject user=new JSONObject();
		String authority=new String();
		
		
		if(userid==null)
		{
			authority="0";
		}
		else
		{
			if(userid.equals("admin"))
			{
				authority="1";
			}
			User u=new User();
			u.setUserid(userid);
			u=userSrv.doValidate(u);
			user.put("userid", u.getUserid());
			user.put("username", u.getUsername());
			user.put("headport", u.getHeadport());
			List<Sysmsg> systemmsgs=new LinkedList<Sysmsg>();
			systemmsgs=sysmsgSrv.MsgsforUser(userid);
			int lens=0;
			for(int i=0;i<systemmsgs.size();i++)
			{
				if(systemmsgs.get(i).getState().equals("unread"))
					lens++;
			}
			String sysmsg=""+lens;//sysmsg 的长度
			returnjo.put("inform", sysmsg);
			returnjo.put("user", user);
		}
//---------------return------------------------
		returnjo.put("authority",authority);
		returnjo.put("eventList", eventList);
		returnjo.put("page", page);
		//String temp="["+jsonarray.toString()+","+jo.toString()+"]";
		//String returncode=new String(temp.getBytes("UTF-8"),"UTF-8");
		//response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");//设置返回信息格式
		response.getWriter().print(returnjo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
	public EventSrv getEventSrv() {
		return eventSrv;
	}
	public void setEventSrv(EventSrv eventSrv) {
		this.eventSrv = eventSrv;
	}
	public List<Event> getAllevents() {
		return allevents;
	}
	public void setAllevents(List<Event> allevents) {
		this.allevents = allevents;
	}
	public SysmsgSrv getSysmsgSrv() {
		return sysmsgSrv;
	}
	public void setSysmsgSrv(SysmsgSrv sysmsgSrv) {
		this.sysmsgSrv = sysmsgSrv;
	}
	public UserSrv getUserSrv() {
		return userSrv;
	}
	public void setUserSrv(UserSrv userSrv) {
		this.userSrv = userSrv;
	}
}
