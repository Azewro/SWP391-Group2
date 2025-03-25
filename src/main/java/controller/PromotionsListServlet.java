package controller;

import dao.PromotionsDAO;
import model.Promotion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/promotions")
public class PromotionsListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PromotionsDAO dao = new PromotionsDAO();
        List<Promotion> activePromotions = dao.getAllPromotions(); // is_active = 1

        request.setAttribute("promotions", activePromotions);
        request.getRequestDispatcher("/components/promotions.jsp").forward(request, response);
    }
}
