package com.example.entity;

import java.io.Serializable;

public class PublicCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** 用户名称 */
    private String name;
    /** 用户电话 */
    private String tel;
    /** 用户邮箱 */
    private String email;
    /** 用户地址 */
    private String address;
    /** 联系人 */
    private String user;
    /** 联系人电话 */
    private String phone;
    /** 用户状态 */
    private String status;
    /** 用户级别 */
    private String level;
    /** 用户来源 */
    private String source;
    /** 所属行业 */
    private String industry;
    /** 用户详情 */
    private String details;





    /** 跟进记录 */
    private String record;
    /** 跟进主体 */
    private String flname;
    /** 跟进商机 */
    private String business;
    /**  联系人 */
    private String contacts;
    /** 跟进方式 */
    private String way;
    /** 跟进内容 */
    private String content;
    /** 跟进时间 */
    private String time;
    /** 跟进人 */
    private String responsible;


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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getFlname() {
        return flname;
    }

    public void setFlname(String flname) {
        this.flname = flname;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
