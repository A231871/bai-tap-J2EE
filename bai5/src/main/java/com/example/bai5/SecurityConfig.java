package com.example.bai5;

import com.example.bai5.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AccountService accountService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        // Cho phép cả USER và ADMIN xem danh sách
                        .requestMatchers("/products").hasAnyRole("USER", "ADMIN")

                        // Yêu cầu quyền ADMIN cho các thao tác Thêm, Sửa, Xóa (các route con của /products/)
                        .requestMatchers("/products/**").hasRole("ADMIN")

                        // Các request khác đều phải đăng nhập
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.defaultSuccessUrl("/products", true).permitAll()) // Cấu hình trang login mặc định
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}