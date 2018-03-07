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

import demo.biz.CommentSrv;
import demo.biz.EUmapSrv;
import demo.biz.EventSrv;
import demo.biz.SysmsgSrv;
import demo.vo.Comment;
import demo.vo.Eumap;
import demo.vo.Event;
import demo.vo.Sysmsg;

public class DeleteeventAction extends ActionSupport {

	/**
	 * @return
	 * action  for admin
	 */
	private SysmsgSrv sysmsgSrv;
	private EventSrv eventSrv;
	private EUmapSrv eumapSrv;
	private CommentSrv commentSrv;
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
			response.getWriter().print(jo.toString());
			response.flushBuffer();
			return SUCCESS;
		}
		jo.put("returncode", "success");
//---------删除活动
		Event event=new Event();
		event.setEventId(eventid);
		event=eventSrv.doValidate(event);
		eventSrv.dodelete(event);
//----------删除活动相关评论
		List<Comment> comments=new LinkedList<Comment>();
		comments=commentSrv.FindByeventid(eventid);
		for(int i=0;i<comments.size();i++)
		{
			commentSrv.dodelete(comments.get(i));
		}
//---------删除活动成员并通知
		Timestamp signuptime= new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
		String date=sdf.format(new Date());
		List<Eumap> eumaps=new LinkedList<Eumap>();
		eumaps=eumapSrv.findeventusers(eventid);
		for(int i=0;i<eumaps.size();i++)
		{
			Eumap eumap=eumaps.get(i);
			String mid=String.format("msg%s%s", date,i);//id
			Sysmsg sysmsg=new Sysmsg();
			sysmsg.setMsgid(mid);
			sysmsg.setContent("你参加的活动：\""+event.getEventName()+"\"已被删除。");
			sysmsg.setType("2");
			sysmsg.setSendid(eventid);
			sysmsg.setSendtime(signuptime);
			sysmsg.setState("unread");
			sysmsg.setUserid(eumap.getUserid());
			sysmsgSrv.AddSysmsg(sysmsg);
			eumapSrv.deleumap(eumap);	
		}
//---------修改系统消息状态
		if(!(msgid.equals("0")))
		{
			Sysmsg sysmsg=new Sysmsg();
			sysmsg.setMsgid(msgid);
			sysmsg=sysmsgSrv.doValiad(sysmsg);
			sysmsg.setState("read");
			sysmsgSrv.doUpdate(sysmsg);
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
}