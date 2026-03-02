package com.example.bai5.service;

import com.example.bai5.model.Product;
import com.example.bai5.mapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> getAll() {
        String sql = "SELECT p.id, p.name, p.image, p.price, c.id AS category_id, c.name AS category_name " +
                "FROM product p INNER JOIN category c ON p.category_id = c.id";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Product get(int id) {
        String sql = "SELECT p.id, p.name, p.image, p.price, c.id AS category_id, c.name AS category_name " +
                "FROM product p INNER JOIN category c ON p.category_id = c.id WHERE p.id = ?";
        List<Product> products = jdbcTemplate.query(sql, new ProductRowMapper(), id);
        return products.isEmpty() ? null : products.get(0);
    }

    public void add(Product newProduct) {
        String sql = "INSERT INTO product (name, image, price, category_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, newProduct.getName(), newProduct.getImage(),
                newProduct.getPrice(), newProduct.getCategory().getId());
    }

    public void update(Product editProduct) {
        String sql = "UPDATE product SET name = ?, image = ?, price = ?, category_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, editProduct.getName(), editProduct.getImage(),
                editProduct.getPrice(), editProduct.getCategory().getId(), editProduct.getId());
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Keep the file upload logic exactly the same
    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        if (!imageProduct.isEmpty()) {
            try {
                // CHANGED: Save to a permanent 'uploads' folder in the root directory
                Path dirImages = Paths.get("uploads");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}