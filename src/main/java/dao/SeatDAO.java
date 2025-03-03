package dao;

import model.Seat;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {
    public List<Seat> getAvailableSeats(int tripId) {
        List<Seat> seats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Seats WHERE bus_id IN (SELECT bus_id FROM BusTrips WHERE trip_id = ?) AND is_available = TRUE")) {
            stmt.setInt(1, tripId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                seats.add(new Seat(rs.getInt("seat_id"), rs.getInt("bus_id"), rs.getInt("seat_number"), rs.getString("seat_type"), rs.getBoolean("is_available")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seats;
    }

    public boolean updateSeatAvailability(int seatId, boolean isAvailable) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Seats SET is_available = ? WHERE seat_id = ?")) {
            stmt.setBoolean(1, isAvailable);
            stmt.setInt(2, seatId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
