package com.mybatis.lean.test;


import com.mybatis.lean.core.Contact;
import com.mybatis.lean.core.Group;
import com.mybatis.lean.core.Icard;
import com.mybatis.lean.core.User;
import com.mybatis.lean.coreImp.ContactsRepository;
import com.mybatis.lean.coreImp.GroupsRepository;
import com.mybatis.lean.coreImp.IcardsRepository;
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


import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;

public class UsersRepositoryTest {
    private SqlSessionFactory sqlSessionFactory;
    private UsersRepository usersRepository;
    private IcardsRepository icardsRepository;
    private ContactsRepository contactsRepository;
    private GroupsRepository groupsRepository;
    private SqlSession session;

    @Before
    public void setUp() throws IOException, SQLException {
        String resource = "mybatis-config.xml";
        
        Reader reader  = Resources.getResourceAsReader(resource);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "test");

        session = sqlSessionFactory.openSession();
        session.getConnection().setAutoCommit(false);
        usersRepository = session.getMapper(UsersRepository.class);
        icardsRepository =session.getMapper(IcardsRepository.class);
        contactsRepository =session.getMapper(ContactsRepository.class);
        groupsRepository =session.getMapper(GroupsRepository.class);

    }
    
    @After
    public void tearDown(){
        session.rollback();
        session.close();
        
    }

    @Test
    public  void find_all_users(){
        List<User> users = usersRepository.findAllUsers();
        assertThat(users.size(),is(2));
        assertThat(users.get(0).getUserName(),is("Jerry"));
        assertThat(users.get(0).getIcard().getIcard_Id(),is(1));
        assertThat(users.get(0).getIcard().getIcardMsg(),is("23020419920820121X"));
        assertThat(users.get(1).getUserName(),is("Tom"));
        assertThat(users.get(1).getIcard().getIcard_Id(),is(2));
        assertThat(users.get(1).getIcard().getIcardMsg(),is("230204199101232325"));
    }

    @Test
    public void get_a_user_by_userid(){
        //One-to-One
        User user = usersRepository.getUserById(1);
        assertThat(user.getUser_Id(),is(1));
        assertThat(user.getUserName(),is("Jerry"));
        assertThat(user.getIcard().getIcardMsg(),is("23020419920820121X"));
        assertThat(user.getIcard().getIcard_Id(),is(1));
        //One-to-N
        List<Contact> contacts = user.getContacts();
        assertThat(contacts.size(),is(2));
        assertThat(contacts.get(0).getValue(),is("13512345678"));
        assertThat(contacts.get(1).getValue(),is("13500000000"));
    }

    @Test
    public void One_to_One_create_a_user_relates_icard_exists(){
        Icard icard =icardsRepository.getIcardById(1);
        if(icard != null){
            System.out.print("already exists");
        }
        icardsRepository.createIcard(icard);
        usersRepository.creatUser(new User(3,"zoe",icard));
        List<User> users = usersRepository.findAllUsers();
        assertThat(users.size(),is(3));
    }

    @Test
    public void One_to_One_create_a_user_not_exist_and_relates_icard(){
        Icard icard =new Icard(5,"23020419930527121X");
        Icard theicard =icardsRepository.getIcardById(5);
        if(theicard != null){
            System.out.print("already exists");
        }
        icardsRepository.createIcard(icard);
        usersRepository.creatUser(new User(3,"zoe",icard));
        List<User> users = usersRepository.findAllUsers();
        assertThat(users.size(),is(3));
    }

    @Test
    public void One_to_One_update_a_relation_between_a_user_and_a_icard_exist(){
        Icard icard =new Icard(3,"10020319951012121x");
        User user =usersRepository.getUserById(34);
        if(user.getIcard()!=null){
            System.out.print("already exists");
        }
        user.setIcard(icard);
        usersRepository.updateUser(user);
        User theuser =usersRepository.getUserById(34);
        assertThat(theuser.getIcard().getIcard_Id(),is(3));
        assertThat(theuser.getIcard().getIcardMsg(),is("10020319951012121x"));
    }

    @Test
    public void  One_to_One_update_a_user(){
        User theUser = usersRepository.getUserById(1);
        theUser.setUserName("Sherry");
        usersRepository.updateUser(theUser);
        User user = usersRepository.getUserById(1);
        assertThat(user.getUserName(),is("Sherry"));
        assertThat(user.getIcard().getIcardMsg(),is("23020419920820121X"));
    }

    @Test
    public void One_to_One_delete_a_user_by_id(){
        User theUser = usersRepository.getUserById(1);
        usersRepository.deleteUserById(theUser);
        List<User> users = usersRepository.findAllUsers();
        assertThat(users.size(),is(1));
    }

    @Test
    public void One_to_N_select_Contacts_For_User(){
        User user =usersRepository.getUserById(1);
        List<Contact> contacts=usersRepository.selectContactsForUser(user);
        assertThat(contacts.size(),is(2));
        assertThat(contacts.get(0).getValue(),is("13512345678"));
        assertThat(contacts.get(1).getValue(),is("13500000000"));

    }

    @Test
    public void One_to_N_create_Contact_with_relation(){
        User user =usersRepository.getUserById(2);
        Contact contact =new Contact(4,"PHONE","13500000002");
        usersRepository.createContact(contact,user);
        List<Contact> contacts=usersRepository.selectContactsForUser(user);
        assertThat(contacts.size(),is(2));
        assertThat(contacts.get(0).getValue(),is("13500000001"));
        assertThat(contacts.get(1).getValue(),is("13500000002"));

    }

    @Test
    public void One_to_N_update_Contact_with_relation(){
        User user =usersRepository.getUserById(1);
        Contact contact = contactsRepository.getContactById(3);
        usersRepository.updateContact(contact,user);
        Contact thecontact = contactsRepository.getContactById(3);
        List<Contact> contacts =usersRepository.selectContactsForUser(user);
        assertThat(contacts.size(),is(3));
        assertThat(contacts.get(2).getValue(),is("13500000001"));
    }

    @Test
    public void N_to_N_select_Groups_For_User(){
        User user =usersRepository.getUserById(1);
        List<Group> groups=usersRepository.selectGroupsForUser(user);
        assertThat(groups.size(),is(2));
        assertThat(groups.get(0).getGroupName(),is("小组1"));
        assertThat(groups.get(1).getGroupName(),is("小组2"));
    }

    @Test
    public void N_to_N_create_Relations(){
        Group group = groupsRepository.getGroupById(2);
        User user =usersRepository.getUserById(2);
        usersRepository.createRelations(user,group);
        List<Group> groups =usersRepository.selectGroupsForUser(user);
        assertThat(groups.size(),is(1));

    }

    @Test
    public void N_to_N_select_Relations(){
        Group group = groupsRepository.getGroupById(1);
        User user =usersRepository.getUserById(1);
        int res = usersRepository.selectRelations(user,group);
        assertThat(res,is(1));
    }

    @Test
    public void N_to_N_delete_Relations(){
        Group group = groupsRepository.getGroupById(1);
        User user =usersRepository.getUserById(1);
        usersRepository.deleteRelations(user,group);
        int res = usersRepository.selectRelations(user,group);
        assertThat(res,is(0));
    }
    
}
