package controller;

import dao.AdminRouteDAO;
import model.Route;
import model.Location;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/routes")
public class AdminRouteServlet extends HttpServlet {
    private AdminRouteDAO routeDAO;

    @Override
    public void init() throws ServletException {
        try {
            routeDAO = new AdminRouteDAO();
        } catch (SQLException e) {
            throw new ServletException("Lỗi kết nối database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                showEditForm(request, response);
            } else if ("delete".equals(action)) {
                deleteRoute(request, response);
            } else {
                listRoutes(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Lỗi xử lý dữ liệu", e);
        }
    }

    private void listRoutes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String search = request.getParameter("search");
        String locationParam = request.getParameter("location");
        Integer locationId = (locationParam == null || locationParam.isEmpty()) ? null : Integer.parseInt(locationParam);


        List<Route> routeList = routeDAO.getAllRoutes(search, locationId);
        request.setAttribute("routes", routeList);
        request.getRequestDispatcher("/admin/route_list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int routeId = Integer.parseInt(request.getParameter("id"));
        Route existingRoute = routeDAO.getRouteById(routeId);
        request.setAttribute("route", existingRoute);
        request.getRequestDispatcher("/admin/route_form.jsp").forward(request, response);
    }

    private void deleteRoute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int routeId = Integer.parseInt(request.getParameter("id"));
        routeDAO.deleteRoute(routeId);
        response.sendRedirect("routes");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("update".equals(action)) {
                updateRoute(request, response);
            }
            if ("add".equals(action)) {
                addRoute(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("Lỗi cập nhật tuyến đường", e);
        }
    }

    private void updateRoute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int routeId = parseInteger(request.getParameter("routeId"), 0);
        String routeName = request.getParameter("routeName");
        int startLocationId = parseInteger(request.getParameter("startLocation"), 0);
        int endLocationId = parseInteger(request.getParameter("endLocation"), 0);
        float distance = parseFloat(request.getParameter("distance"), 0);
        int estimatedDuration = parseInteger(request.getParameter("estimatedDuration"), 0);

        Location startLocation = new Location(startLocationId, "");
        Location endLocation = new Location(endLocationId, "");

        Route route = new Route(routeId, routeName, startLocation, endLocation, distance, estimatedDuration, null);
        routeDAO.updateRoute(route);
        response.sendRedirect("routes");
    }
    private void addRoute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String routeName = request.getParameter("routeName");
        int startLocationId = parseInteger(request.getParameter("startLocation"), 0);
        int endLocationId = parseInteger(request.getParameter("endLocation"), 0);
        float distance = parseFloat(request.getParameter("distance"), 0);
        int estimatedDuration = parseInteger(request.getParameter("estimatedDuration"), 0);

        Location startLocation = new Location(startLocationId, "");
        Location endLocation = new Location(endLocationId, "");

        Route route = new Route(0, routeName, startLocation, endLocation, distance, estimatedDuration, null);
        routeDAO.addRoute(route);

        response.sendRedirect("routes");
    }


    private int parseInteger(String value, int defaultValue) {
        try {
            return (value == null || value.isEmpty()) ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private float parseFloat(String value, float defaultValue) {
        try {
            return (value == null || value.isEmpty()) ? defaultValue : Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }


}
