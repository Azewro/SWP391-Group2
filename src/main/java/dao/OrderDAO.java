package dao;

import model.*;
import util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public static int createOrder(int userId, BigDecimal totalAmount) {
        String sql = "INSERT INTO Orders(user_id, total_amount) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.setBigDecimal(2, totalAmount);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void addOrderDetail(int orderId, int ticketId, BigDecimal price) {
        String sql = "INSERT INTO OrderDetails(order_id, ticket_id, price) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, ticketId);
            ps.setBigDecimal(3, price);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Ticket> getTicketsByOrder(int orderId) {
        List<Ticket> list = new ArrayList<>();
        String sql = """
            SELECT t.*, s.seat_number,
                   r.route_name, bt.departure_time
            FROM OrderDetails od
            JOIN Tickets t ON od.ticket_id = t.ticket_id
            JOIN Seats s ON t.seat_id = s.seat_id
            JOIN BusTrips bt ON t.trip_id = bt.trip_id
            JOIN Routes r ON bt.route_id = r.route_id
            WHERE od.order_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();

                // Ghế
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setSeatNumber(rs.getInt("seat_number"));
                ticket.setSeat(seat);

                // Tuyến + chuyến
                Route route = new Route();
                route.setRouteName(rs.getString("route_name"));
                BusTrip trip = new BusTrip();
                trip.setTripId(rs.getInt("trip_id"));
                trip.setRoute(route);
                trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
                ticket.setTrip(trip);

                // Vé
                ticket.setTicketId(rs.getInt("ticket_id"));
                ticket.setPrice(rs.getBigDecimal("price"));
                ticket.setStatus(rs.getString("status"));

                list.add(ticket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public static List<Order> getOrdersByUser(int userId) {
    List<Order> list = new ArrayList<>();
    String sql = "SELECT * FROM Orders WHERE user_id = ? ORDER BY order_date DESC";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order o = new Order();
            o.setOrderId(rs.getInt("order_id"));
            o.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
            o.setTotalAmount(rs.getBigDecimal("total_amount"));
            o.setStatus(rs.getString("status"));
            list.add(o);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
