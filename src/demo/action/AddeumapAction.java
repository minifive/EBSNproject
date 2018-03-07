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

@SuppressWarnings("serial")
public class AddeumapAction extends ActionSupport {

	/**
	 * @return  event.html中 活动报名  修改eumap表  用户手动报名
	 */
	private EUmapSrv eumapSrv;
	private EventSrv eventSrv;
	private SysmsgSrv sysmsgSrv;
	private String eventid;//in
	private String userid;//in
	private String isbuilder;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		isbuilder="no";
		eventid=request.getParameter("ID");
		userid=(String) request.getSession().getAttribute("userid");
		JSONObject jo=new JSONObject();
		if(userid==null)
		{
			jo.put("returncode","1");
			response.getWriter().print(jo.toString());
			response.flushBuffer();
			return SUCCESS;
			
		}
//----------验证是否已经报过名----------
		List<Eumap> temp=new LinkedList<Eumap>();
		temp=eumapSrv.findbyE_U_id(eventid, userid);
		if(temp.size()!=0)
		{
			System.out.println("该用户已经报名！"+eventid+" + "+userid);
			return ERROR;
		}
//---------修改exsitingnum-----------		
		Event event=new Event();
		event.setEventId(eventid);
		event=eventSrv.doValidate(event);
		String msg=eventSrv.addextingnum(eventid);//更新event中的exstnum属性
		if(msg.equals("false"))
		{
			System.out.println("修改existingnum:error extnum>maxnum"+msg);
			jo.put("returncode","2");
			response.getWriter().print(jo.toString());
			response.flushBuffer();
			return SUCCESS;
		}
		
//-------更新eumap表----------
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
		String date=sdf.format(new Date());
		String eumapid=String.format("M%sE", date);//id
		Eumap eumap=new Eumap();
		eumap.setMapid(eumapid);
		eumap.setEventid(eventid);
		eumap.setUserid(userid);
		eumap.setIsbuilder(isbuilder);
		Timestamp intime= new Timestamp(System.currentTimeMillis());//用户报名时间
		eumap.setIntime(intime);
		eumapSrv.addeumap(eumap);
//------通知活动创建者---------
		List<Eumap> eumaps=eumapSrv.findbuilder(eventid);
		if(eumaps.size()==0)
		{
			System.out.println("builder for event not found");
		}
		else
		{
			Sysmsg sysmsg=new Sysmsg();
			Timestamp signuptime= new Timestamp(System.currentTimeMillis());
			String msgid=String.format("msg%s", date);//id
			sysmsg.setMsgid(msgid);
			sysmsg.setUserid(eumaps.get(0).getUserid());
			sysmsg.setSendtime(signuptime);
			sysmsg.setType("2");
			sysmsg.setContent(userid+"报名参加了你的活动："+event.getEventName());
			sysmsg.setState("unread");
			sysmsg.setSendid(eventid);
			sysmsgSrv.AddSysmsg(sysmsg);
		}
//--------response----
		jo.put("returncode","success");
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
	public SysmsgSrv getSysmsgSrv() {
		return sysmsgSrv;
	}
	public void setSysmsgSrv(SysmsgSrv sysmsgSrv) {
		this.sysmsgSrv = sysmsgSrv;
	}
}