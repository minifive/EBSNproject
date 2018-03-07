package demo.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class CreatEventAction extends ActionSupport {

	/**
	 * @return
	 */
	private EventSrv eventSrv;
	private EUmapSrv eumapSrv;
	private SysmsgSrv sysmsgSrv;
	private String eventname;//输入
	private String poster;//输入
	private String address;//输入
	private Timestamp starttime;//输入
	private Timestamp endtime;//输入 
	private String isgroupevent;//输入
	private String groupid;//默认为空
	private String coordinate;//in
	private String plimit;//in
	private String maxnum;//in
	private String extnum="1";//默认为1 创建者加入
	private String introduce;//输入
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		eventname=request.getParameter("name");
		String stime=request.getParameter("sDate");
		String etime=request.getParameter("eDate");
		//String stime="2016-04-08 00:59:00";
		//String etime="2016-04-08T00:59";
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss");
		stime=stime.replace('T', ' ');
		etime=etime.replace('T', ' ');
		System.out.println(stime);
		starttime=Timestamp.valueOf(stime+":00");
		endtime=Timestamp.valueOf(etime+":00");
		address=request.getParameter("location");
		coordinate=request.getParameter("coord");
		isgroupevent=request.getParameter("gLimit");
		groupid=request.getParameter("group");
		plimit=request.getParameter("pLimit");
		maxnum=request.getParameter("num");
		introduce=request.getParameter("info");
		poster=request.getParameter("image");
		
//--------------------save event--------------------------------------------		
		Event event=new Event();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
		String date=sdf.format(new Date());
		String eventid=String.format("E%s", date);//id
		event.setEventId(eventid);
		Timestamp signuptime= new Timestamp(System.currentTimeMillis());
		event.setSignuptime(signuptime);//
		event.setEventName(eventname);
		event.setStarttime(starttime);
		event.setEndtime(endtime);
		event.setAddress(address);
		event.setCoordinate(coordinate);
		event.setPoster(poster);
		System.out.println("iplimit:"+isgroupevent);
		if(isgroupevent.equals("false"))//如果是群组活动的话则设置groupid否则不设置
			groupid=null;
		System.out.println("groupid:"+groupid);
		event.setGroupid(groupid);
		if(plimit.equals("false"))//如果活动maxnum不收限制，设置其为一万
			maxnum="不限";
		event.setMaxnum(maxnum);
		event.setExstnum(extnum);
		event.setIntroduce(introduce);
		event.setState("auditing");//当前活动在审核阶段
		System.out.println("save to  database~");
		eventSrv.createvent(event);
//-----------------修改eumap表，添加一个item------------------------------------
		JSONObject jo=new JSONObject();
		String userid=(String) request.getSession().getAttribute("userid");
		if(userid==null)
		{
			jo.put("returncode","1");
		}
		else
		{
			jo.put("returncode","success");
			Eumap eumap=new Eumap();
			String mapid=String.format("M%s", date);//id
			eumap.setMapid(mapid);
			eumap.setEventid(eventid);
			eumap.setUserid(userid);
			eumap.setIsbuilder("yes");
			eumap.setIntime(signuptime);
			eumapSrv.addeumap(eumap);
//-----------发送消息给管理员------
			Sysmsg sysmsg=new Sysmsg();
			String msgid=String.format("msg%s", date);//id
			sysmsg.setMsgid(msgid);
			sysmsg.setUserid("admin");//管理员为收件人
			sysmsg.setType("5");//
			sysmsg.setSendid(eventid);
			sysmsg.setState("unread");
			sysmsg.setSendtime(signuptime);
			sysmsg.setContent("用户"+userid+",申请创建活动："+eventname);
			sysmsgSrv.AddSysmsg(sysmsg);
		}	
//-----------response---------------	
		jo.put("eventid", eventid);
		response.setCharacterEncoding("UTF-8");
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
	public SysmsgSrv getSysmsgSrv() {
		return sysmsgSrv;
	}
	public void setSysmsgSrv(SysmsgSrv sysmsgSrv) {
		this.sysmsgSrv = sysmsgSrv;
	}
}