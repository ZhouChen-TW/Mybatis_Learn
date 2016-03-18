package com.mybatis.lean.coreImp;

import com.mybatis.lean.core.Icard;

import java.util.List;

/**
 * Created by zoe on 3/15/16.
 */
public interface IcardsRepository {
    void createIcard(Icard icard);//insert

    List<Icard> findAllIcard();//select

    Icard getIcardById(Integer icard_Id);//select

    void updateIcard(Icard icard);//update

    void deleteIcard(Icard icard);//delete
}
