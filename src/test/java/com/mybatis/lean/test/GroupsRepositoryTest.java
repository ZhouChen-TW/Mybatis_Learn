package com.mybatis.lean.test;

import com.mybatis.lean.core.Group;
import com.mybatis.lean.core.User;
import com.mybatis.lean.coreImp.GroupsRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class GroupsRepositoryTest {
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private GroupsRepository groupsRepository;

    @Before
    public void setUp() throws IOException, SQLException {
        String resource = "mybatis-config.xml";
        
        Reader reader  = Resources.getResourceAsReader(resource);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "test");

        session = sqlSessionFactory.openSession();
        session.getConnection().setAutoCommit(false);
        groupsRepository = session.getMapper(GroupsRepository.class);

    }
    
    @After
    public void tearDown(){
        session.rollback();
        session.close();
        
    }

    @Test
    public void find_All_Groups(){
        List<Group> groups =groupsRepository.findAllGroups();
        assertThat(groups.size(),is(2));
        assertThat(groups.get(0).getGroupName(),is("小组1"));
        assertThat(groups.get(1).getGroupName(),is("小组2"));
    }

    @Test
    public void select_Users_For_Group(){
        Group group =groupsRepository.getGroupById(1);
        List<User> users =groupsRepository.selectUsersForGroup(group);
        assertThat(users.size(),is(1));
        assertThat(users.get(0).getUserName(),is("Jerry"));
    }

    @Test
    public void get_Group_By_Id(){
        Group group =groupsRepository.getGroupById(1);
        assertThat(group.getGroup_Id(),is(1));
        assertThat(group.getGroupName(),is("小组1"));
    }

    @Test
    public void create_Group(){
        Group group = new Group(3,"小组3");
        groupsRepository.createGroup(group);
        List<Group> groups =groupsRepository.findAllGroups();
        assertThat(groups.size(),is(3));
        assertThat(groups.get(0).getGroupName(),is("小组1"));
        assertThat(groups.get(1).getGroupName(),is("小组2"));
        assertThat(groups.get(2).getGroupName(),is("小组3"));
    }

    @Test
    public void update_Group(){
        Group group =groupsRepository.getGroupById(1);
        group.setGroupName("小组3");
        groupsRepository.updateGroup(group);
        Group thegroup =groupsRepository.getGroupById(1);
        assertThat(thegroup.getGroup_Id(),is(1));
        assertThat(thegroup.getGroupName(),is("小组3"));
    }

    @Test
    public void delete_Group(){
        Group group =groupsRepository.getGroupById(1);
        groupsRepository.deleteGroup(group);
        List<Group> groups =groupsRepository.findAllGroups();
        assertThat(groups.size(),is(1));
        assertThat(groups.get(0).getGroupName(),is("小组2"));

    }

}
