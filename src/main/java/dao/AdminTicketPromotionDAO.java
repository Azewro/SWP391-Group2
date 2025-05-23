package dao;

import model.TicketPromotion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminTicketPromotionDAO {
    private Connection conn;

    public AdminTicketPromotionDAO(Connection conn) {
        this.conn = conn;
    }

    // 1️⃣ Lấy tất cả mã giảm giá theo vé xe
    public List<TicketPromotion> getAllTicketPromotions() {
        List<TicketPromotion> promotions = new ArrayList<>();
        String query = "SELECT * FROM TicketPromotions ORDER BY expiration_date DESC";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                promotions.add(new TicketPromotion(
                        rs.getInt("promo_id"),
                        rs.getInt("ticket_id"),
                        rs.getDouble("discount_percentage"),
                        rs.getString("promo_code"),
                        rs.getTimestamp("expiration_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    // 2️⃣ Thêm mã giảm giá cho vé
    public boolean addTicketPromotion(TicketPromotion promo) {
        String query = "INSERT INTO TicketPromotions (ticket_id, discount_percentage, promo_code, expiration_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, promo.getTicketId());
            stmt.setDouble(1, promo.getDiscountPercentage().doubleValue());
            stmt.setString(3, promo.getPromoCode());
            stmt.setTimestamp(4, promo.getExpirationDate());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3️⃣ Cập nhật mã giảm giá theo vé
    public boolean updateTicketPromotion(TicketPromotion promo) {
        String query = "UPDATE TicketPromotions SET discount_percentage = ?, promo_code = ?, expiration_date = ? WHERE promo_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, promo.getDiscountPercentage().doubleValue());
            stmt.setString(2, promo.getPromoCode());
            stmt.setTimestamp(3, promo.getExpirationDate());
            stmt.setInt(4, promo.getPromoId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4️⃣ Xóa mã giảm giá theo vé
    public boolean deleteTicketPromotion(int promoId) {
        String query = "DELETE FROM TicketPromotions WHERE promo_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, promoId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách các chuyến xe có hơn `percentage`% số ghế trống
    public List<Integer> getLowTrafficTrips(int percentage) {
        List<Integer> lowTrafficTrips = new ArrayList<>();
        String query = """
        SELECT bt.trip_id 
        FROM BusTrips bt
        JOIN (
            SELECT trip_id, COUNT(ticket_id) AS booked_seats
            FROM Tickets 
            GROUP BY trip_id
        ) AS t ON bt.trip_id = t.trip_id
        WHERE (t.booked_seats / bt.available_seats) * 100 < ?
    """;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, percentage);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lowTrafficTrips.add(rs.getInt("trip_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lowTrafficTrips;
    }

    // Lấy danh sách chuyến xe khởi hành trong khoảng `startHour - endHour`
    public List<Integer> getOffPeakTrips(int startHour, int endHour) {
        List<Integer> offPeakTrips = new ArrayList<>();
        String query = "SELECT trip_id FROM BusTrips WHERE HOUR(departure_time) BETWEEN ? AND ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, startHour);
            stmt.setInt(2, endHour);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                offPeakTrips.add(rs.getInt("trip_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offPeakTrips;
    }




}
