package controller;

import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderDetail;
import model.Ticket;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/modify-booking")
public class ModifyBookingServlet extends HttpServlet {
    private BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        try {
            List<OrderDetail> orderDetails = bookingDAO.getOrderDetailsByOrderId(orderId);
            req.setAttribute("orderDetails", orderDetails);
            req.getRequestDispatcher("modify-booking.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            // Có thể chuyển sang trang lỗi hoặc hiển thị thông báo
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi truy vấn dữ liệu");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}