package controller;

import dao.PromotionsDAO;
import model.Promotion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/promotion-detail")
public class PromotionDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String promoCode = request.getParameter("promoCode");

        if (promoCode != null && !promoCode.trim().isEmpty()) {
            PromotionsDAO dao = new PromotionsDAO();
            Promotion promo = dao.getPromotionByCode(promoCode);

            if (promo != null) {
                request.setAttribute("promotion", promo);
                request.getRequestDispatcher("/components/promotion-detail.jsp").forward(request, response);
                return;
            }
        }

        // Nếu không tìm thấy mã hoặc lỗi
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Khuyến mãi không tồn tại");
    }
}
