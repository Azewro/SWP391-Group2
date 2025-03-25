<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chỉnh sửa Vé</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-container { width: 500px; margin: auto; border: 1px solid #ccc; padding: 20px; border-radius: 8px; }
        .form-container h2 { text-align: center; }
        label { display: block; margin-top: 10px; }
        input, select, button { width: 100%; padding: 8px; margin-top: 5px; }
        button { background-color: #4CAF50; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>
<!-- Header -->
<jsp:include page="/components/header.jsp" />
<div class="form-container">
    <h2>Chỉnh sửa Thông Tin Vé</h2>

    <c:if test="${not empty orderDetail}">
        <form action="modify-booking-detail" method="post">
            <input type="hidden" name="orderDetailId" value="${orderDetail.orderDetailId}" />

            <label>Mã Vé (Ticket ID):</label>
            <input type="text" value="${orderDetail.ticket.ticketId}" readonly />

            <label>Ghế hiện tại:</label>
            <input type="text" value="${orderDetail.ticket.seat.seatNumber}" readonly />

            <label>Chuyến hiện tại (Trip ID):</label>
            <input type="text" value="${orderDetail.ticket.trip.tripId}" readonly />

            <label>Chọn Ghế mới (Nhập Seat ID):</label>
            <input type="number" name="seatId" required placeholder="Nhập ID ghế mới" />

            <label>Chọn Chuyến xe mới (Nhập Trip ID):</label>
            <input type="number" name="tripId" required placeholder="Nhập ID chuyến xe mới" />

            <button type="submit">Cập nhật Vé</button>
        </form>
    </c:if>

    <c:if test="${empty orderDetail}">
        <p style="color: red;">Không tìm thấy thông tin vé cần sửa!</p>
    </c:if>
</div>

</body>
</html>
