package dao;

import model.Location;
import model.Route;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilterRoutesDAO {

    public List<Route> getRoutesByCity(String cityName) throws SQLException {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT r.route_id, r.route_name, l1.name AS start_location, l2.name AS end_location, " +
               "r.distance, r.estimated_duration, r.base_price " +
               "FROM Routes r " +
               "JOIN Locations l1 ON r.start_location_id = l1.location_id " +
               "JOIN Locations l2 ON r.end_location_id = l2.location_id " +
               "JOIN Cities c ON l1.address = c.city_name " +
               "WHERE c.city_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, cityName);

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
