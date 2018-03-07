package demo.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class DeleumapoAction extends ActionSupport {

	/**
	 * @return event-member,html 踢出活动 
	 */
	private String eventid;//in
	private String userid;//in
	private EUmapSrv eumapSrv;
	private EventSrv eventSrv;
	private SysmsgSrv sysmsgSrv;
	private UserSrv userSrv;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		userid=request.getParameter("userid");
		eventid=request.getParameter("eventid");
		JSONObject jo=new JSONObject();
//------------------验证是否已报名活动
		List<Eumap> temp=eumapSrv.findbyE_U_id(eventid, userid);
		if(temp.size()==0)
		{
			System.out.println("Userid is not in this event! not need to signout! in DeleumapAction!");
			jo.put("returncode", "2");
		}
		else{
		//------------------从eumap中删除---------------------------
			eumapSrv.deleumap(temp.get(0));
			//修改event的estunm参数
			Event e=eventSrv.delextingnum(eventid, 1);
			jo.put("returncode", "success");
		//-----------------sysmsg-----------------------------
			User user=new User();
			user.setUserid(userid);
			user=userSrv.doValidate(user) ;
			if(user==null)
			{
				System.out.println("user not found in DeleumapoAction");
				return ERROR;
			}
			Sysmsg sysmsg=new Sysmsg();
			Timestamp signuptime= new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss"); 
			String date=sdf.format(new Date());
			String msgid=String.format("msg%s", date);//id
			sysmsg.setMsgid(msgid);
			sysmsg.setUserid(userid);
			sysmsg.setSendtime(signuptime);
			sysmsg.setType("4");
			sysmsg.setContent(user.getUsername()+",你被移出活动："+e.getEventName());
			sysmsg.setState("unread");
			sysmsg.setSendid(eventid);
			sysmsgSrv.AddSysmsg(sysmsg);
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