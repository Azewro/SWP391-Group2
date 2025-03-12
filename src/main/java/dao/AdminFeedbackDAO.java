package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.CustomerFeedback;
import util.DatabaseConnection;

public class AdminFeedbackDAO {

    // Lấy danh sách tất cả phản hồi
    public List<CustomerFeedback> getAllFeedbacks() {
        List<CustomerFeedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM CustomerFeedback ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                feedbackList.add(mapResultSetToFeedback(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    // Tìm kiếm phản hồi theo điểm đánh giá (rating)
    public List<CustomerFeedback> searchFeedbackByRating(int rating) {
        List<CustomerFeedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM CustomerFeedback WHERE rating = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, rating);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    feedbackList.add(mapResultSetToFeedback(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    // Duyệt phản hồi (Approve)
    public boolean approveFeedback(int feedbackId) {
        return updateFeedbackStatus(feedbackId, "Approved");
    }

    // Từ chối phản hồi (Reject)
    public boolean rejectFeedback(int feedbackId) {
        return updateFeedbackStatus(feedbackId, "Rejected");
    }

    // Cập nhật trạng thái phản hồi
    private boolean updateFeedbackStatus(int feedbackId, String status) {
        String query = "UPDATE CustomerFeedback SET status = ? WHERE feedback_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, feedbackId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Chuyển ResultSet thành đối tượng CustomerFeedback
    private CustomerFeedback mapResultSetToFeedback(ResultSet rs) throws SQLException {
        return new CustomerFeedback(
                rs.getInt("feedback_id"),
                rs.getInt("ticket_id"),
                rs.getInt("rating"),
                rs.getString("comment"),
                rs.getTimestamp("created_at"),
                rs.getString("status")
        );
    }
}
