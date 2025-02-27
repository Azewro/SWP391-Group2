package servlet;

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
        Integer locationId = request.getParameter("location") != null ? Integer.parseInt(request.getParameter("location")) : null;

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
        int routeId = Integer.parseInt(request.getParameter("routeId"));
        String routeName = request.getParameter("routeName");
        int startLocationId = Integer.parseInt(request.getParameter("startLocation"));
        int endLocationId = Integer.parseInt(request.getParameter("endLocation"));
        float distance = Float.parseFloat(request.getParameter("distance"));
        int estimatedDuration = Integer.parseInt(request.getParameter("estimatedDuration"));

        Location startLocation = new Location(startLocationId, "");
        Location endLocation = new Location(endLocationId, "");

        Route route = new Route(routeId, routeName, startLocation, endLocation, distance, estimatedDuration, null);
        routeDAO.updateRoute(route);
        response.sendRedirect("routes");
    }
    private void addRoute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String routeName = request.getParameter("routeName");
        int startLocationId = Integer.parseInt(request.getParameter("startLocation"));
        int endLocationId = Integer.parseInt(request.getParameter("endLocation"));
        float distance = Float.parseFloat(request.getParameter("distance"));
        int estimatedDuration = Integer.parseInt(request.getParameter("estimatedDuration"));

        Location startLocation = new Location(startLocationId, "");
        Location endLocation = new Location(endLocationId, "");

        Route route = new Route(0, routeName, startLocation, endLocation, distance, estimatedDuration, null);
        routeDAO.addRoute(route);

        response.sendRedirect("routes");
    }


}
