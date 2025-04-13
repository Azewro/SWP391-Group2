// ✅ TicketLookupServlet.java
package controller;

import dao.TicketDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Ticket;

import java.io.IOException;
import java.util.List;

@WebServlet("/ticket-lookup")
public class TicketLookupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phone = request.getParameter("phone");
        String ticketId = request.getParameter("ticketId");

        TicketDAO dao = new TicketDAO();
        List<Ticket> tickets = dao.findTickets(phone, ticketId); // ✅ gọi hàm trả về list

        request.setAttribute("tickets", tickets); // ✅ gán list vào attribute
        request.setAttribute("phone", phone);
        request.setAttribute("ticketId", ticketId);

        request.getRequestDispatcher("components/ticket-lookup.jsp").forward(request, response);
    }
}
