package com.example.bai5.service;

import com.example.bai5.model.Category;
import com.example.bai5.mapper.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> getAll() {
        String sql = "SELECT * FROM category";
        return jdbcTemplate.query(sql, new CategoryRowMapper());
    }

    public Category get(int id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        List<Category> categories = jdbcTemplate.query(sql, new CategoryRowMapper(), id);
        return categories.isEmpty() ? null : categories.get(0);
    }
}