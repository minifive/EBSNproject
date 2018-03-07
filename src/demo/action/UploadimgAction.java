package demo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import demo.biz.UserSrv;
import demo.vo.User;

@SuppressWarnings("serial")
public class UploadimgAction extends ActionSupport{
	
	private UserSrv usersrv;
	private User user=new User();
	private List<File>userHead;
	private String filename;

	//	private static final String CONTENT_TYPE     = "text/html; charset=utf-8";
	HttpServletRequest request;
	HttpServletResponse response;
	public String execute() throws IOException {
		request = ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		filename=new String();
		JSONObject jo = new JSONObject();
		String uid=(String)request.getSession().getAttribute("userid");//session中的id保存三十分钟
		System.out.println("''uid:'''"+uid);
		if(uid==null)
		{
			System.out.println("returncode=uiderror");
			jo.put("returncode", "uiderror");
			jo.put("headimg", filename);
		}
		else
		{
			System.out.println("12345");
			System.out.println(userHead.size());
			for(int i=0;i<userHead.size();i++)
			{
				uploadfile(i);//上传文件
			}
			user.setUserid(uid);
			User u=usersrv.doValidate(user);//获取id以外的其他信息（用户名等）
			u.setHeadport(filename);//修改当前用户的头像，保存上传的头像文件名
			usersrv.doupdate(u);//更新用户信息
			jo.put("returncode", "success");
			jo.put("headimg", u.getHeadport());
		}
		System.out.println("''''''");
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
	private void uploadfile(int i) throws IOException{
		System.out.println("----kaishiuploadimg----");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
		String date=sdf.format(new Date());
		filename=String.format("avator%s.jpg", date);//以日期为文件名
		String loadpath = request.getSession().getServletContext().getRealPath("/")+"img/avator";
		
		try {
			InputStream in=new FileInputStream(userHead.get(i));//通过IOstream来按byte流传文件
			File uploadFile=new File(loadpath,filename);
			OutputStream out=new FileOutputStream(uploadFile);
			byte[] buffer=new byte[1024*1024];
			int length;
			while((length=in.read(buffer))>0)
			{
				out.write(buffer,0,length);
				System.out.println("filename:"+filename+",path:"+loadpath);
			}
			in.close();
			out.close();		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public UserSrv getUsersrv() {
		return usersrv;
	}

	public void setUsersrv(UserSrv usersrv) {
		this.usersrv = usersrv;
	}
	public List<File> getUserHead() {
		return userHead;
	}
	public void setUserHead(List<File> userHead) {
		this.userHead = userHead;
	}
	
}