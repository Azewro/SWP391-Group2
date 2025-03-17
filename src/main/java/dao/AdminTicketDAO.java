package dao;

import model.BusTrip;
import model.Seat;
import model.Ticket;
import model.User;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminTicketDAO {
    public List<Ticket> getAllSoldTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT t.ticket_id, u.full_name AS customer_name, " +
                "b.trip_id, s.seat_number, t.price, t.status " +
                "FROM Tickets t " +
                "JOIN Users u ON t.user_id = u.user_id " +
                "JOIN BusTrips b ON t.trip_id = b.trip_id " +
                "JOIN Seats s ON t.seat_id = s.seat_id " +
                "WHERE t.status IN ('Booked', 'Used')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(rs.getInt("ticket_id"));
                ticket.setStatus(rs.getString("status"));
                ticket.setPrice(rs.getBigDecimal("price"));

                User user = new User();
                user.setFullName(rs.getString("customer_name"));
                ticket.setUser(user);

                BusTrip trip = new BusTrip();
                trip.setTripId(rs.getInt("trip_id"));
                ticket.setTrip(trip);

                Seat seat = new Seat();
                seat.setSeatNumber(Integer.parseInt(rs.getString("seat_number")));
                ticket.setSeat(seat);

                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }


    public boolean updateTicketStatus(int ticketId, String status) {
        String sql = "UPDATE Tickets SET status = ? WHERE ticket_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, ticketId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
