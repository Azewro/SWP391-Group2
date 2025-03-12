package controller;

import dao.BookingDAO;
import model.Ticket;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewBookingHistory")
public class ViewBookingHistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        try {
            int userId = user.getUserId();
            BookingDAO bookingDAO = new BookingDAO();
            List<Ticket> tickets = bookingDAO.viewBookingHistory(userId);
            request.setAttribute("tickets", tickets);
            request.getRequestDispatcher("booking-history.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("booking-history.jsp?error=true");
        }
    }
}
