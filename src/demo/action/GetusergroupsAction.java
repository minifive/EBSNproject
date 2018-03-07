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

import demo.biz.GUmapSrv;
import demo.biz.GroupSrv;
import demo.vo.Group;
import demo.vo.Gumap;
import demo.vo.Mygroup;

@SuppressWarnings("serial")
public class GetusergroupsAction extends ActionSupport {

	/**
	 * @return  creat-event.html 限制群组     display-group  我参与的群组
	 */
	private GUmapSrv gumapSrv;
	private GroupSrv groupSrv;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String userid=(String)request.getSession().getAttribute("userid");
		JSONObject jo=new JSONObject();
		System.out.println(userid);
		if(userid==null)
		{
			jo.put("returncode", "1");
		}
		else{
			List<Gumap> temp=new LinkedList<Gumap>();
			List<Mygroup> groups=new LinkedList<Mygroup>();
			
			System.out.println(temp.size()+" - "+userid);
			if(userid.equals("admin"))
			{
				temp=gumapSrv.findall();//如果过是管理员 返回所有活动
				jo.put("returncode","3");
			}
			else
			{
				temp=gumapSrv.allgroupsforuser(userid);
			}
			if(temp.size()==0)
			{
				System.out.println("该用户没有加入群组");
				jo.put("returncode", "2");
			}
			else
			{
				int auditing=0;//记录当前正在审核的群组个数
				for(int i=0;i<temp.size();i++)
				{
					Group group=new Group();
					group.setGroupid(temp.get(i).getGroupid());
					group=groupSrv.dovalidate(group);
					if(group.getLabels().equals("audited"))//只显示审核通过的群组
					{
						Mygroup mygroup=new Mygroup();
						mygroup.setGroupid(temp.get(i).getGroupid());
						mygroup.setGroupname(group.getGroupname());
						mygroup.setGroupnum(group.getMemberceiling());
						mygroup.setExtnum(group.getExistmemnum());
						groups.add(mygroup);
					}
					else
					{
						auditing++;
					}
				}
				jo.put("auditing",auditing);
				jo.put("returncode", "success");
				jo.put("group", groups);
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		response.flushBuffer();
	
		return SUCCESS;
	}
	public GUmapSrv getGumapSrv() {
		return gumapSrv;
	}
	public void setGumapSrv(GUmapSrv gumapSrv) {
		this.gumapSrv = gumapSrv;
	}
	public GroupSrv getGroupSrv() {
		return groupSrv;
	}
	public void setGroupSrv(GroupSrv groupSrv) {
		this.groupSrv = groupSrv;
	}

}