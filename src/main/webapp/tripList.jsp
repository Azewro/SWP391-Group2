<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách chuyến xe</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f0f0f0;
        }
        .btn {
            padding: 6px 12px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<jsp:include page="/components/header.jsp" />
<h2>Danh sách chuyến xe</h2>

<table>
    <thead>
    <tr>
        <th>Tuyến</th>
        <th>Điểm đi</th>
        <th>Điểm đến</th>
        <th>Giờ khởi hành</th>
        <th>Giá vé</th>
        <th>Còn trống</th>
        <th>Chọn</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="trip" items="${tripList}">
        <tr>
            <td>${trip.route.routeName}</td>
            <td>${trip.route.startLocation.name}</td>
            <td>${trip.route.endLocation.name}</td>
            <td>${trip.departureTime}</td>
            <td>${trip.currentPrice} đ</td>
            <td>${trip.availableSeats}</td>
            <td>
                <a href="selectSeats.jsp?tripId=${trip.tripId}" class="btn">Chọn ghế</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
