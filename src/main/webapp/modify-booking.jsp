<%--
  Created by IntelliJ IDEA.
  User: LONG
  Date: 3/12/2025
  Time: 9:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.OrderDetail" %>
<%@ page import="model.Ticket" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    // Lấy đối tượng OrderDetail từ request (được servlet load dựa trên orderId)
    OrderDetail orderDetail = (OrderDetail) request.getAttribute("orderDetail");
    if (orderDetail == null) {
        out.println("<h3>Không tìm thấy thông tin đặt vé cho đơn hàng này.</h3>");
        return;
    }
    Ticket ticket = orderDetail.getTicket();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa đặt vé - Order ID: <%= orderDetail.getOrder().getOrderId() %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .container { margin-top: 30px; max-width: 700px; }
        .card { border-radius: 10px; padding: 20px; }
    </style>
</head>
<body>
<div class="container">
    <h2 class="mb-4">Thông tin đặt vé</h2>
    <div class="card mb-4">
        <h5>Thông tin hiện tại</h5>
        <div class="mb-2">
            <strong>Order ID:</strong> <%= orderDetail.getOrder().getOrderId() %>
        </div>
        <div class="mb-2">
            <strong>Ngày đặt:</strong>
            <%
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                out.print(orderDetail.getOrder().getOrderDate().format(formatter));
            %>
        </div>
        <div class="mb-2">
            <strong>Tổng tiền:</strong> <%= orderDetail.getOrder().getTotalAmount() %> VNĐ
        </div>
        <div class="mb-2">
            <strong>Trạng thái:</strong> <%= orderDetail.getOrder().getStatus() %>
        </div>
        <hr>
        <h5>Thông tin vé</h5>
        <div class="mb-2">
            <strong>Ticket ID:</strong> <%= ticket.getTicketId() %>
        </div>
        <div class="mb-2">
            <strong>Ghế hiện tại:</strong> <%= ticket.getSeat().getSeatId() %>
        </div>
        <div class="mb-2">
            <strong>Chuyến xe hiện tại:</strong> <%= ticket.getTrip().getTripId() %>
        </div>
        <div class="mb-2">
            <strong>Giá hiện tại:</strong> <%= ticket.getPrice() %>
        </div>
    </div>

    <h2 class="mb-4">Chỉnh sửa đặt vé</h2>
    <!-- Form cập nhật vé -->
    <form action="modify-booking" method="post">
        <!-- Gửi OrderDetail ID để truy xuất đúng đối tượng -->
        <input type="hidden" name="orderDetailId" value="<%= orderDetail.getOrderDetailId() %>">
        <div class="mb-3">
            <label for="newSeatId" class="form-label">Nhập Seat ID mới (nếu muốn thay đổi):</label>
            <input type="number" class="form-control" id="newSeatId" name="newSeatId" placeholder="Ví dụ: 101">
        </div>
        <div class="mb-3">
            <label for="newTripId" class="form-label">Nhập Trip ID mới (nếu muốn thay đổi):</label>
            <input type="number" class="form-control" id="newTripId" name="newTripId" placeholder="Ví dụ: 202">
        </div>
        <!-- Không cần nhập Price -->
        <button type="submit" class="btn btn-primary">Cập nhật vé</button>
    </form>

    <!-- Form Huỷ đặt vé -->
    <form action="cancelBooking" method="post" style="margin-top:20px;">
        <!-- Gửi OrderDetail ID để xác định vé cần huỷ -->
        <input type="hidden" name="orderDetailId" value="<%= orderDetail.getOrderDetailId() %>">
        <button type="submit" class="btn btn-danger">Huỷ đặt vé</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
