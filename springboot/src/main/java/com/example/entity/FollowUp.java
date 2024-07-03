package com.example.entity;

import java.io.Serializable;

/**
 * 跟进记录
 */
public class FollowUp implements Serializable {
    /** ID */
    private Integer id;
    /** 跟进记录 */
    private String record;
    /** 跟进主体 */
    private String flname;
    /** 跟进商机 */
    private String business;
    /** 跟进方式 */
    private String way;
    /**  联系人 */
    private String contacts;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
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
}
