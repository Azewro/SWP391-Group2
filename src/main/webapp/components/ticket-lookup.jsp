<!-- ✅ ticket-lookup.jsp -->
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tra cứu vé</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="/components/header.jsp" />
    <div class="container py-5">
        <h2 class="mb-4">🔍 Tra cứu thông tin vé xe</h2>

<form method="get" action="ticket-lookup" class="mb-4 row g-3">
    <div class="col-md-5">
        <label class="form-label">Số điện thoại</label>
        <input type="text" name="phone" class="form-control" value="${phone}" required>
    </div>
    <div class="col-md-5">
        <label class="form-label">Mã vé (tùy chọn)</label>
        <input type="text" name="ticketId" class="form-control" value="${ticketId}">
    </div>
    <div class="col-md-2 d-flex align-items-end">
        <button type="submit" class="btn btn-primary w-100">Tra cứu</button>
    </div>
</form>

<c:if test="${not empty ticket}">
    <div class="alert alert-success">
        <h4>Kết quả tra cứu:</h4>
        <p><strong>Họ tên:</strong> ${ticket.user.fullName}</p>
        <p><strong>Số điện thoại:</strong> ${ticket.user.phone}</p>
        <p><strong>Tuyến:</strong> ${ticket.trip.route.routeName}</p>
        <p><strong>Thời gian khởi hành:</strong> ${ticket.trip.departureTime}</p>
        <p><strong>Số ghế:</strong> ${ticket.seat.seatNumber}</p>
        <p><strong>Giá vé:</strong> ${ticket.price}</p>
        <p><strong>Trạng thái:</strong> ${ticket.status}</p>
    </div>
    <form method="get" action="print-ticket" target="_blank">
        <button type="submit" class="btn btn-outline-success">📄 In thẻ vé (PDF)</button>
    </form>
</c:if>

<c:if test="${empty ticket && param.phone != null}">
    <div class="alert alert-danger">Không tìm thấy vé khớp với thông tin đã nhập.</div>
</c:if>

    </div>
</body>
</html>
