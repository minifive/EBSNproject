package demo.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.CommentSrv;
import demo.vo.Comment;


@SuppressWarnings("serial")
public class AddcommentAction extends ActionSupport {

	/**
	 * @return event.html 发表评论
	 */
	private CommentSrv commentSrv;
	private String eventid;//in
	private String userid;//in
	private String content;//in
	public String execute() throws IOException {
		// TODO Auto-generated method stub
//		--------add to DataBase
		HttpServletRequest request= ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("UTF-8");
		eventid=request.getParameter("ID");
		content=request.getParameter("comment");
		userid=(String) request.getSession().getAttribute("userid");
		content=new String(content.getBytes("8859_1"),"utf-8");//转换编码格式
		System.out.println(content+"-----");
		JSONObject jo=new JSONObject();
		if(userid==null)
		{
			jo.put("returncode", "1");
			response.getWriter().print(jo.toString());
			response.flushBuffer();
			return SUCCESS;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
		String date=sdf.format(new Date());
		String commentid=String.format("C%s", date);//id
		
		Comment comment=new Comment();
		comment.setCommentid(commentid);
		comment.setEventid(eventid);
		comment.setUserid(userid);
		comment.setContent(content);
		Timestamp intime= new Timestamp(System.currentTimeMillis());//当前时间
		comment.setPublishtime(intime);
		commentSrv.savecomment(comment);
		jo.put("returncode", "success");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}

	public CommentSrv getCommentSrv() {
		return commentSrv;
	}

	public void setCommentSrv(CommentSrv commentSrv) {
		this.commentSrv = commentSrv;
	}

}