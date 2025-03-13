/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import model.BlogCategory;
import util.DatabaseConnection;

/**
 *
 * @author author
 */
public class BlogDAO {

    public List<Blog> getBlogs(String title, Integer categoryId, int offset, int limit) throws SQLException {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT * FROM blogs WHERE title LIKE ? " + (categoryId != null ? "AND category_id = ? " : "") + "LIMIT ?, ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%%" + title + "%%");
            if (categoryId != null) {
                stmt.setInt(2, categoryId);
                stmt.setInt(3, offset);
                stmt.setInt(4, limit);
            } else {
                stmt.setInt(2, offset);
                stmt.setInt(3, limit);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                blogs.add(new Blog(rs.getInt("id"), rs.getString("title"), rs.getString("content"), rs.getString("summary"), rs.getString("img"), getCategory(rs.getInt("category_id")), rs.getInt("author")));
            }
        }
        return blogs;
    }

    public Blog getBlog(Integer id) throws SQLException {
        String sql = "SELECT * FROM blogs WHERE id =   " + id;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Blog(rs.getInt("id"), rs.getString("title"), rs.getString("content"), rs.getString("summary"), rs.getString("img"), getCategory(rs.getInt("category_id")), rs.getInt("author"));
            }
        }
        return null;
    }

    public int countBlogs(String title, Integer categoryId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM blogs WHERE title LIKE ? " + (categoryId != null ? "AND category_id = ? " : "");
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%%" + title + "%%");
            if (categoryId != null) {
                stmt.setInt(2, categoryId);
            } else {
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public void createBlog(Blog blog) throws SQLException {
        String sql = "INSERT INTO blogs (title, content, summary, img, category_id, author, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setString(3, blog.getSummary());
            stmt.setString(4, blog.getImg());
            stmt.setObject(5, blog.getCategory() != null ? blog.getCategory().getId() : null, Types.INTEGER);
            stmt.setInt(6, blog.getAuthor());
            stmt.executeUpdate();
        }
    }

    public void updateBlog(Blog blog) throws SQLException {
        String sql = "UPDATE blogs SET title = ?, content = ?, summary = ?, img = ?, category_id = ?, author = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setString(3, blog.getSummary());
            stmt.setString(4, blog.getImg());
            stmt.setObject(5, blog.getCategory() != null ? blog.getCategory().getId() : null, Types.INTEGER);
            stmt.setInt(6, blog.getAuthor());
            stmt.setInt(7, blog.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteBlog(int id) throws SQLException {
        String sql = "DELETE FROM blogs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<BlogCategory> getCategories(String name, int offset, int limit) throws SQLException {
        List<BlogCategory> categories = new ArrayList<>();
        String sql = "SELECT * FROM blogcategory WHERE name LIKE ? LIMIT ?, ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%%" + name + "%%");
            stmt.setInt(2, offset);
            stmt.setInt(3, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(new BlogCategory(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
            }
        }
        return categories;
    }

    public BlogCategory getCategory(int id) throws SQLException {
        String sql = "SELECT * FROM blogcategory WHERE id = " + id;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new BlogCategory(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
            }
        }
        return null;
    }

    public int countCategories(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM blogcategory WHERE name LIKE '" + "%" + name + "%" + "'";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public void createCategory(BlogCategory category) throws SQLException {
        String sql = "INSERT INTO blogcategory (name, description, created_at, updated_at) VALUES (?, ?, NOW(), NOW())";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.executeUpdate();
        }
    }

    public void updateCategory(BlogCategory category) throws SQLException {
        String sql = "UPDATE blogcategory SET name = ?, description = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, category.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteCategory(int id) throws SQLException {
        String sql = "DELETE FROM blogcategory WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
