package controller;

import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cancelBooking")
public class CancelBookingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            BookingDAO bookingDAO = new BookingDAO();
            boolean success = bookingDAO.cancelBooking(ticketId);
            response.sendRedirect("viewBookingHistory?cancel=" + success);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("viewBookingHistory?cancel=false");
        }
    }
}