package controller;

import model.Booking;
import model.User;
import dao.BookingDAO;
import util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/bookings")
public class BookingServlet extends HttpServlet {

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
        List<Booking> bookings= bookingDAO.findByUser(user);

        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("bookings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

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
            request.setAttribute("bookingTitle", bookingTitle);
            request.setAttribute("bookingDate", bookingDateStr);
            List<Booking> bookings = bookingDAO.findByUser(user);
            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("bookings.jsp").forward(request, response);
            return;
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookingTitle(bookingTitle);
        booking.setBookingDate(bookingDate);

        bookingDAO.save(booking);

        response.sendRedirect(request.getContextPath() + "/bookings");
    }
}