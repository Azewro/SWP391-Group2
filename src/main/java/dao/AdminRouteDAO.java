package dao;

import model.Route;
import model.Location;
import util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRouteDAO {

    // Lấy danh sách tất cả tuyến đường
    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT r.route_id, r.route_name, "
                + "r.start_location_id, s.name AS start_location_name, "
                + "r.end_location_id, e.name AS end_location_name, "
                + "r.distance, r.estimated_duration, r.base_price "
                + "FROM Routes r "
                + "JOIN Locations s ON r.start_location_id = s.location_id "
                + "JOIN Locations e ON r.end_location_id = e.location_id";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Route route = new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        new Location(rs.getInt("start_location_id"), rs.getString("start_location_name")),
                        new Location(rs.getInt("end_location_id"), rs.getString("end_location_name")),
                        rs.getFloat("distance"),
                        rs.getInt("estimated_duration"),
                        rs.getBigDecimal("base_price")
                );
                routes.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    // Lấy thông tin tuyến đường theo ID
    public Route getRouteById(int routeId) {
        String query = "SELECT * FROM Routes WHERE route_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, routeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        new Location(rs.getInt("start_location_id")),
                        new Location(rs.getInt("end_location_id")),
                        rs.getFloat("distance"),
                        rs.getInt("estimated_duration"),
                        rs.getBigDecimal("base_price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm mới một tuyến đường
    public boolean addRoute(Route route) {
        String query = "INSERT INTO Routes (route_name, start_location_id, end_location_id, distance, estimated_duration, base_price, estimated_stops, route_type) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, route.getRouteName());
            stmt.setInt(2, route.getStartLocation().getLocationId());
            stmt.setInt(3, route.getEndLocation().getLocationId());
            stmt.setFloat(4, route.getDistance());
            stmt.setInt(5, route.getEstimatedDuration());
            stmt.setBigDecimal(6, route.getBasePrice());
            stmt.setObject(7, route.getEstimatedStops(), Types.INTEGER);
            stmt.setString(8, route.getRouteType());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin tuyến đường
    public boolean updateRoute(Route route) {
        String query = "UPDATE Routes SET route_name = ?, start_location_id = ?, end_location_id = ?, distance = ?, estimated_duration = ?, base_price = ?, estimated_stops = ?, route_type = ? "
                + "WHERE route_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, route.getRouteName());
            stmt.setInt(2, route.getStartLocation().getLocationId());
            stmt.setInt(3, route.getEndLocation().getLocationId());
            stmt.setFloat(4, route.getDistance());
            stmt.setInt(5, route.getEstimatedDuration());
            stmt.setBigDecimal(6, route.getBasePrice());
            stmt.setObject(7, route.getEstimatedStops(), Types.INTEGER);
            stmt.setString(8, route.getRouteType());
            stmt.setInt(9, route.getRouteId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa tuyến đường theo ID
    public boolean deleteRoute(int routeId) {
        String query = "DELETE FROM Routes WHERE route_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, routeId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Route> searchRoutes(String search, String locationFilter) {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT r.route_id, r.route_name, "
                + "r.start_location_id, s.name AS start_location_name, "
                + "r.end_location_id, e.name AS end_location_name, "
                + "r.distance, r.estimated_duration, r.base_price "
                + "FROM Routes r "
                + "JOIN Locations s ON r.start_location_id = s.location_id "
                + "JOIN Locations e ON r.end_location_id = e.location_id "
                + "WHERE 1=1 ";

        if (search != null && !search.isEmpty()) {
            query += "AND r.route_name LIKE ? ";
        }
        if (locationFilter != null && !locationFilter.isEmpty()) {
            query += "AND (r.start_location_id = ? OR r.end_location_id = ?) ";
        }

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            if (search != null && !search.isEmpty()) {
                stmt.setString(paramIndex++, "%" + search + "%");
            }
            if (locationFilter != null && !locationFilter.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(locationFilter));
                stmt.setInt(paramIndex++, Integer.parseInt(locationFilter));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                routes.add(new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        new Location(rs.getInt("start_location_id"), rs.getString("start_location_name")),
                        new Location(rs.getInt("end_location_id"), rs.getString("end_location_name")),
                        rs.getFloat("distance"),
                        rs.getInt("estimated_duration"),
                        rs.getBigDecimal("base_price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    public static void main(String[] args) {
        AdminRouteDAO routeDAO = new AdminRouteDAO();

        // Kiểm tra lấy tất cả tuyến đường
        System.out.println("Danh sách tuyến đường:");
        for (Route route : routeDAO.getAllRoutes()) {
            System.out.println(route.getRouteId() + " - " + route.getRouteName());
        }

        // Kiểm tra lấy tuyến đường theo ID
        int testRouteId = 1; // Cập nhật ID phù hợp với database
        Route route = routeDAO.getRouteById(testRouteId);
        if (route != null) {
            System.out.println("\nChi tiết tuyến đường:");
            System.out.println("ID: " + route.getRouteId());
            System.out.println("Tên: " + route.getRouteName());
        } else {
            System.out.println("\nKhông tìm thấy tuyến đường với ID: " + testRouteId);
        }
    }

    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        String query = "SELECT location_id, name FROM Locations";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Location location = new Location(
                        rs.getInt("location_id"),
                        rs.getString("name")
                );
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    public Route getRoute(int id) {
        String query = "SELECT r.route_id, r.route_name, "
                + "r.start_location_id, s.name AS start_location_name, "
                + "r.end_location_id, e.name AS end_location_name, "
                + "r.distance, r.estimated_duration, r.base_price "
                + "FROM Routes r "
                + "JOIN Locations s ON r.start_location_id = s.location_id "
                + "JOIN Locations e ON r.end_location_id = e.location_id where r.route_id = " + id;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Route route = new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        new Location(rs.getInt("start_location_id"), rs.getString("start_location_name")),
                        new Location(rs.getInt("end_location_id"), rs.getString("end_location_name")),
                        rs.getFloat("distance"),
                        rs.getInt("estimated_duration"),
                        rs.getBigDecimal("base_price")
                );
                return route;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
