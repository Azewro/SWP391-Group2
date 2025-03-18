package controller;

import dao.AdminPromotionDAO;
import model.Promotion;
import util.DatabaseConnection;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/promotions")
public class AdminPromotionServlet extends HttpServlet {
    private AdminPromotionDAO promotionDAO;

    @Override
    public void init() {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        promotionDAO = new AdminPromotionDAO(conn);
    }

    // 1️⃣ Lấy danh sách tất cả chương trình khuyến mãi (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Promotion> promotions = promotionDAO.getAllPromotions();
        request.setAttribute("promotions", promotions);
        request.getRequestDispatcher("/admin/admin_promotion.jsp").forward(request, response);
    }

    // 2️⃣ Thêm mới chương trình khuyến mãi (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String promoCode = request.getParameter("promo_code");
        BigDecimal discountAmount = new BigDecimal(request.getParameter("discount_amount"));
        BigDecimal discountPercentage = new BigDecimal(request.getParameter("discount_percentage"));
        Timestamp validFrom = Timestamp.valueOf(request.getParameter("valid_from"));
        Timestamp validTo = Timestamp.valueOf(request.getParameter("valid_to"));
        boolean isActive = Boolean.parseBoolean(request.getParameter("is_active"));

        Promotion promotion = new Promotion(0, promoCode, discountAmount, discountPercentage, validFrom, validTo, isActive);
        promotionDAO.addPromotion(promotion);

        response.sendRedirect("/admin/promotions");
    }

    // 3️⃣ Cập nhật chương trình khuyến mãi (PUT)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int promoId = Integer.parseInt(request.getParameter("promotion_id"));
        String promoCode = request.getParameter("promo_code");
        BigDecimal discountAmount = new BigDecimal(request.getParameter("discount_amount"));
        BigDecimal discountPercentage = new BigDecimal(request.getParameter("discount_percentage"));
        Timestamp validFrom = Timestamp.valueOf(request.getParameter("valid_from"));
        Timestamp validTo = Timestamp.valueOf(request.getParameter("valid_to"));
        boolean isActive = Boolean.parseBoolean(request.getParameter("is_active"));

        Promotion promotion = new Promotion(promoId, promoCode, discountAmount, discountPercentage, validFrom, validTo, isActive);
        promotionDAO.updatePromotion(promotion);

        response.sendRedirect("/admin/promotions");
    }

    // 4️⃣ Xóa hoặc vô hiệu hóa chương trình khuyến mãi (DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int promoId = Integer.parseInt(request.getParameter("promotion_id"));
        promotionDAO.disablePromotion(promoId);

        response.sendRedirect("/admin/promotions");
    }
}
