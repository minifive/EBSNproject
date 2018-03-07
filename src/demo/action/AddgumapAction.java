package demo.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.GUmapSrv;
import demo.biz.GroupSrv;
import demo.vo.Gumap;

public class AddgumapAction extends ActionSupport {

	/**
	 * @return 为gumap添加成员，当一个用户确认加入某个群组时
	 */
	private GUmapSrv gumapSrv;
	private GroupSrv groupSrv;
	private String userid;//输入
	private String groupid;//输入
	public String execute() {
		// TODO Auto-generated method stub
		userid="09012433";//test
		groupid="1";//test
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
		String date=sdf.format(new Date());
		String gumapid=String.format("M%sG", date);//id
		
		Gumap gumap=new Gumap();
		gumap.setMapid(gumapid);
		gumap.setGroupid(groupid);
		gumap.setUserid(userid);
		gumap.setIsbuilder("no");
		gumapSrv.doaddgumap(gumap);//更新gumap表
		String msg=groupSrv.addextingnum(groupid);//更新group表，existing user number+1
		System.out.println("修改existingnum:"+msg);
		
		
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