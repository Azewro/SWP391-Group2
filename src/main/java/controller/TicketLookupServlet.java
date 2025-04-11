// ✅ TicketLookupServlet.java
package controller;

import dao.TicketDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Ticket;
import java.io.IOException;

@WebServlet("/ticket-lookup")
public class TicketLookupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phone = request.getParameter("phone");
        String ticketId = request.getParameter("ticketId");

        TicketDAO dao = new TicketDAO();
        Ticket ticket = dao.findTicket(phone, ticketId);

        // ✅ Lưu ticket vào session để in PDF
        if (ticket != null) {
            request.getSession().setAttribute("ticket", ticket);
        }

        request.setAttribute("ticket", ticket);
        request.setAttribute("phone", phone);
        request.setAttribute("ticketId", ticketId);
        request.getRequestDispatcher("components/ticket-lookup.jsp").forward(request, response);
    }
}