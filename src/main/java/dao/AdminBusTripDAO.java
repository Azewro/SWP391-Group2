package dao;

import model.BusTrip;
import model.Route;
import model.Bus;
import model.User;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminBusTripDAO {

    // Lấy danh sách tất cả chuyến xe
    public List<BusTrip> getAllBusTrips() {
        List<BusTrip> busTrips = new ArrayList<>();
        String sql = "SELECT * FROM BusTrips";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BusTrip trip = extractBusTripFromResultSet(rs);
                busTrips.add(trip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busTrips;
    }

    // Lấy thông tin một chuyến xe theo ID
    public BusTrip getBusTripById(int tripId) {
        String sql = "SELECT * FROM BusTrips WHERE trip_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tripId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractBusTripFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm một chuyến xe mới
    public boolean addBusTrip(BusTrip trip) {
        String sql = "INSERT INTO BusTrips (route_id, bus_id, driver_id, departure_time, arrival_time, status, available_seats, current_price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, trip.getRoute().getRouteId());
            ps.setInt(2, trip.getBus().getBusId());
            ps.setInt(3, trip.getDriver().getUserId());
            ps.setTimestamp(4, Timestamp.valueOf(trip.getDepartureTime()));
            ps.setTimestamp(5, trip.getArrivalTime() != null ? Timestamp.valueOf(trip.getArrivalTime()) : null);
            ps.setString(6, trip.getStatus());
            ps.setInt(7, trip.getAvailableSeats());
            ps.setBigDecimal(8, trip.getCurrentPrice());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin chuyến xe
    public boolean updateBusTrip(BusTrip trip) {
        String sql = "UPDATE BusTrips SET route_id = ?, bus_id = ?, driver_id = ?, departure_time = ?, arrival_time = ?, status = ?, " +
                "available_seats = ?, current_price = ? WHERE trip_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, trip.getRoute().getRouteId());
            ps.setInt(2, trip.getBus().getBusId());
            ps.setInt(3, trip.getDriver().getUserId());
            ps.setTimestamp(4, Timestamp.valueOf(trip.getDepartureTime()));
            ps.setTimestamp(5, trip.getArrivalTime() != null ? Timestamp.valueOf(trip.getArrivalTime()) : null);
            ps.setString(6, trip.getStatus());
            ps.setInt(7, trip.getAvailableSeats());
            ps.setBigDecimal(8, trip.getCurrentPrice());
            ps.setInt(9, trip.getTripId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa một chuyến xe
    public boolean deleteBusTrip(int tripId) {
        String sql = "DELETE FROM BusTrips WHERE trip_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tripId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức hỗ trợ để tạo đối tượng BusTrip từ ResultSet
    private BusTrip extractBusTripFromResultSet(ResultSet rs) throws SQLException {
        BusTrip trip = new BusTrip();
        trip.setTripId(rs.getInt("trip_id"));

        Route route = new Route();
        route.setRouteId(rs.getInt("route_id"));
        trip.setRoute(route);

        Bus bus = new Bus();
        bus.setBusId(rs.getInt("bus_id"));
        trip.setBus(bus);

        User driver = new User();
        driver.setUserId(rs.getInt("driver_id"));
        trip.setDriver(driver);

        trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        if (rs.getTimestamp("arrival_time") != null) {
            trip.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
        }
        trip.setStatus(rs.getString("status"));
        trip.setAvailableSeats(rs.getInt("available_seats"));
        trip.setCurrentPrice(rs.getBigDecimal("current_price"));
        trip.setDelayReason(rs.getString("delay_reason"));

        return trip;
    }

    public List<User> getAllDrivers() {
        List<User> drivers = new ArrayList<>();
        String sql = "SELECT user_id, full_name FROM Users WHERE role_id = 4 AND is_active = TRUE";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User driver = new User();
                driver.setUserId(rs.getInt("user_id"));
                driver.setFullName(rs.getString("full_name"));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }


}
