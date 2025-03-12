package controller;

import dao.BookingDAO;
import model.Ticket;
import model.BusTrip;
import model.Seat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/modifyBooking")
public class ModifyBookingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("modify-booking.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            int tripId = Integer.parseInt(request.getParameter("tripId"));
            int seatId = Integer.parseInt(request.getParameter("seatId"));
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            String status = request.getParameter("status");

            BusTrip trip = new BusTrip();
            trip.setTripId(tripId);

            Seat seat = new Seat();
            seat.setSeatId(seatId);

            Ticket ticket = new Ticket();
            ticket.setTicketId(ticketId);
            ticket.setTrip(trip);
            ticket.setSeat(seat);
            ticket.setPrice(price);
            ticket.setStatus(status);

            BookingDAO bookingDAO = new BookingDAO();
            boolean success = bookingDAO.modifyBooking(ticket);
            response.sendRedirect("booking.jsp?update=" + success);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("booking.jsp?update=false");
        }
    }
}