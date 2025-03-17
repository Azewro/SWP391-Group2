package dao;

import model.Payment;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminPaymentDAO {
    public List<Payment> getPendingRefunds() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE refund_status = 'Pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setRefundStatus(rs.getString("refund_status"));
                payment.setRefundReason(rs.getString("refund_reason"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public boolean updateRefundStatus(int paymentId, String refundStatus) {
        String sql = "UPDATE Payments SET refund_status = ? WHERE payment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, refundStatus);
            ps.setInt(2, paymentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
