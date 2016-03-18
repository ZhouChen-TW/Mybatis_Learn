package com.mybatis.lean.test;


import com.mybatis.lean.core.Contact;
import com.mybatis.lean.core.Group;
import com.mybatis.lean.core.Icard;
import com.mybatis.lean.core.User;
import com.mybatis.lean.coreImp.ContactsRepository;
import com.mybatis.lean.coreImp.GroupsRepository;
import com.mybatis.lean.coreImp.IcardsRepository;
import com.mybatis.lean.coreImp.UsersRepository;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;


import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertEquals;
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

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public  void find_all_users(){
        List<User> users = usersRepository.findAllUsers();
        assertThat(users.size(),is(3));
        assertThat(users.get(0).getName(),is("Jerry"));
        assertThat(users.get(0).getIcard().getId(),is(1));
        assertThat(users.get(0).getIcard().getIcardMsg(),is("23020419920820121X"));
        assertThat(users.get(1).getName(),is("Tom"));
        assertThat(users.get(1).getIcard().getId(),is(2));
        assertThat(users.get(1).getIcard().getIcardMsg(),is("230204199101232325"));
        assertThat(users.get(2).getName(),is("Zoe"));
    }

    @Test
    public void get_a_user_by_userid(){
        //One-to-One
        User user = usersRepository.getUserById(1);
        assertThat(user.getId(),is(1));
        assertThat(user.getName(),is("Jerry"));
        assertThat(user.getIcard().getIcardMsg(),is("23020419920820121X"));
        assertThat(user.getIcard().getId(),is(1));
        //One-to-N
        List<Contact> contacts = user.getContacts();
        assertThat(contacts.size(),is(2));
        assertThat(contacts.get(0).getValue(),is("13512345678"));
        assertThat(contacts.get(1).getValue(),is("13500000000"));
        //N-to-N
        List<Group> groups = user.getGroups();
        assertThat(groups.size(),is(2));
        assertThat(groups.get(0).getName(),is("小组1"));
        assertThat(groups.get(1).getName(),is("小组2"));
    }

    @Test
    public void One_to_One_update_a_Icard_with_relation(){
        User user =usersRepository.getUserById(3);
        Icard icard =icardsRepository.getIcardById(3);
        user.setIcard(icard);
        usersRepository.updateIcard(user);
        Icard icard1 = icardsRepository.getIcardById(3);
        assertThat(icard1.getId(),is(3));
        assertThat(icard1.getIcardMsg(),is("340301199010012223"));
    }

    @Test
    public void One_to_One_create_a_Icard_with_relation(){
        User user =usersRepository.getUserById(3);
        Icard icard = new Icard(3,"340301199010012223");
        user.setIcard(icard);
        usersRepository.createIcard(user);
        List<Icard> icards = icardsRepository.findAllIcard();
        assertThat(icards.size(),is(3));
    }
    @Test
    public void One_to_One_create_a_user(){
        usersRepository.creatUser(new User(4,"Sherry",null));
        List<User> users = usersRepository.findAllUsers();
        assertThat(users.size(),is(4));
        assertThat(users.get(0).getName(),is("Jerry"));
        assertThat(users.get(1).getName(),is("Tom"));
        assertThat(users.get(2).getName(),is("Zoe"));
        assertThat(users.get(3).getName(),is("Sherry"));
    }

    @Test
    public void  One_to_One_update_a_user(){
        User theUser = usersRepository.getUserById(1);
        theUser.setName("Sherry");
        usersRepository.updateUser(theUser);
        User user = usersRepository.getUserById(1);
        assertThat(user.getName(),is("Sherry"));
        assertThat(user.getIcard().getIcardMsg(),is("23020419920820121X"));
    }

    @Test
    public void One_to_One_and_One_to_N_and_N_to_N_delete_a_user_Casede(){
        User theUser = usersRepository.getUserById(1);
        usersRepository.deleteUser(theUser);
        List<User> users = usersRepository.findAllUsers();
        assertThat(users.size(),is(2));
        Icard icard = icardsRepository.getIcardById(1);
        assertEquals(icard,null);
        List<Contact> contacts =usersRepository.selectContactsForUser(theUser);
        assertThat(contacts.size(),is(0));
        List<Group> groups =usersRepository.selectGroupsForUser(theUser);
        assertThat(groups.size(),is(0));

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
        user.setName("hello");
        List<Contact> contacts = user.getContacts();
        contacts.get(0).setValue("13500000002");
        usersRepository.updateContact(contacts.get(0),user);
        User theUser = usersRepository.getUserById(1);
        assertThat(theUser.getName(),is("hello"));
        Contact contact1 = contactsRepository.getContactById(1);
        assertThat(contact1.getValue(),is("13500000002"));
    }

    @Test
    public void One_to_N_update_All_Contacts_For_User_Casede(){
        User user =usersRepository.getUserById(1);
        user.setName("Sherry");
        List<Contact> contacts =user.getContacts();
        contacts.get(0).setValue("13500000002");
        contacts.get(1).setValue("13500000003");
        usersRepository.updateAllContactsForUser(user);
        User theUser = usersRepository.getUserById(1);
        assertThat(user.getName(),is("Sherry"));
        Contact contact =contactsRepository.getContactById(1);
        Contact contact1 = contactsRepository.getContactById(2);
        assertThat(contact.getValue(),is("13500000002"));
        assertThat(contact1.getValue(),is("13500000003"));
    }

    @Test
    public void N_to_N_select_Groups_For_User(){
        User user =usersRepository.getUserById(1);
        List<Group> groups=usersRepository.selectGroupsForUser(user);
        assertThat(groups.size(),is(2));
        assertThat(groups.get(0).getName(),is("小组1"));
        assertThat(groups.get(1).getName(),is("小组2"));
    }

    @Test
    public void N_to_N_create_Relations(){
        Group group = groupsRepository.getGroupById(2);
        User user =usersRepository.getUserById(2);
        usersRepository.createRelations(user,group);
        List<Group> groups =usersRepository.selectGroupsForUser(user);
        assertThat(groups.size(),is(2));

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

    @Test
    public void N_to_N_update_All_Groups_For_User_Casede(){
        User user =usersRepository.getUserById(1);
        user.setName("Sherry");
        List<Group> groups =user.getGroups();
        groups.get(0).setName("one");
        groups.get(1).setName("two");
        usersRepository.updateAllGroupsForUser(user);
        User theUser = usersRepository.getUserById(1);
        assertThat(theUser.getName(),is("Sherry"));
        Group group=groupsRepository.getGroupById(1);
        Group group1 =groupsRepository.getGroupById(2);
        assertThat(group.getName(),is("one"));
        assertThat(group1.getName(),is("two"));
    }

    //test exceptions

    @Test
    public void One_to_One_and_One_to_N_and_N_to_N_foreign_key_restrict_throws_PersistenceException(){
       User user = usersRepository.getUserById(1);
        thrown.expect(PersistenceException.class);
        usersRepository.deleteUserExpectException(user);
    }

    @Test
    public void One_to_One_foreign_key_unique_restrict_throws_PersistenceException(){
        User user = new User(1,"Sherry",null);
        Icard icard =icardsRepository.getIcardById(3);
        user.setIcard(icard);
        thrown.expect(PersistenceException.class);
        usersRepository.updateIcard(user);
    }

    @Test
    public void user_primary_key_restrict_throws_PersistenceException(){
        User user =new User(1,"Sherry",null);
        thrown.expect(PersistenceException.class);
        usersRepository.creatUser(user);
    }

    @Test
    public void One_to_N_and_N_to_N_foreign_key_restrict_update_class_not_exist_throws_PersistenceException(){
        User user =new User(4,"Kery",null);
        Contact contact =contactsRepository.getContactById(1);
        thrown.expect(PersistenceException.class);
        usersRepository.updateContact(contact,user);
        Group group =new Group(3,"three");
        usersRepository.createRelations(user,group);
    }


    
}
