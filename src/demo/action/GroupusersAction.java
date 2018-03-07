package demo.action;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.GUmapSrv;
import demo.biz.UserSrv;
import demo.vo.Group;
import demo.vo.Gumap;
import demo.vo.Myuser;
import demo.vo.User;

public class GroupusersAction extends ActionSupport {

	/**
	 * @return  用于邀请页获取相关群组用户
	 */
	private GUmapSrv gumapSrv;
	private UserSrv userSrv;
	private String groupid; //前台传过来groupid
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		groupid=request.getParameter("groupid");
		JSONObject jo=new JSONObject();
		
		String uid=(String) request.getSession().getAttribute("userid");
		if(uid==null)
		{
			jo.put("returncode", "1");//登录失效
		}
		else
		{
			jo.put("returncode", "success");
			List<Gumap> gumaps=new LinkedList<Gumap>();
			List<Myuser> myusers=new LinkedList<Myuser>();
			gumaps=gumapSrv.findusersfromgroup(groupid);
			for(int i=0;i<gumaps.size();i++)
			{
				User user=new User();
				Myuser myuser=new Myuser();
				user.setUserid(gumaps.get(i).getUserid());
				user=userSrv.doValidate(user);
				myuser.setUsername(user.getUsername());
				myuser.setUserid(user.getUserid());
				myuser.setHeadport(user.getHeadport());
				myusers.add(myuser);
			}
			jo.put("users",myusers);
		}
		
		response.setCharacterEncoding("UTF-8");
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
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public UserSrv getUserSrv() {
		return userSrv;
	}
	public void setUserSrv(UserSrv userSrv) {
		this.userSrv = userSrv;
	}
}