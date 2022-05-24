package com.rishabh.trelloclone.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "loginuser")
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loginserialnumber")
    private int loginSerialNo;   //PK
    @Column(name = "secretkey")
    private String secretKey;
    @Column(name = "createkeytime")
    private Date KeyCreateTime;
    @Column(name = "expirekeytime")
    private Date KeyExpireTime;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_userid",nullable = false)
    private User user_userID;       //FK


    public int getLoginSerialNo() {
        return loginSerialNo;
    }

    public void setLoginSerialNo(int loginSerialNo) {
        this.loginSerialNo = loginSerialNo;
    }

    public User getUserID() {
        return user_userID;
    }

    public void setUserID(User user_userID) {
        this.user_userID = user_userID;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Date getKeyCreateTime() {
        return KeyCreateTime;
    }

    public void setKeyCreateTime(Date keyCreateTime) {
        KeyCreateTime = keyCreateTime;
    }

    public Date getKeyExpireTime() {
        return KeyExpireTime;
    }

    public void setKeyExpireTime(Date keyExpireTime) {
        KeyExpireTime = keyExpireTime;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "loginSerialNo=" + loginSerialNo +
                ", userID=" + user_userID +
                ", secretKey='" + secretKey + '\'' +
                ", KeyCreateTime=" + KeyCreateTime +
                ", KeyExpireTime=" + KeyExpireTime +
                '}';
    }
}
