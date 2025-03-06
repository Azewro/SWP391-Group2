package dao;

import model.BusMaintenanceLog;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminBusMaintenanceDAO {

    // Lấy danh sách lịch sử bảo trì của một xe buýt
    public List<BusMaintenanceLog> getMaintenanceLogsByBusId(int busId) {
        List<BusMaintenanceLog> logs = new ArrayList<>();
        String query = "SELECT * FROM BusMaintenanceLogs WHERE bus_id = ? ORDER BY maintenance_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, busId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BusMaintenanceLog log = new BusMaintenanceLog(
                        rs.getInt("log_id"),
                        rs.getInt("bus_id"),
                        rs.getTimestamp("maintenance_date").toLocalDateTime(),
                        rs.getString("description"),
                        rs.getBigDecimal("cost"),
                        rs.getString("status")
                );
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    // Thêm một bản ghi bảo trì mới
    public boolean insertMaintenanceLog(BusMaintenanceLog log) {
        String query = "INSERT INTO BusMaintenanceLogs (bus_id, maintenance_date, description, cost, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, log.getBusId());
            stmt.setTimestamp(2, Timestamp.valueOf(log.getMaintenanceDate()));
            stmt.setString(3, log.getDescription());
            stmt.setBigDecimal(4, log.getCost());
            stmt.setString(5, log.getStatus());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin bảo trì
    public boolean updateMaintenanceLog(BusMaintenanceLog log) {
        String query = "UPDATE BusMaintenanceLogs SET maintenance_date = ?, description = ?, cost = ?, status = ? WHERE log_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, Timestamp.valueOf(log.getMaintenanceDate()));
            stmt.setString(2, log.getDescription());
            stmt.setBigDecimal(3, log.getCost());
            stmt.setString(4, log.getStatus());
            stmt.setInt(5, log.getLogId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa một bản ghi bảo trì theo ID
    public boolean deleteMaintenanceLog(int logId) {
        String query = "DELETE FROM BusMaintenanceLogs WHERE log_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, logId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
