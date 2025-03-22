package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

import dao.AdminReportDAO;
import model.CustomerFeedback;
import util.DatabaseConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/admin/report")
public class AdminReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            AdminReportDAO dao = new AdminReportDAO(conn);

            // Ngày mặc định: 7 ngày gần nhất
            Calendar cal = Calendar.getInstance();
            Date to = new Date(cal.getTimeInMillis());
            cal.add(Calendar.DAY_OF_MONTH, -7);
            Date from = new Date(cal.getTimeInMillis());

            // Gửi dữ liệu thống kê đến JSP
            BigDecimal totalRevenue = dao.getTotalRevenue(from, to);
            int totalTickets = dao.getTotalTicketsSold(from, to);
            int totalOrders = dao.getTotalOrders(from, to);
            List<CustomerFeedback> topFeedbacks = dao.getTopPendingFeedbacks();
            int[] ratingDist = dao.getRatingDistribution();
            Map<String, BigDecimal> revenueByRoute = dao.getRevenueByRoute();
            Map<String, BigDecimal> topCustomers = dao.getTopCustomers(5);
            Map<String, Integer> ticketsPerDate = dao.getTicketsSoldByDate(from, to);

            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("totalTickets", totalTickets);
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("topFeedbacks", topFeedbacks);
            request.setAttribute("ratingDist", ratingDist);
            request.setAttribute("revenueByRoute", revenueByRoute);
            request.setAttribute("topCustomers", topCustomers);
            request.setAttribute("ticketsPerDate", ticketsPerDate);
            request.setAttribute("fromDate", from.toString());
            request.setAttribute("toDate", to.toString());

            // Điều hướng sang trang thống kê
            RequestDispatcher rd = request.getRequestDispatcher("/admin-report.jsp");
            rd.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error in AdminReportServlet", e);
        }
    }
}
