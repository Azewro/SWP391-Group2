package dao;

import util.DatabaseConnection;

import java.sql.*;
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
        return getInt("SELECT SUM(amount) FROM Payments WHERE DATE(payment_time) = CURDATE() AND status = 'Success'");
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
        String sql = "SELECT DATE_FORMAT(date, '%a') FROM DailyStats ORDER BY date DESC LIMIT 7";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                labels.add(rs.getString(1)); // Thứ viết tắt: Mon, Tue,...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.reverse(labels); // để đúng thứ tự thời gian
        return labels;
    }

    public List<Integer> getLast7DaysRevenue() {
        List<Integer> data = new ArrayList<>();
        String sql = "SELECT total_revenue FROM DailyStats ORDER BY date DESC LIMIT 7";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                data.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.reverse(data);
        return data;
    }

    public List<Integer> getLast7DaysTicketCount() {
        List<Integer> data = new ArrayList<>();
        String sql = "SELECT total_tickets_sold FROM DailyStats ORDER BY date DESC LIMIT 7";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                data.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.reverse(data);
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
