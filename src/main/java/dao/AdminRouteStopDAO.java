package dao;

import model.RouteStop;
import model.Route;
import model.BusStop;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRouteStopDAO {
    private Connection conn;

    public AdminRouteStopDAO() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }

    // Lấy danh sách điểm dừng của tuyến đường
    public List<RouteStop> getRouteStops(int routeId) throws SQLException {
        List<RouteStop> routeStops = new ArrayList<>();
        String sql = "SELECT rs.id, rs.stop_order, rs.distance_from_start, rs.estimated_time_from_start, " +
                "bs.stop_id, bs.stop_name " +
                "FROM RouteStops rs " +
                "JOIN BusStops bs ON rs.stop_id = bs.stop_id " +
                "WHERE rs.route_id = ? ORDER BY rs.stop_order";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, routeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BusStop stop = new BusStop(rs.getInt("stop_id"), rs.getString("stop_name"));
                Route route = new Route(routeId);
                RouteStop routeStop = new RouteStop(
                        rs.getInt("id"), route, stop, rs.getInt("stop_order"),
                        rs.getBigDecimal("distance_from_start"), rs.getInt("estimated_time_from_start")
                );
                routeStops.add(routeStop);
            }
        }
        return routeStops;
    }

    // Thêm điểm dừng vào tuyến đường
    public boolean addRouteStop(RouteStop routeStop) throws SQLException {
        String sql = "INSERT INTO RouteStops (route_id, stop_id, stop_order, distance_from_start, estimated_time_from_start) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, routeStop.getRoute().getRouteId());
            stmt.setInt(2, routeStop.getStop().getStopId());
            stmt.setInt(3, routeStop.getStopOrder());
            stmt.setBigDecimal(4, routeStop.getDistanceFromStart());
            stmt.setInt(5, routeStop.getEstimatedTimeFromStart());
            return stmt.executeUpdate() > 0;
        }
    }

    // Cập nhật điểm dừng của tuyến đường
    public boolean updateRouteStop(RouteStop routeStop) throws SQLException {
        String sql = "UPDATE RouteStops SET stop_order = ?, distance_from_start = ?, estimated_time_from_start = ? " +
                "WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, routeStop.getStopOrder());
            stmt.setBigDecimal(2, routeStop.getDistanceFromStart());
            stmt.setInt(3, routeStop.getEstimatedTimeFromStart());
            stmt.setInt(4, routeStop.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa điểm dừng khỏi tuyến đường
    public boolean deleteRouteStop(int routeStopId) throws SQLException {
        String sql = "DELETE FROM RouteStops WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, routeStopId);
            return stmt.executeUpdate() > 0;
        }
    }

    public RouteStop getRouteStopById(int stopId) throws SQLException {
        String sql = "SELECT rs.id, rs.stop_order, rs.distance_from_start, rs.estimated_time_from_start, " +
                "bs.stop_id, bs.stop_name " +
                "FROM RouteStops rs " +
                "JOIN BusStops bs ON rs.stop_id = bs.stop_id " +
                "WHERE rs.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stopId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BusStop stop = new BusStop(rs.getInt("stop_id"), rs.getString("stop_name"));
                return new RouteStop(
                        rs.getInt("id"),
                        stop,
                        rs.getInt("stop_order"),
                        rs.getBigDecimal("distance_from_start"),
                        rs.getInt("estimated_time_from_start")
                );
            }
        }
        return null;
    }


}
