package com.mybatis.lean.core;

import java.util.List;

/**
 * Created by zoe on 3/15/16.
 */
public class User {
    private Integer id;
    private String name;
    private Icard icard;
    private List<Contact> contacts;
    private List<Group> groups;

    public User(){}

    public User(Integer id,String name,Icard icard){
        this.id=id;
        this.name =name;
        this.icard=icard;
    }

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
