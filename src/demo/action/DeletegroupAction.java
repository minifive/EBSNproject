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
import demo.biz.GUmapSrv;
import demo.biz.GroupSrv;
import demo.biz.SysmsgSrv;
import demo.vo.Comment;
import demo.vo.Event;
import demo.vo.Group;
import demo.vo.Gumap;
import demo.vo.Sysmsg;

public class DeletegroupAction extends ActionSupport {

	/**
	 * @return
	 * action  for admin
	 */
	private SysmsgSrv sysmsgSrv;
	private GroupSrv groupSrv;
	private GUmapSrv gumapSrv;
	private EventSrv eventSrv;
	private EUmapSrv eumapSrv;
	private CommentSrv commentSrv;
	private String groupid;
	private String msgid;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		groupid=request.getParameter("groupid");
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
//-------------删除群组相关活动
		List<Event> events=new LinkedList<Event>();
		events=eventSrv.findgroupevent(groupid);
		for(int i=0;i<events.size();i++)
		{
			eventSrv.dodelete(events.get(i));//删除群组相关的活动
			eumapSrv.dodelete(events.get(i).getEventId());//删除eumap中的活动成员
			List<Comment> comments=new LinkedList<Comment>();//删除活动评论
			comments=commentSrv.FindByeventid(events.get(i).getEventId());
			for(int j=0;j<comments.size();j++)
			{
				commentSrv.dodelete(comments.get(j));
			}
		}
//---------删除群组
		Group group=new Group();
		group.setGroupid(groupid);
		group=groupSrv.dovalidate(group);
		groupSrv.dodelete(group);
//---------删除群组成员并给每个成员发送系统消息
		Timestamp signuptime= new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
		String date=sdf.format(new Date());
		List<Gumap> gumaps=new LinkedList<Gumap>();
		gumaps=gumapSrv.findusersfromgroup(groupid);
		for(int i=0;i<gumaps.size();i++)
		{
			Gumap gumap=gumaps.get(i);
			String mid=String.format("msg%s%s", date,i);//id
			Sysmsg sysmsg=new Sysmsg();
			sysmsg.setMsgid(mid);
			sysmsg.setContent("你参加的群组：\""+group.getGroupname()+"\"已被解散。");
			sysmsg.setType("2");
			sysmsg.setSendid(groupid);
			sysmsg.setSendtime(signuptime);
			sysmsg.setState("unread");
			sysmsg.setUserid(gumap.getUserid());
			sysmsgSrv.AddSysmsg(sysmsg);
			gumapSrv.dodelete(gumap);
		}
		
		//gumapSrv.dodelete(groupid);
//---------修改当前系统消息状态
		if(!(msgid.equals("0")))//如果不是从群组界面删除（管理员对信息的拒绝操作）
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
	public GroupSrv getGroupSrv() {
		return groupSrv;
	}
	public void setGroupSrv(GroupSrv groupSrv) {
		this.groupSrv = groupSrv;
	}
	public GUmapSrv getGumapSrv() {
		return gumapSrv;
	}
	public void setGumapSrv(GUmapSrv gumapSrv) {
		this.gumapSrv = gumapSrv;
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