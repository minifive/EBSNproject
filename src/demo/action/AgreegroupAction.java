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

import demo.biz.GUmapSrv;
import demo.biz.GroupSrv;
import demo.biz.SysmsgSrv;
import demo.vo.Group;
import demo.vo.Gumap;
import demo.vo.Sysmsg;

public class AgreegroupAction extends ActionSupport {

	/**
	 * @return
	 * action  for admin
	 */
	private GroupSrv groupSrv;
	private SysmsgSrv sysmsgSrv;
	private GUmapSrv gumapSrv;
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
		}
		else
		{
			jo.put("returncode", "success");
	//-------修改event的状态为审核通过state=audited
			Group group=new Group();
			group.setGroupid(groupid);
			group=groupSrv.dovalidate(group);
			group.setLabels("audited");
			groupSrv.doupdate(group);
	//---------修改消息状态 unread->read
			Sysmsg smsg=new Sysmsg();
			smsg.setMsgid(msgid);
			smsg=sysmsgSrv.doValiad(smsg);
			smsg.setState("read");
			sysmsgSrv.doUpdate(smsg);
	//---------给创建者发系统消息
			String userid=new String();
			List<Gumap> temp=new LinkedList<Gumap>();
			temp=gumapSrv.findusersfromgroup(groupid);
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
			sysmsg.setType("8");
			sysmsg.setSendid(groupid);
			sysmsg.setState("unread");
			sysmsg.setSendtime(signuptime);
			sysmsg.setContent("你的群组："+group.getGroupname()+",已审核通过,可点击查看。");
			sysmsg.setUserid(userid);
			sysmsgSrv.AddSysmsg(sysmsg);
		}
//--------response----
		
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
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
	public SysmsgSrv getSysmsgSrv() {
		return sysmsgSrv;
	}
	public void setSysmsgSrv(SysmsgSrv sysmsgSrv) {
		this.sysmsgSrv = sysmsgSrv;
	}
}