package demo.vo;

/**
 * Gumap entity. @author MyEclipse Persistence Tools
 */

public class Gumap implements java.io.Serializable {

	// Fields

	private String mapid;
	private String groupid;
	private String userid;
	private String isbuilder;

	// Constructors

	/** default constructor */
	public Gumap() {
	}

	/** minimal constructor */
	public Gumap(String mapid) {
		this.mapid = mapid;
	}

	/** full constructor */
	public Gumap(String mapid, String groupid, String userid, String isbuilder) {
		this.mapid = mapid;
		this.groupid = groupid;
		this.userid = userid;
		this.isbuilder = isbuilder;
	}

	// Property accessors

	public String getMapid() {
		return this.mapid;
	}

	public void setMapid(String mapid) {
		this.mapid = mapid;
	}

	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
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

}