package dao;

import model.*;
import util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();

        String sql = """
        SELECT od.order_detail_id, od.ticket_id, od.price, 
               o.order_id, o.order_date, o.total_amount, o.status AS order_status, 
               t.ticket_id, t.seat_id, t.trip_id, t.price AS ticket_price, t.status AS ticket_status
        FROM OrderDetails od
        JOIN Orders o ON od.order_id = o.order_id
        JOIN Tickets t ON od.ticket_id = t.ticket_id
        WHERE o.order_id = ?
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Mapping Order
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                    order.setTotalAmount(rs.getBigDecimal("total_amount"));
                    order.setStatus(rs.getString("order_status"));

                    // Mapping Ticket
                    Ticket ticket = new Ticket();
                    ticket.setTicketId(rs.getInt("ticket_id"));
                    ticket.setPrice(rs.getBigDecimal("ticket_price"));
                    ticket.setStatus(rs.getString("ticket_status"));
                    Seat seat = new Seat();
                    seat.setSeatId(rs.getInt("seat_id"));
                    ticket.setSeat(seat);
                    BusTrip trip = new BusTrip();
                    trip.setTripId(rs.getInt("trip_id"));
                    ticket.setTrip(trip);


                    // Mapping OrderDetail
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderDetailId(rs.getInt("order_detail_id"));
                    orderDetail.setOrder(order);
                    orderDetail.setTicket(ticket);
                    orderDetail.setPrice(rs.getBigDecimal("price"));

                    orderDetails.add(orderDetail);
                }
            }
        }
        return orderDetails;
    }

    public OrderDetail getOrderDetailById(int orderDetailId) throws SQLException {
        OrderDetail orderDetail = null;
        String sql = "SELECT od.order_detail_id, od.order_id, od.price AS detail_price, " +
                "t.ticket_id, t.user_id, t.trip_id, t.seat_id, t.purchase_date, t.price AS ticket_price, t.status AS ticket_status, " +
                "s.seat_number, s.seat_type, s.is_available, s.bus_id, " +
                "bt.departure_time, bt.arrival_time, bt.status AS trip_status " +
                "FROM OrderDetails od " +
                "JOIN Tickets t ON od.ticket_id = t.ticket_id " +
                "JOIN Seats s ON t.seat_id = s.seat_id " +
                "JOIN BusTrips bt ON t.trip_id = bt.trip_id " +
                "WHERE od.order_detail_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderDetailId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orderDetail = new OrderDetail();
                    orderDetail.setOrderDetailId(rs.getInt("order_detail_id"));
                    orderDetail.setPrice(rs.getBigDecimal("detail_price"));

                    // Map Order
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    orderDetail.setOrder(order);

                    // Map Ticket
                    Ticket ticket = new Ticket();
                    ticket.setTicketId(rs.getInt("ticket_id"));
                    ticket.setPrice(rs.getBigDecimal("ticket_price"));
                    ticket.setStatus(rs.getString("ticket_status"));
                    ticket.setPurchaseDate(rs.getTimestamp("purchase_date").toLocalDateTime());

                    // Map Seat
                    Seat seat = new Seat();
                    seat.setSeatId(rs.getInt("seat_id"));
                    seat.setSeatNumber(rs.getInt("seat_number"));
                    seat.setSeatType(rs.getString("seat_type"));
                    seat.setAvailable(rs.getBoolean("is_available"));
                    // Optionally map bus_id if cần
                    ticket.setSeat(seat);

                    // Map BusTrip
                    BusTrip trip = new BusTrip();
                    trip.setTripId(rs.getInt("trip_id"));
                    trip.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
                    trip.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
                    trip.setStatus(rs.getString("trip_status"));
                    ticket.setTrip(trip);

                    orderDetail.setTicket(ticket);
                }
            }
        }
        return orderDetail;
    }

    public boolean updateTicket(Ticket ticket) throws SQLException {
        String sql = "UPDATE tickets SET trip_id = ?, seat_id = ?, price = ?, status = ?, purchase_date = ? " +
                "WHERE ticket_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ticket.getTrip().getTripId());
            ps.setInt(2, ticket.getSeat().getSeatId());
            ps.setBigDecimal(3, ticket.getPrice());
            ps.setString(4, ticket.getStatus());
            ps.setTimestamp(5, Timestamp.valueOf(ticket.getPurchaseDate()));
            ps.setInt(6, ticket.getTicketId());

            return ps.executeUpdate() > 0;
        }
    }


    public void cancelBooking(int orderDetailId) throws SQLException {
        String sql = "DELETE FROM orderdetails WHERE order_detail_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderDetailId);
            stmt.executeUpdate();
        }
    }

    // Đếm số lượng vé còn lại trong đơn hàng
    public int countOrderDetails(int orderId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM orderdetails WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Hủy đơn hàng nếu không còn vé
    public void cancelOrder(int orderId) throws SQLException {
        String sql = "UPDATE orders SET status = 'CANCELLED' WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        }
    }


    public List<Order> viewBookingHistory(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT order_id, order_date, total_amount, status FROM orders WHERE user_id = ? ORDER BY order_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                // Chuyển từ Timestamp -> LocalDateTime
                Timestamp timestamp = rs.getTimestamp("order_date");
                if (timestamp != null) {
                    order.setOrderDate(timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                }
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
