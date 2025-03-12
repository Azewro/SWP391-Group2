package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;
import model.User;
import dao.BookingDAO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/modify-booking")
public class ModifyBookingServlet extends HttpServlet {
    private BookingDAO bookingDAO;

    @Override
    public void init() {
        bookingDAO = new BookingDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String bookingIdStr = request.getParameter("id");
        if (bookingIdStr == null || bookingIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/bookings");
            return;
        }

        try {
            long bookingId = Long.parseLong(bookingIdStr);
            Booking booking = bookingDAO.findById(bookingId);
            if (booking == null || booking.getUser().getUserId() != user.getUserId()) {
                response.sendRedirect(request.getContextPath() + "/bookings");
                return;
            }

            request.setAttribute("booking", booking);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            request.setAttribute("bookingDateFormatted", sdf.format(booking.getBookingDate()));
            request.getRequestDispatcher("/WEB-INF/views/modify-booking.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/bookings");
        }
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
        if (bookingIdStr == null || bookingIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/bookings");
            return;
        }

        try {
            long bookingId = Long.parseLong(bookingIdStr);
            Booking booking = bookingDAO.findById(bookingId);
            if (booking == null || booking.getUser().getUserId() != user.getUserId()) {
                response.sendRedirect(request.getContextPath() + "/bookings");
                return;
            }

            String bookingTitle = request.getParameter("bookingTitle");
            String bookingDateStr = request.getParameter("bookingDate");
            boolean hasError = false;

            if (bookingTitle == null || bookingTitle.trim().isEmpty()) {
                request.setAttribute("titleError", "Booking title is required");
                hasError = true;
            }

            Date bookingDate = null;
            if (bookingDateStr == null || bookingDateStr.trim().isEmpty()) {
                request.setAttribute("dateError", "Booking date is required");
                hasError = true;
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    bookingDate = sdf.parse(bookingDateStr);
                    if (bookingDate.before(new Date())) {
                        request.setAttribute("dateError", "Booking date must be in the future");
                        hasError = true;
                    }
                } catch (ParseException e) {
                    request.setAttribute("dateError", "Invalid date format");
                    hasError = true;
                }
            }

            if (hasError) {
                request.setAttribute("booking", booking);
                request.setAttribute("bookingTitle", bookingTitle);
                request.setAttribute("bookingDateFormatted", bookingDateStr);
                request.getRequestDispatcher("/WEB-INF/views/modify-booking.jsp").forward(request, response);
                return;
            }

            booking.setBookingTitle(bookingTitle);
            booking.setBookingDate(bookingDate);
            bookingDAO.update(booking);
            response.sendRedirect(request.getContextPath() + "/bookings?updated=true");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/bookings");
        }
    }
}
