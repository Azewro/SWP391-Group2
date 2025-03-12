package controller;

import dao.AdminOrderDAO;
import dao.AdminOrderDetailDAO;
import model.Order;
import model.OrderDetail;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/order-details")
public class AdminOrderDetailServlet extends HttpServlet {
    private AdminOrderDetailDAO orderDetailDAO = new AdminOrderDetailDAO();
    private AdminOrderDAO orderDAO = new AdminOrderDAO();  // Thêm DAO để lấy Order

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy orderId từ request
            int orderId = Integer.parseInt(request.getParameter("orderId"));

            // Lấy thông tin đơn hàng
            Order order = orderDAO.getOrderById(orderId);
            List<OrderDetail> orderDetails = orderDetailDAO.getOrderDetailsByOrderId(orderId);

            // Kiểm tra nếu order không tồn tại
            if (order == null) {
                request.setAttribute("errorMessage", "Đơn hàng không tồn tại.");
                request.getRequestDispatcher("/admin/orders.jsp").forward(request, response);
                return;
            }

            // Đặt dữ liệu vào requestScope
            request.setAttribute("order", order);
            request.setAttribute("orderDetails", orderDetails);

            // Chuyển hướng đến trang JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/AdminOrderDetails.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Lỗi: ID đơn hàng không hợp lệ.");
            request.getRequestDispatcher("/admin/orders.jsp").forward(request, response);
        }
    }
}
