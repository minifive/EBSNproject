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
import demo.biz.UserSrv;
import demo.vo.Group;
import demo.vo.Gumap;
import demo.vo.Sysmsg;
import demo.vo.User;

@SuppressWarnings("serial")
public class IvtgroupmsgAction extends ActionSupport {

	/**
	 * @return
	 */
	private String groupid;
	private String inviteid;
	private SysmsgSrv sysmsgSrv;
	private UserSrv userSrv;
	private GroupSrv groupSrv;
	private GUmapSrv gumapSrv;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		groupid=request.getParameter("groupid");
		inviteid=request.getParameter("inviteid");
		JSONObject jo=new JSONObject();
		String userid=(String) request.getSession().getAttribute("userid");
		if(userid==null)
		{
			jo.put("returncode", "1");
		}
		else
		{
			Group group=new Group();
			group.setGroupid(groupid);
			group=groupSrv.dovalidate(group);
			if(group.getExistmemnum().equals(group.getMemberceiling()))
			{
				jo.put("returncode", "3");//群组人数已满，不发送邀请
				response.getWriter().print(jo.toString());
				response.flushBuffer();
				return SUCCESS;
			}
			User user=new User();
			user.setUserid(inviteid);
			user=userSrv.doValidate(user);
			if(user==null||group==null)
			{
				jo.put("returncode", "0");
				
			}
			else
			{
	//--------------------判断用户是否已加入群组
				user.setUserid(userid);
				user=userSrv.doValidate(user);
				Gumap gumap=new Gumap();
				gumap.setGroupid(groupid);
				gumap.setUserid(inviteid);
				List<Gumap> temp=new LinkedList<Gumap>();
				temp=gumapSrv.userfroumgroup(inviteid, groupid);
				if(temp.size()!=0)
				{
					jo.put("returncode", "2");//该用户已经加入群组
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
					sysmsg.setType("1");
					sysmsg.setContent(user.getUsername()+",邀请你加入群组："+group.getGroupname());
					sysmsg.setState("unread");
					sysmsg.setSendid(groupid);
					sysmsgSrv.AddSysmsg(sysmsg);
					jo.put("returncode", "success");
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

}