package dao;

import model.BusTrip;
import model.OrderDetail;
import model.Seat;
import model.Ticket;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminOrderDetailDAO {
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        String sql = "SELECT od.order_detail_id, od.price, " +
                "t.ticket_id, t.status AS ticket_status, " +
                "b.trip_id, s.seat_number " +
                "FROM OrderDetails od " +
                "JOIN Tickets t ON od.ticket_id = t.ticket_id " +
                "JOIN BusTrips b ON t.trip_id = b.trip_id " +
                "JOIN Seats s ON t.seat_id = s.seat_id " +
                "WHERE od.order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetail detail = new OrderDetail();
                    detail.setOrderDetailId(rs.getInt("order_detail_id"));
                    detail.setPrice(rs.getBigDecimal("price"));

                    Ticket ticket = new Ticket();
                    ticket.setTicketId(rs.getInt("ticket_id"));
                    ticket.setStatus(rs.getString("ticket_status"));

                    BusTrip trip = new BusTrip();
                    trip.setTripId(rs.getInt("trip_id"));
                    ticket.setTrip(trip);

                    Seat seat = new Seat();
                    seat.setSeatNumber(Integer.parseInt(rs.getString("seat_number")));
                    ticket.setSeat(seat);

                    detail.setTicket(ticket);
                    details.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

}
