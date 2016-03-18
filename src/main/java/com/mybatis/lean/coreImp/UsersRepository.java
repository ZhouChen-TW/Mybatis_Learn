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

    void createIcard(User user);//insert relation

    List<User> findAllUsers();//select

    User getUserById(Integer userId);//select

    void updateIcard(User user);//update relation

    void updateUser(User user);//update  级联

    void deleteUser(User user);//delete 级联

    void deleteUserExpectException(User user);

    //user:contact=1:n
    void createContact(Contact contact,User user);//insert relation

    List<Contact> selectContactsForUser(User user);//select relation

    void updateContact(Contact contact,User user);//update relation

    void updateAllContactsForUser(User user);//update Casede

    //user:group=n:n
    void updateAllGroupsForUser(User user);//update Casede

    List<Group> selectGroupsForUser(User user);

    int selectRelations(User user,Group group);

    void createRelations(User user,Group group);

    void deleteRelations(User user,Group group);

}
