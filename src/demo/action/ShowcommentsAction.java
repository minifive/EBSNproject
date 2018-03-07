package demo.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.CommentSrv;
import demo.biz.UserSrv;
import demo.vo.Comment;
import demo.vo.User;

public class ShowcommentsAction extends ActionSupport {

	/**
	 * @return  在活动详情页显示用户评论
	 */
	private CommentSrv commentSrv;
	private UserSrv userSrv;
	private String eventid;//需传递过来eventid
	private List<commentsitem> Items1;//传入前段 采用迭代器显示
	
	
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		eventid="1";//test
		HttpServletResponse response=ServletActionContext.getResponse();
		List<Comment>temp=commentSrv.FindByeventid(eventid);//第一次访问数据库，获取到eventid相关的评论
		List<commentsitem> returntemp=new LinkedList<commentsitem>();
		for(int i=0;i<temp.size();i++)
		{
			commentsitem item=new commentsitem();
			item.content=temp.get(i).getContent();
			item.publishtime=temp.get(i).getPublishtime();
			User user=new User();
			user.setUserid(temp.get(i).getUserid());
			user=userSrv.doValidate(user);  //第二次访问数据库，根据第一次访问到的userid获取user相关信息；
			item.username=user.getUsername();
			item.headport=user.getHeadport();
			returntemp.add(item);
		}
		setItems1(returntemp);
		
		JSONArray jsonarray=JSONArray.fromObject(Items1);
		response.getWriter().print(jsonarray.toString());
		response.flushBuffer();
		
/////////for test		
		for(int j=0;j<Items1.size();j++)
		{
			System.out.println(Items1.get(j).username+"-"+Items1.get(j).content);
		}
///////
		return SUCCESS;
	}
	public CommentSrv getCommentSrv() {
		return commentSrv;
	}
	public void setCommentSrv(CommentSrv commentSrv) {
		this.commentSrv = commentSrv;
	}
	
	public List<commentsitem> getItems1() {
		return Items1;
	}
	public void setItems1(List<commentsitem> items1) {
		Items1 = items1;
	}

	public UserSrv getUserSrv() {
		return userSrv;
	}
	public void setUserSrv(UserSrv userSrv) {
		this.userSrv = userSrv;
	}

	private class commentsitem
	{
		private String username;
		private String headport;
		private String content;
		private Timestamp publishtime;
	}
}