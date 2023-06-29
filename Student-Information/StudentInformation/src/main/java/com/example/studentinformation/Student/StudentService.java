package com.example.studentinformation.Student;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class StudentService {
    private final StudentInformationRepository studentInformationRepository;
    private final StudentMarksRepository studentMarksRepository;

    public StudentService(StudentInformationRepository studentInformationRepository, StudentMarksRepository studentMarksRepository) {
        this.studentInformationRepository = studentInformationRepository;
        this.studentMarksRepository = studentMarksRepository;
    }

    // Methods to handle student information and marks

    public List<Person> getAllStudents() {
        return studentInformationRepository.findAll();
    }

    public Person getStudentById(Long studentId) {
        return studentInformationRepository.findById(studentId).orElse(null);
    }

    public void addStudent(Person studentInformation) {
        studentInformationRepository.save(studentInformation);
    }

    public void updateStudent(Person studentInformation) {
        studentInformationRepository.save(studentInformation);
    }

    public void deleteStudent(Long studentId) {
        studentInformationRepository.deleteById(studentId);
    }

    public void addStudentMarks(Long studentId, Marks studentMarks) {
        Person student = studentInformationRepository.findById(studentId).orElse(null);
        if (student != null) {
            studentMarks.setStudent(student);
            studentMarksRepository.save(studentMarks);
        }
    }

    public List<Marks> getStudentMarks(Long studentId) {
        return studentMarksRepository.findByStudentId(studentId);
    }
}
