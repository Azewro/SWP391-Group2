package controller;

import dao.AdminDashboardDAO;
import dao.AdminFeedbackDAO;
import model.CustomerFeedback;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private AdminDashboardDAO dashboardDAO;
    private AdminFeedbackDAO feedbackDAO;

    @Override
    public void init() {
        dashboardDAO = new AdminDashboardDAO();
        feedbackDAO = new AdminFeedbackDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. Thống kê tổng quan
        List<Map<String, String>> dashboardStats = new ArrayList<>();
        dashboardStats.add(stat("Vé đã bán", String.valueOf(dashboardDAO.getTotalTickets()), "fas fa-ticket-alt", "primary"));
        dashboardStats.add(stat("Vé đã hủy", String.valueOf(dashboardDAO.getCancelledTickets()), "fas fa-times-circle", "danger"));
        dashboardStats.add(stat("Đơn hàng chờ", String.valueOf(dashboardDAO.getPendingOrders()), "fas fa-hourglass-half", "warning"));
        dashboardStats.add(stat("Doanh thu hôm nay", dashboardDAO.getTodayRevenue() + "₫", "fas fa-dollar-sign", "success"));
        dashboardStats.add(stat("Chuyến xe hôm nay", String.valueOf(dashboardDAO.getUpcomingTripsToday()), "fas fa-bus", "info"));
        dashboardStats.add(stat("Người dùng mới", String.valueOf(dashboardDAO.getTodayNewUsers()), "fas fa-user-plus", "dark"));

        request.setAttribute("dashboardStats", dashboardStats);

        // 2. Phản hồi chờ duyệt (top 3)
        List<CustomerFeedback> pendingFeedbacks = feedbackDAO.getTopPendingFeedbacks(3);
        request.setAttribute("pendingFeedbacks", pendingFeedbacks);

        // 3. Dữ liệu biểu đồ (doanh thu & số vé theo ngày)
        List<String> labels = dashboardDAO.getLast7DaysLabels(); // ["T2", "T3", ..., "CN"]
        List<Integer> revenueData = dashboardDAO.getLast7DaysRevenue(); // [1000000, ...]
        List<Integer> ticketData = dashboardDAO.getLast7DaysTicketCount(); // [20, ...]

        Gson gson = new Gson();
        request.setAttribute("chartLabels", gson.toJson(labels));
        request.setAttribute("revenueData", gson.toJson(revenueData));
        request.setAttribute("ticketData", gson.toJson(ticketData));

        Map<Integer, Integer> ratingCounts = feedbackDAO.countFeedbackByRating();

// Ghép thành list dữ liệu dạng [count_1sao, ..., count_5sao]
        List<Integer> pieData = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            pieData.add(ratingCounts.getOrDefault(i, 0));
        }

        request.setAttribute("pieFeedbackData", new Gson().toJson(pieData));

        // Chuyển tới JSP
        request.getRequestDispatcher("/admin/admin-dashboard.jsp").forward(request, response);
    }

    private Map<String, String> stat(String label, String value, String icon, String color) {
        Map<String, String> map = new HashMap<>();
        map.put("label", label);
        map.put("value", value);
        map.put("icon", icon);
        map.put("color", color);
        return map;
    }
}
