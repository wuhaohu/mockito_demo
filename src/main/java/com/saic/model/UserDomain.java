package com.saic.model;

public class UserDomain {
    private Integer user_Id;

    private String user_Name;

    private String password;

    private String phone;

    public Integer getUserId() {
        return user_Id;
    }

    public void setUserId(Integer userId) {
        this.user_Id = userId;
    }

    public String getUserName() {
        return user_Name;
    }

    public void setUserName(String userName) {
        this.user_Name = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}