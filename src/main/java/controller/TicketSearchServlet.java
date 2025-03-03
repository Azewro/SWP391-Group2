package controller;

import dao.TicketDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ticket;

import java.io.IOException;
import java.util.List;

public class TicketSearchServlet {

        private static final long serialVersionUID = 1L;
        private TicketDAO ticketDAO = new TicketDAO();

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int userId = Integer.parseInt(request.getParameter("userId"));
            List<Ticket> tickets = ticketDAO.getTicketsByUser(userId);
            request.setAttribute("tickets", tickets);
            request.getRequestDispatcher("ticketSearch.jsp").forward(request, response);
        }
    }

