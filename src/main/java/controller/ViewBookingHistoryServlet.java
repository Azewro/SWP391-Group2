package controller;

import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/booking-history")
public class ViewBookingHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingDAO bookingDAO;

    @Override
    public void init() throws ServletException {
        bookingDAO = new BookingDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy session hiện tại, không tạo mới nếu chưa có
        HttpSession session = request.getSession(false);

        // Kiểm tra đăng nhập
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy user từ session
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId(); // Lấy userId từ đối tượng User

        // Lấy lịch sử booking của user
        List<Order> orderList = bookingDAO.viewBookingHistory(userId);

        // Đặt dữ liệu vào request để đẩy sang JSP
        request.setAttribute("orderList", orderList);
        request.getRequestDispatcher("/booking-history.jsp").forward(request, response);
    }
}
