package com.example.course_registration.repository;

import com.example.course_registration.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Câu 8: Tìm kiếm học phần theo tên (Không phân biệt hoa thường) + Phân trang (Câu 1)
    Page<Course> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
