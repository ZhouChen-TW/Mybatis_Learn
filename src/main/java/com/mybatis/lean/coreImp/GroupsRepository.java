package com.mybatis.lean.coreImp;

import com.mybatis.lean.core.Group;
import com.mybatis.lean.core.User;

import java.util.List;

/**
 * Created by zoe on 3/15/16.
 */
public interface GroupsRepository {
    void createGroup(Group group);//insert

    List<Group> findAllGroups();//select

    List<User> selectUsersForGroup(Group group);//select relation

    Group getGroupById(Integer group_Id);//select

    void updateGroup(Group group);//update 级联

    void deleteGroup(Group group);//delete

}
