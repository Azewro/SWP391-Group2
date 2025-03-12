package dao;

import model.Ticket;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean modifyBooking(Ticket ticket) {
        String sql = "UPDATE Tickets SET trip_id = ?, seat_id = ?, price = ?, status = ? WHERE ticket_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticket.getTrip().getTripId());
            stmt.setInt(2, ticket.getSeat().getSeatId());
            stmt.setBigDecimal(3, ticket.getPrice());
            stmt.setString(4, ticket.getStatus());
            stmt.setInt(5, ticket.getTicketId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelBooking(int ticketId) {
        String sql = "UPDATE Tickets SET status = 'Cancelled' WHERE ticket_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ticket> viewBookingHistory(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Tickets WHERE user_id = ? ORDER BY purchase_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(rs.getInt("ticket_id"));
                ticket.setPurchaseDate(rs.getTimestamp("purchase_date").toLocalDateTime());
                ticket.setPrice(rs.getBigDecimal("price"));
                ticket.setStatus(rs.getString("status"));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
