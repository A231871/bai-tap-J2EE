package com.example.course_registration.controller;

import com.example.course_registration.entity.Course;
import com.example.course_registration.repository.CategoryRepository;
import com.example.course_registration.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public AdminCourseController(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    // 1. READ: Hiển thị danh sách tất cả học phần
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "admin/courses";
    }

    // 2. CREATE: Hiển thị form Thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/course-form";
    }

    // 3. UPDATE: Hiển thị form Sửa (Lấy dữ liệu cũ đổ vào form)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        model.addAttribute("course", course);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/course-form";
    }

    // 4. LƯU VÀO DB VÀ UPLOAD ẢNH
    @PostMapping("/save")
    public String saveCourse(@ModelAttribute Course course,
                             @RequestParam("imageFile") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) {
        try {
            // Nếu người dùng có chọn file ảnh mới
            if (!multipartFile.isEmpty()) {
                // Lấy tên file gốc
                String fileName = multipartFile.getOriginalFilename();
                // Tạo thư mục uploads nếu chưa tồn tại
                String uploadDir = "uploads/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Đổi tên file để tránh trùng lặp (thêm timestamp)
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                Path filePath = uploadPath.resolve(uniqueFileName);

                // Lưu file vào ổ cứng
                Files.copy(multipartFile.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                // Cập nhật đường dẫn ảnh vào Database
                course.setImage("/uploads/" + uniqueFileName);
            } else {
                // Nếu đang Edit mà KHÔNG tải ảnh mới lên -> Giữ nguyên ảnh cũ
                if (course.getId() != null) {
                    Course existingCourse = courseRepository.findById(course.getId()).orElse(null);
                    if (existingCourse != null) {
                        course.setImage(existingCourse.getImage());
                    }
                } else {
                    // Nếu Thêm mới mà quên chọn ảnh -> Dùng ảnh mặc định
                    course.setImage("https://picsum.photos/400/200");
                }
            }

            courseRepository.save(course);
            redirectAttributes.addFlashAttribute("successMsg", "Lưu khóa học thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Lỗi khi upload ảnh: " + e.getMessage());
        }
        return "redirect:/admin/courses";
    }

    // 5. DELETE: Xóa học phần
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMsg", "Xóa khóa học thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Không thể xóa! Khóa học này đã có sinh viên đăng ký.");
        }
        return "redirect:/admin/courses";
    }
}