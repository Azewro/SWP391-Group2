package controller;

import dao.AdminBusTripDAO;
import model.BusTrip;
import model.Bus;
import model.Route;
import model.User;
import util.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/admin/bus-trips")
public class AdminBusTripServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
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
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int pageSize = 5;

        List<BusTrip> busTrips = busTripDAO.searchBusTrips(searchRoute, searchDriver, page, pageSize);
        int totalTrips = busTripDAO.countBusTrips(searchRoute, searchDriver);
        int totalPages = (int) Math.ceil((double) totalTrips / pageSize);

        request.setAttribute("busTrips", busTrips);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/admin/admin-bus-trips.jsp").forward(request, response);
    }



    // Hiển thị form chỉnh sửa hoặc thêm chuyến xe
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tripIdParam = request.getParameter("tripId");
        BusTrip busTrip = null;

        if (tripIdParam != null) {
            int tripId = Integer.parseInt(tripIdParam);
            busTrip = busTripDAO.getBusTripById(tripId);
        }

        // Lấy danh sách tài xế, tuyến đường, xe từ DAO
        List<User> drivers = busTripDAO.getAllDrivers();
        List<Route> routes = busTripDAO.getAllRoutes();
        List<Bus> buses = busTripDAO.getAllBuses();

        request.setAttribute("busTrip", busTrip);
        request.setAttribute("drivers", drivers);
        request.setAttribute("routes", routes);
        request.setAttribute("buses", buses);
        request.getRequestDispatcher("/admin/admin-edit-bus-trip.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addBusTrip(request, response);
                    break;
                case "update":
                    updateBusTrip(request, response);
                    break;
                default:
                    response.sendRedirect("bus-trips");
            }
        }
    }

    // Xử lý thêm chuyến xe mới
    private void addBusTrip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BusTrip trip = parseBusTripFromRequest(request);
        if (trip != null && busTripDAO.addBusTrip(trip)) {
            response.sendRedirect("bus-trips");
        } else {
            response.sendRedirect("bus-trips?action=edit&error=add_failed");
        }
    }

    // Xử lý cập nhật chuyến xe
    private void updateBusTrip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BusTrip trip = parseBusTripFromRequest(request);
        if (trip != null && busTripDAO.updateBusTrip(trip)) {
            response.sendRedirect("bus-trips");
        } else {
            response.sendRedirect("bus-trips?action=edit&error=update_failed");
        }
    }

    // Xử lý xóa chuyến xe
    private void deleteBusTrip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int tripId = Integer.parseInt(request.getParameter("tripId"));
        busTripDAO.deleteBusTrip(tripId);
        response.sendRedirect("bus-trips");
    }

    // Chuyển request thành đối tượng BusTrip
    private BusTrip parseBusTripFromRequest(HttpServletRequest request) {
        try {
            int tripId = request.getParameter("tripId") != null ? Integer.parseInt(request.getParameter("tripId")) : 0;
            int routeId = Integer.parseInt(request.getParameter("routeId"));
            int busId = Integer.parseInt(request.getParameter("busId"));
            int driverId = Integer.parseInt(request.getParameter("driverId"));

            // Kiểm tra driverId có hợp lệ không (phải có role_id = 4)
            List<User> validDrivers = busTripDAO.getAllDrivers();
            boolean isValidDriver = validDrivers.stream().anyMatch(driver -> driver.getUserId() == driverId);
            if (!isValidDriver) {
                return null; // Trả về null nếu tài xế không hợp lệ
            }

            LocalDateTime departureTime = LocalDateTime.parse(request.getParameter("departureTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime arrivalTime = request.getParameter("arrivalTime") != null && !request.getParameter("arrivalTime").isEmpty() ?
                    LocalDateTime.parse(request.getParameter("arrivalTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;

            String status = request.getParameter("status");
            int availableSeats = Integer.parseInt(request.getParameter("availableSeats"));
            BigDecimal currentPrice = new BigDecimal(request.getParameter("currentPrice"));

            BusTrip trip = new BusTrip();
            trip.setTripId(tripId);
            trip.setRoute(new Route());
            trip.getRoute().setRouteId(routeId);
            trip.setBus(new Bus());
            trip.getBus().setBusId(busId);
            trip.setDriver(new User());
            trip.getDriver().setUserId(driverId);
            trip.setDepartureTime(departureTime);
            trip.setArrivalTime(arrivalTime);
            trip.setStatus(status);
            trip.setAvailableSeats(availableSeats);
            trip.setCurrentPrice(currentPrice);

            return trip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}

