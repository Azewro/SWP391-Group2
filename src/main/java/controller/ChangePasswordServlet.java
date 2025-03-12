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

@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {

    private UserProfileDAO userprofileDAO;

    @Override
    public void init() {
        userprofileDAO = new UserProfileDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("change-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validation
        boolean hasError = false;

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("confirmError", "Passwords do not match");
            hasError = true;
        }

        if (hasError) {
            request.getRequestDispatcher("change-password.jsp").forward(request, response);
            return;
        }

        // Update password using JDBC DAO
        boolean successful = userprofileDAO.updatePassword(user.getUserId(), currentPassword, newPassword);

        if (successful) {
            request.setAttribute("successMessage", "Password changed successfully");
        } else {
            request.setAttribute("currentError", "Current password is incorrect");
        }

        request.getRequestDispatcher("change-password.jsp").forward(request, response);
    }
}

