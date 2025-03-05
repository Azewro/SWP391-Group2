package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // URL của cơ sở dữ liệu
    private static final String DB_URL = "jdbc:mysql://localhost:3306/G2BusTicketSystem";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456789";

    // Hàm để lấy kết nối
    public static Connection getConnection() throws SQLException {
        try {
            // Register driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }

        // connect
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Kết nối thành công!");
            } else {
                System.out.println("Kết nối thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}