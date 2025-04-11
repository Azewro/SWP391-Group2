package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import model.Bus;
import model.BusTrip;
import model.Location;
import model.Route;
import model.User;
import util.DatabaseConnection;

public class BusTripDAO {

    public static List<BusTrip> findTrips(int startLocationId, int endLocationId, String departureDate) {
        List<BusTrip> list = new ArrayList<>();

        String sql = """
            SELECT bt.*, 
                   r.route_id, r.route_name, r.distance, r.estimated_duration, r.base_price, 
                   r.estimated_stops, r.route_type,
                   
                   sl.location_id AS start_location_id, sl.name AS start_location_name,
                   el.location_id AS end_location_id, el.name AS end_location_name,

                   b.bus_id, b.plate_number, b.capacity, b.bus_type,
                   u.user_id, u.full_name
            FROM BusTrips bt
            JOIN Routes r ON bt.route_id = r.route_id
            JOIN Locations sl ON r.start_location_id = sl.location_id
            JOIN Locations el ON r.end_location_id = el.location_id
            JOIN Bus b ON bt.bus_id = b.bus_id
            JOIN Users u ON bt.driver_id = u.user_id
            WHERE r.start_location_id = ? AND r.end_location_id = ?
              AND DATE(bt.departure_time) = ?
              AND bt.status = 'Scheduled'
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, startLocationId);
            ps.setInt(2, endLocationId);
            ps.setString(3, departureDate); // yyyy-MM-dd

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BusTrip trip = new BusTrip();

                // Set basic trip fields
                trip.setTripId(rs.getInt("trip_id"));
                trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());

                Timestamp arr = rs.getTimestamp("arrival_time");
                if (arr != null) trip.setArrivalTime(arr.toLocalDateTime());

                Timestamp actualArr = rs.getTimestamp("actual_arrival");
                if (actualArr != null) trip.setActualArrival(actualArr.toLocalDateTime());

                trip.setStatus(rs.getString("status"));
                trip.setAvailableSeats(rs.getInt("available_seats"));
                trip.setCurrentPrice(rs.getBigDecimal("current_price"));
                trip.setDelayReason(rs.getString("delay_reason"));

                // Set Route and its Locations
                Location startLoc = new Location();
                startLoc.setLocationId(rs.getInt("start_location_id"));
                startLoc.setName(rs.getString("start_location_name"));

                Location endLoc = new Location();
                endLoc.setLocationId(rs.getInt("end_location_id"));
                endLoc.setName(rs.getString("end_location_name"));

                Route route = new Route();
                route.setRouteId(rs.getInt("route_id"));
                route.setRouteName(rs.getString("route_name"));
                route.setStartLocation(startLoc);
                route.setEndLocation(endLoc);
                route.setDistance(rs.getFloat("distance"));
                route.setEstimatedDuration(rs.getInt("estimated_duration"));
                route.setBasePrice(rs.getBigDecimal("base_price"));
                route.setEstimatedStops(rs.getObject("estimated_stops") != null ? rs.getInt("estimated_stops") : null);
                route.setRouteType(rs.getString("route_type"));

                trip.setRoute(route);

                // Set Bus
                Bus bus = new Bus();
                bus.setBusId(rs.getInt("bus_id"));
                bus.setPlateNumber(rs.getString("plate_number"));
                bus.setCapacity(rs.getInt("capacity"));
                bus.setBusType(rs.getString("bus_type"));
                trip.setBus(bus);

                // Set Driver
                User driver = new User();
                driver.setUserId(rs.getInt("user_id"));
                driver.setFullName(rs.getString("full_name"));
                trip.setDriver(driver);

                list.add(trip);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public static List<BusTrip> findTripsByRouteAndDate(int routeId, String departureDate) {
    List<BusTrip> list = new ArrayList<>();

    String sql = """
        SELECT bt.*, 
               r.route_name,
               s.location_id AS start_id, s.name AS start_name,
               e.location_id AS end_id, e.name AS end_name,
               b.bus_id, b.plate_number, b.capacity, b.bus_type,
               u.user_id, u.full_name
        FROM BusTrips bt
        JOIN Routes r ON bt.route_id = r.route_id
        JOIN Locations s ON r.start_location_id = s.location_id
        JOIN Locations e ON r.end_location_id = e.location_id
        JOIN Bus b ON bt.bus_id = b.bus_id
        JOIN Users u ON bt.driver_id = u.user_id
        WHERE bt.route_id = ? 
          AND DATE(bt.departure_time) = ? 
          AND bt.status = 'Scheduled'
    """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, routeId);
        ps.setString(2, departureDate);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            BusTrip trip = new BusTrip();
            trip.setTripId(rs.getInt("trip_id"));
            trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
            if (rs.getTimestamp("arrival_time") != null)
                trip.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
            trip.setAvailableSeats(rs.getInt("available_seats"));
            trip.setCurrentPrice(rs.getBigDecimal("current_price"));
            trip.setStatus(rs.getString("status"));

            // Set Route
            Route route = new Route();
            route.setRouteId(routeId);
            route.setRouteName(rs.getString("route_name"));

            // Start Location
            Location start = new Location();
            start.setLocationId(rs.getInt("start_id"));
            start.setName(rs.getString("start_name"));

            // End Location
            Location end = new Location();
            end.setLocationId(rs.getInt("end_id"));
            end.setName(rs.getString("end_name"));

            route.setStartLocation(start);
            route.setEndLocation(end);

            trip.setRoute(route);

            // Set Bus
            Bus bus = new Bus();
            bus.setBusId(rs.getInt("bus_id"));
            bus.setPlateNumber(rs.getString("plate_number"));
            bus.setCapacity(rs.getInt("capacity"));
            bus.setBusType(rs.getString("bus_type"));
            trip.setBus(bus);

            // Set Driver
            User driver = new User();
            driver.setUserId(rs.getInt("user_id"));
            driver.setFullName(rs.getString("full_name"));
            trip.setDriver(driver);

            list.add(trip);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

public static BusTrip getTripById(int tripId) {
    BusTrip trip = new BusTrip();
    String sql = """
        SELECT bt.*, r.*, l1.name AS start_name, l2.name AS end_name
        FROM BusTrips bt
        JOIN Routes r ON bt.route_id = r.route_id
        JOIN Locations l1 ON r.start_location_id = l1.location_id
        JOIN Locations l2 ON r.end_location_id = l2.location_id
        WHERE bt.trip_id = ?
    """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, tripId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            trip.setTripId(rs.getInt("trip_id"));
            trip.setCurrentPrice(rs.getBigDecimal("current_price"));
            Route route = new Route();
            route.setRouteId(rs.getInt("route_id"));
            route.setRouteName(rs.getString("route_name"));
            trip.setRoute(route);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return trip;
}


    public static void updateAvailableSeats(int tripId, int delta) {
        String query = "UPDATE BusTrips SET available_seats = available_seats + ? WHERE trip_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, delta); // nếu delta là âm → trừ chỗ
            stmt.setInt(2, tripId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // nên ghi log hoặc xử lý lỗi
        }
    }

}
