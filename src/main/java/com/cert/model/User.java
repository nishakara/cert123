package com.cert.model;


public class User {

    private String userName;

    private String password;

    private String groupId;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
