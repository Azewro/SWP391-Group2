package dao;

import model.Promotion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminPromotionDAO {
    private Connection conn;

    public AdminPromotionDAO(Connection conn) {
        this.conn = conn;
    }

    // 1️⃣ Lấy danh sách tất cả chương trình khuyến mãi
    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        String query = "SELECT * FROM Promotions ORDER BY valid_to DESC";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                promotions.add(new Promotion(
                        rs.getInt("promotion_id"),
                        rs.getString("promo_code"),
                        rs.getDouble("discount_amount"),
                        rs.getDouble("discount_percentage"),
                        rs.getTimestamp("valid_from"),
                        rs.getTimestamp("valid_to"),
                        rs.getBoolean("is_active")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    // 2️⃣ Thêm mới một chương trình khuyến mãi
    public boolean addPromotion(Promotion promo) {
        String query = "INSERT INTO Promotions (promo_code, discount_amount, discount_percentage, valid_from, valid_to, is_active) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, promo.getPromoCode());
            stmt.setDouble(2, promo.getDiscountAmount());
            stmt.setDouble(3, promo.getDiscountPercentage());
            stmt.setTimestamp(4, promo.getValidFrom());
            stmt.setTimestamp(5, promo.getValidTo());
            stmt.setBoolean(6, promo.isActive());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3️⃣ Cập nhật thông tin chương trình khuyến mãi
    public boolean updatePromotion(Promotion promo) {
        String query = "UPDATE Promotions SET promo_code = ?, discount_amount = ?, discount_percentage = ?, valid_from = ?, valid_to = ?, is_active = ? WHERE promotion_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, promo.getPromoCode());
            stmt.setDouble(2, promo.getDiscountAmount());
            stmt.setDouble(3, promo.getDiscountPercentage());
            stmt.setTimestamp(4, promo.getValidFrom());
            stmt.setTimestamp(5, promo.getValidTo());
            stmt.setBoolean(6, promo.isActive());
            stmt.setInt(7, promo.getPromotionId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4️⃣ Vô hiệu hóa (Disable) một chương trình khuyến mãi
    public boolean disablePromotion(int promoId) {
        String query = "UPDATE Promotions SET is_active = FALSE WHERE promotion_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, promoId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5️⃣ Lấy thông tin một chương trình khuyến mãi theo ID
    public Promotion getPromotionById(int promoId) {
        String query = "SELECT * FROM Promotions WHERE promotion_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, promoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Promotion(
                        rs.getInt("promotion_id"),
                        rs.getString("promo_code"),
                        rs.getDouble("discount_amount"),
                        rs.getDouble("discount_percentage"),
                        rs.getTimestamp("valid_from"),
                        rs.getTimestamp("valid_to"),
                        rs.getBoolean("is_active")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
