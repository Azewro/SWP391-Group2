package controller;

import dao.AdminFeedbackDAO;
import model.CustomerFeedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/feedback")
public class AdminFeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminFeedbackDAO feedbackDAO;

    @Override
    public void init() throws ServletException {
        feedbackDAO = new AdminFeedbackDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "search":
                searchFeedback(request, response);
                break;
            default:
                listFeedback(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "approve":
                updateFeedbackStatus(request, response, "Approved");
                break;
            case "reject":
                updateFeedbackStatus(request, response, "Rejected");
                break;
            default:
                listFeedback(request, response);
                break;
        }
    }

    // Hiển thị danh sách phản hồi
    private void listFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CustomerFeedback> feedbackList = feedbackDAO.getAllFeedbacks();
        request.setAttribute("feedbackList", feedbackList);
        request.getRequestDispatcher("/admin/feedback_list.jsp").forward(request, response);
    }

    // Tìm kiếm phản hồi theo điểm đánh giá
    private void searchFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int rating = Integer.parseInt(request.getParameter("rating"));
        List<CustomerFeedback> feedbackList = feedbackDAO.searchFeedbackByRating(rating);
        request.setAttribute("feedbackList", feedbackList);
        request.getRequestDispatcher("/admin/feedback_list.jsp").forward(request, response);
    }

    // Duyệt hoặc từ chối phản hồi
    private void updateFeedbackStatus(HttpServletRequest request, HttpServletResponse response, String status) throws ServletException, IOException {
        int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
        boolean success = status.equals("Approved") ? feedbackDAO.approveFeedback(feedbackId) : feedbackDAO.rejectFeedback(feedbackId);

        if (success) {
            request.setAttribute("message", "Cập nhật phản hồi thành công!");
        } else {
            request.setAttribute("error", "Cập nhật phản hồi thất bại!");
        }
        listFeedback(request, response);
    }
}
