package controller;

import dao.UserProfileDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class UserProfileServlet extends HttpServlet {

    private UserProfileDAO userProfileDAO;

    @Override
    public void init() {
        userProfileDAO = new UserProfileDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        boolean hasError = false;

        // Kiểm tra email có thay đổi không
        if (!user.getEmail().equals(email)) {
            User existingUser = userProfileDAO.findByEmail(email);
            if (existingUser != null && existingUser.getUserId() != user.getUserId()) {
                request.setAttribute("emailError", "Email đã tồn tại");
                hasError = true;
            }
        }

        // Xử lý đổi mật khẩu nếu có nhập mật khẩu mới
        if (oldPassword != null && !oldPassword.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            boolean passwordUpdated = userProfileDAO.updatePassword(user.getUserId(), oldPassword, newPassword);
            if (!passwordUpdated) {
                request.setAttribute("passwordError", "Mật khẩu cũ không đúng");
                hasError = true;
            }
        }

        if (hasError) {
            request.setAttribute("user", user);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("phoneNumber", phoneNumber);
            request.getRequestDispatcher("userprofile.jsp").forward(request, response);
            return;
        }

        // Cập nhật thông tin user
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phoneNumber);

        userProfileDAO.updateProfile(user);

        // Cập nhật lại session
        session.setAttribute("user", user);

        request.setAttribute("user", user);
        request.setAttribute("successMessage", "Cập nhật hồ sơ thành công");
        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
    }
}
