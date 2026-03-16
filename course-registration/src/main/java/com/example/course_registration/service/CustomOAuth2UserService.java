package com.example.course_registration.service;

import com.example.course_registration.entity.Role;
import com.example.course_registration.entity.Student;
import com.example.course_registration.repository.RoleRepository;
import com.example.course_registration.repository.StudentRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;

    public CustomOAuth2UserService(StudentRepository studentRepository, RoleRepository roleRepository) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Lấy thông tin user từ Google
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Kiểm tra xem email này đã có trong Database chưa
        Student student = studentRepository.findByEmail(email).orElse(null);

        if (student == null) {
            // Nếu chưa có, tự động tạo tài khoản mới
            student = new Student();
            // Lấy phần trước chữ @gmail.com làm username
            student.setUsername(email.split("@")[0]);
            student.setEmail(email);
            // Mật khẩu bỏ trống hoặc random vì họ đăng nhập bằng Google
            student.setPassword("");

            // Gán quyền STUDENT mặc định
            Role studentRole = roleRepository.findByName("STUDENT").orElseThrow();
            student.getRoles().add(studentRole);

            studentRepository.save(student);
        }

        // Chuyển đổi Role từ Database thành Quyền (Authority) của Spring Security
        List<GrantedAuthority> authorities = student.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        // Trả về User cho Spring Security quản lý phiên đăng nhập
        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
    }
}