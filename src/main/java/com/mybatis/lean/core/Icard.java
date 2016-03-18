package com.mybatis.lean.core;

/**
 * Created by zoe on 3/15/16.
 */
public class Icard {
    private Integer id;
    private String icardMsg;

    public Icard(){}

    public Icard(Integer id,String icardMsg){
        this.id=id;
        this.icardMsg =icardMsg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcardMsg() {
        return icardMsg;
    }

    public void setIcardMsg(String icardMsg) {
        this.icardMsg = icardMsg;
    }
}
