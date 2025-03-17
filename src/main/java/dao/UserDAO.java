package dao;

import model.Role;
import model.User;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
    private Connection conn;

    public UserDAO() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }


    public boolean saveUser(User user) {
        String sql = "INSERT INTO Users (username, password_hash, email, phone, full_name, role_id, is_active, auth_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getFullName());
            stmt.setInt(6, 3);
            stmt.setBoolean(7, true);
            stmt.setString(8, "local");

            int rowsInserted = stmt.executeUpdate();
            conn.commit(); // Xác nhận nếu thành công
            return rowsInserted > 0;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback nếu có lỗi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.setAutoCommit(true); // Khôi phục trạng thái ban đầu
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Kiểm tra user có tồn tại không bằng email hoặc username
     */
    public boolean isUserExist(String email, String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE email = ? OR username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Tìm user bằng username hoặc email
     */
    public User findByUsernameOrEmail(String usernameOrEmail) {
        String sql = "SELECT * FROM Users WHERE username = ? OR email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("role_id"),
                        rs.getBoolean("is_active"),
                        rs.getString("status_reason"),
                        rs.getString("password_hash")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePassword(String email, String newPasswordHash) {
        String sql = "UPDATE Users SET password_hash = ? WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPasswordHash);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT user_id, username, full_name, email, phone, role_id, is_active FROM Users WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getInt("role_id"),
                            rs.getBoolean("is_active")
                    );
                }
            }
        }
        return null;
    }



    /**
     * Lấy danh sách người dùng theo vai trò
     */
    public List<User> getUsersByRole(int roleId) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username, full_name, email, phone, role_id, is_active FROM Users WHERE role_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("role_id"),
                        rs.getBoolean("is_active")
                ));
            }
        }
        return users;
    }

    public List<User> getAllUsers(String search, Boolean isActive) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.user_id, u.username, u.full_name, u.email, u.phone, u.is_active, r.role_id, r.role_name " +
                "FROM Users u JOIN Roles r ON u.role_id = r.role_id " +
                "WHERE (? IS NULL OR u.username LIKE ? OR u.email LIKE ?) " +
                "AND (? IS NULL OR u.is_active = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, search);
            stmt.setString(2, "%" + search + "%");
            stmt.setString(3, "%" + search + "%");

            if (isActive == null) {
                stmt.setNull(4, Types.BOOLEAN);
                stmt.setNull(5, Types.BOOLEAN);
            } else {
                stmt.setBoolean(4, isActive);
                stmt.setBoolean(5, isActive);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Role role = new Role(rs.getInt("role_id"), rs.getString("role_name"));
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        role.getRoleId(),
                        rs.getBoolean("is_active")
                );
                users.add(user);
            }
        }
        return users;
    }


    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET full_name = ?, email = ?, phone = ?, is_active = ?, role_id = ? WHERE user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setBoolean(4, user.isActive());
            stmt.setInt(5, user.getRoleId());
            stmt.setInt(6, user.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(int userId) throws SQLException {
        String sql = "UPDATE Users SET is_active = false WHERE user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean restoreUser(int userId) throws SQLException {
        String sql = "UPDATE Users SET is_active = true, status_reason = NULL WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }



    public boolean disableUser(int userId, String statusReason) throws SQLException {
        // Kiểm tra xem user có tồn tại không trước khi cập nhật
        User user = getUserById(userId);
        if (user == null) {
            System.out.println("❌ Không tìm thấy người dùng có ID: " + userId);
            return false;
        }

        String sql = "UPDATE Users SET is_active = false, status_reason = ? WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, statusReason);
            stmt.setInt(2, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Vô hiệu hóa tài khoản ID " + userId + " thành công. Lý do: " + statusReason);
            } else {
                System.out.println("❌ Không thể vô hiệu hóa tài khoản ID " + userId);
            }
            return rowsUpdated > 0;
        }
    }


    public void testUserDAO() {
        try {
            System.out.println("===== TESTING UserDAO =====");

            // 1. Lấy danh sách tất cả người dùng
            System.out.println("Lấy danh sách người dùng:");
            List<User> users = getAllUsers(null, null);
            for (User user : users) {
                System.out.println(user);
            }


            // Test vô hiệu hóa tài khoản
            int userIdToDisable = 4;  // Thay đổi ID người dùng cần vô hiệu hóa
            boolean disableSuccess = disableUser(userIdToDisable, "Vi phạm chính sách");
            System.out.println(disableSuccess ? "✅ Test vô hiệu hóa thành công!" : "❌ Test vô hiệu hóa thất bại!");



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.testUserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
