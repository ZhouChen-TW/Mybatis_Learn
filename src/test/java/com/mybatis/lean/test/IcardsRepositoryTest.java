package com.mybatis.lean.test;

import com.mybatis.lean.core.Icard;
import com.mybatis.lean.coreImp.IcardsRepository;
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

public class IcardsRepositoryTest {
    private SqlSessionFactory sqlSessionFactory;
    private IcardsRepository icardsRepository;
    private SqlSession session;

    @Before
    public void setUp() throws IOException, SQLException {
        String resource = "mybatis-config.xml";
        
        Reader reader  = Resources.getResourceAsReader(resource);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "test");

        session = sqlSessionFactory.openSession();
        session.getConnection().setAutoCommit(false);
        icardsRepository = session.getMapper(IcardsRepository.class);

    }
    
    @After
    public void tearDown(){
        session.rollback();
        session.close();
        
    }

    @Test
    public void find_All_Icard(){
        List<Icard> icards =icardsRepository.findAllIcard();
        assertThat(icards.size(),is(3));
        assertThat(icards.get(0).getIcardMsg(),is("23020419920820121X"));
        assertThat(icards.get(1).getIcardMsg(),is("230204199101232325"));
        assertThat(icards.get(2).getIcardMsg(),is("10020319951012121x"));
    }

    @Test
    public void get_a_icard_by_id(){
        Icard icard =icardsRepository.getIcardById(1);
        assertThat(icard.getIcard_Id(),is(1));
        assertThat(icard.getIcardMsg(),is("23020419920820121X"));
    }

    @Test
    public void create_a_icard_when_is_not_exist(){
        icardsRepository.createIcard(new Icard(4,"34030419691028121X"));
        List<Icard> icards = icardsRepository.findAllIcard();
        assertThat(icards.size(),is(4));
        assertThat(icards.get(3).getIcardMsg(),is("34030419691028121X"));
    }

    @Test
    public void update_a_icard(){
        Icard icard =icardsRepository.getIcardById(1);
        icard.setIcardMsg("1234");
        icardsRepository.updateIcard(icard);
        assertThat(icard.getIcardMsg(),is("1234"));
    }

    @Test
    public void delete_a_icard(){
        Icard icard =icardsRepository.getIcardById(1);
        icardsRepository.deleteIcard(icard);
        List<Icard> icards = icardsRepository.findAllIcard();
        assertThat(icards.size(),is(2));
    }

    
    
}
