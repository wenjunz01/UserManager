package com.example.entity;

import java.io.Serializable;

/**
 * 合同
 */
public class Contract implements Serializable {

    /**
     * ID
     */
    private Integer id;
    /**
     * 合同标题
     */
    private String title;
    /**
     * 用户ID
     */
    private Integer customerId;
    /**
     * 第三方ID
     */
    private Integer tripartiteId;
    /**
     * 商机信息
     */
    private String business;

    /**
     * 合同签订日期
     */
    private String signingdate;
    /**
     * 合同到期日期
     */
    private String dusdate;
    /**
     * 负责人
     */
    private String cname;
    /**
     * 负责人电话
     */
    private String phone;
    /**
     * 用户地址
     */
    private String address;
    /**
     * 合同状态
     */
    private String status;
    /**
     * 用户名称
     */
    private String customerName;
    /**
     * 第三方名称
     */
    private String tripartiteName;
    /**
     * 合同金额
     */
    private String price;
    /**
     * 合同金额大写
     */
    private String money;
    /**
     * 合同附件
     */
    private String annex;
    /**
     * 未通过审核原因
     */
    private String rejectReason;
    /**
     * 合同编号
     *
     */
    private String generated;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getTripartiteId() {
        return tripartiteId;
    }

    public void setTripartiteId(Integer tripartiteId) {
        this.tripartiteId = tripartiteId;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSigningdate() {
        return signingdate;
    }

    public void setSigningdate(String signingdate) {
        this.signingdate = signingdate;
    }

    public String getDusdate() {
        return dusdate;
    }

    public void setDusdate(String dusdate) {
        this.dusdate = dusdate;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTripartiteName() {
        return tripartiteName;
    }

    public void setTripartiteName(String tripartiteName) {
        this.tripartiteName = tripartiteName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getGenerated() {
        return generated;
    }

    public void setGenerated(String generated) {
        this.generated = generated;
    }
}
