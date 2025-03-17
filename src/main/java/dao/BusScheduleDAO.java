/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Location;
import model.Route;
import util.DatabaseConnection;

/**
 *
 * @author ktleg
 */
public class BusScheduleDAO {
    public List<Route> getBusSchedules() throws SQLException {
    Set<Route> routeSet = new HashSet<>();
    String query = "SELECT DISTINCT r.route_id, r.route_name, l1.name AS start_location, "
                 + "l2.name AS end_location, r.distance, r.estimated_duration, r.base_price "
                 + "FROM Routes r "
                 + "JOIN Locations l1 ON r.start_location_id = l1.location_id "
                 + "JOIN Locations l2 ON r.end_location_id = l2.location_id";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            Route route = new Route();
            route.setRouteId(rs.getInt("route_id"));
            route.setRouteName(rs.getString("route_name"));

            Location startLocation = new Location();
            startLocation.setName(rs.getString("start_location"));
            route.setStartLocation(startLocation);

            Location endLocation = new Location();
            endLocation.setName(rs.getString("end_location"));
            route.setEndLocation(endLocation);

            route.setDistance(rs.getFloat("distance"));
            route.setEstimatedDuration(rs.getInt("estimated_duration"));
            route.setBasePrice(rs.getBigDecimal("base_price"));

            routeSet.add(route);
        }
    }
    return new ArrayList<>(routeSet); // Chuyển Set thành List để tránh trùng
}

}
