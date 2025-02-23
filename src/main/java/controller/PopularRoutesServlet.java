package controller;

import model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.PopularRoutesDAO;

@WebServlet("/popular-routes")
public class PopularRoutesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PopularRoutesDAO popularRoutesDAO = new PopularRoutesDAO();
        try {
            List<Route> popularRoutes = popularRoutesDAO.getPopularRoutes();
            HttpSession session = request.getSession();
            request.setAttribute("popularRoutes", popularRoutes);
            response.sendRedirect("/components/popularRoutes.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load popular routes.");
        }
    }
}