package dao;

import model.Seat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

public class SeatDAO {
    public static List<Seat> getSeatsForTrip(int tripId) {
        List<Seat> list = new ArrayList<>();

        String sql = """
            SELECT s.seat_id, s.seat_number, s.seat_type,
                   CASE WHEN t.seat_id IS NOT NULL THEN FALSE ELSE TRUE END AS is_available
            FROM Seats s
            JOIN BusTrips bt ON s.bus_id = bt.bus_id
            LEFT JOIN Tickets t ON s.seat_id = t.seat_id AND t.trip_id = ? AND t.status = 'Booked'
            WHERE bt.trip_id = ?
            ORDER BY s.seat_number
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tripId);
            ps.setInt(2, tripId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setSeatNumber(rs.getInt("seat_number"));
                seat.setSeatType(rs.getString("seat_type"));
                seat.setAvailable(rs.getBoolean("is_available"));
                list.add(seat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
