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
import demo.vo.Group;
import demo.vo.Gumap;
import demo.vo.Sysmsg;

public class CreatGroupAction extends ActionSupport {

	/**
	 * @return  常见群组，修改 group表
	 */
	private GroupSrv groupSrv;
	private GUmapSrv gumapSrv;
	private SysmsgSrv sysmsgSrv;
	private String groupname;//输入
	private String userid;//输入
	private String memberceiling;//输入    群组成员上限
	private String existmemnum="1";//默认现有成员数为0
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		groupname=request.getParameter("name");
		//groupname="2222";
		memberceiling=request.getParameter("number");
		userid=(String) request.getSession().getAttribute("userid");
		//userid="09012432";
		JSONObject jo=new JSONObject();
		if(userid==null)//如果session中的uid为空 需要重新登录
		{
			System.out.println("ERROR the uid in session is null! greate group action");
			jo.put("returncode", "1");
		}
		else
		{
			Group group=new Group();
			group.setGroupname(groupname);
			System.out.println(" find by groupname+"+groupname);
			List<Group>temp=new LinkedList<Group>();
			temp=groupSrv.FindByGroupname(group);//查询数据库中是否有重名
			if(temp.size()!=0)
			{
				System.out.println("ERROR group name rewrite!");
				jo.put("returncode", "2");//群组名称冲突
			}
			else
			{
				jo.put("returncode", "success");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
				String date=sdf.format(new Date());
				String groupid=String.format("G%s", date);//id
				group.setGroupid(groupid);
				group.setBuilderid(userid);
				group.setMemberceiling(memberceiling);
				group.setExistmemnum(existmemnum);
				group.setLabels("auditing");//当前群组的状态为审核阶段
				groupSrv.dobuildgroup(group);
//-------------------add gumap-----------------------
				String mapid=String.format("M%sG", date);//id
				Gumap gumap=new Gumap();
				gumap.setMapid(mapid);
				gumap.setUserid(userid);
				gumap.setGroupid(groupid);
				gumap.setIsbuilder("yes");
				gumapSrv.addusertogroup(gumap);
				
//-----------发送消息给管理员------
				Timestamp signuptime= new Timestamp(System.currentTimeMillis());
				Sysmsg sysmsg=new Sysmsg();
				String msgid=String.format("msg%s", date);//id
				sysmsg.setMsgid(msgid);
				sysmsg.setUserid("admin");//管理员为收件人
				sysmsg.setType("6");//6为申请创建群组的信息
				sysmsg.setSendid(groupid);
				sysmsg.setState("unread");
				sysmsg.setSendtime(signuptime);
				sysmsg.setContent("用户"+userid+",申请创建群组："+groupname);
				sysmsgSrv.AddSysmsg(sysmsg);
				
			}
		}
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
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

	public SysmsgSrv getSysmsgSrv() {
		return sysmsgSrv;
	}

	public void setSysmsgSrv(SysmsgSrv sysmsgSrv) {
		this.sysmsgSrv = sysmsgSrv;
	}
}