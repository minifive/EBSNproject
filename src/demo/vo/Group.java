package demo.vo;

/**
 * Group entity. @author MyEclipse Persistence Tools
 */

public class Group implements java.io.Serializable {

	// Fields

	private String groupid;
	private String groupname;
	private String builderid;
	private String memberceiling;
	private String existmemnum;
	private String labels;

	// Constructors

	/** default constructor */
	public Group() {
	}

	/** minimal constructor */
	public Group(String groupid) {
		this.groupid = groupid;
	}

	/** full constructor */
	public Group(String groupid, String groupname, String builderid,
			String memberceiling, String existmemnum, String labels) {
		this.groupid = groupid;
		this.groupname = groupname;
		this.builderid = builderid;
		this.memberceiling = memberceiling;
		this.existmemnum = existmemnum;
		this.labels = labels;
	}

	// Property accessors

	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getBuilderid() {
		return this.builderid;
	}

	public void setBuilderid(String builderid) {
		this.builderid = builderid;
	}

	public String getMemberceiling() {
		return this.memberceiling;
	}

	public void setMemberceiling(String memberceiling) {
		this.memberceiling = memberceiling;
	}

	public String getExistmemnum() {
		return this.existmemnum;
	}

	public void setExistmemnum(String existmemnum) {
		this.existmemnum = existmemnum;
	}

	public String getLabels() {
		return this.labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

}