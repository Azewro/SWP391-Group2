package dao;

import model.CustomerFeedback;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.math.BigDecimal;

public class AdminReportDAO {
    private Connection conn;

    public AdminReportDAO(Connection conn) {
        this.conn = conn;
    }

    // 1. Tổng doanh thu trong khoảng thời gian
    public BigDecimal getTotalRevenue(Date from, Date to) throws SQLException {
        String sql = "SELECT SUM(amount) FROM Payments WHERE payment_time BETWEEN ? AND ? AND status = 'Success'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(from.getTime()));
            ps.setTimestamp(2, new Timestamp(to.getTime()));
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
        }
    }

    // 2. Tổng số vé đã bán
    public int getTotalTicketsSold(Date from, Date to) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Tickets WHERE purchase_date BETWEEN ? AND ? AND status = 'Used'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(from.getTime()));
            ps.setTimestamp(2, new Timestamp(to.getTime()));
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // 3. Tổng số đơn hàng
    public int getTotalOrders(Date from, Date to) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Orders WHERE order_date BETWEEN ? AND ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(from.getTime()));
            ps.setTimestamp(2, new Timestamp(to.getTime()));
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // 4. Top 3 phản hồi chờ duyệt (mới nhất)
    public List<CustomerFeedback> getTopPendingFeedbacks() throws SQLException {
        String sql = "SELECT * FROM CustomerFeedback WHERE status = 'Pending' ORDER BY created_at DESC LIMIT 3";
        List<CustomerFeedback> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CustomerFeedback fb = new CustomerFeedback();
                fb.setFeedbackId(rs.getInt("feedback_id"));
                fb.setTicketId(rs.getInt("ticket_id"));
                fb.setRating(rs.getInt("rating"));
                fb.setComment(rs.getString("comment"));
                fb.setCreatedAt(rs.getTimestamp("created_at"));
                fb.setStatus(rs.getString("status"));
                list.add(fb);
            }
        }
        return list;
    }

    // 5. Phân phối số sao đánh giá (1-5 sao)
    public int[] getRatingDistribution() throws SQLException {
        int[] distribution = new int[5]; // index 0: 1 sao → index 4: 5 sao
        String sql = "SELECT rating, COUNT(*) as count FROM CustomerFeedback GROUP BY rating";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int rating = rs.getInt("rating");
                int count = rs.getInt("count");
                if (rating >= 1 && rating <= 5) {
                    distribution[rating - 1] = count;
                }
            }
        }
        return distribution;
    }

    // 6. Thống kê doanh thu theo tuyến xe (Route)
    public Map<String, BigDecimal> getRevenueByRoute() throws SQLException {
        String sql = """
            SELECT r.route_name, SUM(t.price) AS total_revenue
            FROM Tickets t
            JOIN BusTrips bt ON t.trip_id = bt.trip_id
            JOIN Routes r ON bt.route_id = r.route_id
            WHERE t.status = 'Used'
            GROUP BY r.route_name
            """;

        Map<String, BigDecimal> routeRevenue = new LinkedHashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                routeRevenue.put(rs.getString("route_name"), rs.getBigDecimal("total_revenue"));
            }
        }
        return routeRevenue;
    }

    // 7. Top khách hàng chi tiêu nhiều nhất
    public Map<String, BigDecimal> getTopCustomers(int limit) throws SQLException {
        String sql = """
            SELECT u.full_name, SUM(o.total_amount) AS total_spent
            FROM Orders o
            JOIN Users u ON o.user_id = u.user_id
            WHERE o.status = 'Completed'
            GROUP BY u.full_name
            ORDER BY total_spent DESC
            LIMIT ?
            """;

        Map<String, BigDecimal> topCustomers = new LinkedHashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                topCustomers.put(rs.getString("full_name"), rs.getBigDecimal("total_spent"));
            }
        }
        return topCustomers;
    }

    // 8. Số lượng vé bán theo ngày (dùng để vẽ biểu đồ)
    public Map<String, Integer> getTicketsSoldByDate(Date from, Date to) throws SQLException {
        String sql = """
            SELECT DATE(purchase_date) as date, COUNT(*) as ticket_count
            FROM Tickets
            WHERE purchase_date BETWEEN ? AND ? AND status = 'Used'
            GROUP BY DATE(purchase_date)
            ORDER BY date ASC
            """;

        Map<String, Integer> data = new LinkedHashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(from.getTime()));
            ps.setTimestamp(2, new Timestamp(to.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.put(rs.getString("date"), rs.getInt("ticket_count"));
            }
        }
        return data;
    }
}
