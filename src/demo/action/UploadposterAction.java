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

@SuppressWarnings("serial")
public class UploadposterAction extends ActionSupport {

	/**
	 * @return creat-event.html 上传海报
	 */
	private List<File>pic;
	private String filename;
	HttpServletRequest request;
	HttpServletResponse response;
	public String execute() throws IOException {
		// TODO Auto-generated method stub
		request= ServletActionContext.getRequest();
		response=ServletActionContext.getResponse();
		filename=new String();
		JSONObject jo=new JSONObject();
		for(int i=0;i<pic.size();i++)
		{
			uploadfile(i);
		}
		jo.put("imgname", filename);
		response.getWriter().print(jo.toString());
		response.flushBuffer();
		return SUCCESS;
	}
	private void uploadfile(int i) throws IOException{
		System.out.println("----kaishiuploadimg----");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
		String date=sdf.format(new Date());
		filename=String.format("poster%s.jpg", date);//以日期为文件名
		String loadpath = request.getSession().getServletContext().getRealPath("/")+"img/poster";
		
		try {
			InputStream in=new FileInputStream(pic.get(i));//通过IOstream来按byte流传文件
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
	public List<File> getPic() {
		return pic;
	}
	public void setPic(List<File> pic) {
		this.pic = pic;
	}

}