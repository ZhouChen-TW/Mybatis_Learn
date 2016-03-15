package com.mybatis.lean.test;

import com.mybatis.lean.core.Contact;
import com.mybatis.lean.coreImp.ContactsRepository;
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
import static org.junit.Assert.assertThat;

public class ContactsRepositoryTest {
    private SqlSessionFactory sqlSessionFactory;
    private ContactsRepository contactsRepository;
    private SqlSession session;

    @Before
    public void setUp() throws IOException, SQLException {
        String resource = "mybatis-config.xml";
        
        Reader reader  = Resources.getResourceAsReader(resource);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "test");

        session = sqlSessionFactory.openSession();
        session.getConnection().setAutoCommit(false);
        contactsRepository = session.getMapper(ContactsRepository.class);

    }
    
    @After
    public void tearDown(){
        session.rollback();
        session.close();
        
    }

    @Test
    public void find_all_contacts(){
        List<Contact> contacts =contactsRepository.findAllContacts();
        assertThat(contacts.size(),is(3));
        assertThat(contacts.get(0).getValue(),is("13512345678"));
        assertThat(contacts.get(1).getValue(),is("13500000000"));
        assertThat(contacts.get(2).getValue(),is("13500000001"));
    }

    @Test
    public void get_Contact_By_Id(){
        Contact contact = contactsRepository.getContactById(1);
        assertThat(contact.getContact_Id(),is(1));
        assertThat(contact.getType(),is("PHONE"));
        assertThat(contact.getValue(),is("13512345678"));
    }

    @Test
    public void create_Contact_without_create_relation(){
        contactsRepository.createContact(new Contact(4,"PHONE","13500000002"));
        List<Contact> contacts =contactsRepository.findAllContacts();
        assertThat(contacts.size(),is(4));
        assertThat(contacts.get(0).getValue(),is("13512345678"));
        assertThat(contacts.get(1).getValue(),is("13500000000"));
        assertThat(contacts.get(2).getValue(),is("13500000001"));
        assertThat(contacts.get(3).getValue(),is("13500000002"));
    }

    @Test
    public void update_Contact_without_update_relation(){
        Contact contact =contactsRepository.getContactById(1);
        contact.setType("address");
        contact.setValue("13500000003");
        contactsRepository.updateContact(contact);
        Contact thecontact = contactsRepository.getContactById(1);
        assertThat(thecontact.getType(),is("address"));
        assertThat(thecontact.getValue(),is("13500000003"));
    }

    @Test
    public void delete_a_Contact_by_id(){
        Contact contact =contactsRepository.getContactById(1);
        contactsRepository.deleteContactId(contact);
        List<Contact> contacts =contactsRepository.findAllContacts();
        assertThat(contacts.size(),is(2));
        assertThat(contacts.get(0).getValue(),is("13500000000"));
        assertThat(contacts.get(1).getValue(),is("13500000001"));
    }



}
