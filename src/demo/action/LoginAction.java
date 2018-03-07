package demo.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.UserSrv;
import demo.vo.User;

@SuppressWarnings("serial")
public class LoginAction extends ActionSupport {

	/**
	 * xiaowu 2016年3月
	 */
	private User user=new User();
	private UserSrv usersrv;
	private String ID;
	private String password;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		ID=request.getParameter("ID");
		password=request.getParameter("password");
		user.setUserid(ID);
		user.setPassword(password);
		System.out.println("login user id:"+user.getUserid());
		User u=usersrv.dologin(user);
		JSONObject jo=new JSONObject();
		if(u==null)
		{
			jo.put("returncode", "error");
		}
		else
		{
			request.getSession().setAttribute("userid", u.getUserid());//设置浏览器session
			jo.put("returncode", "success");
		}
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
	public UserSrv getUsersrv() {
		return usersrv;
	}
	public void setUsersrv(UserSrv usersrv) {
		this.usersrv = usersrv;
	}

}