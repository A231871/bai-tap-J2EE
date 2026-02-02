package com.bai4.service;

import com.bai4.model.Category;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final List<Category> listCategory = new ArrayList<>();

    public CategoryService() {
        // Mock data
        listCategory.add(new Category(1, "Laptop"));
        listCategory.add(new Category(2, "Điện thoại"));
    }

    public List<Category> getAll() {
        return listCategory;
    }

    public Category get(int id) {
        return listCategory.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}