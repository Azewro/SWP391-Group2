package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Location;
import model.Route;
import util.DatabaseConnection;

public class PopularRoutesDAO {
    public List<Route> getPopularRoutes() throws SQLException {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT \n" +
"    c.city_name AS start_location,  -- Cần đặt alias đúng\n" +
"    r.route_id, \n" +
"    r.route_name, \n" +
"    l2.name AS end_location, \n" +
"    r.distance, \n" +
"    r.estimated_duration, \n" +
"    r.base_price\n" +
"FROM Routes r\n" +
"JOIN Locations l1 ON r.start_location_id = l1.location_id\n" +
"JOIN Locations l2 ON r.end_location_id = l2.location_id\n" +
"JOIN Cities c ON l1.address = c.city_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
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
        return routes;
    }
}
