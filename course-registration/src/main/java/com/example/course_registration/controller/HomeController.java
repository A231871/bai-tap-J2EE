package com.example.course_registration.controller;

import com.example.course_registration.entity.Course;
import com.example.course_registration.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final CourseRepository courseRepository;

    public HomeController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Xử lý đường dẫn trang chủ
    @GetMapping({"/", "/home"})
    public String home(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "") String keyword,
            Model model) {

        // CÂU 1: Phân trang - Mỗi trang có 5 học phần
        Pageable pageable = PageRequest.of(page, 5);
        Page<Course> coursePage;

        // CÂU 8: Tìm kiếm và Lọc dữ liệu theo tên Course
        if (keyword != null && !keyword.trim().isEmpty()) {
            coursePage = courseRepository.findByNameContainingIgnoreCase(keyword.trim(), pageable);
        } else {
            coursePage = courseRepository.findAll(pageable);
        }

        // Đẩy dữ liệu sang file home.html
        model.addAttribute("coursePage", coursePage);
        model.addAttribute("keyword", keyword);

        return "home"; // Tương ứng với src/main/resources/templates/home.html
    }
}