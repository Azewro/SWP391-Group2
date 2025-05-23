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
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/routes")
public class AdminRouteServlet extends HttpServlet {
    private final AdminRouteDAO routeDAO = new AdminRouteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            String search = request.getParameter("search");
            String locationFilter = request.getParameter("location");

// Kiểm tra xem có điều kiện lọc hay không
            List<Route> routes;
            if ((search != null && !search.isEmpty()) || (locationFilter != null && !locationFilter.isEmpty())) {
                routes = routeDAO.searchRoutes(search, locationFilter);
            } else {
                routes = routeDAO.getAllRoutes();
            }

// Set dữ liệu trước khi forward
            request.setAttribute("routes", routes);
            request.getRequestDispatcher("/admin/route_list.jsp").forward(request, response);


        } else if (action.equals("edit")) {
            // Lấy thông tin tuyến đường để chỉnh sửa
            int routeId = Integer.parseInt(request.getParameter("id"));
            Route route = routeDAO.getRouteById(routeId);
            request.setAttribute("route", route);
            List<Location> locations = routeDAO.getAllLocations();
            request.setAttribute("locations", locations);

            request.getRequestDispatcher("/admin/route_form.jsp").forward(request, response);
        }else if (action.equals("add-form")) {
            List<Location> locations = routeDAO.getAllLocations();
            request.setAttribute("locations", locations);
            request.getRequestDispatcher("/admin/route_form.jsp").forward(request, response);
        }
        else if (action.equals("delete")) {
            // Xóa tuyến đường
            int routeId = Integer.parseInt(request.getParameter("id"));
            routeDAO.deleteRoute(routeId);
            response.sendRedirect(request.getContextPath() + "/admin/routes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("add")) {
            // Lấy dữ liệu từ form để thêm tuyến đường
            String routeName = request.getParameter("route_name");
            int startLocationId = Integer.parseInt(request.getParameter("start_location_id"));
            int endLocationId = Integer.parseInt(request.getParameter("end_location_id"));
            float distance = Float.parseFloat(request.getParameter("distance"));
            int estimatedDuration = Integer.parseInt(request.getParameter("estimated_duration"));
            BigDecimal basePrice = new BigDecimal(request.getParameter("base_price"));
            String routeType = request.getParameter("route_type");

            Route route = new Route(0, routeName, new Location(startLocationId), new Location(endLocationId), distance, estimatedDuration, basePrice);
            route.setRouteType(routeType);

            routeDAO.addRoute(route);
            response.sendRedirect(request.getContextPath() + "/admin/routes");

        } else if (action.equals("update")) {
            // Cập nhật thông tin tuyến đường
            int routeId = Integer.parseInt(request.getParameter("route_id"));
            String routeName = request.getParameter("route_name");
            int startLocationId = Integer.parseInt(request.getParameter("start_location_id"));
            int endLocationId = Integer.parseInt(request.getParameter("end_location_id"));
            float distance = Float.parseFloat(request.getParameter("distance"));
            int estimatedDuration = Integer.parseInt(request.getParameter("estimated_duration"));
            BigDecimal basePrice = new BigDecimal(request.getParameter("base_price"));
            String routeType = request.getParameter("route_type");

            Route route = new Route(routeId, routeName, new Location(startLocationId), new Location(endLocationId), distance, estimatedDuration, basePrice);
            route.setRouteType(routeType);

            routeDAO.updateRoute(route);
            response.sendRedirect(request.getContextPath() + "/admin/routes");
        }
    }
}
