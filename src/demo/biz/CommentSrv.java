package demo.biz;

import java.util.List;

import demo.vo.Comment;



public interface CommentSrv {
	
	List<Comment> FindByeventid(String eventid);
	
	void savecomment(Comment comment);
	
	void dodelete(Comment comment);
} 
