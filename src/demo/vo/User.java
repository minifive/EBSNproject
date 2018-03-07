package demo.vo;



/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {


    // Fields    

     private String userid;
     private String username;
     private String password;
     private String phone;
     private String department;
     private String role;
     private String headport;


    // Constructors

    /** default constructor */
    public User() {
    }

	/** minimal constructor */
    public User(String userid) {
        this.userid = userid;
    }
    
    /** full constructor */
    public User(String userid, String username, String password, String phone, String department, String role, String headport) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.department = department;
        this.role = role;
        this.headport = headport;
    }

   
    // Property accessors

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public String getHeadport() {
        return this.headport;
    }
    
    public void setHeadport(String headport) {
        this.headport = headport;
    }
   








}