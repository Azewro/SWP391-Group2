<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


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

        <c:if test="${not empty tickets}">
            <div class="alert alert-success">
                <h4>Kết quả tra cứu (${fn:length(tickets)} vé):</h4>
            </div>

            <div class="table-responsive">
                <table class="table table-bordered table-striped align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>Mã vé</th>
                            <th>Họ tên</th>
                            <th>SĐT</th>
                            <th>Tuyến</th>
                            <th>Thời gian khởi hành</th>
                            <th>Ghế</th>
                            <th>Giá vé</th>
                            <th>Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ticket" items="${tickets}">
                            <tr>
                                <td>${ticket.ticketId}</td>
                                <td>${ticket.user.fullName}</td>
                                <td>${ticket.user.phone}</td>
                                <td>${ticket.trip.route.routeName}</td>
                                <td>${ticket.trip.departureTime}</td>
                                <td>${ticket.seat.seatNumber}</td>
                                <td>${ticket.price}</td>
                                <td>${ticket.status}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${empty tickets && param.phone != null}">
            <div class="alert alert-danger">
                Không tìm thấy vé khớp với thông tin đã nhập.
            </div>
        </c:if>
    </div>
</body>
</html>
