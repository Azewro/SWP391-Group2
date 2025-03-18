package controller;

import dao.PromotionDAO;
import model.Promotion;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/view-promotions")
public class ViewPromotionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp"); // Chuyển hướng nếu chưa đăng nhập
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            PromotionDAO promotionDAO = new PromotionDAO();
            List<Promotion> activePromotions = promotionDAO.getActivePromotionsForUser(user.getUserId());
            
            session.setAttribute("activePromotions", activePromotions);
            request.getRequestDispatcher("/components/view_promotions.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải danh sách khuyến mãi.");
        }
    }
}
