package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.BusStop;
import model.Location;
import util.DatabaseConnection;
import static util.DatabaseConnection.getConnection;

public class BusStopDAO {
    public List<BusStop> getBusStopsByRouteId(int routeId) {
        List<BusStop> list = new ArrayList<>();
        String sql = "SELECT b.*, l.name AS locationName " +
                     "FROM BusStops b " +
                     "JOIN Locations l ON b.location_id = l.location_id " +
                     "WHERE b.route_id = ? ORDER BY b.stop_order ASC";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Location location = new Location();
                location.setLocationId(rs.getInt("location_id"));
                location.setName(rs.getString("locationName"));

                list.add(new BusStop(
                        rs.getInt("stop_id"),
                        rs.getString("stop_name"),
                        rs.getInt("stop_order"),
                        rs.getInt("estimated_waiting_time"),
                        rs.getBoolean("is_active"),
                        rs.getString("description"),
                        location
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public static List<BusStop> getStopsByRouteIdOrdered(int routeId) {
    List<BusStop> list = new ArrayList<>();
    String sql = "SELECT * FROM BusStops WHERE route_id = ? ORDER BY stop_order ASC";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, routeId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            BusStop stop = new BusStop();
            stop.setStopId(rs.getInt("stop_id"));
            stop.setStopName(rs.getString("stop_name"));
            stop.setStopOrder(rs.getInt("stop_order"));
            list.add(stop);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
