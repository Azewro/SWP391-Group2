package controller;

import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderDetail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cancelBooking")
public class CancelBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Received request to cancel booking");

        String orderDetailIdStr = request.getParameter("orderDetailId");
        String orderIdStr = request.getParameter("orderId");

        if (orderDetailIdStr == null || orderIdStr == null ||
                orderDetailIdStr.isEmpty() || orderIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Dữ liệu không hợp lệ.");
            request.getRequestDispatcher("modify-booking.jsp").forward(request, response);
            return;
        }

        try {
            int orderDetailId = Integer.parseInt(orderDetailIdStr);
            int orderId = Integer.parseInt(orderIdStr);

            System.out.println("Hủy vé với orderDetailId: " + orderDetailId + ", orderId: " + orderId);
            bookingDAO.cancelBooking(orderDetailId);

            if (bookingDAO.countOrderDetails(orderId) == 0) {
                System.out.println("Không còn vé trong order " + orderId + ", hủy luôn order");
                bookingDAO.cancelOrder(orderId);
            }

            // Lấy danh sách vé còn lại để cập nhật giao diện
            List<OrderDetail> orderDetails = bookingDAO.getOrderDetailsByOrderId(orderId);
            request.setAttribute("orderId", orderId);
            request.setAttribute("orderDetails", orderDetails);

            // Chuyển hướng về modify-booking.jsp
            request.getRequestDispatcher("modify-booking.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Lỗi định dạng số.");
            request.getRequestDispatcher("modify-booking.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống khi hủy vé.");
            request.getRequestDispatcher("modify-booking.jsp").forward(request, response);
        }
    }
}
