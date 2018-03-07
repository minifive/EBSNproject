package demo.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LogoutAction extends ActionSupport {

	/**
	 * @return
	 * @throws IOException 
	 */
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String userid=(String)request.getSession().getAttribute("userid");
		if(userid!=null)
			request.getSession().setAttribute("userid", null);//设置浏览器session
		
		JSONObject jo=new JSONObject();
		jo.put("returncode", "success");
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}

}