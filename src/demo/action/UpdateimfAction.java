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
public class UpdateimfAction extends ActionSupport {

	/**
	 * @return  修改用户个人信息
	 */
	private UserSrv userSrv;
	private String username;
	private String opassword;
	private String npassword;
	private String phone;
	private String image;
	
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String userid=(String)request.getSession().getAttribute("userid");
		username=request.getParameter("name");
		opassword=request.getParameter("opassword");
		npassword=request.getParameter("npassword");
		phone=request.getParameter("phone");
		image=request.getParameter("image");
				
		JSONObject jo = new JSONObject();
		if(userid==null)
		{
			System.out.println("updteimfAction  userid in session is null");
			jo.put("returncode", "1");
		}
		User user=new User();
		user.setUserid(userid);
		user.setPassword(opassword);
		User u=userSrv.dologin(user);//验证输入的旧密码是否正确

		if(u==null)
			jo.put("returncode", "2");
		else{
			u.setUsername(username);
			u.setPassword(npassword);
			u.setPhone(phone);
			userSrv.doupdate(u);
			jo.put("returncode", "success");
		}
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