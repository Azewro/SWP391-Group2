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
    public ArrayList<Route> getPopularRoutes() throws SQLException {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT r.route_id, r.route_name, r.start_location_id, r.end_location_id, r.distance, r.estimated_duration, c.city_name AS start_city, l2.name AS end_city, r.base_price\n" +
"FROM Routes r\n" +
"JOIN Locations l ON r.start_location_id = l.location_id\n" +
"JOIN Cities c ON l.address = c.city_name\n" +
"JOIN Locations l2 ON r.end_location_id = l2.location_id\n" +
"JOIN (\n" +
"   SELECT city_name\n" +
" FROM Cities\n" +
" LEFT JOIN Locations ON Cities.city_name = Locations.address\n" +
"   LEFT JOIN Routes ON Routes.start_location_id = Locations.location_id\n" +
"    GROUP BY city_name\n" +
"ORDER BY COUNT(Routes.route_id) DESC\n" +
" LIMIT 3\n" +
") AS popularCities ON c.city_name = popularCities.city_name\n" +
"ORDER BY c.city_name, r.estimated_duration\n" +
"LIMIT 3";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
    Route route = new Route();
    route.setRouteId(rs.getInt("route_id"));
    route.setRouteName(rs.getString("route_name"));

    Location startLocation = new Location();
    startLocation.setName(rs.getString("start_city"));
    route.setStartLocation(startLocation);

    Location endLocation = new Location();
    endLocation.setName(rs.getString("end_city"));
    route.setEndLocation(endLocation);

    route.setDistance(rs.getFloat("distance"));
    route.setEstimatedDuration(rs.getInt("estimated_duration"));
    route.setBasePrice(rs.getBigDecimal("base_price"));

    routes.add(route);
}

        }
        return (ArrayList<Route>) routes;
    }
}
