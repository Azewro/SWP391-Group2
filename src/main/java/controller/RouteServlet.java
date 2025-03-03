package controller;

import dao.RouteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Route;

import java.io.IOException;
import java.util.List;
@WebServlet("/route")
public class RouteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RouteDAO routeDAO = new RouteDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeDAO.getAllRoutes();
        request.setAttribute("routes", routes);
        request.getRequestDispatcher("routes.jsp").forward(request, response);
    }
}
