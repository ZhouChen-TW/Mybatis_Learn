package com.mybatis.lean.core;

import java.util.List;

/**
 * Created by zoe on 3/15/16.
 */
public class Group {
    private Integer id;
    private String name;
    private List<User> users;

    public Group(){}

    public Group(Integer id,String name){
        this.id =id;
        this.name = name;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
