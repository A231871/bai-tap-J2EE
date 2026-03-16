package com.example.course_registration.repository;

import com.example.course_registration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    Optional<Student> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
