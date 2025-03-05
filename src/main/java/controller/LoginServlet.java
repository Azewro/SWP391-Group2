package controller;

import dao.UserDAO;
import model.User;
import util.PasswordUtils;
import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");

        UserDAO userDAO = null;
        try {
            userDAO = new UserDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        User user = userDAO.findByUsernameOrEmail(usernameOrEmail);

//        if (user != null && PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
        if (user != null && password.endsWith(user.getPasswordHash())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role_id", user.getRoleId()); // Lưu role_id vào session

            // Kiểm tra nếu là admin, chuyển hướng đến admin dashboard
            if (user.getRoleId() == 1) {
                response.sendRedirect("admin/dashboard.jsp");
            } else {
                response.sendRedirect("index.jsp"); // Người dùng bình thường về trang chính
            }
        } else {
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
