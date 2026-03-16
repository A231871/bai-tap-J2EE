package com.example.course_registration.controller;

import com.example.course_registration.entity.Course;
import com.example.course_registration.entity.Enrollment;
import com.example.course_registration.entity.Student;
import com.example.course_registration.repository.CourseRepository;
import com.example.course_registration.repository.EnrollmentRepository;
import com.example.course_registration.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository,
                                StudentRepository studentRepository,
                                CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // --- HÀM HỖ TRỢ LẤY THÔNG TIN TÀI KHOẢN ---
    private Student getStudentFromAuthentication(Authentication authentication) {
        if (authentication == null) return null;
        Object principal = authentication.getPrincipal();

        // Kiểm tra xem User đăng nhập bằng Google hay Form thường
        if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User) {
            // Đăng nhập bằng Google -> Lấy Email
            String email = ((org.springframework.security.oauth2.core.user.OAuth2User) principal).getAttribute("email");
            return studentRepository.findByEmail(email).orElse(null);
        } else {
            // Đăng nhập bình thường -> Lấy Username
            String username = authentication.getName();
            return studentRepository.findByUsername(username).orElse(null);
        }
    }

    // CÂU 6: Xử lý Đăng ký học phần
    @PostMapping("/enroll")
    public String enrollCourse(@RequestParam Long courseId,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {

        // Gọi hàm dùng chung ở trên thay vì authentication.getName()
        Student student = getStudentFromAuthentication(authentication);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (student != null && course != null) {
            // Kiểm tra xem sinh viên đã đăng ký môn này chưa
            if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
                // Flash Attribute giúp truyền thông báo qua Redirect
                redirectAttributes.addFlashAttribute("errorMsg", "Bạn đã đăng ký khóa học này rồi!");
            } else {
                // Tạo mới bản ghi Đăng ký
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollment.setEnrollDate(LocalDateTime.now());
                enrollmentRepository.save(enrollment);

                redirectAttributes.addFlashAttribute("successMsg", "Đăng ký thành công khóa: " + course.getName());
            }
        }
        return "redirect:/home"; // Quay lại trang chủ sau khi bấm
    }

    // CÂU 7: Hiển thị các khóa học đã đăng ký
    @GetMapping("/my-courses")
    public String myCourses(Authentication authentication, Model model) {

        // Gọi hàm dùng chung ở trên thay vì authentication.getName()
        Student student = getStudentFromAuthentication(authentication);

        if (student == null) {
            // Phòng hờ bị null, đẩy về trang login
            return "redirect:/login";
        }

        // Lấy danh sách Enrollments của riêng sinh viên này
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);
        model.addAttribute("enrollments", enrollments);

        return "my-courses";
    }
}