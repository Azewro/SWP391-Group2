package dao;

import model.BusStop;
import model.Route;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminBusStopDAO {

    // Lấy danh sách tất cả điểm dừng
    public List<BusStop> getAllBusStops() {
        List<BusStop> busStops = new ArrayList<>();
        String query = "SELECT stop_id, stop_name FROM BusStops";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                busStops.add(new BusStop(
                        rs.getInt("stop_id"),
                        rs.getString("stop_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busStops;
    }

    // Lấy thông tin điểm dừng theo ID
    public BusStop getBusStopById(int stopId) {
        String query = "SELECT * FROM BusStops WHERE stop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stopId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new BusStop(
                        rs.getInt("stop_id"),
                        rs.getString("stop_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm mới một điểm dừng
    public boolean addBusStop(BusStop busStop) {
        String query = "INSERT INTO BusStops (location_id, stop_name, route_id, stop_order, estimated_waiting_time, is_active, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, busStop.getLocationId());
            stmt.setString(2, busStop.getStopName());
            stmt.setInt(3, busStop.getRoute().getRouteId());
            stmt.setInt(4, busStop.getStopOrder());
            stmt.setObject(5, busStop.getEstimatedWaitingTime(), Types.INTEGER);
            stmt.setBoolean(6, busStop.isActive());
            stmt.setString(7, busStop.getDescription());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin điểm dừng
    public boolean updateBusStop(BusStop busStop) {
        String query = "UPDATE BusStops SET location_id = ?, stop_name = ?, route_id = ?, stop_order = ?, estimated_waiting_time = ?, is_active = ?, description = ? WHERE stop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, busStop.getLocationId());
            stmt.setString(2, busStop.getStopName());
            stmt.setInt(3, busStop.getRoute().getRouteId());
            stmt.setInt(4, busStop.getStopOrder());
            stmt.setObject(5, busStop.getEstimatedWaitingTime(), Types.INTEGER);
            stmt.setBoolean(6, busStop.isActive());
            stmt.setString(7, busStop.getDescription());
            stmt.setInt(8, busStop.getStopId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa điểm dừng theo ID
    public boolean deleteBusStop(int stopId) {
        String query = "DELETE FROM BusStops WHERE stop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stopId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<BusStop> searchBusStops(String search, String routeFilter) {
        List<BusStop> busStops = new ArrayList<>();
        String query = "SELECT b.stop_id, b.stop_name, b.route_id, r.route_name, " +
                "b.stop_order, b.estimated_waiting_time, b.is_active " +
                "FROM BusStops b " +
                "JOIN Routes r ON b.route_id = r.route_id " +
                "WHERE 1=1 ";

        if (search != null && !search.isEmpty()) {
            query += "AND b.stop_name LIKE ? ";
        }
        if (routeFilter != null && !routeFilter.isEmpty()) {
            query += "AND b.route_id = ? ";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            if (search != null && !search.isEmpty()) {
                stmt.setString(paramIndex++, "%" + search + "%");
            }
            if (routeFilter != null && !routeFilter.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(routeFilter));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                busStops.add(new BusStop(
                        rs.getInt("stop_id"),
                        rs.getString("stop_name"),
                        new Route(rs.getInt("route_id"), rs.getString("route_name")),
                        rs.getInt("stop_order"),
                        rs.getInt("estimated_waiting_time"),
                        rs.getBoolean("is_active"),
                        ""
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busStops;
    }



        public static void main(String[] args) {
            AdminBusStopDAO busStopDAO = new AdminBusStopDAO();

            // Kiểm tra lấy tất cả điểm dừng
            System.out.println("Danh sách điểm dừng:");
            for (BusStop busStop : busStopDAO.getAllBusStops()) {
                System.out.println(busStop.getStopId() + " - " + busStop.getStopName());
            }

            // Kiểm tra lấy điểm dừng theo ID
            int testStopId = 1; // Cập nhật ID phù hợp với database
            BusStop busStop = busStopDAO.getBusStopById(testStopId);
            if (busStop != null) {
                System.out.println("\nChi tiết điểm dừng:");
                System.out.println("ID: " + busStop.getStopId());
                System.out.println("Tên: " + busStop.getStopName());
            } else {
                System.out.println("\nKhông tìm thấy điểm dừng với ID: " + testStopId);
            }
        }


}
