package demo.action;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.EUmapSrv;
import demo.biz.EventSrv;
import demo.vo.Eumap;
import demo.vo.Event;
import demo.vo.Myevent;

public class GetusereventsAction extends ActionSupport {

	/**
	 * @return   display-events; eumap  获取一个用户参与的活所有动 
	 */
	private EUmapSrv eumapSrv;
	private EventSrv eventSrv;
	private String userid;//in
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		JSONObject jo=new JSONObject();
		userid=(String)request.getSession().getAttribute("userid");	
		if(userid==null)
		{
			System.out.println("userid in session is null,(GetusereventsAction!)");
			jo.put("returncode","1");
		}
		else
		{
			List<Eumap> temp=new LinkedList<Eumap>();
			List<Myevent> data=new LinkedList<Myevent>();
			if(userid.equals("admin"))
			{
				temp=eumapSrv.findall();//如果过是管理员 返回所有活动
				jo.put("returncode","3");//用于前端区分添加活动的方法
			}
			else
			{
				temp=eumapSrv.finduserevents(userid);
			}
			if(temp.size()==0)
			{
				jo.put("returncode","2");//当前用户参与的活动为空，用于前端提示信息
			}
			else
			{
				int auditing=0;
				for(int i=0;i<temp.size();i++)
				{
					Event event=new Event();
					event.setEventId(temp.get(i).getEventid());
					event=eventSrv.doValidate(event);
					if(event.getState().equals("audited"))//审核通过的活动
					{
						Myevent myevent=new Myevent();
						myevent.setAddress(event.getAddress());
						myevent.setCoordinate(event.getCoordinate());
						myevent.setEndtime(event.getEndtime().toString());
						myevent.setEventId(event.getEventId());
						myevent.setEventName(event.getEventName());
						myevent.setExstnum(event.getExstnum());
						myevent.setIntroduce(event.getIntroduce());
						myevent.setMaxnum(event.getMaxnum());
						//myevent.setPoster(event.getPoster());
						myevent.setStarttime(event.getStarttime().toString());
						data.add(myevent);
					}
					else
					{
						auditing++;
					}
				}
				jo.put("auditing",auditing);
				jo.put("returncode", "success");
				jo.put("eventList", data);
			}
		}
		response.setCharacterEncoding("UTF-8");
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