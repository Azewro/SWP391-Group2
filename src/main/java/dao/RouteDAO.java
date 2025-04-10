package dao;

import model.Location;
import model.Route;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {

    public static List<Route> getAllRoutesWithLocation() {
        List<Route> routes = new ArrayList<>();

        String sql = """
            SELECT r.*, 
                   sl.location_id AS start_id, sl.name AS start_name,
                   el.location_id AS end_id, el.name AS end_name
            FROM Routes r
            JOIN Locations sl ON r.start_location_id = sl.location_id
            JOIN Locations el ON r.end_location_id = el.location_id
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng Route
                Route route = new Route();
                route.setRouteId(rs.getInt("route_id"));
                route.setRouteName(rs.getString("route_name"));
                route.setDistance(rs.getFloat("distance"));
                route.setEstimatedDuration(rs.getInt("estimated_duration"));
                route.setBasePrice(rs.getBigDecimal("base_price"));
                route.setEstimatedStops(rs.getInt("estimated_stops"));
                route.setRouteType(rs.getString("route_type"));

                // Tạo đối tượng Location (start)
                Location start = new Location();
                start.setLocationId(rs.getInt("start_id"));
                start.setName(rs.getString("start_name"));

                // Tạo đối tượng Location (end)
                Location end = new Location();
                end.setLocationId(rs.getInt("end_id"));
                end.setName(rs.getString("end_name"));

                route.setStartLocation(start);
                route.setEndLocation(end);

                routes.add(route);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return routes;
    }
}
