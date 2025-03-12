<%--
  Created by IntelliJ IDEA.
  User: LONG
  Date: 3/6/2025
  Time: 7:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            background-color: #f8f9fa;
        }
        .password-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>

<!-- Header -->
<header class="header bg-dark text-white">
    <div class="text-center py-2">
        <img src="<%= request.getContextPath() %>/assets/images/logo.png" alt="G2 Bus Ticket" height="60">
    </div>
    <div class="container">
        <div class="d-flex justify-content-between align-items-center">
            <nav class="nav">
                <a class="nav-link text-white fw-bold" href="index.jsp">TRANG CHỦ</a>
                <a class="nav-link text-white fw-bold" href="bus-schedule">LỊCH TRÌNH</a>
                <a class="nav-link text-white fw-bold" href="#">TRA CỨU VÉ</a>
                <a class="nav-link text-white fw-bold" href="#">TIN TỨC</a>
                <a class="nav-link text-white fw-bold" href="#">HÓA ĐƠN</a>
                <a class="nav-link text-white fw-bold" href="#">LIÊN HỆ</a>
                <a class="nav-link text-white fw-bold" href="#">VỀ CHÚNG TÔI</a>
            </nav>
            <div class="dropdown">
                <button class="btn btn-outline-light dropdown-toggle" type="button" id="profileDropdown" data-bs-toggle="dropdown">
                </button>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li><a class="dropdown-item" href="userprofile.jsp">Thông tin cá nhân</a></li>
                    <li><a class="dropdown-item" href="change-password.jsp">Đổi mật khẩu</a></li>
                    <li><a class="dropdown-item text-danger" href="logout.jsp">Đăng xuất</a></li>
                </ul>
            </div>
        </div>
    </div>
</header>

<!-- Change Password Form -->
<div class="container password-container">
    <h3 class="text-center">Đổi Mật Khẩu</h3>

    <!-- Hiển thị thông báo từ Servlet -->
    <% if (request.getAttribute("currentError") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("currentError") %></div>
    <% } %>

    <% if (request.getAttribute("passwordError") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("passwordError") %></div>
    <% } %>

    <% if (request.getAttribute("successMessage") != null) { %>
    <div class="alert alert-success"><%= request.getAttribute("successMessage") %></div>
    <% } %>

    <form id="changePasswordForm" action="change-password" method="post">
        <div class="mb-3">
            <label class="form-label">Mật khẩu cũ</label>
            <input type="password" class="form-control" id="oldPassword" name="oldPassword" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Mật khẩu mới</label>
            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Xác nhận mật khẩu mới</label>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Cập nhật mật khẩu</button>
    </form>
</div>

<!-- JavaScript validation -->
<script>
    document.getElementById("changePasswordForm").addEventListener("submit", function(event) {
        let oldPassword = document.getElementById("oldPassword").value.trim();
        let newPassword = document.getElementById("newPassword").value.trim();
        let confirmPassword = document.getElementById("confirmPassword").value.trim();

        // Kiểm tra mật khẩu mới phải có ít nhất 8 ký tự, 1 số, 1 chữ hoa, 1 chữ thường
        let passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;
        if (!passwordRegex.test(newPassword)) {
            alert("Mật khẩu mới phải có ít nhất 8 ký tự, bao gồm ít nhất 1 chữ số, 1 chữ in hoa và 1 chữ thường.");
            event.preventDefault();
            return;
        }

        // Kiểm tra mật khẩu mới không trùng mật khẩu cũ
        if (oldPassword === newPassword) {
            alert("Mật khẩu mới không được giống mật khẩu cũ.");
            event.preventDefault();
            return;
        }

        // Kiểm tra xác nhận mật khẩu
        if (newPassword !== confirmPassword) {
            alert("Mật khẩu mới và xác nhận mật khẩu không khớp.");
            event.preventDefault();
        }
    });
</script>

</body>
</html>
