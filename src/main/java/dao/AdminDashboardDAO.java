package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DatabaseConnection;

public class AdminDashboardDAO {

    public int getTotalTickets() {
        String sql = "SELECT COUNT(*) FROM Tickets WHERE status = 'Booked'";
        return getSingleIntResult(sql);
    }

    public int getCancelledTickets() {
        String sql = "SELECT COUNT(*) FROM Tickets WHERE status = 'Cancelled'";
        return getSingleIntResult(sql);
    }

    public int getPendingOrders() {
        String sql = "SELECT COUNT(*) FROM Orders WHERE status = 'Pending'";
        return getSingleIntResult(sql);
    }

    public int getTodayRevenue() {
        String sql = "SELECT SUM(amount) FROM Payments WHERE DATE(payment_time) = CURDATE() AND status = 'Success'";
        return getSingleIntResult(sql);
    }

    public int getUpcomingTripsToday() {
        String sql = "SELECT COUNT(*) FROM BusTrips WHERE DATE(departure_time) = CURDATE() AND status = 'Scheduled'";
        return getSingleIntResult(sql);
    }

    public int getPendingFeedbacks() {
        String sql = "SELECT COUNT(*) FROM CustomerFeedback WHERE status = 'Pending'";
        return getSingleIntResult(sql);
    }

    public int getTodayNewUsers() {
        String sql = "SELECT COUNT(*) FROM Users WHERE DATE(created_at) = CURDATE()";
        return getSingleIntResult(sql);
    }

    private int getSingleIntResult(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); // COUNT(*) hoặc SUM
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
