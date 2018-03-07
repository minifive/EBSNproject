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

import demo.biz.GUmapSrv;
import demo.biz.GroupSrv;
import demo.biz.SysmsgSrv;
import demo.biz.UserSrv;
import demo.vo.Group;
import demo.vo.Gumap;
import demo.vo.Sysmsg;
import demo.vo.User;

public class DelgumapoAction extends ActionSupport {

	/**
	 * @return  delete a member from group  修改gumap
	 */
	private GUmapSrv gumapSrv;
	private GroupSrv groupSrv;
	private SysmsgSrv sysmsgSrv;
	private UserSrv userSrv;
	private String userid;//输入
	private String groupid;
	
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		userid=request.getParameter("userid");
		groupid=request.getParameter("groupid");
		JSONObject jo=new JSONObject();
		String managerid=(String) request.getSession().getAttribute("userid");
		if(managerid==null)
		{
			System.out.println("userid in session is null in delgumapoaction");
			jo.put("returncode", "1");
		}
//--------------------------delete gumap-------------------------------------		
		int gumapsize=gumapSrv.deletefromgroup(userid, groupid);
		if(gumapsize==0)
		{
			System.out.println("删除失败，该群组中没有此人");
			return ERROR;
		}
		/*修改group中的exitingusernum属性，已有成员数减一*/
		String msg=groupSrv.delextingnum(groupid,gumapsize);
		System.out.println(gumapsize+" msg:"+msg);
//-----------------------send system msg------------------		
		Group group=new Group();
		group.setGroupid(groupid);
		group=groupSrv.dovalidate(group);
		User user=new User();
		user.setUserid(userid);
		user=userSrv.doValidate(user);
		if(user==null||group==null)
		{
			System.out.println("查无此人或群组");
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
		sysmsg.setType("2");
		sysmsg.setContent(user.getUsername()+",你被移出群组："+group.getGroupname());
		sysmsg.setState("unread");
		sysmsg.setSendid(groupid);
		sysmsgSrv.AddSysmsg(sysmsg);
//----------------------------------------------------------------------		
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
	public GUmapSrv getGumapSrv() {
		return gumapSrv;
	}
	public void setGumapSrv(GUmapSrv gumapSrv) {
		this.gumapSrv = gumapSrv;
	}
	public GroupSrv getGroupSrv() {
		return groupSrv;
	}
	public void setGroupSrv(GroupSrv groupSrv) {
		this.groupSrv = groupSrv;
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