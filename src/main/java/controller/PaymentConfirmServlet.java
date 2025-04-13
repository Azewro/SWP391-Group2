package controller;

import dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/payment-confirm")
public class PaymentConfirmServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        List<Ticket> ticketList = (List<Ticket>) session.getAttribute("pendingTickets");
        BigDecimal totalAmount = (BigDecimal) session.getAttribute("pendingTotal");
        Integer tripId = (Integer) session.getAttribute("pendingTripId");

        if (user == null || ticketList == null || totalAmount == null || tripId == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        // ✅ Tạo đơn hàng
        int orderId = OrderDAO.createOrder(user.getUserId(), totalAmount);

        // ✅ Lưu vé và order detail
        for (Ticket ticket : ticketList) {
            ticket.setUser(user);
            ticket.setStatus("Booked");
            int ticketId = TicketDAO.createTicket(ticket);
            OrderDAO.addOrderDetail(orderId, ticketId, ticket.getPrice());
        }

        // ✅ Cập nhật số ghế còn lại
        BusTripDAO.updateAvailableSeats(tripId, -ticketList.size());

        // ✅ Lưu thông tin thanh toán
        Payment payment = new Payment();
        Order order = new Order();
        order.setOrderId(orderId);

        payment.setOrder(order);
        payment.setAmount(totalAmount);
        payment.setPaymentMethod("PAYPAL");
        payment.setStatus("Completed");
        payment.setPaymentTime(LocalDateTime.now());

        PaymentDAO.save(payment);

        // ✅ Dọn session
        session.removeAttribute("pendingTickets");
        session.removeAttribute("pendingTotal");
        session.removeAttribute("pendingTripId");

        // ✅ Điều hướng đến trang cảm ơn
        response.sendRedirect("payment-success.jsp");
    }
}
