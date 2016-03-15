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

    List<User> selectUsersForGroup(Group group);//select

    Group getGroupById(Integer group_Id);//select

    void updateGroup(Group group);//update 级联待处理

    void deleteGroup(Group group);//delete 级联待处理

}
