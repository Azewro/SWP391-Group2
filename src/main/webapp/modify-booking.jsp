<%--
  Created by IntelliJ IDEA.
  User: LONG
  Date: 3/12/2025
  Time: 9:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modify Booking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 600px;
            margin-top: 50px;
        }
        .card {
            border-radius: 10px;
            padding: 20px;
        }
    </style>
</head>
<body>
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

<div class="container">
    <div class="card">
        <h3 class="text-center">Modify Booking</h3>
        <form action="modifyBooking" method="post">
            <div class="mb-3">
                <label for="ticketId" class="form-label">Ticket ID</label>
                <input type="number" class="form-control" id="ticketId" name="ticketId" required>
            </div>
            <div class="mb-3">
                <label for="tripId" class="form-label">Trip ID</label>
                <input type="number" class="form-control" id="tripId" name="tripId" required>
            </div>
            <div class="mb-3">
                <label for="seatId" class="form-label">Seat ID</label>
                <input type="number" class="form-control" id="seatId" name="seatId" required>
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">Price</label>
                <input type="text" class="form-control" id="price" name="price" required>
            </div>
            <div class="mb-3">
                <label for="status" class="form-label">Status</label>
                <select class="form-select" id="status" name="status" required>
                    <option value="Confirmed">Confirmed</option>
                    <option value="Pending">Pending</option>
                    <option value="Cancelled">Cancelled</option>
                </select>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-primary">Update Booking</button>
                <a href="userprofile.jsp" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

