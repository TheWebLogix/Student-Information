package com.example.studentinformation.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Controller
@RequestMapping("/persons")
public class PersonController {
    private final StudentInformationRepository personRepository;

    @Autowired
    public PersonController(StudentInformationRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public String getAllPersons(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "persons";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("person", new Person());
        return "add-person";
    }

    @PostMapping("/add")
    public String addPerson(@ModelAttribute("person") Person person) {
        person.setTimestamp(new Timestamp(System.currentTimeMillis()));
        personRepository.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id: " + id));
        model.addAttribute("person", person);
        return "edit-person";
    }

    @PostMapping("/edit/{id}")
    public String updatePerson(@PathVariable("id") Long id, @ModelAttribute("person") Person updatedPerson) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id: " + id));

        person.setFirstName(updatedPerson.getFirstName());
        person.setLastName(updatedPerson.getLastName());
        person.setDob(updatedPerson.getDob());
        person.setAddress(updatedPerson.getAddress());
        person.setParentsName(updatedPerson.getParentsName());
        person.setCity(updatedPerson.getCity());
        person.setPhone(updatedPerson.getPhone());
        person.setTimestamp(new Timestamp(System.currentTimeMillis()));

        personRepository.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") Long id) {
        personRepository.deleteById(id);
        return "redirect:/persons";
    }
}