package demo.vo;

import java.sql.Timestamp;

/**
 * Sysmsg entity. @author MyEclipse Persistence Tools
 */

public class Sysmsg implements java.io.Serializable {

	// Fields

	private String msgid;
	private String userid;
	private String type;
	private String content;
	private Timestamp sendtime;
	private String state;
	private String sendid;

	// Constructors

	/** default constructor */
	public Sysmsg() {
	}

	/** minimal constructor */
	public Sysmsg(String msgid) {
		this.msgid = msgid;
	}

	/** full constructor */
	public Sysmsg(String msgid, String userid, String type, String content,
			Timestamp sendtime, String state, String sendid) {
		this.msgid = msgid;
		this.userid = userid;
		this.type = type;
		this.content = content;
		this.sendtime = sendtime;
		this.state = state;
		this.sendid = sendid;
	}

	// Property accessors

	public String getMsgid() {
		return this.msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSendid() {
		return this.sendid;
	}

	public void setSendid(String sendid) {
		this.sendid = sendid;
	}

}