package demo.vo;

import java.sql.Timestamp;

/**
 * Eumap entity. @author MyEclipse Persistence Tools
 */

public class Eumap implements java.io.Serializable {

	// Fields

	private String mapid;
	private String eventid;
	private String userid;
	private String isbuilder;
	private Timestamp intime;

	// Constructors

	/** default constructor */
	public Eumap() {
	}

	/** minimal constructor */
	public Eumap(String mapid) {
		this.mapid = mapid;
	}

	/** full constructor */
	public Eumap(String mapid, String eventid, String userid, String isbuilder,
			Timestamp intime) {
		this.mapid = mapid;
		this.eventid = eventid;
		this.userid = userid;
		this.isbuilder = isbuilder;
		this.intime = intime;
	}

	// Property accessors

	public String getMapid() {
		return this.mapid;
	}

	public void setMapid(String mapid) {
		this.mapid = mapid;
	}

	public String getEventid() {
		return this.eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getIsbuilder() {
		return this.isbuilder;
	}

	public void setIsbuilder(String isbuilder) {
		this.isbuilder = isbuilder;
	}

	public Timestamp getIntime() {
		return this.intime;
	}

	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}

}