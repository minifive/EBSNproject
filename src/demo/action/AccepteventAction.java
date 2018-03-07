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
import demo.vo.Sysmsg;

@SuppressWarnings("serial")
public class AccepteventAction extends ActionSupport {

	/**
	 * @return  event.html中 活动报名  修改eumap表 邀请信息后 用户点击接受
	 */
	private EUmapSrv eumapSrv;
	private EventSrv eventSrv;
	private SysmsgSrv sysmsgSrv;
	private String eventid;//in
	private String userid;//in
	private String sysmsgid;//in
	private String isbuilder;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		isbuilder="no";
		eventid=request.getParameter("eventid");
		sysmsgid=request.getParameter("msgid");
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
			jo.put("returncode", "2");
		}
//---------修改exsitingnum-----------		
		else
		{
			String msg=eventSrv.addextingnum(eventid);//更新event中的exstnum属性
			System.out.println("修改existingnum:"+msg);
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
			jo.put("returncode","success");
		}
//-------------set-sysmsg-state-----------
		System.out.println("eventid:::"+eventid+",msgid:"+sysmsgid);
		Sysmsg sysmsg=new Sysmsg();
		sysmsg.setMsgid(sysmsgid);
		sysmsg=sysmsgSrv.doValiad(sysmsg);
		sysmsg.setState("read");
		sysmsgSrv.doUpdate(sysmsg);
//--------response----
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