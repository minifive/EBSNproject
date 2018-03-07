package demo.action;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class HelloAction extends ActionSupport {

	/**
	 * @return
	 */
	private String user;
	public String execute() {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String name = new String();
		//name=request.getParameter("a");
		name="Vicky";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			//byte[] bys = md.digest(name.getBytes());
			byte[] bys = name.getBytes();
			md.update(bys);//初始化
			byte[] newBytes = md.digest();//加密
			//--转换---
			StringBuffer buffer=new StringBuffer(newBytes.length * 2);
			System.out.println("---"+newBytes.toString());
			for(int i=0;i<newBytes.length;i++)
			{
				buffer.append(Character.forDigit((newBytes[i] & 240) >> 4, 16));  
				buffer.append(Character.forDigit(newBytes[i] & 15, 16));  
			}			
			System.out.println(buffer.toString());		
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}