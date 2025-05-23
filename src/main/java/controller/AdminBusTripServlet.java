package controller;

import dao.AdminBusTripDAO;
import model.BusTrip;
import model.Bus;
import model.Route;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/admin/bus-trips")
public class AdminBusTripServlet extends HttpServlet {
    private final AdminBusTripDAO busTripDAO = new AdminBusTripDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            listBusTrips(request, response);
        } else {
            switch (action) {
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteBusTrip(request, response);
                    break;
                default:
                    listBusTrips(request, response);
            }
        }
    }

    private void listBusTrips(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchRoute = request.getParameter("searchRoute");
        String searchDriver = request.getParameter("searchDriver");
        int page = parseIntSafe(request.getParameter("page"));
        if (page <= 0) page = 1;
        int pageSize = 5;

        List<BusTrip> busTrips = busTripDAO.searchBusTrips(searchRoute, searchDriver, page, pageSize);
        int totalTrips = busTripDAO.countBusTrips(searchRoute, searchDriver);
        int totalPages = (int) Math.ceil((double) totalTrips / pageSize);

        request.setAttribute("busTrips", busTrips);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchRoute", searchRoute);
        request.setAttribute("searchDriver", searchDriver);
        request.getRequestDispatcher("/admin/admin-bus-trips.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int tripId = parseIntSafe(request.getParameter("tripId"));
        BusTrip trip = tripId > 0 ? busTripDAO.getBusTripById(tripId) : null;

        List<Route> routes = busTripDAO.getAllRoutes();
        List<Bus> buses = busTripDAO.getAllBuses();
        List<User> drivers = busTripDAO.getAllDrivers();

        request.setAttribute("busTrip", trip);
        request.setAttribute("routes", routes);
        request.setAttribute("buses", buses);
        request.setAttribute("drivers", drivers);
        request.getRequestDispatcher("/admin/admin-edit-bus-trip.jsp").forward(request, response);
    }

    private void deleteBusTrip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int tripId = parseIntSafe(request.getParameter("tripId"));
        busTripDAO.deleteBusTrip(tripId);
        response.sendRedirect("bus-trips");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        BusTrip trip = parseBusTripFromRequest(request);

        if ("add".equals(action)) {
            if (trip != null && busTripDAO.addBusTrip(trip)) {
                response.sendRedirect("bus-trips");
            } else {
                response.sendRedirect("bus-trips?action=edit&error=add_failed");
            }
        } else if ("update".equals(action)) {
            if (trip != null && busTripDAO.updateBusTrip(trip)) {
                response.sendRedirect("bus-trips");
            } else {
                response.sendRedirect("bus-trips?action=edit&tripId=" + trip.getTripId() + "&error=update_failed");
            }
        } else {
            response.sendRedirect("bus-trips");
        }
    }

    private BusTrip parseBusTripFromRequest(HttpServletRequest request) {
        try {
            int tripId = parseIntSafe(request.getParameter("tripId"));
            int routeId = parseIntSafe(request.getParameter("routeId"));
            int busId = parseIntSafe(request.getParameter("busId"));
            int driverId = parseIntSafe(request.getParameter("driverId"));
            String status = request.getParameter("status");
            String departureStr = request.getParameter("departureTime");
            String arrivalStr = request.getParameter("arrivalTime");
            String priceStr = request.getParameter("currentPrice");

            if (routeId == 0 || busId == 0 || driverId == 0 || departureStr == null || departureStr.isEmpty())
                return null;

            LocalDateTime departureTime = LocalDateTime.parse(departureStr);
            LocalDateTime arrivalTime = (arrivalStr != null && !arrivalStr.isEmpty()) ? LocalDateTime.parse(arrivalStr) : null;
            BigDecimal currentPrice = (priceStr != null && !priceStr.isEmpty()) ? new BigDecimal(priceStr) : BigDecimal.ZERO;

            // Validate tài xế
            boolean validDriver = busTripDAO.getAllDrivers().stream().anyMatch(d -> d.getUserId() == driverId);
            if (!validDriver) return null;

            BusTrip trip = new BusTrip();
            trip.setTripId(tripId);
            trip.setRoute(new Route(routeId));
            trip.setBus(new Bus(busId));
            trip.setDriver(new User(driverId));
            trip.setStatus(status != null ? status : "Scheduled");
            trip.setDepartureTime(departureTime);
            trip.setArrivalTime(arrivalTime);
            trip.setCurrentPrice(currentPrice);

// Set số ghế còn lại = capacity của bus
            Bus bus = busTripDAO.getBusById(busId);
            if (bus == null) {
                System.out.println("❌ Bus không tồn tại trong DB!");
                return null;
            }
            trip.setAvailableSeats(bus.getCapacity());


            return trip;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int parseIntSafe(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? Integer.parseInt(value.trim()) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
