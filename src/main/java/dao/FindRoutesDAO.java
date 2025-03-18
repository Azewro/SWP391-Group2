package dao;

import model.Location;
import model.Route;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FindRoutesDAO {

    public List<Route> findRoutes(String startLocation, String endLocation) throws SQLException {
        List<Route> routes = new ArrayList<>();

        String query = "SELECT r.route_id, r.route_name, l1.name AS start_location, l2.name AS end_location, " +
                "r.distance, r.estimated_duration, r.base_price " +
                "FROM Routes r " +
                "JOIN Locations l1 ON r.start_location_id = l1.location_id " +
                "JOIN Locations l2 ON r.end_location_id = l2.location_id " +
                "WHERE (? IS NULL OR l1.name LIKE ?) " +
                "AND (? IS NULL OR l2.name LIKE ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Nếu người dùng không nhập, truyền null để tránh lỗi truy vấn
            ps.setString(1, startLocation.isEmpty() ? null : "%" + startLocation + "%");
            ps.setString(2, startLocation.isEmpty() ? null : "%" + startLocation + "%");
            ps.setString(3, endLocation.isEmpty() ? null : "%" + endLocation + "%");
            ps.setString(4, endLocation.isEmpty() ? null : "%" + endLocation + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Route route = new Route();
                    route.setRouteId(rs.getInt("route_id"));
                    route.setRouteName(rs.getString("route_name"));

                    Location startLoc = new Location();
                    startLoc.setName(rs.getString("start_location"));
                    route.setStartLocation(startLoc);

                    Location endLoc = new Location();
                    endLoc.setName(rs.getString("end_location"));
                    route.setEndLocation(endLoc);

                    route.setDistance(rs.getFloat("distance"));
                    route.setEstimatedDuration(rs.getInt("estimated_duration"));
                    route.setBasePrice(rs.getBigDecimal("base_price"));

                    routes.add(route);
                }
            }
        }
        return routes;
    }
}
