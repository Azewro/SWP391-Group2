package controller;

import dao.OrderDAO;
import dao.TicketDAO;
import dao.BusTripDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy user từ session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = user.getUserId();
        int tripId = Integer.parseInt(request.getParameter("tripId"));
        String[] seatIdParams = request.getParameterValues("seatIds");

        if (seatIdParams == null || seatIdParams.length == 0) {
            response.sendRedirect("selectSeats.jsp?tripId=" + tripId + "&error=empty");
            return;
        }

        // Tính tổng tiền
        List<Ticket> ticketList = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (String seatIdStr : seatIdParams) {
            int seatId = Integer.parseInt(seatIdStr);
            BigDecimal price = TicketDAO.getTicketPrice(tripId);

            // Tạo đối tượng con theo đúng model
            User u = new User();
            u.setUserId(userId);

            BusTrip trip = new BusTrip();
            trip.setTripId(tripId);

            Seat seat = new Seat();
            seat.setSeatId(seatId);

            // Tạo vé
            Ticket ticket = new Ticket();
            ticket.setUser(u);
            ticket.setTrip(trip);
            ticket.setSeat(seat);
            ticket.setPrice(price);
            ticket.setStatus("Booked");

            ticketList.add(ticket);
            totalAmount = totalAmount.add(price);
        }

        // Tạo đơn hàng
        int orderId = OrderDAO.createOrder(userId, totalAmount);

        // Lưu từng vé và ghi vào OrderDetails
        for (Ticket ticket : ticketList) {
            int ticketId = TicketDAO.createTicket(ticket);
            OrderDAO.addOrderDetail(orderId, ticketId, ticket.getPrice());
        }

        // CẬP NHẬT AVAILABLE_SEATS cho chuyến xe
        int seatsBooked = seatIdParams.length;
        BusTripDAO.updateAvailableSeats(tripId, -seatsBooked); // Trừ chỗ ngồi đã đặt

        // Chuyển sang trang xác nhận
        response.sendRedirect("payment.jsp?orderId=" + orderId);
    }
}
