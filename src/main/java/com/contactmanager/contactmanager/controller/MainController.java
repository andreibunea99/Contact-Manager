package com.contactmanager.contactmanager.controller;

import com.contactmanager.contactmanager.dao.ContactDAO;
import com.contactmanager.contactmanager.dao.ContactDAOImpl;
import com.contactmanager.contactmanager.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.List;

@Controller
public class MainController {

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/contactdb");
        dataSource.setUsername("root");
        dataSource.setPassword("andrei");

        return dataSource;
    }

    @Bean
    public ContactDAO getContactDAO(DataSource dataSource) {
        return new ContactDAOImpl(dataSource);
    }

    @Autowired
    private ContactDAO contactDAO;

    @GetMapping(value = "/")
    public String home(Model model) {
        List<Contact> list = contactDAO.list();
        model.addAttribute("contactList", list);
        return "index";
    }

    @GetMapping("/new-contact")
    public String newContact(Model model) {
        Contact contact = new Contact();
        model.addAttribute("contact", contact);
        return "new-contact";
    }

    @PostMapping("/new-contact")
    public String saveContact(@ModelAttribute Contact contact, Model model) {
        contactDAO.save(contact);
        return home(model);
    }

    @GetMapping(value = "/editContact")
    public ModelAndView editContact(HttpServletRequest request) {
        int contactId = Integer.parseInt(request.getParameter("id"));
        Contact contact = contactDAO.get(contactId);
        ModelAndView model = new ModelAndView("new-contact");
        System.out.println(contact);
//        int result = contactDAO.update(contact);
        contactDAO.update(contact);
        System.out.println(contact);
        System.out.println("this guy up there");
        model.addObject("contact", contact);

        return model;
    }

    @PostMapping("/editContact")
    public String editContact(@ModelAttribute Contact contact, Model model) {
        contactDAO.update(contact);
        return home(model);
    }

    @RequestMapping(value = "/deleteContact", method = RequestMethod.GET)
    public ModelAndView deleteContact(HttpServletRequest request) {
        int contactId = Integer.parseInt(request.getParameter("id"));
        contactDAO.delete(contactId);
        return new ModelAndView("redirect:/");
    }
}
