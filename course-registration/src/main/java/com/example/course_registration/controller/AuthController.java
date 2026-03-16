package com.example.course_registration.controller;

import com.example.course_registration.entity.Role;
import com.example.course_registration.entity.Student;
import com.example.course_registration.repository.RoleRepository;
import com.example.course_registration.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(StudentRepository studentRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // Xử lý logic đăng ký tài khoản mới (Câu 3)
    @PostMapping("/register")
    public String registerStudent(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String email,
                                  Model model) {
        if (studentRepository.existsByUsername(username)) {
            model.addAttribute("error", "Username đã tồn tại!");
            return "register";
        }
        if (studentRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email đã được sử dụng!");
            return "register";
        }

        Student newStudent = new Student();
        newStudent.setUsername(username);
        newStudent.setPassword(passwordEncoder.encode(password));
        newStudent.setEmail(email);

        // Gán quyền mặc định là STUDENT (Yêu cầu Câu 3)
        Optional<Role> studentRole = roleRepository.findByName("STUDENT");
        studentRole.ifPresent(role -> newStudent.getRoles().add(role));

        studentRepository.save(newStudent);

        // Đăng ký thành công, chuyển hướng về trang login kèm thông báo
        return "redirect:/login?success";
    }
}