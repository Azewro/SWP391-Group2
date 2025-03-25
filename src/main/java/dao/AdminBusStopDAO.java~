package dao;

import model.BusStop;
import model.Route;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminBusStopDAO {

    // Lấy danh sách tất cả điểm dừng
    public List<BusStop> getAllBusStops() {
        List<BusStop> busStops = new ArrayList<>();
        String query = "SELECT b.stop_id, COALESCE(b.stop_name, 'Không có tên') AS stop_name, " +
                "COALESCE(b.route_id, -1) AS route_id, " +
                "COALESCE(r.route_name, 'Chưa có tuyến') AS route_name, " +
                "COALESCE(b.stop_order, 0) AS stop_order, " +
                "COALESCE(b.estimated_waiting_time, 0) AS estimated_waiting_time, " +
                "b.is_active " +
                "FROM BusStops b " +
                "LEFT JOIN Routes r ON b.route_id = r.route_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int stopId = rs.getInt("stop_id");
                String stopName = rs.getString("stop_name");
                int routeId = rs.getInt("route_id");
                String routeName = rs.getString("route_name");
                int stopOrder = rs.getInt("stop_order");
                int estimatedWaitingTime = rs.getInt("estimated_waiting_time");
                boolean isActive = rs.getBoolean("is_active");

                Route route = new Route(routeId, routeName);
                BusStop busStop = new BusStop(stopId, stopName, route, stopOrder, estimatedWaitingTime, isActive, "");

                // Kiểm tra xem BusStop có hợp lệ không
                if (stopId == 0 || stopName == null) {
                    System.out.println("⚠ Lỗi tạo BusStop: " + busStop);
                } else {
                    busStops.add(busStop);
                }
            }

            // Kiểm tra số lượng phần tử trước khi trả về
            System.out.println("🚀 Tổng số điểm dừng hợp lệ: " + busStops.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busStops;
    }




    // Lấy thông tin điểm dừng theo ID
    public BusStop getBusStopById(int stopId) {
        String query = "SELECT b.stop_id, b.stop_name, b.route_id, " +
                "COALESCE(r.route_name, 'Chưa có tuyến') AS route_name, " +
                "b.stop_order, b.estimated_waiting_time, b.is_active " +
                "FROM BusStops b " +
                "LEFT JOIN Routes r ON b.route_id = r.route_id " +
                "WHERE b.stop_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stopId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new BusStop(
                        rs.getInt("stop_id"),
                        rs.getString("stop_name"),
                        new Route(rs.getInt("route_id"), rs.getString("route_name")),
                        rs.getInt("stop_order"),
                        rs.getObject("estimated_waiting_time") != null ? rs.getInt("estimated_waiting_time") : null,
                        rs.getBoolean("is_active"),
                        ""
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Thêm mới một điểm dừng
    public boolean addBusStop(BusStop busStop) {
        String query = "INSERT INTO BusStops (location_id, stop_name, route_id, stop_order, estimated_waiting_time, is_active, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, busStop.getLocationId());
            stmt.setString(2, busStop.getStopName());
            stmt.setInt(3, busStop.getRoute().getRouteId());
            stmt.setInt(4, busStop.getStopOrder());
            stmt.setObject(5, busStop.getEstimatedWaitingTime(), Types.INTEGER);
            stmt.setBoolean(6, busStop.isActive());
            stmt.setString(7, busStop.getDescription());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin điểm dừng
    public boolean updateBusStop(BusStop busStop) {
        String query = "UPDATE BusStops SET location_id = ?, stop_name = ?, route_id = ?, stop_order = ?, estimated_waiting_time = ?, is_active = ?, description = ? WHERE stop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, busStop.getLocationId());
            stmt.setString(2, busStop.getStopName());
            stmt.setInt(3, busStop.getRoute().getRouteId());
            stmt.setInt(4, busStop.getStopOrder());
            stmt.setObject(5, busStop.getEstimatedWaitingTime(), Types.INTEGER);
            stmt.setBoolean(6, busStop.isActive());
            stmt.setString(7, busStop.getDescription());
            stmt.setInt(8, busStop.getStopId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa điểm dừng theo ID
    public boolean deleteBusStop(int stopId) {
        String query = "DELETE FROM BusStops WHERE stop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, stopId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<BusStop> searchBusStops(String search, String routeFilter) {
        List<BusStop> busStops = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT b.stop_id, b.stop_name, b.route_id, " +
                "COALESCE(r.route_name, 'Chưa có tuyến') AS route_name, " +
                "b.stop_order, b.estimated_waiting_time, b.is_active " +
                "FROM BusStops b " +
                "LEFT JOIN Routes r ON b.route_id = r.route_id WHERE 1=1 ");

        List<Object> parameters = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            query.append("AND b.stop_name LIKE ? ");
            parameters.add("%" + search + "%");
        }
        if (routeFilter != null && !routeFilter.isEmpty()) {
            query.append("AND b.route_id = ? ");
            parameters.add(Integer.parseInt(routeFilter));
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                busStops.add(new BusStop(
                        rs.getInt("stop_id"),
                        rs.getString("stop_name"),
                        new Route(rs.getInt("route_id"), rs.getString("route_name")),
                        rs.getInt("stop_order"),
                        rs.getObject("estimated_waiting_time") != null ? rs.getInt("estimated_waiting_time") : null,
                        rs.getBoolean("is_active"),
                        ""
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busStops;
    }


    public static void main(String[] args) {
        AdminBusStopDAO dao = new AdminBusStopDAO();
        List<BusStop> stops = dao.getAllBusStops();

        System.out.println("🚀 Tổng số điểm dừng trong danh sách: " + stops.size());

        int countValid = 0;
        int countInvalid = 0;

        for (BusStop stop : stops) {
            if (stop.getStopId() == 0 || stop.getStopName() == null) {
                System.out.println("⚠ Cảnh báo: Có dữ liệu sai -> " + stop);
                countInvalid++;
            } else {
                System.out.println("✅ Dữ liệu hợp lệ -> " + stop);
                countValid++;
            }
        }

        System.out.println("📌 Tổng số điểm dừng hợp lệ: " + countValid);
        System.out.println("📌 Tổng số điểm dừng lỗi: " + countInvalid);
    }






}
