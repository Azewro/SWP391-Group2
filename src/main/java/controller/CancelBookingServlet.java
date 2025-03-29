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
        System.out.println("Received request to cancel booking"); // Kiểm tra request có vào servlet không

        String orderDetailIdStr = request.getParameter("orderDetailId");
        String orderIdStr = request.getParameter("orderId");

        System.out.println("orderDetailIdStr: " + orderDetailIdStr); // Debug giá trị nhận được
        System.out.println("orderIdStr: " + orderIdStr);

        if (orderDetailIdStr == null || orderIdStr == null || orderDetailIdStr.isEmpty() || orderIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Dữ liệu không hợp lệ");
            System.out.println("LỖI: Dữ liệu nhận được không hợp lệ!");
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

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Success");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Dữ liệu không hợp lệ");
            System.out.println("LỖI: orderDetailId hoặc orderId không phải số hợp lệ!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Lỗi khi hủy vé");
            System.out.println("LỖI: SQLException xảy ra!");
        }
    }
}
