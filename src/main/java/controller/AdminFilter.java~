package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*") // Chỉ áp dụng cho các trang trong thư mục /admin
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Kiểm tra session có tồn tại không
        if (session == null || session.getAttribute("role_id") == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp"); // Chưa đăng nhập -> về trang login
            return;
        }

        // Lấy role_id từ session
        Integer roleId = (Integer) session.getAttribute("role_id");

        // Nếu không phải admin (role_id != 1) -> không có quyền truy cập
        if (roleId == null || roleId != 1) {
            res.sendRedirect(req.getContextPath() + "/login.jsp"); // Chuyển hướng đến trang không có quyền
            return;
        }

        // Nếu đúng là admin, tiếp tục request
        chain.doFilter(request, response);
    }
}
