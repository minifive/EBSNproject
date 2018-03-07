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

import demo.biz.SysmsgSrv;
import demo.vo.Sysmsg;

@SuppressWarnings("serial")
public class DelsysmsgAction extends ActionSupport {

	/**
	 * @return
	 */
	private String msgid;
	private SysmsgSrv sysmsgSrv;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		JSONObject jo=new JSONObject();
		msgid=request.getParameter("msgid");
		String userid=(String) request.getSession().getAttribute("userid");
		if(userid==null)
		{
			jo.put("returncode", "1");
			response.getWriter().print(jo.toString());
			response.flushBuffer();
			return SUCCESS;
		}
		Sysmsg sysmsg=new Sysmsg();
		sysmsg.setMsgid(msgid);	
		sysmsg=sysmsgSrv.doValiad(sysmsg);
		if(sysmsg==null)
		{
			jo.put("returncode", "0");
		}
		else
		{
			sysmsgSrv.doDelete(sysmsg);
			jo.put("returncode", "success");
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

}