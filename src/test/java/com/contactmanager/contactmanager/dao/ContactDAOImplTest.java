package com.contactmanager.contactmanager.dao;

import com.contactmanager.contactmanager.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactDAOImplTest {

    private DriverManagerDataSource driverManagerDataSource;
    private ContactDAO dao;

    @BeforeEach
    void setupBeforeEach() {
        driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/contactdb");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("andrei");

        dao = new ContactDAOImpl(driverManagerDataSource);
    }

    @Test
    void save() {
        Contact contact = new Contact("Jurgen Klopp", "jurgen.klopp@lfc.uk",
                "Liverpool, UK", "0738294623");

        int result = dao.save(contact);
        assertTrue(result > 0);
    }

    @Test
    void update() {
        Contact contact = new Contact(2,"Ale Miroiu", "ale.miroiu@ucl.uk",
                "London, UK", "0727835638");

        int result = dao.update(contact);
        assertTrue(result > 0);
    }

    @Test
    void get() {
        Integer id = 1;
        Contact contact = dao.get(id);

        if (contact != null) {
            System.out.println(contact);
        }

        assertNotNull(contact);
    }

    @Test
    void delete() {
        Integer id = 11;

        int result = dao.delete(id);

        assertTrue(result > 0);
    }

    @Test
    void list() {
        List<Contact> contactList = dao.list();

        for(Contact obj : contactList) {
            System.out.println(obj);
        }

        assertTrue(!contactList.isEmpty());
    }
}