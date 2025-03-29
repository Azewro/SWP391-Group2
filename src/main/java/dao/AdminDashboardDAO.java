package dao;

import util.DatabaseConnection;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AdminDashboardDAO {

    public int getTotalTickets() {
        return getInt("SELECT COUNT(*) FROM Tickets WHERE status = 'Booked'");
    }

    public int getCancelledTickets() {
        return getInt("SELECT COUNT(*) FROM Tickets WHERE status = 'Cancelled'");
    }

    public int getPendingOrders() {
        return getInt("SELECT COUNT(*) FROM Orders WHERE status = 'Pending'");
    }

    public int getTodayRevenue() {
        return getInt("SELECT SUM(amount) FROM Payments WHERE DATE(payment_time) = CURDATE() AND status = 'Completed'");
    }

    public int getUpcomingTripsToday() {
        return getInt("SELECT COUNT(*) FROM BusTrips WHERE DATE(departure_time) = CURDATE() AND status = 'Scheduled'");
    }

    public int getTodayNewUsers() {
        return getInt("SELECT COUNT(*) FROM Users WHERE DATE(created_at) = CURDATE()");
    }

    // --------------------------
    // Dữ liệu cho biểu đồ 7 ngày
    // --------------------------
    public List<String> getLast7DaysLabels() {
        List<String> labels = new ArrayList<>();
        String sql = "SELECT DATE(payment_time) AS day " +
                "FROM Payments " +
                "WHERE status = 'Completed' AND payment_time >= CURDATE() - INTERVAL 6 DAY " +
                "GROUP BY day ORDER BY day";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Date date = rs.getDate("day");
                labels.add(new java.text.SimpleDateFormat("E").format(date)); // Mon, Tue,...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }

    public List<Integer> getLast7DaysRevenue() {
        List<Integer> data = new ArrayList<>();
        String sql = "SELECT DATE(payment_time) AS day, SUM(amount) AS revenue " +
                "FROM Payments WHERE status = 'Completed' AND payment_time >= CURDATE() - INTERVAL 6 DAY " +
                "GROUP BY day ORDER BY day";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                data.add(rs.getInt("revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<Integer> getLast7DaysTicketCount() {
        List<Integer> data = new ArrayList<>();
        String sql = "SELECT DATE(purchase_date) AS day, COUNT(*) AS ticket_count " +
                "FROM Tickets WHERE status = 'Booked' AND purchase_date >= CURDATE() - INTERVAL 6 DAY " +
                "GROUP BY day ORDER BY day";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                data.add(rs.getInt("ticket_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    // --------------------------
    private int getInt(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
