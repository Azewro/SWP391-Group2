package dao;

import model.*;
import util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public Ticket modifyBooking(int orderDetailId, Integer newSeatId, Integer newTripId, BigDecimal newPrice) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Truy xuất OrderDetail theo orderDetailId và ánh xạ vào model OrderDetail
            String orderDetailQuery = "SELECT order_detail_id, ticket_id, price FROM OrderDetails WHERE order_detail_id = ?";
            OrderDetail orderDetail = new OrderDetail();
            int ticketId;
            BigDecimal currentOrderDetailPrice;
            try (PreparedStatement ps = conn.prepareStatement(orderDetailQuery)) {
                ps.setInt(1, orderDetailId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("OrderDetail với id " + orderDetailId + " không tồn tại.");
                    }
                    orderDetail.setOrderDetailId(rs.getInt("order_detail_id"));
                    ticketId = rs.getInt("ticket_id");
                    currentOrderDetailPrice = rs.getBigDecimal("price");
                    orderDetail.setPrice(currentOrderDetailPrice);
                }
            }

            // 2. Truy xuất Ticket theo ticketId lấy từ OrderDetail
            String ticketQuery = "SELECT ticket_id, status, seat_id, trip_id, price FROM Tickets WHERE ticket_id = ?";
            int currentSeatId, currentTripId;
            BigDecimal currentTicketPrice;
            String currentStatus;
            try (PreparedStatement ps = conn.prepareStatement(ticketQuery)) {
                ps.setInt(1, ticketId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Ticket với id " + ticketId + " không tồn tại.");
                    }
                    currentStatus = rs.getString("status");
                    if (!"Booked".equalsIgnoreCase(currentStatus)) {
                        throw new SQLException("Chỉ những vé có trạng thái 'Booked' mới được sửa đổi.");
                    }
                    currentSeatId = rs.getInt("seat_id");
                    currentTripId = rs.getInt("trip_id");
                    currentTicketPrice = rs.getBigDecimal("price");
                }
            }

            // 3. Xác định giá trị cập nhật: nếu giá trị mới null thì giữ nguyên giá trị hiện tại
            int finalSeatId = (newSeatId != null) ? newSeatId : currentSeatId;
            int finalTripId = (newTripId != null) ? newTripId : currentTripId;
            BigDecimal finalPrice = (newPrice != null) ? newPrice : currentTicketPrice;

            // 4. Nếu có thay đổi ghế, kiểm tra ghế mới có tồn tại và đang khả dụng không
            if (newSeatId != null) {
                String seatQuery = "SELECT is_available FROM Seats WHERE seat_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(seatQuery)) {
                    ps.setInt(1, newSeatId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Ghế với id " + newSeatId + " không tồn tại.");
                        }
                        boolean isAvailable = rs.getBoolean("is_available");
                        if (!isAvailable) {
                            throw new SQLException("Ghế được chọn không khả dụng.");
                        }
                    }
                }
            }

            // 5. Nếu có thay đổi chuyến, kiểm tra chuyến mới có tồn tại không
            if (newTripId != null) {
                String tripQuery = "SELECT trip_id FROM BusTrips WHERE trip_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(tripQuery)) {
                    ps.setInt(1, newTripId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Chuyến xe với id " + newTripId + " không tồn tại.");
                        }
                    }
                }
            }

            // 6. Cập nhật Ticket với các giá trị mới (seat_id, trip_id, price)
            String updateTicketQuery = "UPDATE Tickets SET seat_id = ?, trip_id = ?, price = ? WHERE ticket_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateTicketQuery)) {
                ps.setInt(1, finalSeatId);
                ps.setInt(2, finalTripId);
                ps.setBigDecimal(3, finalPrice);
                ps.setInt(4, ticketId);
                int updated = ps.executeUpdate();
                if (updated != 1) {
                    throw new SQLException("Cập nhật Ticket thất bại.");
                }
            }

            // 7. Cập nhật OrderDetail nếu có thay đổi giá vé (đồng bộ giá vé)
            if (newPrice != null) {
                String updateOrderDetailQuery = "UPDATE OrderDetails SET price = ? WHERE order_detail_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(updateOrderDetailQuery)) {
                    ps.setBigDecimal(1, finalPrice);
                    ps.setInt(2, orderDetailId);
                    int updated = ps.executeUpdate();
                    if (updated != 1) {
                        throw new SQLException("Cập nhật OrderDetail thất bại.");
                    }
                }
            }

            conn.commit();

            // 8. Tạo đối tượng Ticket cập nhật để trả về (chỉ bao gồm thông tin cơ bản)
            Ticket ticket = new Ticket();
            ticket.setTicketId(ticketId);

            // Gán thông tin ghế
            Seat seat = new Seat();
            seat.setSeatId(finalSeatId);
            ticket.setSeat(seat);

            // Gán thông tin chuyến xe
            BusTrip trip = new BusTrip();
            trip.setTripId(finalTripId);
            ticket.setTrip(trip);

            ticket.setPrice(finalPrice);
            ticket.setStatus(currentStatus);  // Vẫn giữ trạng thái là "Booked"

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
        String sql = "SELECT order_date, total_amount, status FROM Orders WHERE user_id = ? ORDER BY order_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
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
