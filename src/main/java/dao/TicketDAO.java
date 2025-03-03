package dao;

import model.Ticket;
import model.User;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    public boolean bookSeat(int userId, int tripId, int seatId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT is_available FROM Seats WHERE seat_id = ?");
             PreparedStatement updateStmt = conn.prepareStatement("UPDATE Seats SET is_available = FALSE WHERE seat_id = ?");
             PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO Tickets (user_id, trip_id, seat_id, status) VALUES (?, ?, ?, 'Booked')")) {

            checkStmt.setInt(1, seatId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getBoolean("is_available")) {
                updateStmt.setInt(1, seatId);
                updateStmt.executeUpdate();

                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, tripId);
                insertStmt.setInt(3, seatId);
                insertStmt.executeUpdate();

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Ticket> getTicketsByUser(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tickets WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(rs.getInt("ticket_id"), rs.getInt("user_id"), rs.getInt("trip_id"), rs.getInt("seat_id"), rs.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }
}


