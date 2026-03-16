package com.example.course_registration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String image;

    private Integer credits;

    private String lecturer;

    // Khóa ngoại liên kết tới bảng Category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
