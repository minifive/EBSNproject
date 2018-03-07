package demo.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.CommentSrv;
import demo.biz.EUmapSrv;
import demo.biz.EventSrv;
import demo.biz.UserSrv;
import demo.vo.Comment;
import demo.vo.Eumap;
import demo.vo.Event;
import demo.vo.Mycomment;
import demo.vo.Myevent;
import demo.vo.User;

public class GetEventImfAction extends ActionSupport {

	/**
	 * @return event.html 显示活动详情  用户权限，活动评论
	 */
	private EventSrv eventSrv;
	private String eventid;//in 
	private EUmapSrv eumapSrv;
	private CommentSrv commentSrv;
	private UserSrv userSrv;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse(); 
		JSONObject jo=new JSONObject();
		request.setCharacterEncoding("UTF-8");
		eventid=request.getParameter("ID");
	//	eventid=new String(eventid.getBytes("iso-8859-1"),"GBK");
		//eventid="E2";
		System.out.println(eventid);
		Event event=new Event();
		event.setEventId(eventid);
		event=eventSrv.doValidate(event);
		if(event==null)
		{
			System.out.println("event id is null");
			jo.put("returncode", "1");
			response.getWriter().print(jo.toString());
			response.flushBuffer();
			return SUCCESS;
		}
		else
		{
			Myevent myevent=new Myevent();
			myevent.setAddress(event.getAddress());
			myevent.setCoordinate(event.getCoordinate());
			myevent.setEndtime(event.getEndtime().toString());
			myevent.setEventId(event.getEventId());
			myevent.setEventName(event.getEventName());
			myevent.setExstnum(event.getExstnum());
			myevent.setIntroduce(event.getIntroduce());
			myevent.setMaxnum(event.getMaxnum());
			myevent.setPoster(event.getPoster());
			myevent.setStarttime(event.getStarttime().toString());
			jo.put("returncode","success");
			jo.put("event", myevent);
		}
//----------------获取userid的权限，判断当前id是否已报名---------------------------
		String userid=(String)request.getSession().getAttribute("userid");
	
			
		if(userid==null)//未登录状态
		{
			jo.put("signup", "1");
		}
		else{
			if(userid.equals("admin"))
			{
				jo.put("signup", "5");//管理员身份
			}
			else
			{
				List<Eumap> temp=new LinkedList<Eumap>();
				temp=eumapSrv.findbyE_U_id( eventid, userid);
				if(temp.size()==0)//temp为空表示该user没有报名该活动
				{
					jo.put("signup", "2");
				}
				else
				{
					if(temp.get(0).getIsbuilder().equals("no"))
					{
						jo.put("signup", "3");
					}
					else
						jo.put("signup", "4");
				}
			}	
		}
//-------------------get event comments
		List<Comment>temp=commentSrv.FindByeventid(eventid);//第一次访问数据库，获取到eventid相关的评论
		List<Mycomment> returntemp=new LinkedList<Mycomment>();
		for(int i=0;i<temp.size();i++)
		{
			Mycomment item=new Mycomment();
			item.setContent(temp.get(i).getContent());
			item.setPublishtime(temp.get(i).getPublishtime().toString());
			item.setUserid(temp.get(i).getUserid());
			User user=new User();
			user.setUserid(temp.get(i).getUserid());
			user=userSrv.doValidate(user);  //第二次访问数据库，根据第一次访问到的userid获取user相关信息；
			item.setUsername(user.getUsername());
			item.setHeadport(user.getHeadport());
			returntemp.add(item);
		}
		jo.put("eventcomments", returntemp);
//--------------------------------
		response.setCharacterEncoding("UTF-8");//设置返回信息格式
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
	public EventSrv getEventSrv() {
		return eventSrv;
	}
	public void setEventSrv(EventSrv eventSrv) {
		this.eventSrv = eventSrv;
	}
	public EUmapSrv getEumapSrv() {
		return eumapSrv;
	}
	public void setEumapSrv(EUmapSrv eumapSrv) {
		this.eumapSrv = eumapSrv;
	}
	public CommentSrv getCommentSrv() {
		return commentSrv;
	}
	public void setCommentSrv(CommentSrv commentSrv) {
		this.commentSrv = commentSrv;
	}
	public UserSrv getUserSrv() {
		return userSrv;
	}
	public void setUserSrv(UserSrv userSrv) {
		this.userSrv = userSrv;
	}
}