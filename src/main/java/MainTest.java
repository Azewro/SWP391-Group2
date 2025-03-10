import dao.AdminBusTripDAO;
import model.Bus;
import model.Route;
import model.User;

import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        AdminBusTripDAO dao = new AdminBusTripDAO();

        // Test lấy danh sách tuyến đường
        System.out.println("==== DANH SÁCH TUYẾN ĐƯỜNG ====");
        List<Route> routes = dao.getAllRoutes();
        if (routes.isEmpty()) {
            System.out.println("Không có dữ liệu tuyến đường!");
        } else {
            for (Route route : routes) {
                System.out.println("Route ID: " + route.getRouteId() + " - Name: " + route.getRouteName());
            }
        }

        // Test lấy danh sách xe bus
        System.out.println("\n==== DANH SÁCH XE BUS ====");
        List<Bus> buses = dao.getAllBuses();
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
        List<User> drivers = dao.getAllDrivers();
        if (drivers.isEmpty()) {
            System.out.println("Không có tài xế nào có role_id = 4!");
        } else {
            for (User driver : drivers) {
                System.out.println("Driver ID: " + driver.getUserId() + " - Tên: " + driver.getFullName());
            }
        }
    }
}
