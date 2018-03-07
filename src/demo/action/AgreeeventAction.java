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
import demo.vo.Eumap;
import demo.vo.Event;
import demo.vo.Sysmsg;

public class AgreeeventAction extends ActionSupport {

	/**
	 * @return
	 * action  for admin
	 */
	private EventSrv eventSrv;
	private SysmsgSrv sysmsgSrv;
	private EUmapSrv eumapSrv;
	private String eventid;
	private String msgid;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		eventid=request.getParameter("eventid");
		msgid=request.getParameter("msgid");
		String adminid=(String) request.getSession().getAttribute("userid");
		JSONObject jo=new JSONObject();
		if(adminid==null)
		{
			jo.put("returncode", "1");
		}
		else
		{
			jo.put("returncode", "success");
	//-------修改event的状态为审核通过state=audited
			Event event=new Event();
			event.setEventId(eventid);
			event=eventSrv.doValidate(event);
			event.setState("audited");
			eventSrv.doupdate(event);
	//---------修改消息状态 unread->read
			Sysmsg smsg=new Sysmsg();
			smsg.setMsgid(msgid);
			smsg=sysmsgSrv.doValiad(smsg);
			smsg.setState("read");
			sysmsgSrv.doUpdate(smsg);
	//---------给创建者发系统消息
			String userid=new String();
			List<Eumap> temp=new LinkedList<Eumap>();
			temp=eumapSrv.findeventusers(eventid);
			for(int i=0;i<temp.size();i++)//获取到创建者的id
			{
				if(temp.get(i).getIsbuilder().equals("yes"))
				{
					userid=temp.get(i).getUserid();
				}
			}
			Sysmsg sysmsg=new Sysmsg();
			Timestamp signuptime= new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
			String date=sdf.format(new Date());
			String mid=String.format("msg%s", date);//id
			sysmsg.setMsgid(mid);
			sysmsg.setType("7");
			sysmsg.setSendid(eventid);
			sysmsg.setState("unread");
			sysmsg.setSendtime(signuptime);
			sysmsg.setContent("你的活动："+event.getEventName()+",已审核通过,可点击查看。");
			sysmsg.setUserid(userid);
			sysmsgSrv.AddSysmsg(sysmsg);
		}
//--------response----
		
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

	public SysmsgSrv getSysmsgSrv() {
		return sysmsgSrv;
	}

	public void setSysmsgSrv(SysmsgSrv sysmsgSrv) {
		this.sysmsgSrv = sysmsgSrv;
	}

	public EUmapSrv getEumapSrv() {
		return eumapSrv;
	}

	public void setEumapSrv(EUmapSrv eumapSrv) {
		this.eumapSrv = eumapSrv;
	}
}