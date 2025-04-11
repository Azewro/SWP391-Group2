package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Location;
import util.DatabaseConnection;

public class LocationDAO {
    public static List<Location> getAllLocations() {
        List<Location> list = new ArrayList<>();
        String sql = "SELECT location_id, name FROM Locations WHERE is_active = TRUE";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Location loc = new Location();
                loc.setLocationId(rs.getInt("location_id"));
                loc.setName(rs.getString("name"));
                list.add(loc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
