package demo.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.EventSrv;
import demo.vo.Event;
import demo.vo.Myevent;

@SuppressWarnings("serial")
public class SearchAction extends ActionSupport {

	/**
	 * @return 模糊搜索
	 */
	private EventSrv eventSrv;
	private String keyword;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		JSONObject jo=new JSONObject();
		keyword=request.getParameter("word");
		keyword=new String(keyword.getBytes("8859_1"),"utf-8");//转换编码格式 get 
		
		//keyword="自行车";
		List<Event> events=new LinkedList<Event>();
		List<Myevent> myevents=new LinkedList<Myevent>();
		events=eventSrv.search(keyword);
		System.out.println(events.size());
		for(int i=0;i<events.size();i++)
		{
			if((events.get(i).getGroupid()==null)&&events.get(i).getState().equals("audited"))
			{
				Myevent myevent=new Myevent();
				myevent.setEventId(events.get(i).getEventId());
				myevent.setEventName(events.get(i).getEventName());
				myevent.setIntroduce(events.get(i).getIntroduce());
				myevent.setStarttime(events.get(i).getStarttime().toString());
				myevent.setEndtime(events.get(i).getEndtime().toString());
				myevents.add(myevent);
			}
		}
		jo.put("searchlist", myevents);
	
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
	public EventSrv getEventSrv() {
		return eventSrv;
	}
	public void setEventSrv(EventSrv eventSrv) {
		this.eventSrv = eventSrv;
	}
}