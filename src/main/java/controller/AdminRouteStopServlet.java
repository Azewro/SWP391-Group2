package controller;

import dao.AdminRouteStopDAO;
import model.Route;
import model.RouteStop;
import model.BusStop;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/route-stops")
public class AdminRouteStopServlet extends HttpServlet {
    private AdminRouteStopDAO routeStopDAO;

    @Override
    public void init() throws ServletException {
        try {
            routeStopDAO = new AdminRouteStopDAO();
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
                deleteRouteStop(request, response);
            } else {
                listRouteStops(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Lỗi xử lý dữ liệu", e);
        }
    }

    private void listRouteStops(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int routeId = Integer.parseInt(request.getParameter("routeId"));
        List<RouteStop> routeStops = routeStopDAO.getRouteStops(routeId);

        request.setAttribute("routeStops", routeStops);
        request.setAttribute("routeId", routeId);
        request.getRequestDispatcher("/admin/route_stops.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int stopId = Integer.parseInt(request.getParameter("id"));
        RouteStop routeStop = routeStopDAO.getRouteStopById(stopId);

        request.setAttribute("routeStop", routeStop);
        request.getRequestDispatcher("/admin/route_stops_form.jsp").forward(request, response);
    }

    private void deleteRouteStop(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int stopId = Integer.parseInt(request.getParameter("id"));
        int routeId = Integer.parseInt(request.getParameter("routeId"));

        routeStopDAO.deleteRouteStop(stopId);
        response.sendRedirect("route-stops?routeId=" + routeId);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("update".equals(action)) {
                updateRouteStop(request, response);
            } else if ("add".equals(action)) {
                addRouteStop(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Lỗi cập nhật điểm dừng", e);
        }
    }

    private void updateRouteStop(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int stopId = Integer.parseInt(request.getParameter("stopId"));
        int stopOrder = Integer.parseInt(request.getParameter("stopOrder"));
        BigDecimal distanceFromStart = new BigDecimal(request.getParameter("distanceFromStart"));
        int estimatedTimeFromStart = Integer.parseInt(request.getParameter("estimatedTimeFromStart"));

        RouteStop routeStop = new RouteStop(stopId, stopOrder, distanceFromStart, estimatedTimeFromStart);
        routeStopDAO.updateRouteStop(routeStop);

        int routeId = Integer.parseInt(request.getParameter("routeId"));
        response.sendRedirect("route-stops?routeId=" + routeId);
    }

    private void addRouteStop(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int routeId = Integer.parseInt(request.getParameter("routeId"));
        int busStopId = Integer.parseInt(request.getParameter("busStopId"));
        int stopOrder = Integer.parseInt(request.getParameter("stopOrder"));
        BigDecimal distanceFromStart = new BigDecimal(request.getParameter("distanceFromStart"));
        int estimatedTimeFromStart = Integer.parseInt(request.getParameter("estimatedTimeFromStart"));

        Route route = new Route(routeId);
        BusStop stop = new BusStop(busStopId, "");
        RouteStop routeStop = new RouteStop(route, stop, stopOrder, distanceFromStart, estimatedTimeFromStart);

        routeStopDAO.addRouteStop(routeStop);
        response.sendRedirect("route-stops?routeId=" + routeId);
    }
}
