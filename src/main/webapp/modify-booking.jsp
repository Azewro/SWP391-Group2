<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách Vé</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <style>
        body { background-color: #f8f9fa; padding: 20px; }
        .table thead { background-color: #343a40; color: #fff; }
        .table tbody tr:hover { background-color: #f1f1f1; }
        .btn-warning { color: #fff; }
        h2 { text-align: center; margin-bottom: 30px; color: #343a40; }
        .card { box-shadow: 0 4px 8px rgba(0,0,0,0.1); border-radius: 15px; }
        .card-body { padding: 30px; }
        .price { font-size: 1.1rem; font-weight: bold; color: #28a745; }
        .action-btn { margin-right: 8px; }
    </style>
</head>
<body>

<jsp:include page="/components/header.jsp" />

<div class="container">
    <div class="card">
        <div class="card-body">
            <h2>🚌 Danh sách vé của Đơn hàng #${orderId}</h2>

            <table class="table table-bordered table-hover align-middle">
                <thead class="table-dark text-center">
                <tr>
                    <th>Ghế</th>
                    <th>Chuyến xe</th>
                    <th>Giá vé</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody class="text-center">
                <c:forEach var="od" items="${orderDetails}">
                    <tr>
                        <td>${od.ticket.seat.seatId}</td>
                        <td>${od.ticket.trip.tripId}</td>
                        <td class="price">${od.ticket.price} VNĐ</td>
                        <td>
                            <a href="modify-booking-detail?orderDetailId=${od.orderDetailId}" class="btn btn-warning btn-sm action-btn">✏️ Chỉnh sửa</a>

                            <!-- Form POST để hủy vé an toàn -->
                            <form action="cancelBooking" method="post" style="display:inline-block;"
                            onsubmit="return confirm('Bạn có chắc chắn muốn hủy vé này không?');">
                                <input type="hidden" name="orderDetailId" value="${od.orderDetailId}">
                                <input type="hidden" name="orderId" value="${orderId}">
                                <button type="submit" class="btn btn-danger btn-sm"
                                        onclick="cancelTicket(${od.orderDetailId}, ${orderId}, this)">❌ Hủy vé</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="text-center mt-4">
                <a href="booking-history" class="btn btn-secondary">⬅️ Quay lại danh sách đơn hàng</a>
            </div>
        </div>
    </div>
</div>

<script>
    function cancelTicket(orderDetailId, orderId, btn) {
        if (!confirm("Bạn có chắc chắn muốn hủy vé này không?")) {
            return;
        }

        fetch('cancelBooking', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `orderDetailId=${orderDetailId}&orderId=${orderId}`
        })
            .then(response => {
                if (!response.ok) throw new Error('Lỗi khi hủy vé');
                return response.text();
            })
            .then(() => {
                // Xóa hàng của vé bị hủy khỏi bảng
                const row = btn.closest('tr');
                row.remove();
            })
            .catch(error => {
                alert("Không thể hủy vé, vui lòng thử lại.");
                console.error(error);
            });
    }
</script>

</body>
</html>
