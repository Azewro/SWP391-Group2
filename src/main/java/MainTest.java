import dao.AdminBusTripDAO;
import dao.AdminOrderDAO;
import dao.AdminOrderDetailDAO;
import model.Bus;
import model.Order;
import model.Route;
import model.User;
import java.util.List;
import model.OrderDetail;
import model.Ticket;

public class MainTest {
    public static void main(String[] args) {
        AdminBusTripDAO busTripDAO = new AdminBusTripDAO();
        AdminOrderDAO orderDAO = new AdminOrderDAO(); // Khai báo DAO quản lý đơn hàng

        // Test lấy danh sách tuyến đường
        System.out.println("==== DANH SÁCH TUYẾN ĐƯỜNG ====");
        List<Route> routes = busTripDAO.getAllRoutes();
        if (routes.isEmpty()) {
            System.out.println("Không có dữ liệu tuyến đường!");
        } else {
            for (Route route : routes) {
                System.out.println("Route ID: " + route.getRouteId() + " - Name: " + route.getRouteName());
            }
        }

        // Test lấy danh sách xe bus
        System.out.println("\n==== DANH SÁCH XE BUS ====");
        List<Bus> buses = busTripDAO.getAllBuses();
        if (buses.isEmpty()) {
            System.out.println("Không có dữ liệu xe bus!");
        } else {
            for (Bus bus : buses) {
                System.out.println("Bus ID: " + bus.getBusId() +
                        " - Biển số: " + bus.getPlateNumber() +
                        " - Loại xe: " + bus.getBusType() +
                        " - Số chỗ: " + bus.getCapacity());
            }
        }

        // Test lấy danh sách tài xế
        System.out.println("\n==== DANH SÁCH TÀI XẾ ====");
        List<User> drivers = busTripDAO.getAllDrivers();
        if (drivers.isEmpty()) {
            System.out.println("Không có tài xế nào có role_id = 4!");
        } else {
            for (User driver : drivers) {
                System.out.println("Driver ID: " + driver.getUserId() + " - Tên: " + driver.getFullName());
            }
        }

        // ============================ KIỂM TRA DANH SÁCH ĐƠN HÀNG ============================
        System.out.println("\n==== DANH SÁCH ĐƠN HÀNG ====");
        List<Order> orders = orderDAO.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("⚠️ Không có đơn hàng nào trong database!");
        } else {
            for (Order order : orders) {
                System.out.println("-----------------------------");
                System.out.println("🆔 Order ID: " + order.getOrderId());
                System.out.println("👤 Khách hàng: " + (order.getUser() != null ? order.getUser().getFullName() : "❌ Không có khách hàng"));
                System.out.println("📅 Ngày đặt: " + (order.getOrderDate() != null ? order.getOrderDate() : "❌ Không có ngày đặt"));
                System.out.println("💰 Tổng tiền: " + order.getTotalAmount() + " VNĐ");
                System.out.println("📌 Trạng thái: " + order.getStatus());
            }
        }

        // ============================ KIỂM TRA CHI TIẾT ĐƠN HÀNG ============================
        AdminOrderDetailDAO lmao = new AdminOrderDetailDAO();
        int testOrderId = 1; // Nhập ID đơn hàng cần kiểm tra
        System.out.println("\n==== CHI TIẾT ĐƠN HÀNG (Order ID: " + testOrderId + ") ====");
        List<OrderDetail> details = lmao.getOrderDetailsByOrderId(testOrderId);

        if (details.isEmpty()) {
            System.out.println("⚠️ Không có chi tiết đơn hàng nào cho Order ID: " + testOrderId);
        } else {
            for (OrderDetail detail : details) {
                Ticket ticket = detail.getTicket();
                System.out.println("-----------------------------");
                System.out.println("🎟️ ID Vé: " + ticket.getTicketId());
                System.out.println("🚌 Chuyến xe: " + (ticket.getTrip() != null ? ticket.getTrip().getTripId() : "❌ Không có dữ liệu"));
                System.out.println("💺 Ghế: " + (ticket.getSeat() != null ? ticket.getSeat().getSeatNumber() : "❌ Không có dữ liệu"));
                System.out.println("💰 Giá: " + detail.getPrice() + " VNĐ");
                System.out.println("📌 Trạng thái vé: " + (ticket.getStatus() != null ? ticket.getStatus() : "❌ Không có dữ liệu"));
            }
        }
    }

    }

