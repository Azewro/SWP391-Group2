package controller;

import dao.BookingDAO;
import jakarta.servlet.http.HttpSession;
import model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.util.List;

import static model.User_.userId;

@WebServlet("/booking-history")
public class ViewBookingHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingDAO bookingDAO;

    @Override
    public void init() throws ServletException {
        bookingDAO = new BookingDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy user từ session
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");

            // Kiểm tra nếu chưa đăng nhập
            if (currentUser == null) {
                response.sendRedirect("login.jsp"); // Nếu chưa đăng nhập, chuyển về trang login
                return;
            }

            int userId = currentUser.getUserId(); // Lấy userId từ session

            // Gọi DAO để lấy danh sách đơn hàng theo userId
            List<Order> bookingHistory = bookingDAO.viewBookingHistory(userId);

            // Đưa danh sách đơn hàng vào request để hiển thị trên JSP
            request.setAttribute("bookingHistory", bookingHistory);
            request.getRequestDispatcher("booking-history.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp"); // Nếu có lỗi, chuyển về trang chính
        }
    }
}
