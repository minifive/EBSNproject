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
public class AuthorityAction extends ActionSupport {

	/**
	 * @return   权限管理 
	 * @throws IOException 
	 */
	
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String uid=(String)request.getSession().getAttribute("userid");//session中的id保存三十分钟
		JSONObject jo=new JSONObject();
		if(uid==null)
		{
			jo.put("authority", "tourist");
		}
		else
		{
			jo.put("authority", "user");
		}
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
}