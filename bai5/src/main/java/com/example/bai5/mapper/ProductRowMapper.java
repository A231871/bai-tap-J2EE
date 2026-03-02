package com.example.bai5.mapper;

import com.example.bai5.model.Category;
import com.example.bai5.model.Product;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setImage(rs.getString("image"));
        p.setPrice(rs.getLong("price"));

        // Map Category object associated with the product
        Category c = new Category();
        c.setId(rs.getInt("category_id"));
        c.setName(rs.getString("category_name"));
        p.setCategory(c);

        return p;
    }
}