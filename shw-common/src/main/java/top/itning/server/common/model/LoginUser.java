package top.itning.server.common.model;

import java.io.Serializable;

/**
 * 登陆用户
 */
public class LoginUser implements Serializable {
    /**
     * 学生用户
     */
    public static final String STUDENT_USER = "99";
    /**
     * 教师用户
     * 不准确
     */
    @Deprecated
    public static final String TEACHER_USER = "13";


    /**
     * id : ID
     */
    private String id;
    /**
     * no : 学号或教师编号
     */
    private String no;
    /**
     * loginName : 身份证号码或登录名
     */
    private String loginName;
    /**
     * userType : 99(学生) 13(教师)
     */
    private String userType;
    /**
     * name : 姓名
     */
    private String name;
    /**
     * phone :
     */
    private String phone;
    /**
     * email :
     */
    private String email;
    /**
     * mobile :
     */
    private String mobile;
    /**
     * loginIp : 登陆IP
     */
    private String loginIp;
    /**
     * remarks : uninitialized
     */
    private String remarks;
    /**
     * roleId  :
     */
    private String roleId;
    /**
     * dormId :
     */
    private String dormId;
    /**
     * clazzId :
     */
    private String clazzId;
    /**
     * companyId :
     */
    private String companyId;
    /**
     * officeId :
     */
    private String officeId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDormId() {
        return dormId;
    }

    public void setDormId(String dormId) {
        this.dormId = dormId;
    }

    public String getClazzId() {
        return clazzId;
    }

    public void setClazzId(String clazzId) {
        this.clazzId = clazzId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "id='" + id + '\'' +
                ", no='" + no + '\'' +
                ", loginName='" + loginName + '\'' +
                ", userType='" + userType + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", remarks='" + remarks + '\'' +
                ", roleId='" + roleId + '\'' +
                ", dormId='" + dormId + '\'' +
                ", clazzId='" + clazzId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", officeId='" + officeId + '\'' +
                '}';
    }
}
