package com.example.entity;

import java.io.Serializable;

public class Clue  implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** 用户名称 */
    private String name;
    /** 用户电话 */
    private String tel;
    /** 联系人 */
    private String user;
    /** 部门 */
    private String department;
    /** 职务 */
    private String duties;
    /** 线索来源 */
    private String sources;
    /** 线索详情 */
    private String cdetails;
    /** 线索状态 */
    private String status;
    /** 用户来源 */
    private String source;
    /** 所属行业 */
    private String industry;
    /** 用户详情 */
    private String details;
    /** 回退原因 */
    private String fallback;
    /** 邮箱 */
    private String email;
    /** 用户地址 */
    private String address;
    /** 用户级别 */
    private String level;
    /** 联系人电话 */
    private String phone;







    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getCdetails() {
        return cdetails;
    }

    public void setCdetails(String cdetails) {
        this.cdetails = cdetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    public String getFallback() {
        return fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
