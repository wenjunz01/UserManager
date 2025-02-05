package com.example.entity;

import java.io.Serializable;

/**
 * 第三方
*/
public class Tripartite implements Serializable {
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;
    /** 第三方名称 */
    private String name;
    /** 第三方地址 */
    private String address;
    /** 第三方电话 */
    private String tel;
    /** 第三方邮箱 */
    private String email;
    /** 邮编 */
    private String zipCode;
    /** 联系人 */
    private String user;
    /** 联系人电话 */
    private String phone;
    /** 开户银行 */
    private String bank;
    /** 开户行账号 */
    private String bankCard;
    /** 状态 */
    private String status;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}