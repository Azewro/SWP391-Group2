package controller;

import dao.AdminDashboardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private AdminDashboardDAO dashboardDAO;

    @Override
    public void init() throws ServletException {
        dashboardDAO = new AdminDashboardDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Lấy số liệu thống kê từ DAO
        int totalTickets = dashboardDAO.getTotalTickets();
        int cancelledTickets = dashboardDAO.getCancelledTickets();
        int pendingOrders = dashboardDAO.getPendingOrders();
        int todayRevenue = dashboardDAO.getTodayRevenue();
        int upcomingTrips = dashboardDAO.getUpcomingTripsToday();
        int pendingFeedbacks = dashboardDAO.getPendingFeedbacks();
        int todayNewUsers = dashboardDAO.getTodayNewUsers();

        // Đẩy số liệu lên JSP
        request.setAttribute("totalTickets", totalTickets);
        request.setAttribute("cancelledTickets", cancelledTickets);
        request.setAttribute("pendingOrders", pendingOrders);
        request.setAttribute("todayRevenue", todayRevenue);
        request.setAttribute("upcomingTrips", upcomingTrips);
        request.setAttribute("pendingFeedbacks", pendingFeedbacks);
        request.setAttribute("todayNewUsers", todayNewUsers);

        // Chuyển hướng đến trang JSP
        request.getRequestDispatcher("/admin/admin-dashboard.jsp").forward(request, response);
    }
}
