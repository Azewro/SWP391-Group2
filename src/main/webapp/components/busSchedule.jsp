<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDate" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String today = LocalDate.now().toString(); // yyyy-MM-dd
%>

<!DOCTYPE html>
<html>
<head>
    <title>Lịch trình xe buýt</title>
    <style>
        body { font-family: Arial; margin: 0; background: #fff; }
        table { width: 90%; margin: auto; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: center; }
        th { background-color: #f2f2f2; }
        .route-row { background: #ffefef; font-weight: bold; color: #e74c3c; }
        .search-button {
            background: #ff7e29; color: white; border: none;
            padding: 6px 14px; border-radius: 5px; cursor: pointer;
        }
        h1, h2 { text-align: center; margin-top: 30px; }
    </style>
</head>
<body>

<jsp:include page="header.jsp"/>

<h1>Lịch trình các tuyến xe</h1>

<table>
    <thead>
    <tr>
        <th>Tuyến xe</th>
        <th>Loại</th>
        <th>Quãng đường</th>
        <th>Thời gian dự kiến</th>
        <th>Giá vé</th>
        <th>Ngày đi</th>
        <th></th>
        <th>Trạm</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="route" items="${busSchedules}">
        <tr class="route-row">
            <td>${route.startLocation.name} ⇄ ${route.endLocation.name}</td>
            <td>${route.routeType != null ? route.routeType : "---"}</td>
            <td>${route.distance} km</td>
            <td>
                <c:set var="hours" value="${route.estimatedDuration / 60}" />
                <c:set var="minutes" value="${route.estimatedDuration % 60}" />
                <fmt:formatNumber var="hoursInt" value="${hours}" maxFractionDigits="0"/>
                ${hoursInt} giờ ${minutes} phút
            </td>
            <td>
                <c:choose>
                    <c:when test="${route.basePrice != null}">
                        ${route.basePrice} đ
                    </c:when>
                    <c:otherwise>---</c:otherwise>
                </c:choose>
            </td>
            <td>
                <form action="trip-list" method="get" style="display:flex;gap:5px;align-items:center;">
                    <input type="hidden" name="routeId" value="${route.routeId}" />
                    <input type="date" name="departure_date" value="<%= today %>" min="<%= today %>" required />
            </td>
            <td>
                    <button type="submit" class="search-button">Tìm chuyến xe</button>
                </form>
            </td>
            <td>
                <a href="bus-stop-locations?routeId=${route.routeId}">Xem trạm dừng</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${not empty busTrips}">
    <h2>Danh sách chuyến xe tương lai</h2>
    <table>
        <thead>
        <tr>
            <th>Mã chuyến</th>
            <th>Khởi hành</th>
            <th>Đến nơi</th>
            <th>Số ghế trống</th>
            <th>Giá vé</th>
            <th>Chọn ghế</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="trip" items="${busTrips}">
            <tr>
                <td>#${trip.tripId}</td>
                <td><fmt:formatDate value="${trip.departureTime}" pattern="dd/MM/yyyy HH:mm"/></td>
                <td><fmt:formatDate value="${trip.arrivalTime}" pattern="dd/MM/yyyy HH:mm"/></td>
                <td>${trip.availableSeats}</td>
                <td>${trip.currentPrice} đ</td>
                <td>
                    <a href="select-seats.jsp?tripId=${trip.tripId}" class="search-button">Chọn ghế</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

</body>
</html>
