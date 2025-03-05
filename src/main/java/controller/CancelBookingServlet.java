package controller;

import dao.BookingDAO;
import model.Booking;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/cancel-booking")
public class CancelBookingServlet extends HttpServlet {

    private BookingDAO bookingDAO;

    @Override
    public void init() {
        bookingDAO = new BookingDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String bookingIdStr = request.getParameter("bookingId");

        if (bookingIdStr != null && !bookingIdStr.trim().isEmpty()) {
            try {
                long bookingId = Long.parseLong(bookingIdStr);
                Booking booking = bookingDAO.findById(bookingId);

                // Kiểm tra quyền hủy booking (userId kiểu int)
                if (booking != null && booking.getUser().getUserId() == user.getUserId()) {
                    bookingDAO.cancelBooking(bookingId);
                }
            } catch (NumberFormatException ignored) {
                // Xử lý lỗi nếu bookingId không hợp lệ
            }
        }

        response.sendRedirect(request.getContextPath() + "/bookings");
    }
}
