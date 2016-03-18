package com.mybatis.lean.core;

/**
 * Created by zoe on 3/15/16.
 */
public class Contact {
    private Integer id;
    private String type;
    private String value;

    public Contact(){}

    public Contact(Integer id,String type,String value){
        this.id=id;
        this.type = type;
        this.value=value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
