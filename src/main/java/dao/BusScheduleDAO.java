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
import java.util.List;
import model.Location;
import model.Route;
import util.DatabaseConnection;

/**
 *
 * @author ktleg
 */
public class BusScheduleDAO {
    public List<Route> getBusSchedule(String startLocation, String endLocation) throws SQLException {
    List<Route> routes = new ArrayList<>();
    String query = "SELECT r.route_id, r.route_name, l1.name AS start_location, l2.name AS end_location, " +
            "r.distance, r.estimated_duration, r.base_price, b.bus_type " +
            "FROM Routes r " +
            "JOIN Locations l1 ON r.start_location_id = l1.location_id " +
            "JOIN Locations l2 ON r.end_location_id = l2.location_id " +
            "JOIN BusTrips bt ON bt.route_id = r.route_id " + // Dùng BusTrips để kết nối Routes và Bus
            "JOIN Bus b ON b.bus_id = bt.bus_id " +
            "WHERE l1.name LIKE ? AND l2.name LIKE ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setString(1, "%" + startLocation + "%");
        ps.setString(2, "%" + endLocation + "%");

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
