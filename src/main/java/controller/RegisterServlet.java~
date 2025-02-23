package controller;

import service.EmailService;
import service.UserService;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String fullName = request.getParameter("fullName");

        if (userService.isUserExist(email, username)) {
            request.setAttribute("error", "Email hoặc tên đăng nhập đã tồn tại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // ✅ Tạo mã OTP ngẫu nhiên (6 chữ số)
        String otpCode = String.valueOf(new Random().nextInt(900000) + 100000);
        Instant otpCreationTime = Instant.now(); // ✅ Lưu thời gian OTP được tạo

        // ✅ Lưu thông tin vào session để chờ xác thực OTP
        HttpSession session = request.getSession();
        session.setAttribute("otp_code", otpCode);
        session.setAttribute("otp_email", email);
        session.setAttribute("otp_user", new User(username, password, email, phone, fullName));
        session.setAttribute("otp_creation_time", otpCreationTime);

        // ✅ Gửi OTP qua email
        EmailService.sendEmail(email, "Xác thực tài khoản", "Mã OTP của bạn là: " + otpCode + "\nMã này có hiệu lực trong 5 phút.");

        // ✅ Chuyển hướng sang trang nhập OTP
        response.sendRedirect("verify.jsp");
    }
}
