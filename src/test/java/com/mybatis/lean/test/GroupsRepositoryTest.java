package com.mybatis.lean.test;

import com.mybatis.lean.core.Group;
import com.mybatis.lean.core.User;
import com.mybatis.lean.coreImp.GroupsRepository;
import com.mybatis.lean.coreImp.UsersRepository;
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
import java.util.List;

import static org.hamcrest.core.Is.is;;
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
        assertThat(groups.get(0).getName(),is("小组1"));
        assertThat(groups.get(1).getName(),is("小组2"));
    }

    @Test
    public void select_Users_For_Group(){
        Group group =groupsRepository.getGroupById(1);
        List<User> users =groupsRepository.selectUsersForGroup(group);
        assertThat(users.size(),is(2));
        assertThat(users.get(0).getName(),is("Jerry"));
        assertThat(users.get(1).getName(),is("Tom"));
    }

    @Test
    public void get_Group_By_Id(){
        Group group =groupsRepository.getGroupById(1);
        assertThat(group.getId(),is(1));
        assertThat(group.getName(),is("小组1"));
    }

    @Test
    public void create_Group(){
        Group group = new Group(3,"小组3");
        groupsRepository.createGroup(group);
        List<Group> groups =groupsRepository.findAllGroups();
        assertThat(groups.size(),is(3));
        assertThat(groups.get(0).getName(),is("小组1"));
        assertThat(groups.get(1).getName(),is("小组2"));
        assertThat(groups.get(2).getName(),is("小组3"));
    }

    @Test
    public void update_Group_Casede(){
        Group group =groupsRepository.getGroupById(1);
        group.setName("小组3");
        List<User> users =group.getUsers();
        users.get(0).setName("one");
        users.get(1).setName("two");
        groupsRepository.updateGroup(group);
        Group thegroup =groupsRepository.getGroupById(1);
        assertThat(thegroup.getId(),is(1));
        assertThat(thegroup.getName(),is("小组3"));
        List<User> theUser = thegroup.getUsers();
        assertThat(theUser.get(0).getName(),is("one"));
        assertThat(theUser.get(1).getName(),is("two"));
    }

    @Test
    public void delete_Group_Casede(){
        Group group =groupsRepository.getGroupById(1);
        groupsRepository.deleteGroup(group);
        List<Group> groups =groupsRepository.findAllGroups();
        assertThat(groups.size(),is(1));
        assertThat(groups.get(0).getName(),is("小组2"));
        List<User> users =groupsRepository.selectUsersForGroup(group);
        assertThat(users.size(),is(0));
    }

}
