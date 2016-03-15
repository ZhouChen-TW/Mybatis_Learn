package com.mybatis.lean.core;

/**
 * Created by zoe on 3/15/16.
 */
public class Contact {
    private Integer contact_Id;
    private String type;
    private String value;

    public Contact(){}

    public Contact(Integer contact_Id,String type,String value){
        this.contact_Id=contact_Id;
        this.type = type;
        this.value=value;
    }

    public Integer getContact_Id() {
        return contact_Id;
    }

    public void setContact_Id(Integer contact_Id) {
        this.contact_Id = contact_Id;
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
