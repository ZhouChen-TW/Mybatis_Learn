package com.mybatis.lean.core;

/**
 * Created by zoe on 3/15/16.
 */
public class Icard {
    private Integer icard_Id;
    private String icardMsg;

    public Icard(){}

    public Icard(Integer icard_Id,String icardMsg){
        this.icard_Id=icard_Id;
        this.icardMsg =icardMsg;
    }

    public Integer getIcard_Id() {
        return icard_Id;
    }

    public void setIcard_Id(Integer icard_Id) {
        this.icard_Id = icard_Id;
    }

    public String getIcardMsg() {
        return icardMsg;
    }

    public void setIcardMsg(String icardMsg) {
        this.icardMsg = icardMsg;
    }
}
