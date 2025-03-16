<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    List<Order> orders = (List<Order>) request.getAttribute("bookingHistory");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử đặt vé</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 30px;
            max-width: 900px;
        }
        .card {
            border-radius: 10px;
            padding: 20px;
        }
    </style>
</head>
<body>

<!-- Header -->
<header class="bg-dark text-white text-center py-3">
    <h2>🚍 Lịch sử đặt vé</h2>
</header>

<!-- Nội dung chính -->
<div class="container">
    <div class="card shadow-sm">
        <table class="table table-hover">
            <thead class="table-dark">
            <tr>
                <th>Ngày đặt</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <% if (orders != null && !orders.isEmpty()) {
                for (Order order : orders) { %>
            <tr>
                <td><%= order.getOrderDate().format(formatter) %></td>
                <td><%= order.getTotalAmount() %> VNĐ</td>
                <td><%= order.getStatus() %></td>
                <td><a href="modify-booking.jsp?orderId=<%= order.getOrderId() %>" class="btn btn-primary btn-sm">Xem chi tiết</a></td>
            </tr>
            <% }
            } else { %>
            <tr>
                <td colspan="3" class="text-center text-muted">Không có đơn hàng nào.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
