package demo.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.EUmapSrv;
import demo.biz.EventSrv;
import demo.vo.Eumap;
import demo.vo.Event;

@SuppressWarnings("serial")
public class DeleumapAction extends ActionSupport {

	/**
	 * @return event,html 退出活动报名 
	 */
	private String eventid;//in
	private EUmapSrv eumapSrv;
	private EventSrv eventSrv;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String userid=(String)request.getSession().getAttribute("userid");
		JSONObject jo=new JSONObject();
		if(userid==null)
		{
			System.out.println("Userid in session is null! DeleumapAction.java!");
			jo.put("returncode", "1");
		}
//------------------验证是否已报名活动
		eventid=request.getParameter("ID");
		List<Eumap> temp=eumapSrv.findbyE_U_id(eventid, userid);
		if(temp.size()==0)
		{
			System.out.println("Userid is not in this event! not need to signout! in DeleumapAction!");
			jo.put("returncode", "2");
		}
		else{
			//从eumap中删除
			eumapSrv.deleumap(temp.get(0));
			//修改event的estunm参数
			Event e=eventSrv.delextingnum(eventid, 1);
			if(e==null)
				System.out.println("delextingnum error event not exsting");
			jo.put("returncode", "success");
		}
//---------------response--------
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
	public EUmapSrv getEumapSrv() {
		return eumapSrv;
	}
	public void setEumapSrv(EUmapSrv eumapSrv) {
		this.eumapSrv = eumapSrv;
	}
	public EventSrv getEventSrv() {
		return eventSrv;
	}
	public void setEventSrv(EventSrv eventSrv) {
		this.eventSrv = eventSrv;
	}
}