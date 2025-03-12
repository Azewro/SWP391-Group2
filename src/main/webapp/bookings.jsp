<%--
  Created by IntelliJ IDEA.
  User: LONG
  Date: 3/12/2025
  Time: 3:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Booking" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bookings</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
</head>
<body class="bg-light">
<header class="header bg-dark text-white">
    <!-- Logo trên cùng -->
    <div class="text-center py-2">
        <img src="<%= request.getContextPath() %>/assets/images/logo.png" alt="G2 Bus Ticket" height="60">
    </div>

    <!-- Menu & Nút đăng nhập bên dưới -->
    <div class="container">
        <div class="d-flex justify-content-between align-items-center">
            <!-- Menu -->
            <nav class="nav">
                <a class="nav-link text-white fw-bold" href="index.jsp">TRANG CHỦ</a>
                <a class="nav-link text-white fw-bold" href="bus-schedule">LỊCH TRÌNH</a>
                <a class="nav-link text-white fw-bold" href="#">TRA CỨU VÉ</a>
                <a class="nav-link text-white fw-bold" href="#">TIN TỨC</a>
                <a class="nav-link text-white fw-bold" href="#">HÓA ĐƠN</a>
                <a class="nav-link text-white fw-bold" href="#">LIÊN HỆ</a>
                <a class="nav-link text-white fw-bold" href="#">VỀ CHÚNG TÔI</a>
            </nav>

            <!-- Nút đăng nhập / đăng ký -->
            <div>
                <div class="dropdown">
                    <button class="btn btn-outline-light dropdown-toggle" type="button" id="profileDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileDropdown">
                        <li><a class="dropdown-item" href="userprofile.jsp"><i class="bi bi-person"></i> Thông tin cá nhân</a></li>
                        <li><a class="dropdown-item" href="change-password.jsp"><i class="bi bi-key"></i> Đổi mật khẩu</a></li>
                        <li><a class="dropdown-item text-danger" href="logout.jsp"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</header>

<div class="container mt-5">
    <h2 class="text-center mb-4">Your Bookings</h2>

    <% HttpSession sessionObj = request.getSession(false);
        User user = (User) sessionObj.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
    %>

    <div class="card shadow p-3 mb-4">
        <table class="table table-striped">
            <thead class="table-dark">
            <tr>
                <th>Title</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <% if (bookings != null && !bookings.isEmpty()) { %>
            <% for (Booking booking : bookings) { %>
            <tr>
                <td><%= booking.getBookingTitle() %></td>
                <td><%= booking.getBookingDate() %></td>
            </tr>
            <% } %>
            <% } else { %>
            <tr>
                <td colspan="2" class="text-center text-muted">No bookings found</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <div class="card shadow p-4">
        <h3 class="text-center">Add New Booking</h3>
        <form action="bookings" method="post" class="mt-3">
            <div class="mb-3">
                <label for="bookingTitle" class="form-label">Title:</label>
                <input type="text" id="bookingTitle" name="bookingTitle" class="form-control" value="<%= request.getAttribute("bookingTitle") != null ? request.getAttribute("bookingTitle") : "" %>">
                <% if (request.getAttribute("titleError") != null) { %>
                <small class="text-danger"><%= request.getAttribute("titleError") %></small>
                <% } %>
            </div>

            <div class="mb-3">
                <label for="bookingDate" class="form-label">Date:</label>
                <input type="datetime-local" id="bookingDate" name="bookingDate" class="form-control" value="<%= request.getAttribute("bookingDate") != null ? request.getAttribute("bookingDate") : "" %>">
                <% if (request.getAttribute("dateError") != null) { %>
                <small class="text-danger"><%= request.getAttribute("dateError") %></small>
                <% } %>
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-primary"><i class="fas fa-plus"></i> Book</button>
            </div>
        </form>
    </div>

    <div class="text-center mt-4">
        <a href="logout" class="btn btn-danger"><i class="fas fa-sign-out-alt"></i> Logout</a>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>
