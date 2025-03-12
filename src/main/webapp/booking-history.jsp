<%--
  Created by IntelliJ IDEA.
  User: LONG
  Date: 3/12/2025
  Time: 9:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Ticket" %>

<%
    List<Ticket> tickets = (List<Ticket>) request.getAttribute("tickets");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking History</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 50px;
            max-width: 800px;
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
    <h3 class="text-center">Booking History</h3>
    <div class="card">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Ticket ID</th>
                <th>Trip ID</th>
                <th>Seat ID</th>
                <th>Price</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <% if (tickets != null && !tickets.isEmpty()) {
                for (Ticket ticket : tickets) { %>
            <tr>
                <td><%= ticket.getTicketId() %></td>
                <td><%= ticket.getTrip().getTripId() %></td>
                <td><%= ticket.getSeat().getSeatId() %></td>
                <td><%= ticket.getPrice() %></td>
                <td><%= ticket.getStatus() %></td>
                <td>
                    <% if (!"Cancelled".equals(ticket.getStatus())) { %>
                    <form action="cancelBooking" method="post" style="display:inline;">
                        <input type="hidden" name="ticketId" value="<%= ticket.getTicketId() %>">
                        <button type="submit" class="btn btn-danger btn-sm">Cancel Booking</button>
                    </form>
                    <% } else { %>
                    <span class="text-muted">Cancelled</span>
                    <% } %>
                </td>
            </tr>
            <% }
            } else { %>
            <tr>
                <td colspan="6" class="text-center">No bookings found</td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <div class="text-center">
            <a href="userprofile.jsp" class="btn btn-secondary">Back to User Profile</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
