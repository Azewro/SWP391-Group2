package controller;

import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ticket;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cancelBooking")
public class CancelBookingServlet extends HttpServlet {
    private BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy parameter orderDetailId từ request
        String orderDetailIdStr = request.getParameter("orderDetailId");
        if (orderDetailIdStr == null || orderDetailIdStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "OrderDetailId is required.");
            return;
        }
        int orderDetailId;
        try {
            orderDetailId = Integer.parseInt(orderDetailIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid orderDetailId parameter.");
            return;
        }

        try {
            // Gọi hàm DAO để huỷ booking dựa vào orderDetailId
            Ticket cancelledTicket = bookingDAO.cancelBooking(orderDetailId);

            // Trả về kết quả dưới dạng JSON (ví dụ: ticketId và trạng thái)
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = String.format("{\"ticketId\": %d, \"status\": \"%s\"}",
                    cancelledTicket.getTicketId(), cancelledTicket.getStatus());
            response.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error cancelling booking: " + e.getMessage());
        }
    }
}