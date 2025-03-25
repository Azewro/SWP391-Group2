package dao;

import model.*;
import util.DatabaseConnection;
import java.sql.*;

public class TicketDAO {
    public Ticket findTicket(String phone, String ticketIdInput) {
        String sql = """
            SELECT t.ticket_id, t.purchase_date, t.price, t.status,
                   u.user_id, u.full_name, u.phone,
                   s.seat_id, s.seat_number,
                   bt.trip_id, bt.departure_time,
                   r.route_id, r.route_name
            FROM Tickets t
            JOIN Users u ON t.user_id = u.user_id
            JOIN Seats s ON t.seat_id = s.seat_id
            JOIN BusTrips bt ON t.trip_id = bt.trip_id
            JOIN Routes r ON bt.route_id = r.route_id
            WHERE u.phone = ?
              AND (CAST(t.ticket_id AS CHAR) = ? OR ? IS NULL OR ? = '')
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phone);
            ps.setString(2, ticketIdInput);
            ps.setString(3, ticketIdInput);
            ps.setString(4, ticketIdInput);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setPhone(rs.getString("phone"));

                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setSeatNumber(rs.getInt("seat_number"));

                Route route = new Route();
                route.setRouteId(rs.getInt("route_id"));
                route.setRouteName(rs.getString("route_name"));

                BusTrip trip = new BusTrip();
                trip.setTripId(rs.getInt("trip_id"));
                trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
                trip.setRoute(route);

                return new Ticket(
                    rs.getInt("ticket_id"),
                    user,
                    trip,
                    seat,
                    rs.getTimestamp("purchase_date").toLocalDateTime(),
                    rs.getBigDecimal("price"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
