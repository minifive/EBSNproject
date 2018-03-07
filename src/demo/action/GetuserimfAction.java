package demo.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.UserSrv;
import demo.vo.Myuser;
import demo.vo.User;

public class GetuserimfAction extends ActionSupport {

	/**
	 * @return 个人信息页  获取用户信息
	 */
	private String userid;
	private UserSrv userSrv;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		userid=request.getParameter("ID");
		JSONObject jo = new JSONObject();
		User user=new User();
		user.setUserid(userid);
		user=userSrv.doValidate(user);
		if(user==null)
		{
			jo.put("returncode", "0");
		}
		else
		{	
			Myuser myuser=new Myuser();
			myuser.setUsername(user.getUsername());
			myuser.setUserid(user.getUserid());
			myuser.setHeadport(user.getHeadport());
			myuser.setPhone(user.getPhone());
			jo.put("user",myuser);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
	public UserSrv getUserSrv() {
		return userSrv;
	}
	public void setUserSrv(UserSrv userSrv) {
		this.userSrv = userSrv;
	}
}