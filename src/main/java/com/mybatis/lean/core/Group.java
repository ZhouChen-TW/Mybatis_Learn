package com.mybatis.lean.core;

import java.util.List;

/**
 * Created by zoe on 3/15/16.
 */
public class Group {
    private Integer group_Id;
    private String groupName;
    private List<User> users;

    public Group(){}

    public Group(Integer group_Id,String groupName){
        this.group_Id =group_Id;
        this.groupName = groupName;
    }

    public Integer getGroup_Id() {
        return group_Id;
    }

    public void setGroup_Id(Integer group_Id) {
        this.group_Id = group_Id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
