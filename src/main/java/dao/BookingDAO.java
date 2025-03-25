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
        String sql = "UPDATE Tickets SET trip_id = ?, seat_id = ?, price = ?, status = ?, purchase_date = ? " +
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


    public Ticket cancelBooking(int orderDetailId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Truy xuất OrderDetail và ánh xạ vào model OrderDetail
            String orderDetailQuery = "SELECT order_detail_id, ticket_id, price FROM OrderDetails WHERE order_detail_id = ?";
            OrderDetail orderDetail = new OrderDetail();
            int ticketId;
            try (PreparedStatement ps = conn.prepareStatement(orderDetailQuery)) {
                ps.setInt(1, orderDetailId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        orderDetail.setOrderDetailId(rs.getInt("order_detail_id"));
                        ticketId = rs.getInt("ticket_id");
                        orderDetail.setPrice(rs.getBigDecimal("price"));
                    } else {
                        throw new SQLException("Không tìm thấy OrderDetail với id " + orderDetailId);
                    }
                }
            }

            // 2. Truy xuất Ticket theo ticket_id và ánh xạ vào model Ticket
            String ticketQuery = "SELECT ticket_id, status, seat_id, price FROM Tickets WHERE ticket_id = ?";
            Ticket ticket = new Ticket();
            int seatId;
            try (PreparedStatement ps = conn.prepareStatement(ticketQuery)) {
                ps.setInt(1, ticketId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ticket.setTicketId(rs.getInt("ticket_id"));
                        ticket.setStatus(rs.getString("status"));
                        seatId = rs.getInt("seat_id");
                        ticket.setPrice(rs.getBigDecimal("price"));
                    } else {
                        throw new SQLException("Không tìm thấy Ticket với id " + ticketId);
                    }
                }
            }

            // 3. Kiểm tra trạng thái của Ticket: chỉ cho phép huỷ nếu đang ở trạng thái "Booked"
            if (!"Booked".equalsIgnoreCase(ticket.getStatus())) {
                throw new SQLException("Chỉ những vé có trạng thái 'Booked' mới được huỷ.");
            }

            // 4. Cập nhật trạng thái của Ticket thành "Cancelled" (sử dụng model)
            ticket.setStatus("Cancelled");
            String updateTicketQuery = "UPDATE Tickets SET status = ? WHERE ticket_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateTicketQuery)) {
                ps.setString(1, ticket.getStatus());
                ps.setInt(2, ticket.getTicketId());
                int updated = ps.executeUpdate();
                if (updated != 1) {
                    throw new SQLException("Cập nhật trạng thái Ticket thất bại cho ticket_id " + ticket.getTicketId());
                }
            }

            // 5. Truy xuất Seat theo seat_id và ánh xạ vào model Seat
            String seatQuery = "SELECT seat_id, is_available FROM Seats WHERE seat_id = ?";
            Seat seat = new Seat();
            try (PreparedStatement ps = conn.prepareStatement(seatQuery)) {
                ps.setInt(1, seatId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        seat.setSeatId(rs.getInt("seat_id"));
                        seat.setAvailable(rs.getBoolean("is_available"));
                    } else {
                        throw new SQLException("Không tìm thấy Seat với id " + seatId);
                    }
                }
            }

            // 6. Cập nhật trạng thái của Seat (đánh dấu lại là available)
            seat.setAvailable(true);
            String updateSeatQuery = "UPDATE Seats SET is_available = ? WHERE seat_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSeatQuery)) {
                ps.setBoolean(1, seat.isAvailable());
                ps.setInt(2, seat.getSeatId());
                ps.executeUpdate();
            }

            conn.commit();
            return ticket;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public List<Order> viewBookingHistory(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT order_id, order_date, total_amount, status FROM Orders WHERE user_id = ? ORDER BY order_date DESC";

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
