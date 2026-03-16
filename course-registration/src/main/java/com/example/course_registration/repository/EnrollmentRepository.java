package com.example.course_registration.repository;

import com.example.course_registration.entity.Course;
import com.example.course_registration.entity.Enrollment;
import com.example.course_registration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // Câu 7: Lấy danh sách các môn mà sinh viên đã đăng ký
    List<Enrollment> findByStudent(Student student);

    // Kiểm tra xem sinh viên đã đăng ký môn này chưa để tránh đăng ký trùng
    boolean existsByStudentAndCourse(Student student, Course course);
}
