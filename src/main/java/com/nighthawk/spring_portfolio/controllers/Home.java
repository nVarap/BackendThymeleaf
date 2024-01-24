package com.nighthawk.spring_portfolio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nighthawk.hacks.classDataStruct.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;

import java.util.List;

@Controller
public class Home {

    @Autowired
    private PersonDetailsService repository;

    @GetMapping("/home")
    public String showHomePage(Model model) {
        // Get the list of people from the service
        //List<Person> people = personService.getAllPeople();
        

        // Add the list to the model for Thymeleaf to render
        List<com.nighthawk.spring_portfolio.mvc.person.Person> list = repository.listAll();
        model.addAttribute("list", list);
            

        return "home";
    }

    
}
