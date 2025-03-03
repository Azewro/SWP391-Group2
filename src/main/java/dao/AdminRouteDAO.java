package dao;

import model.Route;
import model.Location;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRouteDAO {
    private Connection conn;

    public AdminRouteDAO() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }

    // Lấy danh sách tuyến đường (tìm kiếm theo tên hoặc lọc theo địa điểm)
    public List<Route> getAllRoutes(String search, Integer locationId) throws SQLException {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT r.route_id, r.route_name, r.distance, r.estimated_duration, r.base_price, " +
                "l1.location_id AS start_location_id, l1.name AS start_location_name, " +
                "l2.location_id AS end_location_id, l2.name AS end_location_name " +
                "FROM Routes r " +
                "JOIN Locations l1 ON r.start_location_id = l1.location_id " +
                "JOIN Locations l2 ON r.end_location_id = l2.location_id " +
                "WHERE (? IS NULL OR r.route_name LIKE ?) " +
                "AND (? IS NULL OR r.start_location_id = ? OR r.end_location_id = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, search);
            stmt.setString(2, "%" + search + "%");
            if (locationId == null) {
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.INTEGER);
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(3, locationId);
                stmt.setInt(4, locationId);
                stmt.setInt(5, locationId);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Location startLocation = new Location(rs.getInt("start_location_id"), rs.getString("start_location_name"));
                Location endLocation = new Location(rs.getInt("end_location_id"), rs.getString("end_location_name"));
                Route route = new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        startLocation,
                        endLocation,
                        rs.getFloat("distance"),
                        rs.getInt("estimated_duration"),
                        rs.getBigDecimal("base_price")
                );
                routes.add(route);
            }
        }
        return routes;
    }

    public Route getRouteById(int routeId) throws SQLException {
        String sql = "SELECT r.route_id, r.route_name, r.distance, r.estimated_duration, r.base_price, " +
                "l1.location_id AS start_location_id, l1.name AS start_location_name, " +
                "l2.location_id AS end_location_id, l2.name AS end_location_name " +
                "FROM Routes r " +
                "JOIN Locations l1 ON r.start_location_id = l1.location_id " +
                "JOIN Locations l2 ON r.end_location_id = l2.location_id " +
                "WHERE r.route_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, routeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Location startLocation = new Location(rs.getInt("start_location_id"), rs.getString("start_location_name"));
                Location endLocation = new Location(rs.getInt("end_location_id"), rs.getString("end_location_name"));
                return new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        startLocation,
                        endLocation,
                        rs.getFloat("distance"),
                        rs.getInt("estimated_duration"),
                        rs.getBigDecimal("base_price")
                );
            }
        }
        return null;
    }


    // Thêm tuyến đường mới
    public boolean addRoute(Route route) throws SQLException {
        String sql = "INSERT INTO Routes (route_name, start_location_id, end_location_id, distance, estimated_duration, base_price) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, route.getRouteName());
            stmt.setInt(2, route.getStartLocation().getLocationId());
            stmt.setInt(3, route.getEndLocation().getLocationId());
            stmt.setFloat(4, route.getDistance());
            stmt.setInt(5, route.getEstimatedDuration());
            stmt.setBigDecimal(6, route.getBasePrice());
            return stmt.executeUpdate() > 0;
        }
    }

    // Cập nhật thông tin tuyến đường
    public boolean updateRoute(Route route) throws SQLException {
        String sql = "UPDATE Routes SET route_name = ?, start_location_id = ?, end_location_id = ?, " +
                "distance = ?, estimated_duration = ?, base_price = ? WHERE route_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, route.getRouteName());
            stmt.setInt(2, route.getStartLocation().getLocationId());
            stmt.setInt(3, route.getEndLocation().getLocationId());
            stmt.setFloat(4, route.getDistance());
            stmt.setInt(5, route.getEstimatedDuration());
            stmt.setBigDecimal(6, route.getBasePrice());
            stmt.setInt(7, route.getRouteId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa tuyến đường (xóa cứng)
    public boolean deleteRoute(int routeId) throws SQLException {
        String sql = "DELETE FROM Routes WHERE route_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, routeId);
            return stmt.executeUpdate() > 0;
        }
    }
}
