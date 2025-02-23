package controller;

import dao.UserDAO;
import model.User;
import util.PasswordUtils;
import java.io.IOException;
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

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByUsernameOrEmail(usernameOrEmail);

        if (user != null && PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("index.jsp"); // Chuyển hướng sau khi đăng nhập thành công
        } else {
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
