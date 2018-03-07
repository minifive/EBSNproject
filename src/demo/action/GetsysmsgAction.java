package demo.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.SysmsgSrv;
import demo.vo.Mysysmsg;
import demo.vo.Sysmsg;

@SuppressWarnings("serial")
public class GetsysmsgAction extends ActionSupport {

	/**
	 * @return
	 */
	private SysmsgSrv sysmsgSrv;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		
		String userid=(String)request.getSession().getAttribute("userid");
		String returncode=new String();
		JSONObject returnjo=new JSONObject();
		if(userid==null)
		{
			System.out.println("uid is null,in getsysmsg action");
			returncode="1";
		}
		else
		{
			returncode="success";
			List<Sysmsg> temp=new LinkedList<Sysmsg>();
			temp=sysmsgSrv.MsgsforUser(userid);
			List<Mysysmsg> systemmsgs=new LinkedList<Mysysmsg>();
			for(int i=0;i<temp.size();i++)
			{
				Mysysmsg mymsg=new Mysysmsg();
				mymsg.setContent(temp.get(i).getContent());
				mymsg.setMsgid(temp.get(i).getMsgid());
				mymsg.setSendid(temp.get(i).getSendid());
				mymsg.setSendtime(temp.get(i).getSendtime().toString());
				mymsg.setState(temp.get(i).getState());
				mymsg.setType(temp.get(i).getType());
				mymsg.setUserid(temp.get(i).getUserid());
				systemmsgs.add(mymsg);
			}
			JSONArray msgList=JSONArray.fromObject(systemmsgs);
			returnjo.put("msgList",msgList );
		}	
		returnjo.put("returncode", returncode);
		response.setCharacterEncoding("UTF-8");//设置返回信息格式
		response.getWriter().print(returnjo.toString());
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