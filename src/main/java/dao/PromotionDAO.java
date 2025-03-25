package dao;

import model.Promotion;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {
    private Connection conn;

    public PromotionDAO() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }

    /**
     * Lấy danh sách khuyến mãi hợp lệ cho user_id hiện tại
     */
    public List<Promotion> getActivePromotionsForUser(int userId) throws SQLException {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT p.promotion_id, p.promo_code, p.discount_amount, p.discount_percentage, " +
                     "p.valid_from, p.valid_to, p.is_active " +
                     "FROM Promotions p " +
                     "LEFT JOIN UserPromotions up ON p.promo_code = up.promo_code AND up.user_id = ? " +
                     "WHERE p.is_active = TRUE " +
                     "AND p.valid_from <= NOW() AND p.valid_to >= NOW() " +
                     "AND (up.user_id IS NULL OR up.expiration_date >= NOW()) " +
                     "ORDER BY p.valid_to ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Promotion promo = new Promotion(
                        rs.getInt("promotion_id"),
                        rs.getString("promo_code"),
                        rs.getBigDecimal("discount_amount"),
                        rs.getBigDecimal("discount_percentage"),
                        rs.getTimestamp("valid_from"),
                        rs.getTimestamp("valid_to"),
                        rs.getBoolean("is_active")
                );

                promotions.add(promo);
            }
        }
        return promotions;
    }
}
