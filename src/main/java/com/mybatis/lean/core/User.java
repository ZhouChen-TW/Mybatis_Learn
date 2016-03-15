package com.mybatis.lean.core;

import java.util.List;

/**
 * Created by zoe on 3/15/16.
 */
public class User {
    private Integer user_Id;
    private String userName;
    private Icard icard;
    private List<Contact> contacts;
    private List<Group> groups;

    public User(){}

    public User(Integer user_Id,String userName,Icard icard){
        this.user_Id=user_Id;
        this.userName =userName;
        this.icard=icard;
    }

    public Integer getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(Integer user_Id) {
        this.user_Id = user_Id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Icard getIcard() {
        return icard;
    }

    public void setIcard(Icard icard) {
        this.icard = icard;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
