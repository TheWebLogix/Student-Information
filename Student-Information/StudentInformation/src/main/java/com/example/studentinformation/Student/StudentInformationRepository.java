package com.example.studentinformation.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInformationRepository extends JpaRepository<Person, Long> {

}
