package dao;

import model.Location;
import model.Route;
import util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT r.route_id, r.route_name, l1.*, l2.*, r.distance, r.estimated_duration, r.base_price, r.estimated_stops, r.route_type FROM Routes r JOIN Locations l1 ON r.start_location_id = l1.location_id JOIN Locations l2 ON r.end_location_id = l2.location_id")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                routes.add(new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        new Location(rs.getInt("l1.location_id"), rs.getString("l1.name")),
                        new Location(rs.getInt("l2.location_id"), rs.getString("l2.name")),
                        rs.getFloat("distance"),
                        rs.getInt("estimated_duration"),
                        rs.getBigDecimal("base_price"),
                        rs.getInt("estimated_stops"),
                        rs.getString("route_type")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }


}
