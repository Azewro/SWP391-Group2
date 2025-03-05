package controller;


import dao.AdminBusStopDAO;
import dao.AdminRouteDAO;
import model.BusStop;
import model.Route;
import model.Location;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/bus_stops")
public class AdminBusStopServlet extends HttpServlet {
    private final AdminBusStopDAO busStopDAO = new AdminBusStopDAO();
    private final AdminRouteDAO routeDAO = new AdminRouteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            String search = request.getParameter("search");
            String routeFilter = request.getParameter("route_id");

// Kiểm tra điều kiện lọc
            List<BusStop> busStops;
            if ((search != null && !search.isEmpty()) || (routeFilter != null && !routeFilter.isEmpty())) {
                busStops = busStopDAO.searchBusStops(search, routeFilter);
            } else {
                busStops = busStopDAO.getAllBusStops();
            }

// Chỉ forward một lần
            request.setAttribute("busStops", busStops);
            request.getRequestDispatcher("/admin/bus_stop_list.jsp").forward(request, response);


        } else if (action.equals("edit")) {
            // Lấy thông tin điểm dừng để chỉnh sửa
            int stopId = Integer.parseInt(request.getParameter("id"));
            BusStop busStop = busStopDAO.getBusStopById(stopId);
            List<Route> routes = routeDAO.getAllRoutes(); // Lấy danh sách tuyến đường để chọn
            request.setAttribute("busStop", busStop);
            request.setAttribute("routes", routes);
            request.getRequestDispatcher("/admin/bus_stop_form.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            // Xóa điểm dừng
            int stopId = Integer.parseInt(request.getParameter("id"));
            busStopDAO.deleteBusStop(stopId);
            response.sendRedirect(request.getContextPath() + "/admin/bus_stops");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("add")) {
            // Thêm điểm dừng mới
            String stopName = request.getParameter("stop_name");
            int locationId = Integer.parseInt(request.getParameter("location_id"));
            int routeId = Integer.parseInt(request.getParameter("route_id"));
            int stopOrder = Integer.parseInt(request.getParameter("stop_order"));
            Integer estimatedWaitingTime = request.getParameter("estimated_waiting_time").isEmpty() ? null : Integer.parseInt(request.getParameter("estimated_waiting_time"));
            boolean isActive = request.getParameter("is_active") != null;
            String description = request.getParameter("description");

            BusStop busStop = new BusStop(0, new Location(locationId), stopName, new Route(routeId), stopOrder, estimatedWaitingTime, isActive, description);
            busStopDAO.addBusStop(busStop);

            response.sendRedirect(request.getContextPath() + "/admin/bus_stops");

        } else if (action.equals("update")) {
            // Cập nhật thông tin điểm dừng
            int stopId = Integer.parseInt(request.getParameter("stop_id"));
            String stopName = request.getParameter("stop_name");
            int locationId = Integer.parseInt(request.getParameter("location_id"));
            int routeId = Integer.parseInt(request.getParameter("route_id"));
            int stopOrder = Integer.parseInt(request.getParameter("stop_order"));
            Integer estimatedWaitingTime = request.getParameter("estimated_waiting_time").isEmpty() ? null : Integer.parseInt(request.getParameter("estimated_waiting_time"));
            boolean isActive = request.getParameter("is_active") != null;
            String description = request.getParameter("description");

            BusStop busStop = new BusStop(stopId, new Location(locationId), stopName, new Route(routeId), stopOrder, estimatedWaitingTime, isActive, description);
            busStopDAO.updateBusStop(busStop);

            response.sendRedirect(request.getContextPath() + "/admin/bus_stops");
        }
    }
}

