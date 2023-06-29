package com.example.studentinformation.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentMarksRepository extends JpaRepository<Marks,Long> {
    List<Marks> findByStudentId(Long studentId);


    boolean existsByStudentAndSubject(Person student, String subject);
}
