package controller;

import dao.BusTripDAO;
import dao.RouteDAO;
import model.BusTrip;
import model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/trip-list")
public class TripListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String routeIdStr = request.getParameter("routeId");
        String date = request.getParameter("departure_date");

        if (routeIdStr != null && date != null) {
            int routeId = Integer.parseInt(routeIdStr);
            List<BusTrip> trips = BusTripDAO.findTripsByRouteAndDate(routeId, date);
            request.setAttribute("tripList", trips);

            // Forward đến màn hình hiển thị danh sách chuyến xe theo ngày
            request.getRequestDispatcher("/tripList.jsp").forward(request, response);
            return;
        }

        // Nếu chưa chọn routeId + ngày, hiển thị lại busSchedule mặc định
        List<Route> routes = RouteDAO.getAllRoutesWithLocation();
        request.setAttribute("busSchedules", routes);

        request.getRequestDispatcher("/components/busSchedule.jsp").forward(request, response);
    }
}

