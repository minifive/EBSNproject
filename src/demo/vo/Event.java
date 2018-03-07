package demo.vo;

import java.sql.Timestamp;

/**
 * Event entity. @author MyEclipse Persistence Tools
 */

public class Event implements java.io.Serializable {

	// Fields

	private String eventId;
	private String eventName;
	private String poster;
	private Timestamp signuptime;
	private Timestamp starttime;
	private Timestamp endtime;
	private String coordinate;
	private String maxnum;
	private String exstnum;
	private String address;
	private String groupid;
	private String state;
	private String introduce;

	// Constructors

	/** default constructor */
	public Event() {
	}

	/** minimal constructor */
	public Event(String eventId) {
		this.eventId = eventId;
	}

	/** full constructor */
	public Event(String eventId, String eventName, String poster,
			Timestamp signuptime, Timestamp starttime, Timestamp endtime,
			String coordinate, String maxnum, String exstnum, String address,
			String groupid, String state, String introduce) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.poster = poster;
		this.signuptime = signuptime;
		this.starttime = starttime;
		this.endtime = endtime;
		this.coordinate = coordinate;
		this.maxnum = maxnum;
		this.exstnum = exstnum;
		this.address = address;
		this.groupid = groupid;
		this.state = state;
		this.introduce = introduce;
	}

	// Property accessors

	public String getEventId() {
		return this.eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getPoster() {
		return this.poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Timestamp getSignuptime() {
		return this.signuptime;
	}

	public void setSignuptime(Timestamp signuptime) {
		this.signuptime = signuptime;
	}

	public Timestamp getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public String getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getMaxnum() {
		return this.maxnum;
	}

	public void setMaxnum(String maxnum) {
		this.maxnum = maxnum;
	}

	public String getExstnum() {
		return this.exstnum;
	}

	public void setExstnum(String exstnum) {
		this.exstnum = exstnum;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}