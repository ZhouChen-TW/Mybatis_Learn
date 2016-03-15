package com.mybatis.lean.coreImp;

import com.mybatis.lean.core.Contact;
import com.mybatis.lean.core.Group;
import com.mybatis.lean.core.User;

import java.util.List;

/**
 * Created by zoe on 3/15/16.
 */
public interface UsersRepository {
    //user:icard = 1:1
    void creatUser(User user);//insert

    List<User> findAllUsers();//select

    User getUserById(Integer userId);//select

    void updateUser(User user);//update  级联待处理

    void deleteUserById(User user);//delete 级联待处理

    //user:contact=1:n
    void createContact(Contact contact,User user);

    List<Contact> selectContactsForUser(User user);

    void updateContact(Contact contact,User user);

    //user:group=n:n
    List<Group> selectGroupsForUser(User user);

    int selectRelations(User user,Group group);

    void createRelations(User user,Group group);

    void deleteRelations(User user,Group group);





}
