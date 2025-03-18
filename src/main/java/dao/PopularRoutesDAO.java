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
        String query;
        query = """
                SELECT c.city_name AS start_location, 
                       r.route_id, 
                       r.route_name, 
                       l2.name AS end_location, 
                       r.distance, 
                       r.estimated_duration, 
                       r.base_price,
                       COUNT(t.ticket_id) AS ticket_count
                FROM Routes r
                JOIN Locations l1 ON r.start_location_id = l1.location_id
                JOIN Locations l2 ON r.end_location_id = l2.location_id
                JOIN Cities c ON l1.address = c.city_name
                JOIN BusTrips bt ON r.route_id = bt.route_id
                JOIN Tickets t ON bt.trip_id = t.trip_id
                GROUP BY c.city_name, r.route_id
                ORDER BY ticket_count DESC
                LIMIT 3;""";

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
    
    // 🏆 Thêm hàm main() để kiểm tra truy vấn
    public static void main(String[] args) throws SQLException {
        PopularRoutesDAO p = new PopularRoutesDAO();
        List<Route> routes = p.getPopularRoutes();
        if (routes.isEmpty()) {
            System.out.println("⚠ Không có tuyến xe phổ biến nào được tìm thấy!");
        } else {
            System.out.println("🚍 CÁC TUYẾN XE PHỔ BIẾN:");
            for (Route route : routes) {
                System.out.printf("🔹 %s → %s | %,.1f km | %d giờ | %,d VND\n",
                        route.getStartLocation().getName(), route.getEndLocation().getName(),
                        route.getDistance(), route.getEstimatedDuration(),
                        route.getBasePrice().intValue());
            }
        }
    }
}
