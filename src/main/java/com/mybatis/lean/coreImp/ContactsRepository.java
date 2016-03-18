package com.mybatis.lean.coreImp;

import com.mybatis.lean.core.Contact;

import java.util.List;

/**
 * Created by zoe on 3/15/16.
 */
public interface ContactsRepository {

    void createContact(Contact contact);//insert

    List<Contact> findAllContacts();//select

    Contact getContactById(Integer contact_Id);//select

    void updateContact(Contact contact);//update

    void deleteContact(Contact contact);//delete
}
