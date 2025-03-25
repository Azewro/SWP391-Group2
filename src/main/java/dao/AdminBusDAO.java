package dao;

import model.Bus;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminBusDAO {

    // Lấy danh sách tất cả xe buýt
    public List<Bus> getAllBuses() {
        List<Bus> busList = new ArrayList<>();
        String query = "SELECT * FROM Bus";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bus bus = new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("plate_number"),
                        rs.getInt("capacity"),
                        rs.getString("bus_type"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("last_maintenance") != null ? rs.getTimestamp("last_maintenance").toLocalDateTime() : null
                );
                busList.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busList;
    }

    // Tìm xe buýt theo ID
    public Bus getBusById(int busId) {
        String query = "SELECT * FROM Bus WHERE bus_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, busId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("plate_number"),
                        rs.getInt("capacity"),
                        rs.getString("bus_type"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("last_maintenance") != null ? rs.getTimestamp("last_maintenance").toLocalDateTime() : null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm một xe buýt mới
    public boolean insertBus(Bus bus) {
        String query = "INSERT INTO Bus (plate_number, capacity, bus_type, is_active, last_maintenance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, bus.getPlateNumber());
            stmt.setInt(2, bus.getCapacity());
            stmt.setString(3, bus.getBusType());
            stmt.setBoolean(4, bus.isActive());
            stmt.setTimestamp(5, bus.getLastMaintenance() != null ? Timestamp.valueOf(bus.getLastMaintenance()) : null);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Bus> searchBusByPlateNumber(String keyword) {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM Bus WHERE plate_number LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bus bus = new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("plate_number"),
                        rs.getInt("capacity"),
                        rs.getString("bus_type"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("last_maintenance") != null ? rs.getTimestamp("last_maintenance").toLocalDateTime() : null
                );
                buses.add(bus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buses;
    }


    // Cập nhật thông tin xe buýt
    public boolean updateBus(Bus bus) {
        String query = "UPDATE Bus SET plate_number = ?, capacity = ?, bus_type = ?, is_active = ?, last_maintenance = ? WHERE bus_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bus.getPlateNumber());
            stmt.setInt(2, bus.getCapacity());
            stmt.setString(3, bus.getBusType());
            stmt.setBoolean(4, bus.isActive());
            stmt.setTimestamp(5, bus.getLastMaintenance() != null ? Timestamp.valueOf(bus.getLastMaintenance()) : null);
            stmt.setInt(6, bus.getBusId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa xe buýt theo ID
    public boolean deleteBus(int busId) {
        String query = "DELETE FROM Bus WHERE bus_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, busId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tìm kiếm xe buýt theo biển số
    public List<Bus> searchBusesByPlate(String keyword) {
        List<Bus> busList = new ArrayList<>();
        String query = "SELECT * FROM Bus WHERE plate_number LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bus bus = new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("plate_number"),
                        rs.getInt("capacity"),
                        rs.getString("bus_type"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("last_maintenance") != null ? rs.getTimestamp("last_maintenance").toLocalDateTime() : null
                );
                busList.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busList;
    }

    // Lấy danh sách xe buýt theo trang
    public List<Bus> getBusesByPage(int offset, int limit) {
        List<Bus> busList = new ArrayList<>();
        String query = "SELECT * FROM Bus LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bus bus = new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("plate_number"),
                        rs.getInt("capacity"),
                        rs.getString("bus_type"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("last_maintenance") != null ? rs.getTimestamp("last_maintenance").toLocalDateTime() : null
                );
                busList.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busList;
    }

    // Đếm tổng số xe buýt
    public int getTotalBusCount() {
        String query = "SELECT COUNT(*) FROM Bus";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
