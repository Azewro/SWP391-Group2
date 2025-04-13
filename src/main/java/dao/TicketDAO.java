package dao;

import java.math.BigDecimal;
import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    public List<Ticket> findTickets(String phone, String ticketIdInput) {
    List<Ticket> tickets = new ArrayList<>();

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
        while (rs.next()) {
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

            Ticket ticket = new Ticket(
                rs.getInt("ticket_id"),
                user,
                trip,
                seat,
                rs.getTimestamp("purchase_date").toLocalDateTime(),
                rs.getBigDecimal("price"),
                rs.getString("status")
            );

            tickets.add(ticket);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return tickets;
}

    
    public static BigDecimal getTicketPrice(int tripId) {
        String sql = "SELECT current_price FROM BusTrips WHERE trip_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tripId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("current_price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public static int createTicket(Ticket ticket) {
        String sql = "INSERT INTO Tickets(user_id, trip_id, seat_id, price, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ticket.getUser().getUserId());
            ps.setInt(2, ticket.getTrip().getTripId());
            ps.setInt(3, ticket.getSeat().getSeatId());
            ps.setBigDecimal(4, ticket.getPrice());
            ps.setString(5, ticket.getStatus());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
