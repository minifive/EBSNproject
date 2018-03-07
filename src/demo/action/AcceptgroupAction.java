package demo.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.GUmapSrv;
import demo.biz.GroupSrv;
import demo.biz.SysmsgSrv;
import demo.vo.Gumap;
import demo.vo.Sysmsg;

@SuppressWarnings("serial")
public class AcceptgroupAction extends ActionSupport {

	/**
	 * @return  event.html中 活动报名  修改eumap表  用户手动报名
	 */
	private GroupSrv groupSrv;
	private GUmapSrv gumapSrv;
	private SysmsgSrv sysmsgSrv;
	private String groupid;//in
	private String userid;//in
	private String sysmsgid;//in
	private String isbuilder;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		isbuilder="no";
		groupid=request.getParameter("groupid");
		sysmsgid=request.getParameter("msgid");
		userid=(String) request.getSession().getAttribute("userid");
		JSONObject jo=new JSONObject();
		if(userid==null)
		{
			jo.put("returncode","1");
			response.getWriter().print(jo.toString());
			response.flushBuffer();
			return SUCCESS;
			
		}
//----------验证是否已经报过名----------
		List<Gumap> temp=new LinkedList<Gumap>();
		temp=gumapSrv.userfroumgroup(userid, groupid);
		if(temp.size()!=0)
		{
			System.out.println("该用户已经报名！"+groupid+" + "+userid);
			jo.put("returncode", "2");
		}
		else
		{
			//---------修改exsitingnum-----------		
			String msg=groupSrv.addextingnum(groupid);
			System.out.println("修改existingnum:"+msg);
			//-------更新gumap表----------
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
			String date=sdf.format(new Date());
			String mapid=String.format("M%sG", date);//id
			Gumap gumap=new Gumap();
			gumap.setMapid(mapid);
			gumap.setUserid(userid);
			gumap.setGroupid(groupid);
			gumap.setIsbuilder(isbuilder);
			gumapSrv.doaddgumap(gumap);
			jo.put("returncode","success");
		}
//-------------set-sysmsg-state-----------
		Sysmsg sysmsg=new Sysmsg();
		sysmsg.setMsgid(sysmsgid);
		sysmsg=sysmsgSrv.doValiad(sysmsg);
		sysmsg.setState("read");
		sysmsgSrv.doUpdate(sysmsg);
//--------response----
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
	public GroupSrv getGroupSrv() {
		return groupSrv;
	}
	public void setGroupSrv(GroupSrv groupSrv) {
		this.groupSrv = groupSrv;
	}
	public GUmapSrv getGumapSrv() {
		return gumapSrv;
	}
	public void setGumapSrv(GUmapSrv gumapSrv) {
		this.gumapSrv = gumapSrv;
	}
}