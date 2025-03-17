package controller;

import dao.UserDAO;
import dao.RoleDAO;
import model.Role;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/users")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;
    private RoleDAO roleDAO;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAO();
            roleDAO = new RoleDAO();
        } catch (SQLException e) {
            throw new ServletException("Lỗi kết nối database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                showEditForm(request, response);
            } else if ("delete".equals(action)) {
                deleteUser(request, response);
            } else if ("restore".equals(action)) {
                restoreUser(request, response);
            } else {
                listUsers(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Lỗi xử lý dữ liệu", e);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String search = request.getParameter("search");
        String statusParam = request.getParameter("status");
        Boolean isActive = (statusParam == null || statusParam.isEmpty()) ? null : Boolean.parseBoolean(statusParam);


        List<User> userList = userDAO.getAllUsers(search, isActive);
        List<Role> roleList = roleDAO.getAllRoles();

        request.setAttribute("users", userList);
        request.setAttribute("roles", roleList);
        request.getRequestDispatcher("/admin/user_list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.getUserById(userId);
        List<Role> roleList = roleDAO.getAllRoles();

        request.setAttribute("user", existingUser);
        request.setAttribute("roles", roleList);
        request.getRequestDispatcher("/admin/user_form.jsp").forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String statusReason = request.getParameter("statusReason");

        if (statusReason == null || statusReason.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập lý do vô hiệu hóa tài khoản.");
            try {
                listUsers(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        userDAO.disableUser(userId, statusReason);
        response.sendRedirect("users"); // Quay lại danh sách người dùng
    }


    private void restoreUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        userDAO.restoreUser(userId);
        response.sendRedirect("users"); // Quay lại danh sách người dùng
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("update".equals(action)) {
                updateUser(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Lỗi cập nhật người dùng", e);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        User user = new User(userId, null, fullName, email, phone, roleId, isActive);
        userDAO.updateUser(user);
        response.sendRedirect("users");
    }
}
