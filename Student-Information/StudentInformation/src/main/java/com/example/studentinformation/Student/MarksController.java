package com.example.studentinformation.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/marks")
public class MarksController {
    private final StudentMarksRepository repo;
    private final StudentInformationRepository studentRepo;
    @Autowired StudentService service;

    @Autowired
    public MarksController(StudentMarksRepository repo, StudentInformationRepository studentRepo) {
        this.repo = repo;
        this.studentRepo = studentRepo;
    }

    @GetMapping
    public String getAllMarks(Model model) {
        model.addAttribute("marks", repo.findAll());

//        List<Person> person = service.getAllStudents();
        model.addAttribute("persons", studentRepo.findAll());
        return "marks";
    }


    @GetMapping("/add")
    public String showAddMarks(Model model) {
        model.addAttribute("marks", new Marks());
        model.addAttribute("persons", studentRepo.findAll());
        return "add-marks";
    }

    @PostMapping("/add")
    public String addMarks(@ModelAttribute("marks") Marks marks,Model model) {
        // Retrieve the student using the studentId from marks

        Person person = studentRepo.findById(marks.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + marks.getId()));

        boolean hasDuplicateSubject = repo.existsByStudentAndSubject(marks.getStudent(), marks.getSubject());
        if (hasDuplicateSubject) {
            // Handle the case when the student already has a Marks entry with the same subject
            // You can redirect to an error page or show a validation message
            return "redirect:/error";
        }

        // Set the student for the marks
        marks.setStudent(person);

        // Set the id property of marks (if necessary)
        // marks.setId(null); // or generate the id using @GeneratedValue
        model.addAttribute("persons", studentRepo.findAll());
        repo.save(marks);
        return "redirect:/marks";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Marks marks = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id: " + id));
        model.addAttribute("marks", marks);
        return "edit-marks";
    }

    @PostMapping("/edit/{id}")
    public String updateMarks(@PathVariable("id") Long id, @ModelAttribute("marks") Marks updatedMarks) {
        Marks marks = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id: " + id));

        marks.setMarks(updatedMarks.getMarks());
        marks.setSubject(updatedMarks.getSubject());
        marks.setStudent(marks.getStudent());
        repo.save(marks);
        return "redirect:/marks";
    }

    @GetMapping("/delete/{id}")
    public String deleteMarks(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/marks";
    }
    @GetMapping("/above-60")
    public String getStudentsAbove60Percent(Model model) {
        List<Marks> marksList = repo.findAll();
        model.addAttribute("listAbove60",marksList.stream()
                .filter(marks -> (marks.getMarks() / 100.0) * 100 > 60)
                .map(marks -> marks.getStudent().getFirstName())
                .collect(Collectors.toList()));
        return "listStudent";

    }
}