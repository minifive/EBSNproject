package demo.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import demo.biz.EUmapSrv;
import demo.biz.EventSrv;
import demo.biz.SysmsgSrv;
import demo.biz.UserSrv;
import demo.vo.Eumap;
import demo.vo.Event;
import demo.vo.Sysmsg;
import demo.vo.User;

@SuppressWarnings("serial")
public class IvteventmsgAction extends ActionSupport {

	/**
	 * @return
	 */
	private String eventid;
	private String inviteid;
	private SysmsgSrv sysmsgSrv;
	private UserSrv userSrv;
	private EventSrv eventSrv;
	private EUmapSrv eumapSrv;

	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		eventid=request.getParameter("eventid");
		inviteid=request.getParameter("inviteid");
		JSONObject jo=new JSONObject();
		String userid=(String) request.getSession().getAttribute("userid");
		if(userid==null)
		{
			jo.put("returncode", "1");
		}
		else
		{
			Event event=new Event();
			event.setEventId(eventid);
			event=eventSrv.doValidate(event);
			if(event.getExstnum().equals(event.getMaxnum()))
			{
				jo.put("returncode", "3");//人数已满。。
				response.getWriter().print(jo.toString());
				response.flushBuffer();
				return SUCCESS;
				
			}
			User user=new User();
			user.setUserid(inviteid);
			user=userSrv.doValidate(user);
			if(user==null||event==null)
			{
				jo.put("returncode", "0");
				
			}
			else
			{
	//------------------验证该用户是否已经加入该活动--------
				user.setUserid(userid);
				user=userSrv.doValidate(user);
				List<Eumap>temp=new LinkedList<Eumap>();
				temp=eumapSrv.findbyE_U_id(eventid, inviteid);
				if(temp.size()!=0){
					jo.put("returncode", "2");
				}
				else{
	//--------------------发送系统信息-------------------------------				
					Sysmsg sysmsg=new Sysmsg();
					Timestamp signuptime= new Timestamp(System.currentTimeMillis());
					SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss"); 
					String date=sdf.format(new Date());
					String msgid=String.format("msg%s", date);//id
					sysmsg.setMsgid(msgid);
					sysmsg.setUserid(inviteid);
					sysmsg.setSendtime(signuptime);
					sysmsg.setType("3");
					sysmsg.setContent(user.getUsername()+",邀请你加入活动："+event.getEventName());
					sysmsg.setState("unread");
					sysmsg.setSendid(eventid);
					sysmsgSrv.AddSysmsg(sysmsg);
				}
			}
		}
		
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
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

}