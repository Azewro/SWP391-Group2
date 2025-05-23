package controller;

import dao.AdminBusDAO;
import dao.AdminBusMaintenanceDAO;
import model.Bus;
import model.BusMaintenanceLog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/admin/bus")
public class AdminBusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminBusDAO busDAO;
    private AdminBusMaintenanceDAO maintenanceDAO;

    @Override
    public void init() throws ServletException {
        busDAO = new AdminBusDAO();
        maintenanceDAO = new AdminBusMaintenanceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {
            case "list":
                listBuses(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteBus(request, response);
                break;
            case "maintenance":
                listMaintenanceLogs(request, response);
                break;
            case "search":
                searchBus(request, response);
                break;

            default:
                listBuses(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "insert":
                insertBus(request, response);
                break;
            case "update":
                updateBus(request, response);
                break;
            case "addMaintenance":
                addMaintenanceLog(request, response);
                break;
            default:
                response.sendRedirect("bus?action=list");
                break;
        }
    }

    // 1. Hiển thị danh sách xe buýt
    private void listBuses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        AdminBusDAO busDAO = new AdminBusDAO();
        List<Bus> busList = busDAO.getBusesByPage((page - 1) * recordsPerPage, recordsPerPage);
        int totalRecords = busDAO.getTotalBusCount();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        request.setAttribute("busList", busList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/admin/bus-list.jsp").forward(request, response);
    }

    private void searchBus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");

        List<Bus> busList = busDAO.searchBusByPlateNumber(keyword);
        request.setAttribute("busList", busList);

        // Không cần phân trang khi tìm kiếm
        request.setAttribute("currentPage", 1);
        request.setAttribute("totalPages", 1);

        request.getRequestDispatcher("/admin/bus-list.jsp").forward(request, response);
    }


    // 2. Hiển thị form chỉnh sửa xe buýt
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String busIdParam = request.getParameter("busId");

        if (busIdParam != null && !busIdParam.isEmpty()) { // Chỉ lấy busId nếu sửa xe
            int busId;
            try {
                busId = Integer.parseInt(busIdParam);
            } catch (NumberFormatException e) {
                response.sendRedirect("bus?action=list");
                return;
            }

            Bus existingBus = busDAO.getBusById(busId);
            if (existingBus == null) {
                response.sendRedirect("bus?action=list");
                return;
            }

            request.setAttribute("bus", existingBus);
        }

        request.getRequestDispatcher("/admin/bus-form.jsp").forward(request, response);
    }



    // 3. Thêm xe buýt mới
    private void insertBus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String plateNumber = request.getParameter("plateNumber");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        String busType = request.getParameter("busType");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        Bus newBus = new Bus(0, plateNumber, capacity, busType, isActive, null);
        busDAO.insertBus(newBus);

        response.sendRedirect("bus?action=list");
    }

    // 4. Cập nhật thông tin xe buýt
    private void updateBus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int busId = Integer.parseInt(request.getParameter("busId"));
        String plateNumber = request.getParameter("plateNumber");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        String busType = request.getParameter("busType");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        Bus updatedBus = new Bus(busId, plateNumber, capacity, busType, isActive, null);
        busDAO.updateBus(updatedBus);

        response.sendRedirect("bus?action=list");
    }

    // 5. Xóa xe buýt
    private void deleteBus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int busId = Integer.parseInt(request.getParameter("busId"));
        busDAO.deleteBus(busId);
        response.sendRedirect("bus?action=list");
    }

    // 6. Hiển thị lịch sử bảo trì của xe buýt
    private void listMaintenanceLogs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int busId = Integer.parseInt(request.getParameter("busId"));
        List<BusMaintenanceLog> maintenanceLogs = maintenanceDAO.getMaintenanceLogsByBusId(busId);

        request.setAttribute("maintenanceLogs", maintenanceLogs);
        request.getRequestDispatcher("/admin/bus-maintenance.jsp").forward(request, response);
    }

    // 7. Thêm lịch sử bảo trì mới
    private void addMaintenanceLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int busId = Integer.parseInt(request.getParameter("busId"));
        LocalDateTime maintenanceDate = LocalDateTime.parse(request.getParameter("maintenanceDate"));
        String description = request.getParameter("description");
        BigDecimal cost = new BigDecimal(request.getParameter("cost"));
        String status = request.getParameter("status");

        BusMaintenanceLog log = new BusMaintenanceLog(0, busId, maintenanceDate, description, cost, status);
        maintenanceDAO.insertMaintenanceLog(log);

        response.sendRedirect("bus?action=maintenance&busId=" + busId);
    }




}
