package demo.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.GUmapSrv;
import demo.biz.GroupSrv;
import demo.biz.UserSrv;
import demo.vo.Gumap;
import demo.vo.User;

public class DelgumapAction extends ActionSupport {

	/**
	 * @return  delete a member from group  修改gumap
	 */
	private GUmapSrv gumapSrv;
	private GroupSrv groupSrv;
	private UserSrv userSrv;
	
	private String userid;//输入
	private String groupid;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		userid=(String) request.getSession().getAttribute("userid");
		groupid=request.getParameter("ID");
		JSONObject jo=new JSONObject();
		if(userid==null)
		{
			jo.put("returncode", "1");
			response.getWriter().print(jo.toString());
			response.flushBuffer();
			return SUCCESS;			
		}
		User user=new User();
		user.setUserid(userid);
		user=userSrv.doValidate(user);
		int gumapsize=gumapSrv.deletefromgroup(userid, groupid);
		if(gumapsize==0)
		{
			System.out.println("删除失败，该群组中没有此人。DelgumapAction");
			return ERROR;
		}

		/*修改group中的exitingusernum属性，已有成员数减一*/
		String msg=groupSrv.delextingnum(groupid,gumapsize);		
		System.out.println(gumapsize+" msg:"+msg);
		jo.put("returncode", "success");
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
	public UserSrv getUserSrv() {
		return userSrv;
	}
	public void setUserSrv(UserSrv userSrv) {
		this.userSrv = userSrv;
	}
}