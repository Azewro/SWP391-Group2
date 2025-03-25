package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.Promotion;
import util.DatabaseConnection;

public class PromotionsDAO {

    // ✅ Lấy tất cả khuyến mãi đang hoạt động
    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM Promotions WHERE is_active = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                promotions.add(extractPromotionFromResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return promotions;
    }

    // ✅ Lấy khuyến mãi theo promoCode
    public Promotion getPromotionByCode(String promoCode) {
        String sql = "SELECT * FROM Promotions WHERE promo_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, promoCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractPromotionFromResultSet(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Dùng đúng constructor khớp với BigDecimal
    private Promotion extractPromotionFromResultSet(ResultSet rs) throws Exception {
        int id = rs.getInt("promotion_id");
        String code = rs.getString("promo_code");
        BigDecimal amount = rs.getBigDecimal("discount_amount");
        BigDecimal percent = rs.getBigDecimal("discount_percentage");
        Timestamp validFrom = rs.getTimestamp("valid_from");
        Timestamp validTo = rs.getTimestamp("valid_to");
        boolean isActive = rs.getBoolean("is_active");

        return new Promotion(id, code, amount, percent, validFrom, validTo, isActive);
    }
}
