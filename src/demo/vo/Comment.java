package demo.vo;

import java.sql.Timestamp;

/**
 * Comment entity. @author MyEclipse Persistence Tools
 */

public class Comment implements java.io.Serializable {

	// Fields

	private String commentid;
	private String userid;
	private Timestamp publishtime;
	private String content;
	private String eventid;
	private String picture;

	// Constructors

	/** default constructor */
	public Comment() {
	}

	/** minimal constructor */
	public Comment(String commentid) {
		this.commentid = commentid;
	}

	/** full constructor */
	public Comment(String commentid, String userid, Timestamp publishtime,
			String content, String eventid, String picture) {
		this.commentid = commentid;
		this.userid = userid;
		this.publishtime = publishtime;
		this.content = content;
		this.eventid = eventid;
		this.picture = picture;
	}

	// Property accessors

	public String getCommentid() {
		return this.commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Timestamp getPublishtime() {
		return this.publishtime;
	}

	public void setPublishtime(Timestamp publishtime) {
		this.publishtime = publishtime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEventid() {
		return this.eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}