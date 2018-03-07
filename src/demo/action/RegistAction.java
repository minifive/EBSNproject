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
public class RegistAction extends ActionSupport {

	/**
	 * @return
	 */
	
	private UserSrv usersrv;
	private String ID;
	private String password;
	private String name;
	
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		name=request.getParameter("name");
		ID=request.getParameter("ID");
		password=request.getParameter("password");
		//name=new String(name.getBytes("8859_1"),"utf-8");//转换编码格式
	//	System.out.println("开始注册 :username:"+name);
	//	System.out.println("开始注册 :id:"+ID);
	//	System.out.println("开始注册 :psw:"+password);
		User user=new User();
		user.setUserid(ID);
		user.setUsername(name);
		user.setPassword(password);
		user.setHeadport("default.jpg");
		JSONObject jo = new JSONObject();
		if(usersrv.doValidate(user)!=null)
		{
			System.out.println("该用户id已被注册,userid:"+user.getUserid());
			jo.put("returnCode", "1");//学号已被注册
		}
		else
		{
			usersrv.doregist(user);
			jo.put("returnCode", "0");	
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