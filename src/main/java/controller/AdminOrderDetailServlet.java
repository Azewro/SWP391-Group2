package controller;

import dao.AdminOrderDetailDAO;
import model.OrderDetail;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/order-details")
public class AdminOrderDetailServlet extends HttpServlet {
    private AdminOrderDetailDAO orderDetailDAO = new AdminOrderDetailDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        List<OrderDetail> orderDetails = orderDetailDAO.getOrderDetailsByOrderId(orderId);

        request.setAttribute("orderDetails", orderDetails);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/AdminOrderDetails.jsp");
        dispatcher.forward(request, response);
    }
}
