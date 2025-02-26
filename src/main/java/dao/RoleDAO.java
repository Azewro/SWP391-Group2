package dao;

import model.Role;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private Connection conn;

    public RoleDAO() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }

    public List<Role> getAllRoles() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT role_id, role_name FROM Roles";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                roles.add(new Role(rs.getInt("role_id"), rs.getString("role_name")));
            }
        }
        return roles;
    }

    public static void main(String[] args) {
        try {
            RoleDAO roleDAO = new RoleDAO();
            List<Role> roles = roleDAO.getAllRoles();

            if (roles.isEmpty()) {
                System.out.println("❌ Không có vai trò nào trong database.");
            } else {
                System.out.println("✅ Danh sách vai trò:");
                for (Role role : roles) {
                    System.out.println("ID: " + role.getRoleId() + " - Tên: " + role.getRoleName());
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠ Lỗi kết nối database:");
            e.printStackTrace();
        }
    }
}
