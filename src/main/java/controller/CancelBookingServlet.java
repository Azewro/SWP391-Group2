package controller;

import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cancelBooking")
public class CancelBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tham số từ form
        String orderDetailIdStr = request.getParameter("orderDetailId");
        String orderIdStr = request.getParameter("orderId");

        // Kiểm tra null hoặc rỗng tránh lỗi NumberFormatException
        if (orderDetailIdStr == null || orderIdStr == null || orderDetailIdStr.isEmpty() || orderIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu không hợp lệ");
            return;
        }

        try {
            int orderDetailId = Integer.parseInt(orderDetailIdStr);
            int orderId = Integer.parseInt(orderIdStr);

            // Gọi DAO để hủy vé
            bookingDAO.cancelBooking(orderDetailId);

            // Sau khi hủy redirect về booking-history của đơn hàng đó
            response.sendRedirect("booking-history?orderId=" + orderId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu không hợp lệ");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi hủy vé");
        }
    }
}
