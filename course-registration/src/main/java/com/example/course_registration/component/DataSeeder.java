package com.example.course_registration.component;

import com.example.course_registration.entity.Category;
import com.example.course_registration.entity.Course;
import com.example.course_registration.entity.Role;
import com.example.course_registration.entity.Student;
import com.example.course_registration.repository.CategoryRepository;
import com.example.course_registration.repository.CourseRepository;
import com.example.course_registration.repository.RoleRepository;
import com.example.course_registration.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(RoleRepository roleRepository, StudentRepository studentRepository,
                      CategoryRepository categoryRepository, CourseRepository courseRepository,
                      PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Tạo Roles nếu chưa có
        if (roleRepository.count() == 0) {
            Role adminRole = new Role(null, "ADMIN");
            Role studentRole = new Role(null, "STUDENT");
            roleRepository.saveAll(Set.of(adminRole, studentRole));

            // 2. Tạo User Admin
            Student admin = new Student();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setEmail("admin@unicourse.com");
            admin.getRoles().add(adminRole);
            studentRepository.save(admin);

            // 3. Tạo User Student
            Student student = new Student();
            student.setUsername("student");
            student.setPassword(passwordEncoder.encode("123456"));
            student.setEmail("student@unicourse.com");
            student.getRoles().add(studentRole);
            studentRepository.save(student);

            // 4. Tạo Categories
            Category itCat = categoryRepository.save(new Category(null, "Information Technology"));
            Category bizCat = categoryRepository.save(new Category(null, "Business"));
            Category designCat = categoryRepository.save(new Category(null, "Design"));

            // 5. Tạo 10 Khóa học mẫu để test Phân trang (Câu 1)
            courseRepository.save(new Course(null, "Spring Boot Mastery", "https://picsum.photos/seed/spring/400/200", 3, "John Doe", itCat));
            courseRepository.save(new Course(null, "Advanced ReactJS", "https://picsum.photos/seed/react/400/200", 3, "Jane Smith", itCat));
            courseRepository.save(new Course(null, "Digital Marketing", "https://picsum.photos/seed/mkt/400/200", 2, "Alice Brown", bizCat));
            courseRepository.save(new Course(null, "UI/UX Design Basics", "https://picsum.photos/seed/ui/400/200", 4, "Bob White", designCat));
            courseRepository.save(new Course(null, "Data Structures", "https://picsum.photos/seed/data/400/200", 3, "Tom Clark", itCat));
            courseRepository.save(new Course(null, "Corporate Finance", "https://picsum.photos/seed/finance/400/200", 3, "Nancy Drew", bizCat));
            courseRepository.save(new Course(null, "Machine Learning", "https://picsum.photos/seed/ml/400/200", 4, "Alan Turing", itCat));
            courseRepository.save(new Course(null, "Graphic Design Pro", "https://picsum.photos/seed/graphic/400/200", 2, "Da Vinci", designCat));
            courseRepository.save(new Course(null, "Cloud Computing AWS", "https://picsum.photos/seed/aws/400/200", 3, "Jeff Bezos", itCat));
            courseRepository.save(new Course(null, "Business Ethics", "https://picsum.photos/seed/ethics/400/200", 2, "Socrates", bizCat));
        }
    }
}
