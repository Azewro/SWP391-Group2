package controller;

import model.User;
import dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@WebServlet("/verify")
public class VerifyOTPServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String otpCode = (String) session.getAttribute("otp_code");
        User tempUser = (User) session.getAttribute("otp_user");
        Instant otpCreationTime = (Instant) session.getAttribute("otp_creation_time");

        if (otpCode == null || tempUser == null || otpCreationTime == null) {
            response.sendRedirect("register.jsp?error=Phiên đã hết hạn, vui lòng đăng ký lại.");
            return;
        }

        // ✅ Kiểm tra thời gian OTP (chỉ có hiệu lực trong 5 phút)
        Instant now = Instant.now();
        Duration duration = Duration.between(otpCreationTime, now);
        if (duration.toMinutes() > 5) {
            session.removeAttribute("otp_code");
            session.removeAttribute("otp_creation_time");
            session.removeAttribute("otp_user");
            request.setAttribute("error", "Mã OTP đã hết hạn, vui lòng đăng ký lại.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        String enteredOTP = request.getParameter("otp");
        if (!enteredOTP.equals(otpCode)) {
            request.setAttribute("error", "Mã OTP không chính xác!");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
            return;
        }

        // ✅ Hash mật khẩu trước khi lưu vào database
        tempUser.setPasswordHash(BCrypt.hashpw(tempUser.getPasswordHash(), BCrypt.gensalt(12)));

        // ✅ Lưu user vào database với role "customer"
        boolean success = userDAO.saveUser(tempUser);

        // ✅ Xóa thông tin OTP khỏi session sau khi xác thực thành công
        session.removeAttribute("otp_code");
        session.removeAttribute("otp_creation_time");
        session.removeAttribute("otp_user");

        if (success) {
            response.sendRedirect("login.jsp?registerSuccess=true");
        } else {
            request.setAttribute("error", "Lỗi hệ thống, vui lòng thử lại.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
