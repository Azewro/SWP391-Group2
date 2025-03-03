package controller;
import java.io.IOException;
import java.util.List;

import dao.SeatDAO;
import dao.TicketDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Seat;

@WebServlet("/BookingServlet")
public class SeatBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SeatDAO seatDAO = new SeatDAO();
    private TicketDAO ticketDAO = new TicketDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int tripId = Integer.parseInt(request.getParameter("tripId"));
        List<Seat> seats = seatDAO.getAvailableSeats(tripId);
        request.setAttribute("seats", seats);
        request.getRequestDispatcher("selectSeat.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int seatId = Integer.parseInt(request.getParameter("seatId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        int tripId = Integer.parseInt(request.getParameter("tripId"));

        boolean success = ticketDAO.bookSeat(userId, tripId, seatId);
        if (success) {
            request.setAttribute("message", "Đặt chỗ thành công!");
            request.getRequestDispatcher("bookingConfirmation.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Chỗ ngồi đã được đặt trước!");
            request.getRequestDispatcher("selectSeat.jsp").forward(request, response);
        }
    }
}
