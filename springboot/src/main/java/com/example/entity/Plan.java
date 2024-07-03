package com.example.entity;

import java.io.Serializable;

public class Plan extends Account implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** 公海用户ID */
    private String publiccustomerId;
    /** 计划内容 */
    private String content;
    /** 执行人 */
    private String executor;
    /** 计划时间 */
    private String time;
    /** 计划状态 */
    private String status;
    /** 用户名称 */
    private String publiccustomerName;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPubliccustomerId() {
        return publiccustomerId;
    }

    public void setPubliccustomerId(String publiccustomerId) {
        this.publiccustomerId = publiccustomerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPubliccustomerName() {
        return publiccustomerName;
    }

    public void setPubliccustomerName(String publiccustomerName) {
        this.publiccustomerName = publiccustomerName;
    }
}
