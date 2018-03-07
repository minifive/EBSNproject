package demo.biz.impl;

import java.util.LinkedList;
import java.util.List;

import demo.biz.CommentSrv;
import demo.dao.CommentDAO;
import demo.vo.Comment;



public class CommentSrvImpl implements CommentSrv{
	
	private CommentDAO commentDAO;

	public CommentDAO getCommentDAO() {
		return commentDAO;
	}

	public void setCommentDAO(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> FindByeventid(String eventid) {
		// TODO Auto-generated method stub
		List<Comment> temp=new LinkedList<Comment>();
		temp=commentDAO.findByEventid(eventid);
		return temp;
	}

	@Override
	public void savecomment(Comment comment) {
		commentDAO.save(comment);
	}

	@Override
	public void dodelete(Comment comment) {
	
		commentDAO.delete(comment);
		
	}

}